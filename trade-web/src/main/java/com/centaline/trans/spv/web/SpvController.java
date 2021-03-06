/**
 * AISThink.com Inc.
 * Copyright (c) 2015-2015 All Rights Reserved.
 */
package com.centaline.trans.spv.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aist.common.exception.BusinessException;
import com.aist.common.web.validate.AjaxResponse;
import com.aist.uam.auth.remote.UamSessionService;
import com.aist.uam.auth.remote.vo.SessionUser;
import com.aist.uam.permission.remote.UamPermissionService;
import com.aist.uam.permission.remote.vo.App;
import com.aist.uam.userorg.remote.UamUserOrgService;
import com.aist.uam.userorg.remote.vo.Org;
import com.aist.uam.userorg.remote.vo.User;
import com.alibaba.fastjson.JSONObject;
import com.centaline.trans.attachment.entity.ToAccesoryList;
import com.centaline.trans.attachment.service.ToAccesoryListService;
import com.centaline.trans.cases.entity.ToCase;
import com.centaline.trans.cases.service.ToCaseService;
import com.centaline.trans.cases.vo.CaseBaseVO;
import com.centaline.trans.common.enums.AppTypeEnum;
import com.centaline.trans.common.enums.WorkFlowEnum;
import com.centaline.trans.common.service.MessageService;
import com.centaline.trans.common.vo.FileUploadVO;
import com.centaline.trans.engine.entity.ToWorkFlow;
import com.centaline.trans.engine.service.ProcessInstanceService;
import com.centaline.trans.engine.service.ToWorkFlowService;
import com.centaline.trans.engine.service.WorkFlowManager;
import com.centaline.trans.engine.vo.StartProcessInstanceVo;
import com.centaline.trans.mgr.Consts;
import com.centaline.trans.product.entity.Product;
import com.centaline.trans.product.entity.ProductCategory;
import com.centaline.trans.product.service.ProductCategoryService;
import com.centaline.trans.product.service.ProductService;
import com.centaline.trans.spv.entity.ToCashFlow;
import com.centaline.trans.spv.entity.ToSpv;
import com.centaline.trans.spv.entity.ToSpvAccount;
import com.centaline.trans.spv.entity.ToSpvDeCond;
import com.centaline.trans.spv.entity.ToSpvDeRec;
import com.centaline.trans.spv.service.CashFlowInService;
import com.centaline.trans.spv.service.CashFlowOutService;
import com.centaline.trans.spv.service.SpvCloseApplyService;
import com.centaline.trans.spv.service.ToSpvService;
import com.centaline.trans.spv.vo.SpvBaseInfoVO;
import com.centaline.trans.spv.vo.SpvChargeInfoVO;
import com.centaline.trans.spv.vo.SpvCloseInfoVO;
import com.centaline.trans.spv.vo.SpvDeRecVo;
import com.centaline.trans.spv.vo.SpvVo;
import com.centaline.trans.task.entity.ToApproveRecord;
import com.centaline.trans.task.service.ToApproveRecordService;
import com.centaline.trans.task.vo.ProcessInstanceVO;


@Controller
@RequestMapping(value="/spv")
public class SpvController {
	
	@Autowired
	private ToSpvService toSpvService;	
	@Autowired
	private ToApproveRecordService toApproveRecordService;	
	@Autowired
	private UamSessionService uamSessionService;
	@Autowired
	private UamUserOrgService uamUserOrgService;
	@Autowired
	private ToCaseService toCaseService;	 
	@Autowired
	private ToAccesoryListService toAccesoryListService;
	@Autowired
	private WorkFlowManager workFlowManager;
	@Autowired
	private CashFlowOutService cashFlowOutService;
	@Autowired
	private SpvCloseApplyService spvCloseApplyService;
	
	@Autowired
	MessageService messageService;
	@Autowired
	ProductCategoryService productCategoryService;
	@Autowired
	ProductService productService;
	@Autowired
	ToWorkFlowService flowService;
	@Autowired
	ProcessInstanceService processInstanceService;
	@Autowired
	private UamPermissionService uamPermissionService;
	@Autowired
	private CashFlowInService cashFlowInService;

	
	//列表页面
	@RequestMapping("spvList")
	public String spvList(HttpServletRequest request){
		SessionUser currentUser = uamSessionService.getSessionUser();
		String currentDeptId = currentUser.getServiceDepId();
		Org curentOrg = uamUserOrgService.getOrgById(currentDeptId);
		Org parentOrg = uamUserOrgService.getOrgById(curentOrg.getParentId());
	
		request.setAttribute("rcOrgId", curentOrg.getId());
		request.setAttribute("orgId", parentOrg.getId());
		return "spv/SpvList";
	}
	
	//流水列表页面
	@RequestMapping("spvFlowApplyList")
	public String spvFlowApplyList(HttpServletRequest request){
/*		SessionUser currentUser = uamSessionService.getSessionUser();
		String currentDeptId = currentUser.getServiceDepId();
		Org curentOrg = uamUserOrgService.getOrgById(currentDeptId);
		Org parentOrg = uamUserOrgService.getOrgById(curentOrg.getParentId());
	
		request.setAttribute("orgId", parentOrg.getId());*/
		return "spv/SpvFlowApplyList";
	}
	
	@RequestMapping("spvFlowList")
	public String spvFlowList(HttpServletRequest request){
/*		SessionUser currentUser = uamSessionService.getSessionUser();
		String currentDeptId = currentUser.getServiceDepId();
		Org curentOrg = uamUserOrgService.getOrgById(currentDeptId);
		Org parentOrg = uamUserOrgService.getOrgById(curentOrg.getParentId());
	
		request.setAttribute("orgId", parentOrg.getId());*/
		return "spv/SpvFlowList";
	}
	
	//新增页面
	@RequestMapping("saveHTML")
	public String saveHTML(Long pkid,HttpServletRequest request){
		
		Org riskControllerOrg = uamUserOrgService.getOrgByCode("112A011");

		toSpvService.findSpvBaseInfoVOAndSetAttr(request,pkid);
		
		toAccesoryListService.getAccesoryList(request, "SpvApplyApprove");
		
		request.setAttribute("orgId", riskControllerOrg.getId());
		request.setAttribute("urlType", "spv");
		return "spv/saveSpvCase";
	}

