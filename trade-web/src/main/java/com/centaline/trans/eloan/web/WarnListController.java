package com.centaline.trans.eloan.web;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aist.common.web.validate.AjaxResponse;
import com.aist.uam.auth.remote.UamSessionService;
import com.aist.uam.auth.remote.vo.SessionUser;
import com.aist.uam.basedata.remote.UamBasedataService;
import com.aist.uam.userorg.remote.UamUserOrgService;
import com.aist.uam.userorg.remote.vo.Org;
import com.aist.uam.userorg.remote.vo.User;
import com.centaline.trans.cases.entity.ToCase;
import com.centaline.trans.cases.entity.ToCaseInfo;
import com.centaline.trans.cases.service.MyCaseListService;
import com.centaline.trans.cases.service.ToCaseInfoService;
import com.centaline.trans.cases.service.ToCaseService;
import com.centaline.trans.common.entity.TgGuestInfo;
import com.centaline.trans.common.entity.ToPropertyInfo;
import com.centaline.trans.common.entity.ToWorkFlow;
import com.centaline.trans.common.enums.DepTypeEnum;
import com.centaline.trans.common.enums.TransJobs;
import com.centaline.trans.common.enums.TransPositionEnum;
import com.centaline.trans.common.enums.WorkFlowEnum;
import com.centaline.trans.common.service.OrgService;
import com.centaline.trans.common.service.TgGuestInfoService;
import com.centaline.trans.common.service.ToPropertyInfoService;
import com.centaline.trans.common.service.ToWorkFlowService;
import com.centaline.trans.eloan.entity.ToEloanCase;
import com.centaline.trans.eloan.entity.ToEloanRel;
import com.centaline.trans.eloan.service.ToEloanCaseService;
import com.centaline.trans.eloan.service.ToEloanRelService;
import com.centaline.trans.eloan.vo.ToEloanRelListVO;
import com.centaline.trans.engine.service.ProcessInstanceService;
import com.centaline.trans.loan.entity.LoanAgent;
import com.centaline.trans.loan.service.LoanAgentService;
import com.centaline.trans.mgr.Consts;
import com.centaline.trans.mgr.service.TsFinOrgService;
import com.centaline.trans.task.entity.ToApproveRecord;
import com.centaline.trans.task.entity.ToPropertyResearchVo;
import com.centaline.trans.task.service.ToApproveRecordService;
import com.centaline.trans.utils.DateUtil;

@Controller
@RequestMapping(value="/eloan")
public class WarnListController {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired(required = true)
	UamSessionService uamSessionService;
	
	@Autowired(required = true)
	UamUserOrgService uamUserOrgService;
	
	@Autowired
	private ToApproveRecordService toApproveRecordService;
	
	@Autowired
	LoanAgentService loanAgentService;
	@Autowired
	OrgService orgService;
	@Autowired
	TsFinOrgService finorgService;
	@Autowired
	ToEloanCaseService toEloanCaseService;
	@Autowired
	ToEloanRelService toEloanRelService;
	@Autowired
	private UamBasedataService uamBasedataService;
	@Autowired(required = true)
	ToPropertyInfoService toPropertyInfoService;
	@Autowired(required = true)
	ToCaseService toCaseService;
	@Autowired(required = true)
	ToCaseInfoService toCaseInfoService;
	@Autowired(required = true)
	TgGuestInfoService tgGuestInfoService;
	@Autowired
	private MyCaseListService myCaseListService;
	@Autowired
	ToWorkFlowService flowService;
	@Autowired
	ProcessInstanceService processInstanceService;
	
	//E+列表
	@RequestMapping("Eloanlist")
	public String submit(HttpServletRequest request) {
		SessionUser user = uamSessionService.getSessionUser();
		//记录产调申请人的组织
		ToPropertyResearchVo pro = toPropertyInfoService.getPropertyDepInfoByuserDepIdEloan(user.getServiceDepId());
		if(pro != null)
			request.setAttribute("orgId", pro.getPrApplyDepId());

		return "/eloan/task/taskEloanList";
	}
	
	//E+申请页面 ，填写信息保存
	@RequestMapping(value="/task/eloanApply/process")
	public String eloanApply(HttpServletRequest request, HttpServletResponse response,String businessKey,
			String taskitem, String processInstanceId){
		SessionUser user = uamSessionService.getSessionUser();
		Org parentOrg = uamUserOrgService.getParentOrgByDepHierarchy(user.getServiceDepId(), DepTypeEnum.TYCQY.getCode());
		
		Org yucui = uamUserOrgService.getOrgById(parentOrg.getParentId());

		request.setAttribute("orgId", parentOrg.getId());
		request.setAttribute("yucuiOrgId", yucui.getId());
		request.setAttribute("excutorId", user.getId());
		request.setAttribute("excutorName", user.getRealName());
		
		if(StringUtils.isNotBlank(processInstanceId)) {
			setAttribute(request,response,businessKey,taskitem,processInstanceId);
		}
		
		
		//E+ 申请查询审核结果
		ToApproveRecord toApproveRecordForItem=new ToApproveRecord();					
		toApproveRecordForItem.setProcessInstance(processInstanceId);
		toApproveRecordForItem.setPartCode("eApplyApprove");
		getApproveRecordForItem(toApproveRecordForItem,request);
		
    	return "eloan/task/taskEloanApply";
	}
	
