package com.centaline.trans.mortgage.enums;

public enum ConfirmCode {

	PRICING("1","正在询价中"),
	PRICED("2","已询价完成"),
	RECALL("3","询价结果被撤回"),
	PRICING_FEEDBACK("4","撤回后询价反馈"),
	PRICING_LOCKED("5","询价已锁定，可申请报告");
	
	private String code;
	private String value;
	
	ConfirmCode(String code,String value){
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