	/**
	 * 详情
	 * @param pkid
	 * @param model
	 * @return
	 */
	@RequestMapping("spvDetail")
	public String SpvDetail(long pkid ,HttpServletRequest request){
/*		SpvBaseInfoVO baseInfoVO=new SpvBaseInfoVO();
		//合约基本信息

		baseInfoVO.setToSpv(spv);
		//买卖双方信息
		List<ToSpvCust> custs=toSpvService.findCustBySpvCode(spv.getSpvCode());
		baseInfoVO.setSpvCustList(custs);
		//房屋信息
		ToSpvProperty spvProperty=toSpvService.findPropertyBySpvCode(spv.getSpvCode());
		baseInfoVO.setToSpvProperty(spvProperty);
		//资金账户
		List<ToSpvAccount> accounts=toSpvService.findAccountBySpvCode(spv.getSpvCode());
		baseInfoVO.setToSpvAccountList(accounts);
		*/
		ToSpv spv= toSpvService.selectByPrimaryKey(pkid);
		SpvBaseInfoVO spvBaseInfoVO = toSpvService.findSpvBaseInfoVOByPkid(pkid);
		User officer=uamUserOrgService.getUserById(spvBaseInfoVO.getToSpv().getRiskControlOfficer());
		String name=officer.getRealName();
		String phone=officer.getMobile();
		//spvBaseInfoVO.getToSpv().setCreateBy(name);
		//经办人
		ToCase toCase= toCaseService.findToCaseByCaseCode(spv.getCaseCode());
		//申请人
		User applyUser =uamUserOrgService.getUserById(spv.getApplyUser());
		//人物信息
		User jingban = null; 
		if(StringUtils.isNotBlank(toCase.getLeadingProcessId())){
			jingban =uamUserOrgService.getUserById(toCase.getLeadingProcessId());
		}
		//风控总监
		List<User> zj =uamUserOrgService.getUserByOrgIdAndJobCode(officer.getOrgId(), "JYFKZJ");
		User FKZJ=new User();
		if(zj.size()>0){
			FKZJ=zj.get(0);
		}
		//驳回原因
    if(spv.getStatus()!="0"&&spv.getApplyTime()!=null){
		ToWorkFlow record=new ToWorkFlow();
		record.setBizCode(spv.getSpvCode());
		record.setBusinessKey(WorkFlowEnum.SPV_DEFKEY.getCode());
	    ToWorkFlow workFlow= flowService.queryToWorkFlowByBizCodeBusKey(record);
		 
		//查询审核结果
	    if(workFlow !=null){
		ToApproveRecord toApproveRecordForItem=new ToApproveRecord();
		if(spvBaseInfoVO.getToSpv() != null && spvBaseInfoVO.getToSpv().getCaseCode() != null){
			toApproveRecordForItem.setCaseCode(spvBaseInfoVO.getToSpv().getCaseCode());
		}		
		toApproveRecordForItem.setProcessInstance(workFlow.getInstCode());
		toApproveRecordForItem.setPartCode("SpvApplyApprove");		
		ToApproveRecord toApproveRecord=toApproveRecordService.queryToApproveRecordForSpvApply(toApproveRecordForItem);		
		request.setAttribute("toApproveRecord", toApproveRecord);
      }
     }
        cashFlowOutService.getCashFlowList(request,spv.getSpvCode());
        request.setAttribute("spvBaseInfoVO", spvBaseInfoVO);
		request.setAttribute("createPhone", phone);
		request.setAttribute("officer", officer);
		request.setAttribute("jingban", jingban);
	    request.setAttribute("zj",FKZJ);
	    request.setAttribute("applyUser",applyUser);
		return "spv/SpvDetail";
	}
   
	/**
	 * 删除
	 * @param pkid
	 * @param model
	 * @return
	 */
	@RequestMapping("deleteSpv")
	public AjaxResponse<String>  deleteSpv(long pkid ,ServletRequest request){
		SpvBaseInfoVO spvBaseInfoVO = toSpvService.findSpvBaseInfoVOByPkid(pkid);
		spvBaseInfoVO.getToSpv().setIsDeleted("1");
		spvBaseInfoVO.getToSpvDe().setIsDeleted("1");
		spvBaseInfoVO.getToSpvProperty().setIsDeleted("1");
        for (int i=0;i<spvBaseInfoVO.getSpvCustList().size();i++) {
        	if(spvBaseInfoVO.getSpvCustList().get(i)!=null){
        		spvBaseInfoVO.getSpvCustList().get(i).setIsDeleted("1");
        	}
		}
        for (int i=0;i<spvBaseInfoVO.getToSpvAccountList().size();i++) {
        	spvBaseInfoVO.getToSpvAccountList().get(i).setIsDeleted("1");
		}
        for (int i=0;i<spvBaseInfoVO.getToSpvDeDetailList().size();i++) {
        	spvBaseInfoVO.getToSpvDeDetailList().get(i).setIsDeleted("1");
		}
    	AjaxResponse<String> response = new AjaxResponse<String>();
    	try{
    		//保存相关信息
    		SessionUser user= uamSessionService.getSessionUser();
    		toSpvService.saveNewSpv(spvBaseInfoVO,user);
    		ToWorkFlow record=new ToWorkFlow();
			record.setBusinessKey(WorkFlowEnum.SPV_DEFKEY.getCode());
			record.setCaseCode(spvBaseInfoVO.getToSpv().getCaseCode());
		    ToWorkFlow workFlow= flowService.queryActiveToWorkFlowByCaseCodeBusKey(record);
			if(workFlow!=null){
			workFlow.setStatus("4");
		    flowService.updateByPrimaryKey(workFlow);
			processInstanceService.deleteProcess(workFlow.getInstCode());
			}
    		response.setSuccess(true);
    		response.setMessage("监管合约删除成功！");
    	}catch(Exception e){
    		response.setSuccess(false);
    		response.setMessage(e.getMessage());
    	}
    	return response;
	}
	
	@RequestMapping("spvOutApply/process")
	public String toSpvOutApplyProcess(HttpServletRequest request,
			HttpServletResponse response,String caseCode,String source,String instCode,String taskitem){

		request.setAttribute("source", source);
		CaseBaseVO caseBaseVO = toCaseService.getCaseBaseVO(caseCode);
		request.setAttribute("caseBaseVO", caseBaseVO);
		
		toAccesoryListService.getAccesoryList(request, taskitem);
    	ToSpv toSpv = toSpvService.queryToSpvByCaseCode(caseCode);
		request.setAttribute("toSpv", toSpv);
		SpvDeRecVo spvDeRecVo = toSpvService.findByProcessInstanceId(instCode);
		request.setAttribute("spvDeRecVo", spvDeRecVo);
		return "task/taskSpvOutApply";
	}
	@RequestMapping("spvOutApprove/process")
	public String toSpvOutApproveProcess(HttpServletRequest request,
			HttpServletResponse response,String caseCode,String source,String instCode,String taskitem){
		SessionUser user= uamSessionService.getSessionUser();
		request.setAttribute("source", source);
		CaseBaseVO caseBaseVO = toCaseService.getCaseBaseVO(caseCode);
		request.setAttribute("caseBaseVO", caseBaseVO);
		
		toAccesoryListService.getAccesoryList(request, taskitem);
		
		request.setAttribute("approveType", "6");
		request.setAttribute("operator", user != null ? user.getId():"");
		
		SpvDeRecVo spvDeRecVo = toSpvService.findByProcessInstanceId(instCode);
		
		request.setAttribute("spvDeRecVo", spvDeRecVo);
		return "task/taskSpvOutApprove";
	}
	
