package com.centaline.trans.common.enums;
/**
 * 案件同步时，参与者类型信息枚举
 * @author yinchao
 *
 */
public enum CaseParticipantEnum {
	
	AGENT("agent", "经纪人"),SECRETARY("secretary","秘书"), WARRANT("warrant", "过户权证"),
	LOAN("loan", "贷款权证"), FINANCIAL("financial", "金融权证"),ASSISTANT("assistant","权证秘书");

	private String code;

	private String name;

	private CaseParticipantEnum(String code, String name) {
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
		for (CaseParticipantEnum sde : CaseParticipantEnum.values()) {
			if (code.equalsIgnoreCase(sde.getCode())) {
				return sde.getName();
			}
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

}