	//E+ 改版新增页面
	@RequestMapping(value="/task/newEloanApply/process")
	public String newEloanApply(HttpServletRequest request, HttpServletResponse response,String businessKey,
			String taskitem, String processInstanceId){
		SessionUser user = uamSessionService.getSessionUser();
		Org parentOrg = uamUserOrgService.getParentOrgByDepHierarchy(user.getServiceDepId(), DepTypeEnum.TYCQY.getCode());
		request.setAttribute("orgId", parentOrg.getId());
		request.setAttribute("excutorId", user.getId());
		request.setAttribute("excutorName", user.getRealName());
		
		if(StringUtils.isNotBlank(processInstanceId)) {
			setAttribute(request,response,businessKey,taskitem,processInstanceId);
		}
    	return "eloan/task/taskNewEloanApply";
	}
	
	//得到人的员工编号
		@RequestMapping(value="/EmployeeCode")
		public User getEmployeeCode(HttpServletRequest request,String userId){
			User u=uamUserOrgService.getUserById(userId);
	        return u;
		}
		
	
	//获取E+详细信息
	@RequestMapping("getEloanCaseDetails")
	public String getEloanDetail(Long pkid, Model model, String action) {
		if (pkid != null) {
			
			ToEloanCase eloanCase= toEloanCaseService.getToEloanCaseByPkId(pkid);
			ToCase toCase= toCaseService.findToCaseByCaseCode(eloanCase.getCaseCode());
			//人物信息
			User jingban =uamUserOrgService.getUserById(toCase.getLeadingProcessId());//交易顾问
			User excutor =uamUserOrgService.getUserById(eloanCase.getExcutorId());//E+执行人			
			
			Map<String,Object> object = new HashMap<String,Object>();
			if(excutor!=null){
			object.put("excutorName", excutor.getRealName());
			object.put("excutorPhone", excutor.getMobile());
			}
			object.put("jingbanName", jingban.getRealName());
			object.put("jingbanPhone",jingban.getMobile());
			// 物业信息
			ToPropertyInfo toPropertyInfo = toPropertyInfoService.findToPropertyInfoByCaseCode(toCase.getCaseCode());
			object.put("propertyAddr", toPropertyInfo.getPropertyAddr());
			//放款信息
			BigDecimal releaseAmount=new BigDecimal(0);
			List<ToEloanRel> eloanRels= toEloanRelService.getEloanRelByEloanCode(eloanCase.getEloanCode());
			for (ToEloanRel toEloanRel : eloanRels) {
				if(toEloanRel.getConfirmStatus().equals("1")){
					releaseAmount=releaseAmount.add(toEloanRel.getReleaseAmount());
				
				}
			}
			object.put("releaseAmount",releaseAmount);
			//状态
			String status="";
			if(eloanCase.getApplyTime()!=null){
				status="apply";
			}
			if(eloanCase.getApplyConfTime()!=null){
				status="confirmApply";
			}
			if(eloanCase.getSignTime()!=null){
				status="sign";
			}
		    if(eloanCase.getSignConfTime()!=null){
				status="confirmSign";
			}
			if(eloanRels.size()>0){
				status="release";
			}
			object.put("status",status);
			//申请时间
			SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
			String applyTime=dateFormat.format(eloanCase.getApplyTime());
			object.put("applyTime",applyTime );
			//合作机构查询
			String finOrgName=finorgService.findBankByFinOrg(eloanCase.getFinOrgCode()).getFinOrgName();
			
			ToCaseInfo toCaseInfo = toCaseInfoService.findToCaseInfoByCaseCode(toCase.getCaseCode());
			User agentUser = null;
			// 经纪人
			if (!StringUtils.isBlank(toCaseInfo.getAgentCode())) {
				agentUser = uamUserOrgService.getUserById(toCaseInfo.getAgentCode());
			}
			
			// 上下家
			List<TgGuestInfo> guestList = tgGuestInfoService.findTgGuestInfoByCaseCode(toCase.getCaseCode());
			StringBuffer seller = new StringBuffer();
			StringBuffer sellerMobil = new StringBuffer();
			StringBuffer buyer = new StringBuffer();
			StringBuffer buyerMobil = new StringBuffer();
			for (TgGuestInfo guest : guestList) {
				if (guest.getTransPosition().equals(TransPositionEnum.TKHSJ.getCode())) {
					seller.append(guest.getGuestName());
					sellerMobil.append(guest.getGuestPhone());
					seller.append("/");
					sellerMobil.append("/");
				} else if (guest.getTransPosition().equals(TransPositionEnum.TKHXJ.getCode())) {
					buyer.append(guest.getGuestName());
					buyerMobil.append(guest.getGuestPhone());
					buyer.append("/");
					buyerMobil.append("/");
				}
			}

			if (guestList.size() > 0) {
				if (seller.length() > 1) {
					seller.deleteCharAt(seller.length() - 1);
					sellerMobil.deleteCharAt(sellerMobil.length() - 1);
				}

				if (buyer.length() > 1) {
					buyer.deleteCharAt(buyer.length() - 1);
					buyerMobil.deleteCharAt(buyerMobil.length() - 1);
				}
			}			
			object.put("agentName",agentUser==null?"":agentUser.getRealName());
			object.put("sellerName",seller.toString());
			object.put("buyerName",buyer.toString());	
			
			object.put("finOrgName",finOrgName );
			model.addAttribute("info", object);
			model.addAttribute("eloanRelList", eloanRels);
			model.addAttribute("eloanCase", eloanCase);
			model.addAttribute("pkId", pkid);
			
			SessionUser user = uamSessionService.getSessionUser();
			model.addAttribute("userName", user.getRealName());
		}
		
		if("update".equals(action)){
			return "/eloan/task/modifyEloanInfo";
		}else{
			return "/eloan/task/taskEloanDetail";
		}
		
	}
	
	
	//E+  产品部分信息修改
	@RequestMapping("modifyEloanCaseInfo")	
	@ResponseBody
	public AjaxResponse<String> modifyEloanCaseInfo(@RequestBody ToEloanRelListVO eloanRelListVO){
	/*public String modifyEloanCaseInfo(@RequestBody ToEloanRelListVO eloanRelListVO){*/
		
		ToEloanCase  toEloanCase  = new  ToEloanCase();
		try{
			if(null != eloanRelListVO){
				ToEloanCase  eloanCase = eloanRelListVO.getToEloanCase();
				if(null != eloanCase){		
					toEloanCase.setEloanCode(eloanCase.getEloanCode()==null?"":eloanCase.getEloanCode());
					toEloanCase.setCustName(eloanCase.getCustName()==null?"":eloanCase.getCustName());
					toEloanCase.setApplyAmount(eloanCase.getApplyAmount());
					toEloanCase.setCustPhone(eloanCase.getCustPhone()==null?"":eloanCase.getCustPhone());
					toEloanCase.setMonth(eloanCase.getMonth());
				}
				
				List<ToEloanRel> eloanRelList = eloanRelListVO.getEloanRelList();
				if (null != eloanRelList && eloanRelList.size()>0) {
					toEloanRelService.updateEloanRelByEloanCodeForModify(eloanRelList);
				}
				
			}
			toEloanCaseService.eloanInfoForUpdate(toEloanCase);	
			return AjaxResponse.success("操作成功");
					
		} catch(Exception e) {
			logger.debug("修改E+贷款信息失败", e);
			return AjaxResponse.fail("操作失败");
		}
		
		//return  "";
	}
	
	
	
