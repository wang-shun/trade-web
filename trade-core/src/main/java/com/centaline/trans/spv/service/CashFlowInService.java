package com.centaline.trans.spv.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.aist.common.web.validate.AjaxResponse;
import com.centaline.trans.common.vo.FileUploadVO;
import com.centaline.trans.spv.vo.SpvChargeInfoVO;
import com.centaline.trans.spv.vo.SpvRecordedsVO;
import com.centaline.trans.spv.vo.SpvReturnCashflowVO;

public interface CashFlowInService {
	/**
	 * @Title: cashFlowInPage 
	 * @Description: 入款申请新增、修改页面
	 * @author: hejf  
	 * @throws
	 */
	void cashFlowInPage(HttpServletRequest request,String source,String instCode,String taskId,String handle,String businessKey) ;
	
	/**
	 * @Title: cashFlowInPageDeal 
	 * @Description: 入款申请新增、修改操作
	 * @author: hejf 
	 * @throws
	 */
    void cashFlowInPageDeal(HttpServletRequest request,String handle,SpvRecordedsVO spvRecordedsVO,String businessKey) throws Exception ;
	
	/** 
	 * @Title: cashFlowInApplyProcess 
	 * @Description: 入款申请页面
	 * @author: hejf  
	 * @throws
	 */
    void cashFlowInApplyProcess(HttpServletRequest request,String source,String instCode,String taskId,String handle,String businessKey) ;
	
	/**
	 * @Title: cashFlowInApplyDeal 
	 * @Description: 入款申请操作
	 * @author: hejf 
	 * @throws
	 */
     void cashFlowInApplyDeal(HttpServletRequest request, String instCode, String taskId,
			String handle, SpvRecordedsVO spvRecordedsVO, String businessKey, Boolean chargeInAppr) throws Exception ;
	
	/**
	 * @Title: cashFlowInDirectorAduitProcess 
	 * @Description: 入款总监审批页面
	 * @author: hejf 
	 * @throws
	 */
	void cashFlowInDirectorAduitProcess(HttpServletRequest request,String source,String instCode,String taskId,String handle,String businessKey) ;
	
	/**
	 * @Title: cashFlowInDirectorAduitDeal 
	 * @Description: 入款总监审批操作
	 * @author: hejf 
	 * @throws
	 */
	void cashFlowInDirectorAduitDeal(HttpServletRequest request,String instCode,String taskId,String handle,SpvRecordedsVO spvRecordedsVO,String businessKey, Boolean chargeInAppr) throws Exception ;
	
/*	*//**
	 * @Title: cashFlowInDirectorAduitDeal 
	 * @Description: 入款总监审批操作
	 * @author: hejf 
	 * @throws
	 *//*
	void cashFlowInDirectorAduitDeal(HttpServletRequest request,String instCode,String taskId,String handle,SpvChargeInfoVO spvChargeInfoVO,String businessKey, Boolean chargeInAppr) throws Exception ;
	
*/	/**
	 * @Title: cashFlowInFinanceAduitProcess 
	 * @Description: 入款财务初审页面
	 * @author: hejf  
	 * @throws
	 */
	void cashFlowInFinanceAduitProcess(HttpServletRequest request,String source,String instCode,String taskId,String handle,String businessKey) ;
	
	/**
	 * @Title: cashFlowInFinanceAduitDeal 
	 * @Description: 入款财务初审操作
	 * @author: hejf  
	 * @throws
	 */
	void cashFlowInFinanceAduitDeal(HttpServletRequest request,String instCode,String taskId,String handle,SpvRecordedsVO spvRecordedsVO,String businessKey, Boolean chargeInAppr) throws Exception ;
	
/*	*//**
	 * @Title: cashFlowInFinanceAduitDeal 
	 * @Description: 入款财务初审操作
	 * @author: hejf  
	 * @throws
	 *//*
	void cashFlowInFinanceAduitDeal(HttpServletRequest request,String instCode,String taskId,String handle,SpvChargeInfoVO spvChargeInfoVO,String businessKey, Boolean chargeInAppr) throws Exception ;
	
*/	/**
	 * @Title: cashFlowInFinanceSecondAduitProcess 
	 * @Description: 入款财务复审页面
	 * @author: hejf  
	 * @throws
	 */
	void cashFlowInFinanceSecondAduitProcess(HttpServletRequest request,String source,String instCode,String taskId,String handle,String businessKey) ;
	
	/**
	 * @Title: cashFlowInFinanceSecondAduitDeal 
	 * @Description: 入款财务复审操作
	 * @author: hejf  
	 * @throws
	 */
	void cashFlowInFinanceSecondAduitDeal(HttpServletRequest request,String instCode,String taskId,String handle,SpvChargeInfoVO spvChargeInfoVO,String businessKey, Boolean chargeInAppr) throws Exception ;

	/**
	 * @Title: cashFlowInFinanceSecondAduitProcess 
	 * @Description: 财务入款页面
	 * @author: hejf  
	 * @throws
	 */
	void cashFlowInDealProcess(HttpServletRequest request,String source,String instCode,String taskId,String handle,String businessKey) ;
	
	/**
	 * @Title: cashFlowInFinanceSecondAduitDeal 
	 * @Description: 财务入款操作
	 * @author: hejf 
	 * @throws
	 */
	void cashFlowInDeal(HttpServletRequest request,String instCode,String taskId,String handle,SpvChargeInfoVO spvChargeInfoVO, Boolean chargeInAppr) throws Exception ;
	
	void cashFlowInPageDealApply(HttpServletRequest request, String instCode, String taskId, String handle, SpvRecordedsVO spvRecordedsVO, String businessKey) throws Exception;
	
	SpvReturnCashflowVO saveCashFlowApply(HttpServletRequest request, String handle, SpvRecordedsVO spvRecordedsVO, String businessKey) throws Exception;
	/**
	 * @Title: cashFlowOutApprDealAppDelete 
	 * @Description: 删除入账流水信息
	 * @author: hejf 
	 * @throws
	 */
	void cashFlowOutApprDealAppDelete(HttpServletRequest request, String instCode, String taskId,
			String handle, SpvRecordedsVO spvRecordedsVO, String businessKey, Boolean chargeInAppr) throws Exception ;
	
	void saveAttachments(FileUploadVO fileUploadVO,String cashFlowCode);
	
	void delAttachment(List<Long> pkIdArr);
	
	 void cashFlowOutApprDeleteCashFlowAll(HttpServletRequest request, String instCode, String pkid,
				String handle) throws Exception;
}
