package com.centaline.trans.mortgage.service;

import com.aist.common.web.validate.AjaxResponse;


public interface LoanerProcessService {    
    
    /**
     * 启动信贷员审批流程
     * @param caseCode
     * @return
     */
    AjaxResponse<String> startLoanerOrderWorkFlow(String caseCode,String loanerUserId, String loanerOrgId, String bankOrgCode,int bankLevel);




}
