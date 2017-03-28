package com.centaline.trans.mortgage.service;

import com.aist.common.web.validate.AjaxResponse;
import com.centaline.trans.mortgage.entity.ToMortgage;


public interface LoanerProcessService {    
    
    /**
     * 启动交易顾问派单流程
     * @param caseCode
     * @return
     */
    AjaxResponse<String> startLoanerOrderWorkFlow(String caseCode,String loanerUserId, String loanerOrgId, String bankOrgCode,int bankLevel);

    
    /**
     * 信贷员收单确认
     * @param caseCode
     * @return
     */
	AjaxResponse<String> isLoanerAcceptCase(boolean isAcceptCase, String taskId,String processInstanceId,String caseCode);

    /**
     * 信贷员收单，银行审批操作流程
     * @param caseCode
     * @return
     */
	AjaxResponse<String> isBankAcceptCase(boolean isAcceptCase, String taskId,
			String processInstanceId, String caseCode);


    /**
     * 交易顾问派单流程结束
     * @param caseCode
     * @return
     */
	AjaxResponse<String> loanerProcessDelete(String caseCode, String taskitem,String processInstanceId);

    /**
     * 交易顾问派单 信贷员信息提交
     * @param caseCode
     * @return
     */
	AjaxResponse<String> loanerProcessSubmit(ToMortgage toMortgage,String caseCode, String taskitem,String processInstanceId,int bankLevel);



}