	private Model getDetailByPkId(Long pkid, Model model) {
		if (pkid != null) {
			ToEloanCase eloanCase= toEloanCaseService.getToEloanCaseByPkId(pkid);
			ToCase toCase= toCaseService.findToCaseByCaseCode(eloanCase.getCaseCode());
			//人物信息
			User jingban =uamUserOrgService.getUserById(toCase.getLeadingProcessId());
			User excutor =uamUserOrgService.getUserById(eloanCase.getExcutorId());
			Map<String,Object> object = new HashMap<String,Object>();
			if(excutor!=null){
			object.put("excutorName", excutor.getRealName());
			object.put("excutorPhone", excutor.getMobile());
			}
			object.put("jingbanName", jingban.getRealName());
			object.put("jingbanPhone",jingban.getMobile());
			// 物业信息
			ToPropertyInfo toPropertyInfo = toPropertyInfoService.findToPropertyInfoByCaseCode(toCase.getCaseCode());
			object.put("propertyAddr", toPropertyInfo.getPropertyAddr());
			//放款信息
			BigDecimal releaseAmount=new BigDecimal(0);
			List<ToEloanRel> eloanRels= toEloanRelService.getEloanRelByEloanCode(eloanCase.getEloanCode());
			for (ToEloanRel toEloanRel : eloanRels) {
				if(toEloanRel.getConfirmStatus().equals("1")){//1审批通过
					releaseAmount=releaseAmount.add(toEloanRel.getReleaseAmount());
				
				}
			}
			object.put("releaseAmount",releaseAmount);
			//状态
			String status="";
			if(eloanCase.getApplyTime()!=null){
				status="apply";
			}
			if(eloanCase.getApplyConfTime()!=null){
				status="confirmApply";
			}
			if(eloanCase.getSignTime()!=null){
				status="sign";
			}
		    if(eloanCase.getSignConfTime()!=null){
				status="confirmSign";
			}
			if(eloanRels.size()>0){
				status="release";
			}
			object.put("status",status);
			//申请时间
			SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
			String applyTime=dateFormat.format(eloanCase.getApplyTime());
			object.put("applyTime",applyTime );
			//合作机构查询
			String finOrgName=finorgService.findBankByFinOrg(eloanCase.getFinOrgCode()).getFinOrgName();
			object.put("finOrgName",finOrgName );
			model.addAttribute("info", object);
			model.addAttribute("eloanRelList", eloanRels);
			model.addAttribute("eloanCase", eloanCase);
			model.addAttribute("pkid", pkid);
		}
		return model;
	}
	