	/**
	 * 转到资金监管签约保存界面
	 * @param caseCode
	 * @param model
	 * @return
	 */
    @RequestMapping(value="/toSaveSpv")
    public String toSaveSpv(String caseCode,Model model){
    
    	SpvVo toSpvVo = toSpvService.findByCaseCode(caseCode);
    	model.addAttribute("toSpvVo",toSpvVo);
    	model.addAttribute("caseCode", caseCode);
    	model.addAttribute("taskitem", Consts.PART_CODE_SPV_SIGN_APPLY);
    	//需上传备件
		ToAccesoryList toAccesoryList = new ToAccesoryList();
		toAccesoryList.setPartCode(Consts.PART_CODE_SPV_SIGN_APPLY);
		List<ToAccesoryList> list = toAccesoryListService.qureyToAccesoryList(toAccesoryList);
		if(list != null && list.size() > 0) {
			int size = list.size();
			model.addAttribute("accesoryList", list);
			List<Long> idList = new ArrayList<Long>(size);
			for(int i=0; i<size; i++) {
				idList.add(list.get(i).getPkid());
			}
			model.addAttribute("idList", idList);
		}
    	return "spv/saveSpv";
    }
	
    /**
     * 保存资金监管签约
     * @param toSpv
     * @param toSpvDeCondList
     * @param delIds
     * @return
     */
    @RequestMapping(value="saveSpv")
    @ResponseBody
    public AjaxResponse<String> saveSpv(ToSpv toSpv,String toSpvDeCondList,String[] delIds,String[] delFlowIds,String toCashFlow,String toCashFlowList){
    	AjaxResponse<String> response = new AjaxResponse<String>();
    	try{
    		SpvVo spvVo = new SpvVo();
    		spvVo.setToSpv(toSpv);
    		spvVo.setToSpvDeCondList(JSONObject.parseArray(toSpvDeCondList, ToSpvDeCond.class));
    		spvVo.setToCashFlow(JSONObject.parseObject(toCashFlow, ToCashFlow.class));
    		spvVo.setToCashFlowList(JSONObject.parseArray(toCashFlowList, ToCashFlow.class));
    		spvVo.setDelIds(delIds);
    		spvVo.setDelFlowIds(delFlowIds);
    		ToCase toCase = toCaseService.findToCaseByCaseCode(toSpv.getCaseCode());
    		toSpvService.saveToSpv(spvVo);
    		response.setContent(String.valueOf(toCase.getPkid()));
    		response.setMessage("保存房款监管签约成功！");
    	}catch(Exception e){
    		response.setMessage(e.getMessage());
    		
    	}
    	return response;
    }
    
    /**
     * 保存资金监管签约
     */
    @RequestMapping(value="saveNewSpv")
    @ResponseBody
    public AjaxResponse<String> saveNewSpv(SpvBaseInfoVO spvBaseInfoVO){   	
    	AjaxResponse<String> response = new AjaxResponse<String>();
    	try{
    		//保存相关信息
    		SessionUser user= uamSessionService.getSessionUser();
    		toSpvService.saveNewSpv(spvBaseInfoVO,user);
    		response.setSuccess(true);
    		response.setContent(spvBaseInfoVO.getToSpv().getPkid().toString());
    		response.setMessage("保存资金监管签约成功！");
    	}catch(Exception e){
			setExMsgForResp(response,e);
    	}
    	return response;
    }
    
    /**
     * 提交资金监管签约
     */
    @RequestMapping(value="submitNewSpv")
    @ResponseBody
    public AjaxResponse<String> submitNewSpv(SpvBaseInfoVO spvBaseInfoVO){
    	AjaxResponse<String> response = new AjaxResponse<String>();
    	try{
    		//保存相关信息
    		SessionUser user= uamSessionService.getSessionUser();
    		toSpvService.submitNewSpv(spvBaseInfoVO,user);
    		response.setSuccess(true);
    		response.setMessage("开启资金监管流程成功！");
    	}catch(Exception e){
			setExMsgForResp(response,e);
    	}
    	return response;
    }
    
    /**
     * 转向资金监管解除记录保存界面
     * @param caseCode
     * @param model
     * @return
     */
    @RequestMapping(value="/toSpvDeRec")
    public String toSpvDeRec(String caseCode,Model model){
    
    	ToSpv toSpv = toSpvService.queryToSpvByCaseCode(caseCode);
 //   	SpvVo toSpvVo = toSpvService.findByProcessInstanceId(processInstanceId);
    	model.addAttribute("toSpv",toSpv);
    	model.addAttribute("caseCode", caseCode);
    	model.addAttribute("taskitem", Consts.PART_CODE_SPV_OUT_APPLY);
    	//需上传备件
		ToAccesoryList toAccesoryList = new ToAccesoryList();
		toAccesoryList.setPartCode(Consts.PART_CODE_SPV_OUT_APPLY);
		List<ToAccesoryList> list = toAccesoryListService.qureyToAccesoryList(toAccesoryList);
		if(list != null && list.size() > 0) {
			int size = list.size();
			model.addAttribute("accesoryList", list);
			List<Long> idList = new ArrayList<Long>(size);
			for(int i=0; i<size; i++) {
				idList.add(list.get(i).getPkid());
			}
			model.addAttribute("idList", idList);
		}
    	return "spv/spvDeRec";
    }
    
    /**
     * 查询资金监管解除条件
     * @param spvCode
     * @return
     */
    @RequestMapping(value="querySpvDeCond")
    @ResponseBody
    public List<ToSpvDeCond> querySpvDeCond(String spvCode){
    	
    	List<ToSpvDeCond> list = toSpvService.findToSpvCondBySpvCode(spvCode);
    	return list;
    }
    
