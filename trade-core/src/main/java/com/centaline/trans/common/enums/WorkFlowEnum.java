package com.centaline.trans.common.enums;

public enum WorkFlowEnum {
	WCLAIM("claim", "claim"), 
	WCOMPLETE("complete", "complete"), 
	WSPENDING("pending", "pending"), 
	WDELEGATE("delegate", "delegate"), 
	WRESOLVE("resolve", "resolve"), 
	WBUSSKEY("operation_process", "businessKey"), 
	SRV_BUSSKEY("service_change", "businessKey"), 
	EVA_WBUSSKEY("OfflineEva", "businessKey"),
	ELOAN_BUSSKEY("EloanProcess", "businessKey"), 
	SPV_DEFKEY("SpvProcess", "processDefinitionKey"), 
	SPV_CASHFLOW_OUT_DEFKEY("SPVCashflowOutProcess","processDefinitionKey"),
	SPV_CASHFLOW_IN_DEFKEY("SpvCashflowInProcess","processDefinitionKey"),
	SPV_CLOSE_DEFKEY("SpvCloseApplyProcess","processDefinitionKey"),
	TMP_BANK_DEFKEY("TempBankAudit_Process","processDefinitionKey"),
	SPV_OUT("spv_out","businessKey"),
	SERVICE_RESTART("serviceRestart","serviceRestartBusinesskey"),
	COMLOAN_PROCESS("ComLoan_Process:4:645463","ComLoan_Process"),	
	LOANLOST_PROCESS("LoanLost_Process:10:950004","LoanLost_Process"),
	//LOANLOST_PROCESS("LoanLost_Process:1:641492","LoanLost_Process"),
	PSFLOAN_PROCESS("PSFLoan_Process:1:645460","PSFLoan_Process"),
	OPERATION_PROCESS("operation_process:40:645454","processDefinitionId"),
	REQ_STUFF("reqStuffProcess","reqStuffProcess");
	private String name;
	private String code;

	private WorkFlowEnum(String code, String name) {
		this.name = name;
		this.code = code;
	}

	/**
	 * 根据code获取Name
	 * 
	 * @param code
	 * @return
	 */
	public static String getName(String code) {
		for (WorkFlowEnum cte : WorkFlowEnum.values()) {
			if (code.equalsIgnoreCase(cte.getCode()))
				return cte.getCode();
		}
		return null;
	}

	/**
	 * Getter method for property <tt>name</tt>.
	 * 
	 * @return property value of name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Setter method for property <tt>name</tt>.
	 * 
	 * @param name
	 *            value to be assigned to property name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Getter method for property <tt>code</tt>.
	 * 
	 * @return property value of code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * Setter method for property <tt>code</tt>.
	 * 
	 * @param code
	 *            value to be assigned to property code
	 */
	public void setCode(String code) {
		this.code = code;
	}
}
