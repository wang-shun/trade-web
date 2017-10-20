package com.centaline.trans.common.enums;

public enum RansomStopStatusEnum {
	
	UNSTOP("710170001", "未中止"),
	STOPING("710170002", "中止流程已开启"),
	STOPED("710170003", "已中止");
	
	private RansomStopStatusEnum(String code, String name) {
		this.code = code;
		this.name = name;
	}
	
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
	private String code;
	private String name;

}
