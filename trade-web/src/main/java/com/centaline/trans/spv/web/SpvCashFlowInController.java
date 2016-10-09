/**
 * AISThink.com Inc.
 * Copyright (c) 2015-2015 All Rights Reserved.
 */
package com.centaline.trans.spv.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

import com.aist.common.web.validate.AjaxResponse;
import com.aist.uam.auth.remote.UamSessionService;
import com.aist.uam.auth.remote.vo.SessionUser;
import com.aist.uam.permission.remote.UamPermissionService;
import com.aist.uam.permission.remote.vo.App;
import com.aist.uam.userorg.remote.UamUserOrgService;
import com.aist.uam.userorg.remote.vo.Org;
import com.aist.uam.userorg.remote.vo.User;
import com.alibaba.fastjson.JSONObject;
import com.centaline.trans.cases.entity.ToCase;
import com.centaline.trans.cases.service.ToCaseService;
import com.centaline.trans.cases.vo.CaseBaseVO;
import com.centaline.trans.common.entity.ToAccesoryList;
import com.centaline.trans.common.entity.ToWorkFlow;
import com.centaline.trans.common.enums.WorkFlowEnum;
import com.centaline.trans.common.enums.AppTypeEnum;
import com.centaline.trans.common.enums.SpvStatusEnum;
import com.centaline.trans.common.service.MessageService;
import com.centaline.trans.common.service.ToAccesoryListService;
import com.centaline.trans.common.service.ToWorkFlowService;
import com.centaline.trans.engine.bean.RestVariable;
import com.centaline.trans.engine.service.ProcessInstanceService;
import com.centaline.trans.engine.service.WorkFlowManager;
import com.centaline.trans.mgr.Consts;
import com.centaline.trans.product.entity.Product;
import com.centaline.trans.product.entity.ProductCategory;
import com.centaline.trans.product.service.ProductCategoryService;
import com.centaline.trans.product.service.ProductService;
import com.centaline.trans.spv.entity.ToCashFlow;
import com.centaline.trans.spv.entity.ToSpv;
import com.centaline.trans.spv.entity.ToSpvCashFlowApply;
import com.centaline.trans.spv.entity.ToSpvDeCond;
import com.centaline.trans.spv.entity.ToSpvDeRec;
import com.centaline.trans.spv.service.CashFlowInService;
import com.centaline.trans.spv.service.CashFlowOutService;
import com.centaline.trans.spv.service.ToSpvService;
import com.centaline.trans.spv.vo.SpvBaseInfoVO;
import com.centaline.trans.spv.vo.SpvChargeInfoVO;
import com.centaline.trans.spv.vo.SpvDeRecVo;
import com.centaline.trans.spv.vo.SpvRecordReturnVO;
import com.centaline.trans.spv.vo.SpvRecordedInfoVO;
import com.centaline.trans.spv.vo.SpvRecordedsVO;
import com.centaline.trans.spv.vo.SpvRecordedsVOItem;
import com.centaline.trans.spv.vo.SpvReturnCashflowVO;
import com.centaline.trans.spv.vo.SpvVo;
import com.centaline.trans.task.entity.ToApproveRecord;
import com.centaline.trans.task.service.ToApproveRecordService;
import com.centaline.trans.task.vo.ProcessInstanceVO;


@Controller
@RequestMapping(value="/spv/task/cashflowIntApply")
public class SpvCashFlowInController {
	
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
	
	@Autowired
	private CashFlowOutService cashFlowOutService;
	
