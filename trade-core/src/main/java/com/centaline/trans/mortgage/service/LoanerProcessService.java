package com.centaline.trans.mortgage.service;

import com.aist.common.web.validate.AjaxResponse;


public interface LoanerProcessService {    
    
    /**
     * 启动信贷员审批流程
     * @param caseCode
     * @return
     */
    AjaxResponse<String> startLoanerOrderWorkFlow(String caseCode,String loanerUserId, String loanerOrgId, String bankOrgCode,int bankLevel);

    
    /**
     * 信贷员收单审批操作
     * @param caseCode
     * @return
     */
	AjaxResponse<String> isLoanerAcceptCase(boolean isAcceptCase, String taskId,String processInstanceId,String caseCode);

    /**
     * 银行审批操作流程
     * @param caseCode
     * @return
     */
	AjaxResponse<String> isBankAcceptCase(boolean isAcceptCase, String taskId,
			String processInstanceId, String caseCode);


    /**
     * 信贷员派单流程结束
     * @param caseCode
     * @return
     */
	AjaxResponse<String> loanerProcessDelete(String caseCode, String taskitem,String processInstanceId);




}
