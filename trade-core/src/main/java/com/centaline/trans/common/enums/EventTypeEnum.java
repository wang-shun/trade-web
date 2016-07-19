package com.centaline.trans.common.enums;

import com.centaline.trans.utils.ConstantsUtil;

public enum EventTypeEnum {
	
	INTERMEDIATECATCHEVENT(ConstantsUtil.MESSAGE_EVENT,"MortgageFinishMsgEventCatch","MortgageFinishMsgEventCatch "), 
	TRADEBOUNDARYMSG(ConstantsUtil.MESSAGE_EVENT,"TradeBoundaryMsg", "TradeBoundaryMsg");

	private String eventType;

	private String name;

	private String code;

	private EventTypeEnum(String eventType,String code, String name) {
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
		for (EventTypeEnum cte : EventTypeEnum.values()) {
			if (code.equalsIgnoreCase(cte.getCode()))
				return cte.getName();
		}
		return null;
	}
	
	public static String getEventType(String code) {
		for (EventTypeEnum cte : EventTypeEnum.values()) {
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
