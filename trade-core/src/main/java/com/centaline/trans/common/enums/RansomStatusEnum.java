package com.centaline.trans.common.enums;

public enum RansomStatusEnum {
	
	ACCEPT("1", "受理"),
	ACTIVE("2", "在途"),
	END("3", "结束"),
	STOPED("4", "中止");
	
	private RansomStatusEnum(String code, String name) {
		this.code = code;
		this.name = name;
	}
	
	private String code;
	private String name;
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}
