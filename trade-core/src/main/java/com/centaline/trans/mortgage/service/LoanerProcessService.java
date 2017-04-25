package com.centaline.trans.mortgage.service;

import javax.servlet.http.HttpServletRequest;

import com.aist.common.web.validate.AjaxResponse;
import com.centaline.trans.mortgage.entity.ToMortgage;


public interface LoanerProcessService {    
    
    /**
     * 启动交易顾问派单流程
     * @param caseCode
     * @return
     */
    AjaxResponse<String> startLoanerOrderWorkFlow(String caseCode,String loanerUserId, String loanerOrgId, String bankOrgCode,int bankLevel,String isMainLoanBank);

    
    /**
     * 信贷员收单确认
     * @param caseCode
     * @return
     */
	boolean isLoanerAcceptCase(boolean isAcceptCase, String taskId,String processInstanceId,String caseCode,String mortgageId,String stateInBank);

    /**
     * 信贷员收单，银行审批操作流程
     * @param caseCode
     * @return
     */
	boolean isBankAcceptCase(boolean isAcceptCase, String taskId,String processInstanceId, String caseCode,String mortgageId,String stateInBank);


    /**
     * 交易顾问派单流程结束
     * @param caseCode
     * @return
     */
	void loanerProcessDelete(String caseCode, String taskId,String processInstanceId,String isMainLoanBankProcess);
	
	
    /**
     * 交易顾问派单流程取消
     * @param caseCode
     * @return
     */
	void loanerProcessCancle(String caseCode, String taskId,String processInstanceId,String isMainLoanBankProcess,String loanerPkid);
	

    /**
     * 交易顾问派单 信贷员信息提交
     * @param caseCode
     * @return
     */
	void loanerProcessSubmit(ToMortgage toMortgage,String caseCode, String taskitem,String processInstanceId,int bankLevel);


    /**
     * 商贷第3步 起交易顾问派单之前判断是否已经起了流程
     * @param caseCode
     * @return
     */
	AjaxResponse<String> isLoanerProcessStart(String caseCode,String isMainLoanBank);


    /**
     * 启动交易顾问派单流程
     * @param caseCode
     * @return
     */
	AjaxResponse<String> newStartLoanerOrderWorkFlow(ToMortgage toMortgage);


    /**
     * 交易顾问派单流程列表
     * @return 
     *  
     * @return
     */
	void loanerProcessList(HttpServletRequest request);
	
	
    /**
     * 重新派单流程
     * @return 
     *  
     * @return
     */
	void comLoanerChangeProcess(HttpServletRequest request,String caseCode, String taskitem, String processInstanceId);

}
