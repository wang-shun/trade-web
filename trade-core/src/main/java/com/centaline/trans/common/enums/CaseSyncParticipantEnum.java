package com.centaline.trans.common.enums;
/**
 * 案件同步时，参与者类型信息枚举
 * @author yinchao
 *
 */
public enum CaseSyncParticipantEnum {
	
	AGENT("agent", "经纪人"), WARRANT("warrant", "过户权证"),
	LOAN("loan", "贷款权证"), FINANCIAL("financial", "金融权证"),
	WARZONE ("warzone", "战区管理人员");

	private String code;

	private String name;

	private CaseSyncParticipantEnum(String code, String name) {
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
		for (CaseSyncParticipantEnum sde : CaseSyncParticipantEnum.values()) {
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
