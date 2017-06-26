package com.centaline.trans.common.enums;
/**
 * 角色类型
 * @author jjm
 *
 */
public enum RoleTypeEnum {
	/**执行人**/
	EXECUTOR("EXECUTOR", "执行人"),
	/**合作人**/
	PARTNER("PARTNER","合作人");
	
	private String name;
	private String code;

	private RoleTypeEnum(String code, String name) {
		this.setName(name);
		this.setCode(code);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
}