    /**
     * 启动资金监管解除流程
     * @param toSpvDeRec
     * @return
     */
    @RequestMapping(value="startSpvOutApplyProcess")
    @ResponseBody
    public AjaxResponse<ProcessInstanceVO> startSpvOutApplyProcess(ProcessInstanceVO processInstanceVO){
    	AjaxResponse<ProcessInstanceVO> response = new AjaxResponse<ProcessInstanceVO>();
    	try{
    		SpvVo spvVo = toSpvService.findByCaseCode(processInstanceVO.getCaseCode());
    		if(spvVo == null){
    			response.setSuccess(false);
    			response.setMessage("您还未添加房款监管信息，不能解除监管！");
    			return response;
    		}
    		if(CollectionUtils.isEmpty(spvVo.getToSpvDeCondList())){
    			response.setSuccess(false);
    			response.setMessage("您还未添加房款监管解除条件，不能解除监管！");
    			return response;
    		}
    		ProcessInstanceVO vo = toSpvService.startSpvOutApplyProcess(processInstanceVO);
    		response.setContent(vo);
    	}catch(Exception e){
    		response.setMessage(e.getMessage());
    		
    	}
    	return response;
    }
    
    /**
     * 保存资金监管解除记录
     * @param toSpvDeRec
     * @return
     */
    @RequestMapping(value="saveSpvDeRec")
    @ResponseBody
    public AjaxResponse<String> saveSpvDeRec(ToSpvDeRec toSpvDeRec,ProcessInstanceVO processInstanceVO){
    	AjaxResponse<String> response = new AjaxResponse<String>();
    	try{
    		toSpvService.saveToSpvDeRec(toSpvDeRec,processInstanceVO.getProcessInstanceId());
    	}catch(Exception e){
    		response.setMessage(e.getMessage());
    		
    	}
    	return response;
    }
    
    /**
     * 提交资金监管申请
     * @param toSpvDeRec
     * @param processInstanceVO
     * @return
     */
    @RequestMapping(value="submitSpvDeRec")
    @ResponseBody
    public AjaxResponse<String> submitSpvDeRec(ToSpvDeRec toSpvDeRec,ProcessInstanceVO processInstanceVO){
    	AjaxResponse<String> response = new AjaxResponse<String>();
    	try{
    		toSpvService.submitToSpvDeRec(toSpvDeRec,processInstanceVO);
    	}catch(Exception e){
    		response.setMessage(e.getMessage());
    		
    	}
    	return response;
    }
    
    /**
     * 查询资金监管记录
     * @param toSpvDeRec
     * @return
     */
    @RequestMapping(value="getSpvDeRec")
    @ResponseBody
    public AjaxResponse<ToSpvDeRec> getSpvDeRec(ToSpvDeRec toSpvDeRec){
    	AjaxResponse<ToSpvDeRec> response = new AjaxResponse<ToSpvDeRec>();
    	try{
    		ToSpvDeRec spvDeRec = toSpvService.findSpvDeRecBySpvCodeAndCondId(toSpvDeRec);
    		response.setContent(spvDeRec);
    	}catch(Exception e){
    		response.setMessage("查询房款监管解除记录失败！");
    		
    	}
    	return response;
    }
    
    /**
     * 资金监管交易顾问申请页面
     * @param request
     * @param response
     * @param caseCode
     * @param source
     * @param instCode
     * @param taskitem
     * @return
     */
    @RequestMapping("task/SpvApply/process")
	public String toSpvApplyProcess(HttpServletRequest request,Long pkid,String source,String instCode,String taskId){
    	
        SpvBaseInfoVO spvBaseInfoVO = toSpvService.findSpvBaseInfoVOByInstCode(instCode);
		Map<String, Object> caseInfoMap = new HashMap<String, Object>();
		
		if (spvBaseInfoVO != null && spvBaseInfoVO.getToSpv() != null && StringUtils.isNotBlank(spvBaseInfoVO.getToSpv().getCaseCode())) {
		    caseInfoMap	= toSpvService.queryCaseInfoMapByCaseCode(spvBaseInfoVO.getToSpv().getCaseCode());
			request.setAttribute("caseCode", caseInfoMap.get("caseCode"));
		    request.setAttribute("caseInfoMap", caseInfoMap);
		}
		
		//查询审核结果
		ToApproveRecord toApproveRecordForItem=new ToApproveRecord();
		if(spvBaseInfoVO.getToSpv() != null && spvBaseInfoVO.getToSpv().getCaseCode() != null){
			toApproveRecordForItem.setCaseCode(spvBaseInfoVO.getToSpv().getCaseCode());
		}		
		toApproveRecordForItem.setProcessInstance(instCode);
		toApproveRecordForItem.setPartCode("SpvDirectorApprove");		
		ToApproveRecord toApproveRecord=toApproveRecordService.queryToApproveRecordForSpvApply(toApproveRecordForItem);	
		//审批记录：总监审批记录，没有就取专员审批记录
		if(toApproveRecord != null){
			request.setAttribute("toApproveRecord", toApproveRecord);
		}else{
			toApproveRecordForItem.setProcessInstance(instCode);
			toApproveRecordForItem.setPartCode("SpvOfficerApprove");		
			ToApproveRecord record=toApproveRecordService.queryToApproveRecordForSpvApply(toApproveRecordForItem);	
			request.setAttribute("toApproveRecord", record);
		}
		
		if(spvBaseInfoVO != null && spvBaseInfoVO.getToSpv() != null 
				&& !StringUtils.isBlank(spvBaseInfoVO.getToSpv().getRiskControlOfficer())){
			request.setAttribute("riskControlOfficerName",uamSessionService.getSessionUserById(spvBaseInfoVO.getToSpv().getRiskControlOfficer()).getRealName());
		}
		
		request.setAttribute("spvBaseInfoVO", spvBaseInfoVO);
		
		toAccesoryListService.getAccesoryList(request, "SpvApplyApprove");
	    App app = uamPermissionService.getAppByAppName(AppTypeEnum.APP_FILESVR.getCode());
	    request.setAttribute("imgweb", app.genAbsoluteUrl());	    
		
		Org riskControllerOrg = uamUserOrgService.getOrgByCode("112A011");
		
		request.setAttribute("orgId", riskControllerOrg.getId());
    	request.setAttribute("taskId", taskId); 
    	request.setAttribute("instCode", instCode);
		request.setAttribute("pkid", pkid);
		request.setAttribute("source", source);
		request.setAttribute("handle", "SpvApply");
		request.setAttribute("urlType", "myTask");
		
		return "spv/saveSpvCase";
	}
    
