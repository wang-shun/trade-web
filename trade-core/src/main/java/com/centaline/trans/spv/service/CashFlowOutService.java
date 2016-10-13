package com.centaline.trans.spv.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.centaline.trans.common.vo.FileUploadVO;
import com.centaline.trans.spv.entity.ToSpvCashFlowApplyAttach;
import com.centaline.trans.spv.vo.SpvChargeInfoVO;

public interface CashFlowOutService {
	/** 
	 * 出款申请新增、修改页面
	 */
	void cashFlowOutPage(HttpServletRequest request,String source,String instCode,String taskId,String handle,String businessKey) ;
	
	/**
	 * 出款申请提交申请操作 
	 */
    void cashFlowOutPageDeal(HttpServletRequest request,String instCode,String taskId,String taskitem,String handle,SpvChargeInfoVO spvChargeInfoVO,String businessKey) throws Exception ;
	
	/**  
	 *  出款申请页面
	 */
    void cashFlowOutApplyProcess(HttpServletRequest request,String source,String instCode,String taskId,String handle,String businessKey) ;
	
	/**
	 *  出款申请操作
	 */
	void cashFlowOutApplyDeal(HttpServletRequest request,String instCode,String taskId,String taskitem,String handle,SpvChargeInfoVO spvChargeInfoVO,String businessKey,Boolean chargeOutAppr) throws Exception ;
	
	/** 
	 *  出款总监审批页面
	 */
	void cashFlowOutDirectorAduitProcess(HttpServletRequest request,String source,String instCode,String taskId,String handle,String businessKey) ;
	
	/** 
	 *  出款总监审批操作
	 */
	void cashFlowOutDirectorAduitDeal(HttpServletRequest request,String instCode,String taskId,String taskitem,String handle,SpvChargeInfoVO spvChargeInfoVO,String businessKey, Boolean chargeOutAppr) throws Exception ;
	
	/** 
	 *  出款财务初审页面
	 */
	void cashFlowOutFinanceAduitProcess(HttpServletRequest request,String source,String instCode,String taskId, String handle,String businessKey) ;
	
	/**
	 *  出款财务初审操作
	 */
	void cashFlowOutFinanceAduitDeal(HttpServletRequest request,String instCode,String taskId,String taskitem,String handle,SpvChargeInfoVO spvChargeInfoVO,String businessKey, Boolean chargeOutAppr) throws Exception ;
	
	/** 
	 *  出款财务复审页面
	 */
	void cashFlowOutFinanceSecondAduitProcess(HttpServletRequest request,String source,String instCode,String taskId,String handle,String businessKey) ;
	
	/**
	 *  出款财务复审操作
	 */
	void cashFlowOutFinanceSecondAduitDeal(HttpServletRequest request,String instCode,String taskId,String taskitem,String handle,SpvChargeInfoVO spvChargeInfoVO,String businessKey, Boolean chargeOutAppr) throws Exception ;

	/** 
	 *  财务出款页面
	 */
	void cashFlowOutDealProcess(HttpServletRequest request,String source,String instCode,String taskId,String handle,String businessKey) ;
	
	/**
	 *  财务出款操作
	 */
	void cashFlowOutDeal(HttpServletRequest request,String instCode,String taskId,String taskitem,String handle,SpvChargeInfoVO spvChargeInfoVO, Boolean chargeOutAppr) throws Exception ;

	/**
	 *  保存操作
	 * @throws Exception 
	 */
	void saveSpvChargeInfo(SpvChargeInfoVO spvChargeInfoVO) throws Exception;

	/**
	 * 通过申请ID查询附件信息
	 */
	List<ToSpvCashFlowApplyAttach> quereyAttachmentsByCashFlowApplyCode(String cashFlowApplyCode);

	/**
	 * 修改(添加、删除)出账申请附件
	 * @return 
	 */
	String saveAttachments(FileUploadVO fileUploadVO,String cashFlowApplyId);

	/**
	 * 删除出账申请附件
	 */
	void delAttachment(List<Long> pkIdArr);
	
}
