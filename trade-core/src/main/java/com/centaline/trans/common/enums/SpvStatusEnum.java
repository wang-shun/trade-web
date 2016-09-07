package com.centaline.trans.common.enums;

public enum SpvStatusEnum {
DEFAULT("0", "默认"),INPROGRESS("1","进行中"),COMPLETE("2","结束");
	
	private String name;
	private String code;

	private SpvStatusEnum(String code, String name) {
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