    /**
     * 资金监管交易顾问申请操作
     * @param request
     * @param response
     * @param caseCode
     * @param source
     * @param instCode
     * @param taskitem
     * @return
     */
    @RequestMapping("spvApply/deal")
    @ResponseBody
	public AjaxResponse<?> spvApply(SpvBaseInfoVO spvBaseInfoVO){	
    	AjaxResponse<?> response = new AjaxResponse<>();
    	try {
    		//保存相关信息
    		SessionUser user= uamSessionService.getSessionUser();
    		toSpvService.spvApply(spvBaseInfoVO, spvBaseInfoVO.getSpvCode(), spvBaseInfoVO.getCaseCode(), 
    				spvBaseInfoVO.getSource(), spvBaseInfoVO.getInstCode(), spvBaseInfoVO.getTaskId(), user);
    		response.setSuccess(true);
		} catch (Exception e) {
			setExMsgForResp(response,e);
		}
		
		return response;
	}
    
    /**
     * 资金监管风控专员审核页面
     * @param request
     * @param response
     * @param caseCode
     * @param source
     * @param instCode
     * @param taskitem
     * @return
     */
    @RequestMapping("task/SpvAudit/process")
	public String toSpvAuditProcess(HttpServletRequest request,Long pkid,String source,String instCode,String taskId){
    	   	
        SpvBaseInfoVO spvBaseInfoVO = toSpvService.findSpvBaseInfoVOByInstCode(instCode);
		Map<String, Object> caseInfoMap = new HashMap<String, Object>();
		
		if (spvBaseInfoVO != null && spvBaseInfoVO.getToSpv() != null && StringUtils.isNotBlank(spvBaseInfoVO.getToSpv().getCaseCode())) {
		    caseInfoMap	= toSpvService.queryCaseInfoMapByCaseCode(spvBaseInfoVO.getToSpv().getCaseCode());
			request.setAttribute("caseCode", caseInfoMap.get("caseCode"));
		    request.setAttribute("caseInfoMap", caseInfoMap);
		}
		
		//查询审核结果
		ToApproveRecord toApproveRecordForItem=new ToApproveRecord();
		if(spvBaseInfoVO.getToSpv() != null && spvBaseInfoVO.getToSpv().getCaseCode() != null){
			toApproveRecordForItem.setCaseCode(spvBaseInfoVO.getToSpv().getCaseCode());
		}		
		toApproveRecordForItem.setProcessInstance(instCode);
		toApproveRecordForItem.setPartCode("SpvOfficerApprove");		
		ToApproveRecord toApproveRecord=toApproveRecordService.queryToApproveRecordForSpvApply(toApproveRecordForItem);		
		request.setAttribute("toApproveRecord", toApproveRecord);
		
		if(spvBaseInfoVO != null && spvBaseInfoVO.getToSpv() != null 
				&& !StringUtils.isBlank(spvBaseInfoVO.getToSpv().getRiskControlOfficer())){
			request.setAttribute("riskControlOfficerName",uamSessionService.getSessionUserById(spvBaseInfoVO.getToSpv().getRiskControlOfficer()).getRealName());
		}
		
		request.setAttribute("spvBaseInfoVO", spvBaseInfoVO);
		
		toAccesoryListService.getAccesoryList(request, "SpvApplyApprove");
	    App app = uamPermissionService.getAppByAppName(AppTypeEnum.APP_FILESVR.getCode());
	    request.setAttribute("imgweb", app.genAbsoluteUrl());	    
		
	    Org riskControllerOrg = uamUserOrgService.getOrgByCode("112A011");
		
		request.setAttribute("orgId", riskControllerOrg.getId());
    	request.setAttribute("taskId", taskId); 
    	request.setAttribute("instCode", instCode);
		request.setAttribute("pkid", pkid);
		request.setAttribute("source", source);
		request.setAttribute("handle", "SpvAudit");
		request.setAttribute("urlType", "myTask");
		
		return "spv/saveSpvCase";
	}
    
    /**
     * 资金监管风控专员审核操作
     * @param request
     * @param response
     * @param caseCode
     * @param source
     * @param instCode
     * @param taskitem
     * @return
     */
    @RequestMapping("spvAudit/deal")
    @ResponseBody
	public AjaxResponse<?> spvAudit(SpvBaseInfoVO spvBaseInfoVO, String spvCode, Boolean spvApplyApprove, String caseCode, String source, String instCode, 
			String taskId, String remark){
    	AjaxResponse<?> response = new AjaxResponse<>();
    	try {
    		//保存相关信息
    		SessionUser user= uamSessionService.getSessionUser();
    		toSpvService.spvAudit(spvBaseInfoVO, spvApplyApprove, spvCode, caseCode, source, instCode, taskId, remark, user);
    		response.setSuccess(true);
		} catch (Exception e) {
			setExMsgForResp(response,e);
		}
		
		return response;
		
	}
    
    /**
     * 资金监管审批页面
     * @param request
     * @param response
     * @param caseCode
     * @param source
     * @param instCode
     * @param taskitem
     * @return
     */
	@RequestMapping("task/SpvApprove/process")
	public String toSpvApproveProcess(HttpServletRequest request,String source,String instCode,String taskId){	
		
		SpvBaseInfoVO spvBaseInfoVO = toSpvService.findSpvBaseInfoVOByInstCode(instCode);

		//查询审核结果
		ToApproveRecord toApproveRecordForItem=new ToApproveRecord();
		if(spvBaseInfoVO.getToSpv() != null && spvBaseInfoVO.getToSpv().getCaseCode() != null){
			toApproveRecordForItem.setCaseCode(spvBaseInfoVO.getToSpv().getCaseCode());
		}		
		toApproveRecordForItem.setProcessInstance(instCode);
		toApproveRecordForItem.setPartCode("SpvDirectorApprove");		
		ToApproveRecord toApproveRecord=toApproveRecordService.queryToApproveRecordForSpvApply(toApproveRecordForItem);		
		request.setAttribute("toApproveRecord", toApproveRecord);
		
		if(spvBaseInfoVO != null && spvBaseInfoVO.getToSpv() != null 
				&& !StringUtils.isBlank(spvBaseInfoVO.getToSpv().getRiskControlOfficer())){
			request.setAttribute("riskControlOfficerName",uamSessionService.getSessionUserById(spvBaseInfoVO.getToSpv().getRiskControlOfficer()).getRealName());
		}
		
		toAccesoryListService.getAccesoryList(request, "SpvApplyApprove");
	    App app = uamPermissionService.getAppByAppName(AppTypeEnum.APP_FILESVR.getCode());
	    request.setAttribute("imgweb", app.genAbsoluteUrl());
		
		request.setAttribute("spvBaseInfoVO", spvBaseInfoVO);		
		request.setAttribute("taskId", taskId);
    	request.setAttribute("instCode", instCode);
		request.setAttribute("source", source);
		request.setAttribute("handle", "SpvApprove");
		request.setAttribute("urlType", "myTask");
		if(spvBaseInfoVO.getToSpv() != null && spvBaseInfoVO.getToSpv().getCaseCode() != null){
			request.setAttribute("caseCode", spvBaseInfoVO.getToSpv().getCaseCode());
		}
		
		return "spv/saveSpvCase";
	}

