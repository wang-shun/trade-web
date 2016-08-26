package com.centaline.trans.common.enums;

public enum MessageEnum {
	
	START_MORTGAGE_SELECT_MSG("message","StartMortgageSelectMsg","StartMortgageSelectMsg "), 
	MORTGAGE_FINISH_MSG("message","MortgageFinishMsg", "MortgageFinishMsg"),
	SPV_FINISH_MSG("message","SpvFinishMsg", "SpvFinishMsg");

	private String eventType;

	private String name;

	private String code;

	private MessageEnum(String eventType,String code, String name) {
		this.eventType = eventType;
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
		for (MessageEnum cte : MessageEnum.values()) {
			if (code.equalsIgnoreCase(cte.getCode()))
				return cte.getName();
		}
		return null;
	}
	
	public static String getEventType(String code) {
		for (MessageEnum cte : MessageEnum.values()) {
			if (code.equalsIgnoreCase(cte.getCode()))
				return cte.getEventType();
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

	public String getEventType() {
		return eventType;
	}

	public void setEventType(String eventType) {
		this.eventType = eventType;
	}

}