	@RequestMapping(value="saveEloanApply")
	@ResponseBody
	public AjaxResponse<String> saveEloanApply(Model model,ToEloanCase tEloanCase){
		SessionUser user = uamSessionService.getSessionUser();
		try {
			if(StringUtils.isBlank(tEloanCase.getTaskId())) {
				buildFCaseCode(tEloanCase);
				toEloanCaseService.saveEloanApply(user, tEloanCase);
			} else {
				toEloanCaseService.updateEloanApply(user, tEloanCase);
			}
			return AjaxResponse.success("操作成功");
		} catch(Exception e) {
			logger.debug("保存E+申请失败", e);
			return AjaxResponse.fail("操作失败");
		}
	}
	
	@RequestMapping(value="validateEloanApply")
	@ResponseBody
	public AjaxResponse<Boolean> validateEloanApply(Model model,ToEloanCase tEloanCase){		
		AjaxResponse<Boolean> response = new AjaxResponse<Boolean>();
		SessionUser user = uamSessionService.getSessionUser();
		try {
			List<String> validate = toEloanCaseService.validateEloanApply(tEloanCase);
			if(CollectionUtils.isEmpty(validate)) {
				response.setContent(true);
				response.setMessage("");
			} else {
				response.setContent(false);
				response.setMessage("该案件的产品已经存在，不允许重复添加");
			}
			return response;
		} catch(Exception e) {
			logger.debug("保存E+申请失败", e);
			return AjaxResponse.fail("操作失败");
		}
	}
	
	@RequestMapping(value="validateIsFinishRelease")
	@ResponseBody
	public AjaxResponse<Boolean> validateIsFinishRelease(Model model,String eloanCode,Double sumAmount){
		try {
			AjaxResponse<Boolean>  response = toEloanCaseService.validateIsFinishRelease(eloanCode, sumAmount);
			return response;
		} catch(Exception e) {
			logger.debug("放款校验报错", e);
			return AjaxResponse.fail("操作失败");
		}
	}
	
	private void buildFCaseCode(ToEloanCase tEloanCase) {
		if (StringUtils.isNotBlank(tEloanCase.getCaseCode())) {
			String dateStr = DateUtil.getFormatDate(new Date(), "yyyyMMdd");
			String month = dateStr.substring(0, 6);
			tEloanCase.setEloanCode(uamBasedataService.nextSeqVal("ELOAN_CODE",month));
		}
	}
	
	@RequestMapping(value="/task/eloanApplyConfirm/process")
	public String eloanApplyConfirm(HttpServletRequest request, HttpServletResponse response, String businessKey,
			String taskitem, String processInstanceId){
		
		setAttribute(request,response,businessKey,taskitem,processInstanceId);
    	return "eloan/task/taskEloanApplyConfirm";
	}
	
	//主管审批    保存E+申请记录
	@RequestMapping(value="saveEloanApplyConfirm")
	@ResponseBody
/*	Model model,String taskId,String approved,String eloanCode,String processInstanceId,
	String caseCode,String eContent,String custName,String custPhone,String month,String applyAmount*/
	public AjaxResponse<String> saveEloanApplyConfirm(ToEloanCase eloanCase,String eContent,String approved,String taskId){

		SessionUser user = uamSessionService.getSessionUser();
		try  {
			ToEloanCase toEloanCase = new ToEloanCase();
			toEloanCase.setApplyConfUser(user.getId());
			toEloanCase.setApplyConfTime(new Date());
			if(null != eloanCase){
				toEloanCase.setEloanCode(eloanCase.getEloanCode()==null?"":eloanCase.getEloanCode());
/*				toEloanCase.setCustName(eloanCase.getCustName()==null?"":eloanCase.getCustName());
				toEloanCase.setApplyAmount(eloanCase.getApplyAmount());
				toEloanCase.setCustPhone(eloanCase.getCustPhone()==null?"":eloanCase.getCustPhone());
				toEloanCase.setMonth(eloanCase.getMonth());*/
			}
			
			boolean isUpdate = false;
			Map<String,Object> map = new HashMap<String,Object>();
			if("1".equals(approved)) {
				map.put("ApplyApprove", true);
				isUpdate = true;
			} else {
				map.put("ApplyApprove", false);				
			}
			toEloanCaseService.eloanProcessConfirm(taskId, map, toEloanCase,isUpdate);
			
			//E+借贷审核添加 审核说明，条件审核记录到ToApproveRecord
			ToApproveRecord toApproveRecord=new ToApproveRecord();			
			toApproveRecord.setCaseCode(eloanCase.getCaseCode());
			toApproveRecord.setContent(eContent);
			toApproveRecord.setApproveType("9");
			toApproveRecord.setOperator(user.getId());
			toApproveRecord.setTaskId(taskId);
			toApproveRecord.setOperatorTime(new Date());
			toApproveRecord.setPartCode("eApplyApprove");//e+借贷
			toApproveRecord.setProcessInstance(eloanCase.getProcessInstanceId());
			toApproveRecordService.insertToApproveRecord(toApproveRecord);
			
			return AjaxResponse.success("操作成功");
		} catch(Exception e) {
			logger.debug("保存E+申请确认失败", e);
			return AjaxResponse.fail("操作失败");
		}
	}
	