	/**
	 * 起草入账页面保存
	 * @param spvrevo
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "/saveDate")
	@ResponseBody
	public AjaxResponse<?>  saveDate(SpvRecordedsVO spvrevo,HttpServletRequest request) throws Exception{
		AjaxResponse<?> response = new AjaxResponse<>();
		String handle = null;
		String cashflowApplyCode = null;
		SpvReturnCashflowVO spvReturnCashflowVO = cashFlowInService.saveCashFlowApply(request, handle, spvrevo, cashflowApplyCode);
		request.setAttribute("type", "success");
		//return spvRecordReturnVO.getToSpvCashFlowApplyPkid();
		response.setMessage("toSpvCashFlowApplyPkid:"+spvReturnCashflowVO.getToSpvCashFlowApplyPkid()+
				";ToSpvCashFlowPkid:"+spvReturnCashflowVO.getToSpvCashFlowPkid()+
				";ToSpvReceiptPkid:"+spvReturnCashflowVO.getToSpvReceiptPkid()
				);
		response.success("success");
		return response;
	}
	
	/**
	 * 审批通过
	 * @param spvrevo
	 * @return
	 */
	@RequestMapping(value = "/approvalby")
	@ResponseBody
	public AjaxResponse<?>  approvalby(SpvRecordedsVO spvrevo){
		//toSpvService.saveSpvCashFlowApplyVO(spvrevo);
		return AjaxResponse.success("保存成功！");
	}
	/**
	 * 审批驳回
	 * @param spvrevo
	 * @return
	 */
	@RequestMapping(value = "/turndown")
	@ResponseBody
	public AjaxResponse<?>  turndown(SpvRecordedsVO spvrevo){
		//toSpvService.saveSpvCashFlowApplyVO(spvrevo);
		return AjaxResponse.success("保存成功！");
	}
	/**
	 * 驳回页面提交
	 * @param spvrevo
	 * @return
	 */
	@RequestMapping(value = "/appSumbitDate")
	@ResponseBody
	public AjaxResponse<?>  appSumbitDate(SpvRecordedsVO spvrevo){
		//toSpvService.saveSpvCashFlowApplyVO(spvrevo);
		return AjaxResponse.success("保存成功！");
	}
	/**
	 * 驳回页面保存
	 * @param spvrevo
	 * @return
	 */
	@RequestMapping(value = "/appSaveDate")
	@ResponseBody
	public void  appSaveDate(SpvRecordedsVO spvrevo,HttpServletRequest request){
		 //SpvRecordReturnVO spvRecordReturnVO  = toSpvService.saveSpvCashFlowApplyVO(spvrevo);
		 
		//request.setAttribute("type", "success");
	}
	
	/**
	 * 新增入账流水页面
	 * @param pkid
	 * @param caseCode
	 * @param request
	 * @return
	 */
	@RequestMapping("spvRecorded")
	public String spvRecorded(Long pkid,String caseCode,HttpServletRequest request){
		SessionUser currentUser = uamSessionService.getSessionUser();
		String currentDeptId = currentUser.getServiceDepId();
		Org curentOrg = uamUserOrgService.getOrgById(currentDeptId);
		Org parentOrg = uamUserOrgService.getOrgById(curentOrg.getParentId());

		toSpvService.findSpvBaseInfoVOAndSetAttrinCaseFlowApple(request,pkid,caseCode);
		
		
		
		toAccesoryListService.getAccesoryList(request, "SpvApplyApprove");
	    App app = uamPermissionService.getAppByAppName(AppTypeEnum.APP_FILESVR.getCode());
	    request.setAttribute("imgweb", app.genAbsoluteUrl());
		
		request.setAttribute("orgId", parentOrg.getId());
		request.setAttribute("urlType", "spv");
		return "spv/spvRecorded";
	}
	/**
	 * 入账审核页面
	 * @param pkid
	 * @param caseCode
	 * @param request
	 * @return
	 */
	@RequestMapping("spvRecordShow")
	public String spvRecordShow(Long pkid,String caseCode,HttpServletRequest request){
	/*	SessionUser currentUser = uamSessionService.getSessionUser();
		String currentDeptId = currentUser.getServiceDepId();
		Org curentOrg = uamUserOrgService.getOrgById(currentDeptId);
		Org parentOrg = uamUserOrgService.getOrgById(curentOrg.getParentId());
		
		//toSpvService.findSpvBaseInfoVOAndSetAttrinCaseFlowApple(request,pkid,caseCode);
		SpvRecordedInfoVO spvRecordedInfoVO = toSpvService.findSpvRecordedInfoVOByCashFlowApplyCode(caseCode);*/
	//	String a = request.getAttribute("taskId"); 
		/*cashFlowOutService.cashFlowInDirectorAduitProcess(request, source, instCode, taskId, handle, businessKey);
		
		request.setAttribute("spvRecordedInfoVO", spvRecordedInfoVO);
		request.setAttribute("orgId", parentOrg.getId());*/
		return "spv/spvRecordShow";
	}
	/**
	 * 入账驳回页面
	 * @param pkid
	 * @param caseCode
	 * @param request
	 * @return
	 */
	@RequestMapping("spvRecordedApp")
	public String spvRecordedApp(Long pkid,String caseCode,HttpServletRequest request){
		SessionUser currentUser = uamSessionService.getSessionUser();
		String currentDeptId = currentUser.getServiceDepId();
		Org curentOrg = uamUserOrgService.getOrgById(currentDeptId);
		Org parentOrg = uamUserOrgService.getOrgById(curentOrg.getParentId());
		
		toSpvService.findSpvBaseInfoVOAndSetAttrinCaseFlowApple(request,pkid,caseCode);
		
		toAccesoryListService.getAccesoryList(request, "SpvApplyApprove");
		App app = uamPermissionService.getAppByAppName(AppTypeEnum.APP_FILESVR.getCode());
		request.setAttribute("imgweb", app.genAbsoluteUrl());
		
		request.setAttribute("orgId", parentOrg.getId());
		request.setAttribute("urlType", "spv");
		return "spv/spvRecordedApp";
	}

