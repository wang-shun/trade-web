package com.centaline.trans.mgr.enums;

import org.apache.commons.lang3.StringUtils;

public enum EvaCompanyEnum {

	EGU("P00021","E估");

	private String code;
	private String value;
	
	EvaCompanyEnum(String code,String value){
		this.code = code;
		this.value = value;
	}
	public String getCode(){
		return this.code;
	}
	public String getValue(){
		return this.value;
	}
	
	public static String getValueByCode(String code){
		if(StringUtils.isBlank(code)){
			return "";
		}
		EvaCompanyEnum[] enums = EvaCompanyEnum.values();
		for(EvaCompanyEnum e : enums){
			if(e.getCode().equals(code)){
				return e.getValue();
			}
		}
		return "";
	}
}