	@RequestMapping(value="/task/eloanSign/process")
	public String eloanSign(HttpServletRequest request, HttpServletResponse response, String businessKey,
			String taskitem, String processInstanceId){	
		setAttribute(request,response,businessKey,taskitem,processInstanceId);
		
		
		//E+ 申请查询审核结果
		ToApproveRecord toApproveRecordForItem=new ToApproveRecord();					
		toApproveRecordForItem.setProcessInstance(processInstanceId);
		toApproveRecordForItem.setPartCode("eSignApprove");
		getApproveRecordForItem(toApproveRecordForItem,request);
		
    	return "eloan/task/taskEloanSign";
	}
	
	private void setAttribute(HttpServletRequest request, HttpServletResponse response, String businessKey,
			String taskitem, String processInstanceId) {		
		ToEloanCase property = new ToEloanCase();
		property.setEloanCode(businessKey);
		List<ToEloanCase> eloanCaseList = toEloanCaseService.getToEloanCaseListByProperty(property);
		
		ToCase toCase = toCaseService.findToCaseByCaseCode(eloanCaseList.get(0).getCaseCode());
		ToCaseInfo toCaseInfo = toCaseInfoService.findToCaseInfoByCaseCode(toCase.getCaseCode());
		// 物业信息
		ToPropertyInfo toPropertyInfo = toPropertyInfoService.findToPropertyInfoByCaseCode(toCase.getCaseCode());
		User agentUser = null;
		// 经纪人
		if (!StringUtils.isBlank(toCaseInfo.getAgentCode())) {
			agentUser = uamUserOrgService.getUserById(toCaseInfo.getAgentCode());
		}
		// 交易顾问
		User consultUser = uamUserOrgService.getUserById(toCase.getLeadingProcessId());
		//归属人
		User excutor = uamUserOrgService.getUserById(eloanCaseList.get(0).getExcutorId());
		// 上下家
		List<TgGuestInfo> guestList = tgGuestInfoService.findTgGuestInfoByCaseCode(toCase.getCaseCode());
		StringBuffer seller = new StringBuffer();
		StringBuffer sellerMobil = new StringBuffer();
		StringBuffer buyer = new StringBuffer();
		StringBuffer buyerMobil = new StringBuffer();
		for (TgGuestInfo guest : guestList) {
			if (guest.getTransPosition().equals(TransPositionEnum.TKHSJ.getCode())) {
				seller.append(guest.getGuestName());
				sellerMobil.append(guest.getGuestPhone());
				seller.append("/");
				sellerMobil.append("/");
			} else if (guest.getTransPosition().equals(TransPositionEnum.TKHXJ.getCode())) {
				buyer.append(guest.getGuestName());
				buyerMobil.append(guest.getGuestPhone());
				buyer.append("/");
				buyerMobil.append("/");
			}
		}

		if (guestList.size() > 0) {
			if (seller.length() > 1) {
				seller.deleteCharAt(seller.length() - 1);
				sellerMobil.deleteCharAt(sellerMobil.length() - 1);
			}

			if (buyer.length() > 1) {
				buyer.deleteCharAt(buyer.length() - 1);
				buyerMobil.deleteCharAt(buyerMobil.length() - 1);
			}
		}
		request.setAttribute("caseCode", toCase.getCaseCode());
		request.setAttribute("propertyAddr", toPropertyInfo.getPropertyAddr());
		request.setAttribute("processorName", consultUser==null?"":consultUser.getRealName());
		request.setAttribute("agentName", agentUser==null?"":agentUser.getRealName());
		request.setAttribute("sellerName", seller.toString());
		request.setAttribute("buyerName", buyer.toString());
		request.setAttribute("excutorName", excutor.getRealName());
		request.setAttribute("eloanCase", (CollectionUtils.isEmpty(eloanCaseList)==true)?null:eloanCaseList.get(0));
	}
	