	/**
     * 资金监管审批操作
     * @param request
     * @param response
     * @param caseCode
     * @param source
     * @param instCode
     * @param taskitem
     * @return
     */
	@RequestMapping("spvApprove/deal")
	@ResponseBody
	public AjaxResponse<?> spvApprove(String spvCode, Boolean spvApplyApprove, String caseCode, String source, String instCode, 
			String taskId, String remark){
    	AjaxResponse<?> response = new AjaxResponse<>();
    	try {
    		//保存相关信息
    		SessionUser user= uamSessionService.getSessionUser();
    		toSpvService.spvApprove(spvApplyApprove, spvCode, caseCode, source, instCode, taskId, remark, user);
    		response.setSuccess(true);
		} catch (Exception e) {
			setExMsgForResp(response,e);
		}
		
		return response;
	}
	
    /**
     * 资金监管签约页面
     * @param request
     * @param response
     * @param caseCode
     * @param source
     * @param instCode
     * @param taskitem
     * @return
     */
	@RequestMapping("task/SpvSign/process")
	public String toSpvSignProcess(HttpServletRequest request,String caseCode,String source,String instCode,String taskId){		
		SpvBaseInfoVO spvBaseInfoVO = toSpvService.findSpvBaseInfoVOByInstCode(instCode);	
		request.setAttribute("spvBaseInfoVO", spvBaseInfoVO);
		
		toAccesoryListService.getAccesoryList(request, "SpvApplyApprove");
	    App app = uamPermissionService.getAppByAppName(AppTypeEnum.APP_FILESVR.getCode());
	    request.setAttribute("imgweb", app.genAbsoluteUrl());
	    
		if(spvBaseInfoVO != null && spvBaseInfoVO.getToSpv() != null 
				&& !StringUtils.isBlank(spvBaseInfoVO.getToSpv().getRiskControlOfficer())){
			request.setAttribute("riskControlOfficerName",uamSessionService.getSessionUserById(spvBaseInfoVO.getToSpv().getRiskControlOfficer()).getRealName());
		}
		
		request.setAttribute("taskId", taskId);
    	request.setAttribute("instCode", instCode);
		request.setAttribute("source", source);
		request.setAttribute("handle", "SpvSign");
		request.setAttribute("urlType", "myTask");
		if(spvBaseInfoVO.getToSpv() != null && spvBaseInfoVO.getToSpv().getCaseCode() != null){
			request.setAttribute("caseCode", spvBaseInfoVO.getToSpv().getCaseCode());
		}
		
		return "spv/saveSpvCase";
	}
	
	/**
     * 资金监管签约操作
     * @param request
     * @param response
     * @param caseCode
     * @param source
     * @param instCode
     * @param taskitem
     * @return
     */
	@RequestMapping("spvSign/deal")
	@ResponseBody
	public AjaxResponse<?> spvSign(String spvCode, String caseCode, String source, String instCode, String taskId, 
			String spvConCode, Date signTime, Long buyerAccountPkid, String buyerAccountName, String buyerAccountNo, 
			String buyerAccountTelephone, String buyerAccountBank, String buyerAccountBranchBank, Long sellerAccountPkid, String sellerAccountName, String sellerAccountNo, 
			String sellerAccountTelephone, String sellerAccountBank, String sellerAccountBranchBank, Long fundAccountPkid, String fundAccountName, String fundAccountNo, String fundAccountBranchBank){
    	AjaxResponse<?> response = new AjaxResponse<>();
    	try {
    		//保存相关信息
    		SessionUser user= uamSessionService.getSessionUser();
    		
    		ToSpvAccount buyerAcc = new ToSpvAccount();
    		buyerAcc.setPkid(buyerAccountPkid);
    		buyerAcc.setName(buyerAccountName);
    		buyerAcc.setAccount(buyerAccountNo);
    		buyerAcc.setTelephone(buyerAccountTelephone);
    		buyerAcc.setBank(buyerAccountBank);
    		buyerAcc.setBranchBank(buyerAccountBranchBank);
    		
    		ToSpvAccount sellerAcc = new ToSpvAccount();
    		sellerAcc.setPkid(sellerAccountPkid);
    		sellerAcc.setName(sellerAccountName);
    		sellerAcc.setAccount(sellerAccountNo);
    		sellerAcc.setTelephone(sellerAccountTelephone);
    		sellerAcc.setBank(sellerAccountBank);
    		sellerAcc.setBranchBank(sellerAccountBranchBank);
    		
    		ToSpvAccount fundAcc = new ToSpvAccount();
    		fundAcc.setPkid(fundAccountPkid);
    		fundAcc.setName(fundAccountName);
    		fundAcc.setAccount(fundAccountNo);
    		fundAcc.setBranchBank(fundAccountBranchBank);
    		
    		toSpvService.spvSign(spvCode, caseCode, source, instCode, taskId, spvConCode, signTime, buyerAcc, sellerAcc, fundAcc, user);
    		response.setSuccess(true);
		} catch (Exception e) {
			setExMsgForResp(response,e);
		}
		
		return response;
	}
	
	@RequestMapping("queryByCaseCode")
	@ResponseBody
	public AjaxResponse<String> queryByCaseCode(String caseCode){
		
        AjaxResponse<String> response = new AjaxResponse<String>();
    	try{
    		Map<String,Object> caseInfoMap = (Map<String, Object>)toSpvService.queryInfoByCaseCode(caseCode);
    		response.setContent(JSONObject.toJSONString(caseInfoMap));
    		response.setSuccess(true);
    	}catch(Exception e){
    		setExMsgForResp(response, e);
    	}
    	return response;
	}
	
	
	/**
	 * 查询所有的产品分类
	 */
	@RequestMapping("queryPrdCategorys")
	@ResponseBody
	public List<ProductCategory> queryPrdCategorys() {

		List<ProductCategory> prdList = productCategoryService.getAllProductCategoryList();
		
		return prdList;
	}
	
	/**
	 * 查询分类下所有的产品
	 */
	@RequestMapping("queryProdByPrdcCode")
	@ResponseBody
	public List<Product> queryPrdByCateCode(String prdcCode) {

		List<Product> prdList = productService.getProductListByProductCategoryCode(prdcCode);
		
		return prdList;
	}
	