	/**
	 * 起草入账页面提交
	 * @param spvrevo
	 * @return
	 */
	@RequestMapping(value = "/sumbitDate")
	@ResponseBody
	public AjaxResponse<?>  sumbitDate(SpvRecordedsVO spvrevo,HttpServletRequest request){
		
		AjaxResponse<?> response = new AjaxResponse<>();
		SpvChargeInfoVO spvChargeInfoVO = new SpvChargeInfoVO();

		String taskId = "";
		String handle = "";
		String instCode = "";
		
		if(null != spvrevo){
			if(null!= spvrevo.getTaskId())
				taskId = spvrevo.getTaskId();
			if(null!= spvrevo.getHandle())
				handle = spvrevo.getHandle();
			if(null!= spvrevo.getInstCode())
				instCode = spvrevo.getInstCode();
		}
		
		String cashflowApplyCode = "";
		try{
			if(StringUtils.equals(spvrevo.getHandle(), "addCashFlow")){
				cashFlowInService.cashFlowInPageDeal(request, handle, spvrevo, cashflowApplyCode);
			}
			if(StringUtils.equals(handle, "apply")){
				cashFlowInService.cashFlowInApplyDeal(request, instCode, taskId, handle, spvrevo, cashflowApplyCode);
			}
			response.setSuccess(true);
		} catch (Exception e) {
			response.setSuccess(false);
			response.setMessage(""+e.getStackTrace());
			e.printStackTrace();
		}
		
		return AjaxResponse.success("保存成功！");
	}

	   /**
     * @Title: cashFlowOutApprDeal 
     * @Description: 出款申请操作
     * @author: gongjd 
     * @param request
     * @param source
     * @param instCode
     * @param taskId
     * @param handle
     * @param spvChargeInfoVO
     * @return response
     * @throws
     */
    @RequestMapping("deal")
	public AjaxResponse<?> cashFlowOutApprDeal(HttpServletRequest request,String source,String instCode,
			String taskId,String handle,SpvChargeInfoVO spvChargeInfoVO,Boolean chargeInAppr) {
    	AjaxResponse<?> response = new AjaxResponse<>();
    	SpvRecordedsVO spvRecordedsVO = new SpvRecordedsVO();
    	try {
			//String cashflowApplyCode = spvChargeInfoVO.getToSpvCashFlowApply().getCashflowApplyCode();
			String cashflowApplyCode = "";
			if(!StringUtils.isBlank(handle)){ 
				
				//if(StringUtils.isBlank(cashflowApplyCode)) throw new BusinessException("页面没有传入申请号！");
				
				switch (handle) {
				case "apply":
					//cashFlowInService.cashFlowInApplyDeal(request, instCode, taskId, handle, spvChargeInfoVO, cashflowApplyCode);
					break;
			    case "directorAduit":
			    	cashFlowInService.cashFlowInDirectorAduitDeal(request, instCode, taskId, handle, spvChargeInfoVO, cashflowApplyCode,chargeInAppr);
					break;
			    case "financeAduit":
			    	cashFlowInService.cashFlowInFinanceAduitDeal(request, instCode, taskId, handle, spvChargeInfoVO, cashflowApplyCode,chargeInAppr);
			    	break;
				}	
			}else{
				//cashFlowOutService.cashFlowOutPageDeal(request, instCode, taskId, handle, spvChargeInfoVO, cashflowApplyCode);
				//cashFlowInService.cashFlowInPageDeal(request, instCode, taskId, handle, spvRecordedsVO, cashflowApplyCode);
			}

			response.setSuccess(true);
		} catch (Exception e) {
			response.setSuccess(false);
			response.setMessage(""+e.getStackTrace());
			e.printStackTrace();
		}
    	
    	return response;
	}
    
    /** 
     * @Title: cashFlowOutApprSave 
     * @Description: 出款保存操作
     * @author: gongjd 
     * @param spvChargeInfoVO
     * @return response
     * @throws
     */
    @RequestMapping("cashFlowOutAppr/save")
	public AjaxResponse<?> cashFlowOutApprSave(SpvChargeInfoVO spvChargeInfoVO) {
    	AjaxResponse<?> response = new AjaxResponse<>();
    	try {
    		toSpvService.saveSpvChargeInfoVO(spvChargeInfoVO); 
			response.setSuccess(true);
		} catch (Exception e) {
			response.setSuccess(false);
			response.setMessage(e.getMessage());
		}
    	
    	return response;
	}
}