	@RequestMapping(value="saveEloanSign")
	@ResponseBody
	public AjaxResponse<String> saveEloanSign(Model model,String taskId ,ToEloanCase tEloanCase){
		try {
			toEloanCaseService.saveEloanSign(taskId,tEloanCase);
			return AjaxResponse.success("操作成功");
		} catch(Exception e) {
			logger.debug("保存E+签约失败", e);
			return AjaxResponse.fail("操作失败");
		}
	}
	
	@RequestMapping(value="/task/eloanSignConfirm/process")
	public String eloanSignConfirm(HttpServletRequest request, HttpServletResponse response, String businessKey,
			String taskitem, String processInstanceId){
		setAttribute(request,response,businessKey,taskitem,processInstanceId);
    	return "eloan/task/taskEloanSignConfirm";
	}
	
	@RequestMapping(value="saveEloanSignConfirm")
	@ResponseBody
	/*public AjaxResponse<String> saveEloanSignConfirm(Model model,String taskId,String approved,String eloanCode){*/
	public AjaxResponse<String> saveEloanSignConfirm(ToEloanCase eloanCase,String taskId,String approved,String eSignContent){
		
		SessionUser user = uamSessionService.getSessionUser();
		try  {
			ToEloanCase toEloanCase = new ToEloanCase();
			toEloanCase.setSignConfUser(user.getId());
			toEloanCase.setSignConfTime(new Date());
			//toEloanCase.setEloanCode(eloanCode);
			if(null != eloanCase){
				toEloanCase.setEloanCode(eloanCase.getEloanCode()==null?"":eloanCase.getEloanCode());
	/*			toEloanCase.setCustName(eloanCase.getCustName()==null?"":eloanCase.getCustName());
				toEloanCase.setApplyAmount(eloanCase.getApplyAmount());
				toEloanCase.setCustPhone(eloanCase.getCustPhone()==null?"":eloanCase.getCustPhone());
				toEloanCase.setMonth(eloanCase.getMonth());
				toEloanCase.setSignAmount(eloanCase.getSignAmount());*/
			}
			
			boolean isUpdate = false;
			Map<String,Object> map = new HashMap<String,Object>();
			if("1".equals(approved)) {
				map.put("SignApprove", true);
				isUpdate = true;
			} else {
				map.put("SignApprove", false);				
			}
			toEloanCaseService.eloanProcessConfirm(taskId, map, toEloanCase,isUpdate);
			
			//E+借贷审核添加 审核说明，条件审核记录到ToApproveRecord
			ToApproveRecord toApproveRecord=new ToApproveRecord();			
			toApproveRecord.setCaseCode(eloanCase.getCaseCode());
			toApproveRecord.setContent(eSignContent);
			toApproveRecord.setApproveType("10");
			toApproveRecord.setOperator(user.getId());
			toApproveRecord.setTaskId(taskId);
			toApproveRecord.setOperatorTime(new Date());
			toApproveRecord.setPartCode("eSignApprove");//e+借贷
			toApproveRecord.setProcessInstance(eloanCase.getProcessInstanceId());
			toApproveRecordService.insertToApproveRecord(toApproveRecord);
			
			return AjaxResponse.success("操作成功");
		} catch(Exception e) {
			logger.debug("保存E+签约确认失败", e);
			return AjaxResponse.fail("操作失败");
		}
	}
	
	@RequestMapping(value="/task/eloanRelease/process")
	public String eloanRelease(HttpServletRequest request, HttpServletResponse response, String businessKey, String source,
			String taskitem, String processInstanceId){
		setAttribute(request,response,businessKey,taskitem,processInstanceId);
		// 显示所有放款金额
		List<ToEloanRel> eloanRelList = toEloanRelService.getEloanRelByEloanCode(businessKey);
		request.setAttribute("eloanRelList", eloanRelList);
		
		//E+ 申请查询审核结果
		ToApproveRecord toApproveRecordForItem=new ToApproveRecord();					
		toApproveRecordForItem.setProcessInstance(processInstanceId);
		toApproveRecordForItem.setPartCode("eLoanApprove");
		getApproveRecordForItem(toApproveRecordForItem,request);
		
    	return "eloan/task/taskEloanRelease";
	}
	
	@RequestMapping(value="saveEloanRelease")
	@ResponseBody
	public AjaxResponse<String> saveEloanRelease(Model model,@RequestBody ToEloanRelListVO eloanRelListVO){
		try {
			Map<String,Object> map = new HashMap<String,Object>();
			if(null != eloanRelListVO.getEloanRelList() && eloanRelListVO.getEloanRelList().size()>0){
				map.put("ReleaseAmount", eloanRelListVO.getEloanRelList().get(0).getReleaseAmount());
			}else{
				map.put("ReleaseAmount", "");
			}
			
			if("1".equals(eloanRelListVO.getIsRelFinish())) {
				map.put("isRelFinish", true);
			} else {
				map.put("isRelFinish", false);
			}
			
			/*toEloanCaseService.eloanProcessConfirm(eloanRelListVO.getTaskId(), map, eloanRelListVO.getToEloanCase(),true);*/
			toEloanRelService.saveEloanRelease(eloanRelListVO.getTaskId(), eloanRelListVO.getEloanRelList(), map,eloanRelListVO.getIsRelFinish());
			return AjaxResponse.success("操作放款成功");
		} catch(Exception e) {
			logger.debug("保存E+放款信息失败", e);
			return AjaxResponse.fail("操作放款失败");
		}
	}
	
