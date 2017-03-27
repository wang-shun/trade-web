package com.centaline.trans.common.enums;

public enum SpvStatusEnum {
	
	DRAFT("0", "草稿"),AUDIT("1","专员审核"),APPROVE("2","总监审批"),SIGN("3","签约"),SIGNCOMPLETE("4","签约完成"),INTERMINATE("5","中止审批中"),INCOMPLETE("6","结束审批中"),COMPLETE("7","合约完成"),TERMINATE("8","合约中止");
	
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
