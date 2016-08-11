package com.centaline.trans.common.enums;

public enum TmpBankStatusEnum {
DEFAULT("","默认"),REJECT("0", "拒绝"),AGREE("1","同意"),INAPPROVAL("2","审批中");
	
	private String name;
	private String code;

	private TmpBankStatusEnum(String code, String name) {
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
