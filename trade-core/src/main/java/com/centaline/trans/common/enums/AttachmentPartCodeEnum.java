package com.centaline.trans.common.enums;

public enum AttachmentPartCodeEnum {
	
	RISKCONTROL("RiskControl", "公证书"),
	RISKCONTROL_CARD("RiskControl_Card", "押卡附件"),
	RISKCONTROL_MORTGAGE("RiskControl_Mortgage", "抵押附件");
	
	private String code;

	private String name;

	private AttachmentPartCodeEnum(String code, String name) {
		this.code = code;
		this.name = name;
	}

	/**
	 * 根据code获取Name
	 * 
	 * @param code
	 * @return
	 */
	public static String getName(String code) {
		for (AppTypeEnum sde : AppTypeEnum.values()) {
			if (code.equalsIgnoreCase(sde.getCode())) {
				return sde.getName();
			}
		}
		return null;
	}

	/**
	 * 根据code获取Name
	 * 
	 * @param code
	 * @return
	 */
	/*
	 * public static String getName(String code) { for (CustdelTradeTypeEnum sde
	 * : CustdelTradeTypeEnum.values()) { if
	 * (code.equalsIgnoreCase(sde.getCode())){ return sde.getName(); } } return
	 * null; }
	 */

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
