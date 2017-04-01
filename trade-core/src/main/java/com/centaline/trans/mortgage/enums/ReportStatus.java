package com.centaline.trans.mortgage.enums;

public enum ReportStatus {
	NOT_COMPLETE("0","未完成"),
	COMPLETED("1","已完成"),
	SENT_OUT("2","已送出"),
	AUDITING("3","待审核"),
	AUDITED("4","已审核"),
	SIGNED("5","已签收");

	private String code;
	private String value;
	
	ReportStatus(String code,String value){
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
