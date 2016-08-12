package com.centaline.trans.eloan.web;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
import com.centaline.trans.common.entity.TgServItemAndProcessor;
import com.centaline.trans.common.entity.ToPropertyInfo;
import com.centaline.trans.common.enums.DepTypeEnum;
import com.centaline.trans.common.enums.TransPositionEnum;
import com.centaline.trans.common.service.OrgService;
import com.centaline.trans.common.service.TgGuestInfoService;
import com.centaline.trans.common.service.ToPropertyInfoService;
import com.centaline.trans.eloan.entity.ToEloanCase;
import com.centaline.trans.eloan.entity.ToEloanRel;
import com.centaline.trans.eloan.service.ToEloanCaseService;
import com.centaline.trans.eloan.service.ToEloanRelService;
import com.centaline.trans.eloan.vo.ToEloanRelListVO;
import com.centaline.trans.eloan.vo.ToEloanRelVO;
import com.centaline.trans.engine.annotation.TaskOperate;
import com.centaline.trans.loan.entity.LoanAgent;
import com.centaline.trans.loan.enums.LoanCompany;
import com.centaline.trans.loan.enums.LoanType;
import com.centaline.trans.loan.service.LoanAgentService;
import com.centaline.trans.mgr.Consts;
import com.centaline.trans.mgr.service.TsFinOrgService;

@Controller
@RequestMapping(value="/eloan")
public class WarnListController {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired(required = true)
	UamSessionService uamSessionService;
	
	@Autowired(required = true)
	UamUserOrgService uamUserOrgService;
	
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
	//E+列表
	@RequestMapping("Eloanlist")
	public String submit() {
		return "/eloan/task/taskEloanList";
	}
	@RequestMapping(value="/task/eloanApply/process")
	public String eloanApply(HttpServletRequest request, HttpServletResponse response,String businessKey,
			String taskitem, String processInstanceId){
		SessionUser user = uamSessionService.getSessionUser();
		Org parentOrg = uamUserOrgService.getParentOrgByDepHierarchy(user.getServiceDepId(), DepTypeEnum.TYCQY.getCode());
		request.setAttribute("orgId", parentOrg.getId());
		request.setAttribute("excutorId", user.getId());
		request.setAttribute("excutorName", user.getRealName());
		
		if(StringUtils.isNotBlank(processInstanceId)) {
			setAttribute(request,response,businessKey,taskitem,processInstanceId);
		}
    	return "eloan/task/taskEloanApply";
	}
	
	@RequestMapping("getEloanCaseDetails")
	public String getEloanDetail(Long pkid, Model model) {
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
			object.put("finOrgName",finOrgName );
			model.addAttribute("info", object);
			model.addAttribute("eloanRelList", eloanRels);
			model.addAttribute("eloanCase", eloanCase);
		}

		return "/eloan/task/taskEloanDetail";
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
			tEloanCase.setEloanCode(uamBasedataService.nextSeqVal("ZYDK_CODE",new Date()));
		}
	}
	
	@RequestMapping(value="/task/eloanApplyConfirm/process")
	public String eloanApplyConfirm(HttpServletRequest request, HttpServletResponse response, String businessKey,
			String taskitem, String processInstanceId){
		
		setAttribute(request,response,businessKey,taskitem,processInstanceId);
    	return "eloan/task/taskEloanApplyConfirm";
	}
	
	@RequestMapping(value="saveEloanApplyConfirm")
	@ResponseBody
	public AjaxResponse<String> saveEloanApplyConfirm(Model model,String taskId,String approved,String eloanCode){
		SessionUser user = uamSessionService.getSessionUser();
		try  {
			ToEloanCase toEloanCase = new ToEloanCase();
			toEloanCase.setApplyConfUser(user.getId());
			toEloanCase.setApplyConfTime(new Date());
			toEloanCase.setEloanCode(eloanCode);
			
			boolean isUpdate = false;
			Map<String,Object> map = new HashMap<String,Object>();
			if("1".equals(approved)) {
				map.put("ApplyApprove", true);
				isUpdate = true;
			} else {
				map.put("ApplyApprove", false);
			}
			toEloanCaseService.eloanProcessConfirm(taskId, map, toEloanCase,isUpdate);
			
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
	public AjaxResponse<String> saveEloanSignConfirm(Model model,String taskId,String approved,String eloanCode){
		SessionUser user = uamSessionService.getSessionUser();
		try  {
			ToEloanCase toEloanCase = new ToEloanCase();
			toEloanCase.setSignConfUser(user.getId());
			toEloanCase.setSignConfTime(new Date());
			toEloanCase.setEloanCode(eloanCode);
			
			boolean isUpdate = false;
			Map<String,Object> map = new HashMap<String,Object>();
			if("1".equals(approved)) {
				map.put("SignApprove", true);
				isUpdate = true;
			} else {
				map.put("SignApprove", false);
			}
			toEloanCaseService.eloanProcessConfirm(taskId, map, toEloanCase,isUpdate);
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
    	return "eloan/task/taskEloanRelease";
	}
	
	@RequestMapping(value="saveEloanRelease")
	@ResponseBody
	public AjaxResponse<String> saveEloanRelease(Model model,@RequestBody ToEloanRelListVO eloanRelListVO){
		try {
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("ReleaseAmount", eloanRelListVO.getEloanRelList().get(0).getReleaseAmount());
			if("1".equals(eloanRelListVO.getIsRelFinish())) {
				map.put("isRelFinish", true);
			} else {
				map.put("isRelFinish", false);
			}
			
			toEloanRelService.saveEloanRelease(eloanRelListVO.getTaskId(), eloanRelListVO.getEloanRelList(), map,eloanRelListVO.getIsRelFinish());
			return AjaxResponse.success("操作放款成功");
		} catch(Exception e) {
			logger.debug("保存E+放款失败", e);
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
	public AjaxResponse<String> saveEloanReleaseConfirm(Model model,String taskId,String approved,String eloanCode,String processInstanceId){
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
			
			toEloanRelService.saveEloanReleaseConfirm(taskId, approved, eloanCode, user, map,processInstanceId);
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
	
}
