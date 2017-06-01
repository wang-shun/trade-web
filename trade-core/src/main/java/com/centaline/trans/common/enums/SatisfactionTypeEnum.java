package com.centaline.trans.common.enums;

public enum SatisfactionTypeEnum {
	
	NEW("1","新增"),ORIGIN("2","原始");

	private String name;
	private String code;

	private SatisfactionTypeEnum(String code, String name) {
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
