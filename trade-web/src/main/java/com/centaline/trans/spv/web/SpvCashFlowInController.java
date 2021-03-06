/**
 * AISThink.com Inc.
 * Copyright (c) 2015-2015 All Rights Reserved.
 */
package com.centaline.trans.spv.web;


import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.aist.common.web.validate.AjaxResponse;
import com.aist.uam.auth.remote.UamSessionService;
import com.aist.uam.auth.remote.vo.SessionUser;
import com.aist.uam.userorg.remote.UamUserOrgService;
import com.aist.uam.userorg.remote.vo.Org;
import com.centaline.trans.common.service.MessageService;
import com.centaline.trans.engine.service.ProcessInstanceService;
import com.centaline.trans.engine.service.ToWorkFlowService;
import com.centaline.trans.product.service.ProductCategoryService;
import com.centaline.trans.product.service.ProductService;
import com.centaline.trans.spv.service.CashFlowInService;
import com.centaline.trans.spv.service.ToSpvService;
import com.centaline.trans.spv.vo.SpvRecordedsVO;

@Controller
@RequestMapping(value="/spv/task/cashflowIntApply")
public class SpvCashFlowInController {
	
	@Autowired
	private ToSpvService toSpvService;
	@Autowired
	private UamSessionService uamSessionService;
	@Autowired
	private UamUserOrgService uamUserOrgService;
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
	private CashFlowInService cashFlowInService;
	
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

	toSpvService.findSpvBaseInfoVOAndSetAttrinCaseFlowApply(request,pkid,caseCode);
	request.setAttribute("orgId", parentOrg.getId());
	request.setAttribute("urlType", "spv");
	return "spv/spvRecorded";
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
	try{
		if(StringUtils.equals(spvrevo.getHandle(), "addCashFlow")){
			cashFlowInService.cashFlowInPageDeal(request, "addCashFlow", spvrevo);
		}
		response.setSuccess(true);
	} catch (Exception e) {
		response.setSuccess(false);
		response.setMessage(e.getMessage());
		e.printStackTrace();
	}
	return response;
}

/**
 * 起草入账页面保存
 * @param spvrevo
 * @return
 */
@RequestMapping(value = "/saveData")
@ResponseBody
public AjaxResponse<?>  saveData(SpvRecordedsVO spvrevo,HttpServletRequest request){
	AjaxResponse<?> response = new AjaxResponse<>();
	try{
		cashFlowInService.saveSpvRecordsVO(spvrevo);
		response.setSuccess(true);
	} catch (Exception e) {
		response.setSuccess(false);
		response.setMessage(e.getMessage());
		e.printStackTrace();
	}
	return response;
}

   /**
 * @Title: cashFlowOutApprDeal 
 * @Description: 入账
 * @author: hejf 
 * @param request
 * @param source
 * @param instCode
 * @param taskId
 * @param handle
 * @param spvChargeInfoVO
 * @return response
 * @throws
 */
@RequestMapping(value = "/deal")
@ResponseBody
public AjaxResponse<?> cashFlowOutApprDeal(HttpServletRequest request,String source,String instCode, 
		String taskId,String handle,SpvRecordedsVO spvRecordedsVO,Boolean chargeInAppr) {
	AjaxResponse<?> response = new AjaxResponse<>();
	try {
		String cashflowApplyCode = "";
		if(!StringUtils.isBlank(handle)){ 
			switch (handle) {
				case "apply": cashFlowInService.cashFlowInApplyDeal(request, instCode, taskId, handle, spvRecordedsVO, cashflowApplyCode,chargeInAppr);break;
			    case "directorAduit": cashFlowInService.cashFlowInDirectorAduitDeal(request, instCode, taskId, handle, spvRecordedsVO, cashflowApplyCode,chargeInAppr);break;
			    case "financeAduit": cashFlowInService.cashFlowInFinanceAduitDeal(request, instCode, taskId, handle, spvRecordedsVO, cashflowApplyCode,chargeInAppr);break;
			}	
		}
		response.setSuccess(true);
	} catch (Exception e) {
		response.setSuccess(false);
		response.setMessage(e.getMessage());
		e.printStackTrace();
	}
	
	return response;
}
  /**
  * @throws Exception 
  * @Title: cashFlowOutApprDeal 
  * @Description: 入账申请删除流水操作
  * @author:hejf 
  * @param request
  * @param source
  * @param instCode
  * @param taskId
  * @param handle
  * @param spvChargeInfoVO
  * @return response
  * @throws
  */
 @RequestMapping("dealAppDelete")
	public AjaxResponse<?> cashFlowOutApprDealAppDelete(HttpServletRequest request,String source,String instCode,
			String pkid,String handle,SpvRecordedsVO spvRecordedsVO,Boolean chargeInAppr) throws Exception {
 	AjaxResponse<?> response = new AjaxResponse<>();
 	String businessKey = null;
	cashFlowInService.cashFlowOutApprDealAppDelete( request,  instCode,  pkid,
			 handle,  spvRecordedsVO,  businessKey,  chargeInAppr);
	response.setSuccess(true);
 	return response;
	}
 /**
  * @throws Exception 
  * @Title: deleteCashFlowAll 
  * @Description: 根据cashflowappid删除所有相关入账信息
  * @author: hejf 
  * @param request
  * @param instCode
  * @param pkid
  * @param handle
  * @return response
  * @throws
  */
 @RequestMapping("deleteCashFlowAll")
 public AjaxResponse<?> cashFlowOutApprDeleteCashFlowAll(HttpServletRequest request,String instCode,
		 String pkid,String handle) throws Exception {
	 AjaxResponse<?> response = new AjaxResponse<>();
	 try{
		 cashFlowInService.cashFlowOutApprDeleteCashFlowAll( request,  instCode,  pkid, handle);
		 response.setSuccess(true);
	 }catch(Exception e){
		 response.setMessage(e.getMessage());
		 response.setSuccess(false);
		 throw e;
	 }
	 return response;
 }
 
}



