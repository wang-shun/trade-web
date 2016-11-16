package com.centaline.trans.common.enums;

public enum SpvStatusEnum {
	
	DRAFT("0", "草稿"),ADUIT("1","审核"),SIGN("2","签约"),SIGNCOMPLETE("3","签约完成"),COMPLETE("4","合约完成");
	
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