	@RequestMapping(value="/task/eloanReleaseConfirm/process")
	public String eloanReleaseConfirm(HttpServletRequest request, HttpServletResponse response, String businessKey, 
			String taskitem, String processInstanceId){
		setAttribute(request,response,businessKey,taskitem,processInstanceId);
		// 显示所有放款金额
		List<ToEloanRel> eloanRelList = toEloanRelService.getEloanRelByEloanCode(businessKey);
		request.setAttribute("eloanRelList", eloanRelList);
		
    	return "eloan/task/taskEloanReleaseConfirm";
	}
	
	@RequestMapping(value="saveEloanReleaseConfirm")
	@ResponseBody
	
	/*public AjaxResponse<String> saveEloanReleaseConfirm(Model model,String taskId,String approved,String eloanCode,String processInstanceId){*/
	public AjaxResponse<String> saveEloanReleaseConfirm(ToEloanCase eloanCase,String taskId,String approved,String eLoanContent){
		SessionUser user = uamSessionService.getSessionUser();
		try  {
			boolean isUpdate = false;
			Map<String,Object> map = new HashMap<String,Object>();
			if("1".equals(approved)) {
				map.put("ReleaseApprove", true);
				isUpdate = true;
			} else {
				map.put("ReleaseApprove", false);
			}
			
			toEloanRelService.saveEloanReleaseConfirm(taskId, approved, eloanCase.getEloanCode(), user, map,eloanCase.getProcessInstanceId());
			
			//E+借贷审核添加 审核说明，条件审核记录到ToApproveRecord
			ToApproveRecord toApproveRecord=new ToApproveRecord();			
			toApproveRecord.setCaseCode(eloanCase.getCaseCode());
			toApproveRecord.setContent(eLoanContent);
			toApproveRecord.setApproveType("11");
			toApproveRecord.setOperator(user.getId());
			toApproveRecord.setTaskId(taskId);
			toApproveRecord.setOperatorTime(new Date());
			toApproveRecord.setPartCode("eLoanApprove");//e+借贷
			toApproveRecord.setProcessInstance(eloanCase.getProcessInstanceId());
			toApproveRecordService.insertToApproveRecord(toApproveRecord);
			
			return AjaxResponse.success("操作成功");
		} catch(Exception e) {
			logger.debug("保存E+放款确认失败", e);
			return AjaxResponse.fail("操作失败");
		}
	}

	/**
	 * 警示列表
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value="warnList")
	public String warnList(ServletRequest request){
		SessionUser user = uamSessionService.getSessionUser();
		String userOrgId = user.getServiceDepId();
		request.setAttribute("queryOrg", userOrgId);
		
		String adminOrg = "";
		if(StringUtils.isNotBlank(user.getAdminOrg())) {
			adminOrg = user.getAdminOrg().trim();
		}
		request.setAttribute("adminOrg", adminOrg);

		List<Org> districtOrgList = uamUserOrgService.getOrgByDepHierarchy(Consts.YU_SH_ORG_ROOT, Consts.YU_DISTRICT);
		List<Org> showOrgList = new ArrayList<Org>();
		if(StringUtils.isNotBlank(adminOrg)) {
			for(Org org : districtOrgList) {
			     String[] adminOrgs = adminOrg.split(",");
				 for(int i = 0 ;i< adminOrgs.length;i++) {
					 String adminO = adminOrgs[i];
					 if(org.getId().equals(adminO)) {
						 showOrgList.add(org);
					 }
				 }
			}
		}
		request.setAttribute("districtOrgList", showOrgList);
		
		return "eloan/warnList";
	}
	
	@RequestMapping(value="updateWarnListTime")
	@ResponseBody
	public AjaxResponse<LoanAgent> batchUpdateExportTime(Model model, ServletRequest request,String idStr){
		AjaxResponse<LoanAgent> result = new AjaxResponse<>();
		try {
			String[] pkidArr = idStr.split(",");
			for(String pkId : pkidArr) {
				LoanAgent loanAgent = new LoanAgent();
				loanAgent.setPkid(Long.parseLong(pkId));
				loanAgent.setLastExceedExportTime(new Date());
			    loanAgentService.updateLoanAgent(loanAgent);
			}
			result.setSuccess(true);
			result.setMessage("处理成功!");
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage("处理失败!");
		}
		return result;
	}
	
	/*根据caseCoded查询客户详情*/
	@RequestMapping("getCaseDetails")
	@ResponseBody
	public List<TgGuestInfo> getCaseDetailsBypkid(String caseCode){
		List<TgGuestInfo> caseInfos = myCaseListService.findTgGuestInfoByCaseCode(caseCode);
		return caseInfos;
	}
	
