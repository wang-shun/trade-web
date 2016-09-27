package com.centaline.trans.spv.service;

import javax.servlet.http.HttpServletRequest;

import com.aist.common.web.validate.AjaxResponse;
import com.centaline.trans.spv.vo.SpvChargeInfoVO;

public interface CashFlowOutService {
	/**
	 * @Title: cashFlowOutPage 
	 * @Description: 出款申请新增、修改页面
	 * @author: gongjd  
	 * @throws
	 */
	void cashFlowOutPage(HttpServletRequest request,String source,String instCode,String taskId,String handle,String businessKey) ;
	
	/**
	 * @Title: cashFlowOutPageDeal 
	 * @Description: 出款申请新增、修改操作
	 * @author: gongjd 
	 * @throws
	 */
    void cashFlowOutPageDeal(HttpServletRequest request,String instCode,String taskId,String handle,SpvChargeInfoVO spvChargeInfoVO,String businessKey) throws Exception ;
	
	/** 
	 * @Title: cashFlowOutApplyProcess 
	 * @Description: 出款申请页面
	 * @author: gongjd  
	 * @throws
	 */
    void cashFlowOutApplyProcess(HttpServletRequest request,String source,String instCode,String taskId,String handle,String businessKey) ;
	
	/**
	 * @Title: cashFlowOutApplyDeal 
	 * @Description: 出款申请操作
	 * @author: gongjd 
	 * @throws
	 */
	void cashFlowOutApplyDeal(HttpServletRequest request,String instCode,String taskId,String handle,SpvChargeInfoVO spvChargeInfoVO,String businessKey) throws Exception ;
	
	/**
	 * @Title: cashFlowOutDirectorAduitProcess 
	 * @Description: 出款总监审批页面
	 * @author: gongjd 
	 * @throws
	 */
	void cashFlowOutDirectorAduitProcess(HttpServletRequest request,String source,String instCode,String taskId,String handle,String businessKey) ;
	
	/**
	 * @Title: cashFlowOutDirectorAduitDeal 
	 * @Description: 出款总监审批操作
	 * @author: gongjd 
	 * @throws
	 */
	void cashFlowOutDirectorAduitDeal(HttpServletRequest request,String instCode,String taskId,String handle,SpvChargeInfoVO spvChargeInfoVO,String businessKey, Boolean chargeOutAppr) throws Exception ;
	
	/**
	 * @Title: cashFlowOutFinanceAduitProcess 
	 * @Description: 出款财务初审页面
	 * @author: gongjd  
	 * @throws
	 */
	void cashFlowOutFinanceAduitProcess(HttpServletRequest request,String source,String instCode,String taskId,String handle,String businessKey) ;
	
	/**
	 * @Title: cashFlowOutFinanceAduitDeal 
	 * @Description: 出款财务初审操作
	 * @author: gongjd  
	 * @throws
	 */
	void cashFlowOutFinanceAduitDeal(HttpServletRequest request,String instCode,String taskId,String handle,SpvChargeInfoVO spvChargeInfoVO,String businessKey, Boolean chargeOutAppr) throws Exception ;
	
	/**
	 * @Title: cashFlowOutFinanceSecondAduitProcess 
	 * @Description: 出款财务复审页面
	 * @author: gongjd  
	 * @throws
	 */
	void cashFlowOutFinanceSecondAduitProcess(HttpServletRequest request,String source,String instCode,String taskId,String handle,String businessKey) ;
	
	/**
	 * @Title: cashFlowOutFinanceSecondAduitDeal 
	 * @Description: 出款财务复审操作
	 * @author: gongjd  
	 * @throws
	 */
	void cashFlowOutFinanceSecondAduitDeal(HttpServletRequest request,String instCode,String taskId,String handle,SpvChargeInfoVO spvChargeInfoVO,String businessKey, Boolean chargeOutAppr) throws Exception ;

	/**
	 * @Title: cashFlowOutFinanceSecondAduitProcess 
	 * @Description: 财务出款页面
	 * @author: gongjd  
	 * @throws
	 */
	void cashFlowOutDealProcess(HttpServletRequest request,String source,String instCode,String taskId,String handle,String businessKey) ;
	
	/**
	 * @Title: cashFlowOutFinanceSecondAduitDeal 
	 * @Description: 财务出款操作
	 * @author: gongjd 
	 * @throws
	 */
	void cashFlowOutDeal(HttpServletRequest request,String instCode,String taskId,String handle,SpvChargeInfoVO spvChargeInfoVO,String businessKey, Boolean chargeOutAppr) throws Exception ;
	
}
