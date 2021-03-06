package com.centaline.trans.common.enums;

public enum SpvCloseApplyStatusEnum {
	
    DRAFT("0", "草稿"),AUDIT("1","审核"),REAUDIT("2","复审"),COMPLETE("3","完成");
	
	private String name;
	private String code;

	private SpvCloseApplyStatusEnum(String code, String name) {
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
