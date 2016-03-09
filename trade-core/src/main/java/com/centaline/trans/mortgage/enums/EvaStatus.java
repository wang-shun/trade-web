package com.centaline.trans.mortgage.enums;

public enum EvaStatus {
	
	SPECIAL("10","特殊房产"),
	SUCCESS("20","估价失败"),
	FAIL("60","估价成功");
	
	private String code;
	private String value;
	
	EvaStatus(String code,String value){
		this.code = code;
		this.value = value;
	}
	public String getCode(){
		return this.code;
	}
	public String getValue(){
		return this.value;
	}
}