	/*根据pkId删除案件*/
	@RequestMapping("deteleItem")
	@ResponseBody
	public AjaxResponse<ToEloanCase> deteleItem(Long pkid, ServletRequest request){
		AjaxResponse<ToEloanCase> result = new AjaxResponse<>();
		try {
			ToEloanCase eloanCase=toEloanCaseService.getToEloanCaseByPkId(pkid);
			if(eloanCase!=null){
				ToWorkFlow record=new ToWorkFlow();
				record.setBusinessKey(WorkFlowEnum.ELOAN_BUSSKEY.getCode());
				record.setCaseCode(eloanCase.getCaseCode());
			    ToWorkFlow workFlow= flowService.queryActiveToWorkFlowByCaseCodeBusKey(record);
				if(workFlow!=null){
					workFlow.setStatus("4");
			    flowService.updateByPrimaryKey(workFlow);
				toEloanCaseService.deleteById(eloanCase.getPkid());
				 processInstanceService.deleteProcess(workFlow.getInstCode());
				}
			
			}
			result.setSuccess(true);
			result.setMessage("删除成功!");
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage("删除失败!");
		}
		return result;
	}
	
	
	//E+列表
	@RequestMapping("eloanContractList")
	public String eloanContractList(HttpServletRequest request) {
		
		SessionUser user = uamSessionService.getSessionUser();
		String userJob=user.getServiceJobCode();
		boolean queryOrgFlag = false;
		boolean isAdminFlag = false;

        StringBuffer reBuffer = new StringBuffer();
        //判断是否是交易顾问
		if(!userJob.equals(TransJobs.TJYGW.getCode())){
			queryOrgFlag=true;//非交易顾问说明有组织
			String depString = user.getServiceDepHierarchy();
			String userOrgIdString = user.getServiceDepId();
			if(depString.equals(DepTypeEnum.TYCTEAM.getCode())){
				//组别只有对应的组织id
				reBuffer.append(userOrgIdString);
			}else if(depString.equals(DepTypeEnum.TYCQY.getCode())){
				//区域下面对应的多个组织id遍历
				List<Org> orgList = uamUserOrgService.getOrgByDepHierarchy(userOrgIdString, DepTypeEnum.TYCTEAM.getCode());
				for(Org org:orgList){
					reBuffer.append(org.getId());
					reBuffer.append(",");
				}
				reBuffer.deleteCharAt(reBuffer.length()-1);
				
			}else{
				isAdminFlag=true;
			}
		}
		request.setAttribute("queryOrgs", reBuffer.toString());//org_id至jsp、js分割-->数组
		request.setAttribute("queryOrgFlag", queryOrgFlag);//判断是否是交易顾问 即判断是否有上下级组织
		request.setAttribute("isAdminFlag", isAdminFlag);		
		request.setAttribute("serviceDepId", user.getServiceDepId());//登录用户的org_id
		request.setAttribute("serviceDepName", user.getServiceDepName());
		
		//默认显示上周一至周日的时间
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();	
		/*获取当前月份的上月第一天和最后一天
		c1.add(Calendar.MONTH, -1);
		c1.set(Calendar.DAY_OF_MONTH,1);
		c2.set(Calendar.DAY_OF_MONTH,0);*/
		/*获取当前月份的第一天和最后一天*/
		c1.add(Calendar.MONTH, 0);
		c1.set(Calendar.DAY_OF_MONTH,1);//
		c2.set(Calendar.DAY_OF_MONTH, c2.getActualMaximum(Calendar.DAY_OF_MONTH));  
		String start = new SimpleDateFormat("yyyy-MM-dd").format(c1.getTime());//last Monday
		String end 	 = new SimpleDateFormat("yyyy-MM-dd").format(c2.getTime());//last Sunday
		request.setAttribute("startTime", start);
		request.setAttribute("endTime", end);
		
		
		return "/eloan/task/taskEloanContract";
	}
	
	/**
	 * 产调来源报表
	 * @param model
	 * @return
	 */
	
	@RequestMapping(value="eloanRiskCtlList")
	@RequiresPermissions("TRADE.ELOAN.RCLIST")
    public String propertySourceReport(Model model) {
		SessionUser user = uamSessionService.getSessionUser();
		model.addAttribute("userId", user.getId());
		
		return "eloan/eloanRiskCtlList";
	}
	
	
	private boolean getApproveRecordForItem(ToApproveRecord toApproveRecord,HttpServletRequest request) {
		boolean flag = false;
		//E+ 申请查询审核结果		
		ToApproveRecord toApproveRecord2=toApproveRecordService.queryToApproveRecordForSpvApply(toApproveRecord);	
		if(toApproveRecord != null){
			flag=true;
			request.setAttribute("toApproveRecord", toApproveRecord2);
		}		
		
		return flag;
	}
	
}
