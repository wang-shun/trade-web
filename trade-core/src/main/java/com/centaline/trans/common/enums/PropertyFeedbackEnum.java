package com.centaline.trans.common.enums;

public enum PropertyFeedbackEnum {

	PR_PROCESS("PR_process", "受理产调"), PR_RESPONSE("PR_response", "完成产调"),

	AGENT_PR_FINISH_NOTIFICATION("agent_pr_finish_notification", "完成产调"),

	AGENT_PR_ACCEPT_NOTIFICATION("agent_pr_accept_notification", "受理产调");

	private String name;

	private String code;

	private PropertyFeedbackEnum(String code, String name) {
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
		for (PropertyFeedbackEnum cte : PropertyFeedbackEnum.values()) {
			if (code.equalsIgnoreCase(cte.getCode()))
				return cte.getName();
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