	/**
	 * 查询产品所属分类
	 */
	@RequestMapping("queryPrdcCodeByProdCode")
	@ResponseBody
	public Product queryPrdcCodeByProdCode(String prodCode) {
		
		Product product = productService.getProductByCode(prodCode);
		
		return  product;
	}
	
    /**
     * @throws Exception  
     * @Title: cashFlowOutApprProcess 
     * @Description: 出款申请页面
     */
    @RequestMapping("task/cashFlowOutAppr/process")
	public String cashFlowOutApprProcess(HttpServletRequest request,String source,String instCode,
			String taskId,String handle,String businessKey,String spvCode) throws Exception {
    	request.setAttribute("spvCode", spvCode);
    	SessionUser user = uamSessionService.getSessionUser();
    	StartProcessInstanceVo  processInstance = processInstanceService.getHistoryInstances(instCode);
		businessKey = processInstance.getBusinessKey();
    	if(!StringUtils.isBlank(handle)){ 	
        	switch (handle) {
        	case "apply":
        		cashFlowOutService.cashFlowOutApplyProcess(request, source, instCode, taskId, handle, businessKey);
        		break;
            case "directorAudit":
            	cashFlowOutService.cashFlowOutDirectorAuditProcess(request, source, instCode, taskId, handle, businessKey);
        		break;
            case "financeAudit":
            	cashFlowOutService.cashFlowOutFinanceAuditProcess(request, source, instCode, taskId, handle, businessKey);
            	break;
            case "financeSecondAudit":
            	cashFlowOutService.cashFlowOutFinanceSecondAuditProcess(request, source, instCode, taskId, handle, businessKey);
                break;
        	}
    		request.setAttribute("urlType", "myTask");
        }else{
        	cashFlowOutService.cashFlowOutPage(request, source, instCode, taskId, handle, businessKey);
        	request.setAttribute("urlType", "spvApply");
        }
	    
	    request.setAttribute("businessKey", businessKey);
    	request.setAttribute("taskId", taskId); 
    	request.setAttribute("instCode", instCode);
		request.setAttribute("source", source);
		request.setAttribute("handle", handle);
		request.setAttribute("user", user);
		
		return "spv/caseFlowOutApply";
	}
    
    /**
     * @Title: cashFlowOutApprDeal 
     * @Description: 出款申请操作
     */
    @RequestMapping("cashFlowOutAppr/deal")
    @ResponseBody
	public AjaxResponse<?> cashFlowOutApprDeal(HttpServletRequest request,String source,String instCode,String taskitem,
			String taskId,String handle,SpvChargeInfoVO spvChargeInfoVO,Boolean chargeOutAppr) {
    	AjaxResponse<?> response = new AjaxResponse<>();
    	try {	
			if(!StringUtils.isBlank(handle)){ 
				String cashflowApplyCode = spvChargeInfoVO.getToSpvCashFlowApply().getCashflowApplyCode();
				
				if(StringUtils.isBlank(cashflowApplyCode)) throw new BusinessException("页面没有传入申请号！");
				
				switch (handle) {
				case "apply":
					cashFlowOutService.cashFlowOutApplyDeal(request, instCode, taskId, taskitem, handle, spvChargeInfoVO, cashflowApplyCode, chargeOutAppr);
					break;
			    case "directorAudit":
			    	cashFlowOutService.cashFlowOutDirectorAuditDeal(request, instCode, taskId, taskitem, handle, spvChargeInfoVO, cashflowApplyCode,chargeOutAppr);
					break;
			    case "financeAudit":
			    	cashFlowOutService.cashFlowOutFinanceAuditDeal(request, instCode, taskId, taskitem, handle, spvChargeInfoVO, cashflowApplyCode,chargeOutAppr);
			    	break;
			    case "financeSecondAudit":
			    	cashFlowOutService.cashFlowOutFinanceSecondAuditDeal(request, instCode, taskId, taskitem, handle, spvChargeInfoVO, cashflowApplyCode,chargeOutAppr);
			        break;  
				}	
			}else{
				cashFlowOutService.cashFlowOutPageDeal(request, instCode, taskId, taskitem, handle, spvChargeInfoVO, null);
			}

			response.setSuccess(true);
			response.setCode(spvChargeInfoVO.getToSpvCashFlowApply().getCashflowApplyCode());
		} catch (Exception e) {
			setExMsgForResp(response,e);
		}
    	
    	return response;
	}
    
    /**
     * @Title: cashFlowOutApprDeal 
     * @Description: 出款申请操作
     */
    @RequestMapping("cashFlowOutAppr/save")
    @ResponseBody
	public AjaxResponse<?> cashFlowOutApprSave(SpvChargeInfoVO spvChargeInfoVO) {
    	AjaxResponse<?> response = new AjaxResponse<>();
    	try {	
    		toSpvService.saveSpvChargeInfoVO4_3parties(spvChargeInfoVO);
			response.setSuccess(true);
		} catch (Exception e) {
			setExMsgForResp(response,e);
		}
    	
    	return response;
	}
    
    
    
    
    /**
     * spvCloseApplyProcess:合约中止/结束 申请、审批页面. <br/> 
     */
    @RequestMapping("task/spvCloseApply/process")
   	public String spvCloseApplyProcess(HttpServletRequest request,String source,String instCode,
   			String taskId,String handle,String businessKey,String spvCode) throws Exception {
    	
    	SessionUser user = uamSessionService.getSessionUser();

    	if(!StringUtils.isBlank(handle)){ 	
        	switch (handle) {
        	case "apply":
        		spvCloseApplyService.spvCloseApplyProcess(request, instCode, taskId, businessKey);
        		break;
            case "hostAudit":
            	spvCloseApplyService.spvCloseHostAuditProcess(request, instCode, taskId, businessKey);
        		break;
            case "directorAudit":
            	spvCloseApplyService.spvCloseDirectorAuditProcess(request, instCode, taskId, businessKey);
            	break;
        	}
    		request.setAttribute("urlType", "myTask");
        }else{
        	spvCloseApplyService.spvCloseApplyPage(request, spvCode, businessKey);
        	request.setAttribute("urlType", "spvApply");
        }
	    
    	toAccesoryListService.getAccesoryList(request, "SpvApplyApprove");
    	
    	request.setAttribute("spvCode", spvCode);
	    request.setAttribute("businessKey", businessKey);
    	request.setAttribute("taskId", taskId); 
    	request.setAttribute("instCode", instCode);
		request.setAttribute("source", source);
		request.setAttribute("handle", handle);
		request.setAttribute("user", user);
		
		return "spv/spvCloseApply";
    }
    
