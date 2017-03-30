package com.centaline.trans.mortgage.enums;

public enum Whether {

	IS("1","是"),
	NO("0","否");
	
	private String code;
	private String value;
	
	Whether(String code,String value){
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