    /**
     * spvCloseApplyDeal:合约中止/结束 申请、审批操作. <br/> 
     */
    @RequestMapping("/spvCloseApply/deal")
    @ResponseBody
   	public AjaxResponse<?> spvCloseApplyDeal(HttpServletRequest request, SpvCloseInfoVO spvCloseInfoVO,String source,String instCode,String taskitem,
   			String taskId,String handle,String businessKey,Boolean continueApply,Boolean result) throws Exception {
    	AjaxResponse<?> response = new AjaxResponse<>();
    	try {
    		if(!StringUtils.isBlank(handle)){ 			
				switch (handle) {
				case "apply":
					spvCloseApplyService.spvCloseApplyDeal(request, spvCloseInfoVO, taskId, instCode, businessKey, continueApply);
					break;
			    case "hostAudit":
			    	spvCloseApplyService.spvCloseHostAuditDeal(request, spvCloseInfoVO, instCode,
			    			taskitem, taskId, businessKey, result);
					break;
			    case "directorAudit":
			    	spvCloseApplyService.spvCloseDirectorAuditDeal(request, spvCloseInfoVO, instCode,
			    			taskitem, taskId, businessKey, result);
			    	break; 
				}	
			}else{
				spvCloseApplyService.spvClosePageDeal(request, spvCloseInfoVO, instCode);
			}

			response.setSuccess(true);
		} catch (Exception e) {
			setExMsgForResp(response,e);
		}
    	
    	return response;
    }
    
    /**
     * checkInOutWorkFlowProcess: 检查是否存在出入账流程正在进行中. <br/> 
     */
    @RequestMapping("/checkInOutWorkFlowProcess")
    @ResponseBody
   	public AjaxResponse<?> checkInOutWorkFlowProcess(String spvCode) throws Exception {
    	AjaxResponse<?> response = new AjaxResponse<>();
    	String inOutProcessTag = spvCloseApplyService.findInOutWorkFlowProcessBySpvCode(spvCode);
    	response.setSuccess(false);
    	if("in".equals(inOutProcessTag)){
    		response.setMessage("尚有‘入账’流程进行中，不能开启‘中止/结束’流程！");
    	}else if("out".equals(inOutProcessTag)){
    		response.setMessage("尚有‘出账’流程进行中，不能开启‘中止/结束’流程！");
    	}else if("inout".equals(inOutProcessTag)){
    		response.setMessage("尚有‘入账’和‘出账’流程进行中，不能开启‘中止/结束’流程！");
    	}else if("".equals(inOutProcessTag)){
    		response.setSuccess(true);
    	}

    	return response;
    } 

    /**
     * @Title: cashFlowOutApprProcess 
     * @Description: 入账页面
     */
    @RequestMapping("task/spvCashflowInAppr/process")
   	public String cashFlowInAppprProcess(HttpServletRequest request,String source,String instCode,
   			String taskId,String handle,String businessKey)  {
    	
       	String url="";
       	StartProcessInstanceVo  processInstance = processInstanceService.getHistoryInstances(instCode);
		businessKey = processInstance.getBusinessKey();
       	if(!StringUtils.isBlank(handle)){ 	
           	switch (handle) {
	           	case "apply":
	       			  cashFlowInService.cashFlowInApplyProcess(request, source, instCode, taskId, handle, businessKey);
	       			  url="spv/spvRecordedApp";
	       			  break;
	           	case "directorAduit":
	        	      cashFlowInService.cashFlowInDirectorAduitProcess(request, source, instCode, taskId, handle, businessKey);
	        	      url="spv/spvRecordShow";
	        	      break;
	           	case "financeAduit":
	           		  cashFlowInService.cashFlowInFinanceAduitProcess(request, source, instCode, taskId, handle, businessKey);
	        	      url="spv/spvRecordShow";
	           	      break;
           	}
         }
   	    
       	request.setAttribute("taskId", taskId); 
       	request.setAttribute("instCode", instCode);
   		request.setAttribute("source", source);
   		request.setAttribute("handle", handle);
   		
   		return  url;
   	}
    
    /**
	 * @Title: saveSpvReceipt 
	 * @Description: 修改(添加、删除)回单小票附件
	 * @author: hejf 
	 * @param toSpvCashFlowApplyAttach
	 * @return response
	 * @throws
	 */
	@RequestMapping(value = "saveSpvReceipt")
	@ResponseBody
	public AjaxResponse<String> saveSpvReceipt(FileUploadVO fileUploadVO,String cashFlowCode) {
		AjaxResponse<String> response = new AjaxResponse<String>();
		try{
			cashFlowInService.saveAttachments(fileUploadVO,cashFlowCode);
			response.setSuccess(true);
		}catch(Exception e){
			response.setSuccess(false);
			response.setMessage("保存失败！");
			e.printStackTrace();
		}
		return response;
	}
	
	@RequestMapping("batchChangeOfficer")
	@ResponseBody
	public AjaxResponse<String> batchChangeOfficer(String spvCodeListStr, String newOfficer, String newDirector) {
		AjaxResponse<String> response = new AjaxResponse<String>();
		try{
			toSpvService.batchChangeOfficer(spvCodeListStr.split(","), newOfficer, newDirector);
			response.setSuccess(true);
		}catch(Exception e){
			response.setSuccess(false);
			e.printStackTrace();
		}
		return response;
	}
	
	@RequestMapping("changeOfficer")
	@ResponseBody
	public AjaxResponse<String> changeOfficer(String spvCode, String newOfficer, String newDirector) {
		AjaxResponse<String> response = new AjaxResponse<String>();
		try{
			toSpvService.changeOfficer(spvCode, newOfficer, newDirector);
			response.setSuccess(true);
		}catch(Exception e){
			response.setSuccess(false);
			e.printStackTrace();
		}
		return response;
	}
	
	@RequestMapping("selectOfficerAndDirector")
	@ResponseBody
	public AjaxResponse<List<String>> selectOfficerAndDirector(String spvCode) {
		AjaxResponse<List<String>> result = new AjaxResponse<List<String>>();
		try {
			List<String> mixUserList = toSpvService.selectOfficerAndDirector(spvCode);
			result.setSuccess(true);
			result.setMessage("操作成功!");
			result.setContent(mixUserList);
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
			result.setMessage("操作失败!");
		}
		return result;
	}
	
    /**
     * @Title: setExMsgForResp 
     * @Description: 将错误信息封装给response.message
     * @author: gongjd 
     * @param response
     * @param e 
     * @throws
     */
    private void setExMsgForResp(AjaxResponse<?> response,Exception e) {
    	response.setSuccess(false);
		response.setMessage(e.getMessage());
		e.printStackTrace();
	}
}
