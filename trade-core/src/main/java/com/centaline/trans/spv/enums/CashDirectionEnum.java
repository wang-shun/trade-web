package com.centaline.trans.spv.enums;

import org.apache.commons.lang3.StringUtils;

public enum CashDirectionEnum {

	IN("0","进账"),
	OUT("1","出账");

	private String code;
	private String value;
	
	CashDirectionEnum(String code,String value){
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
		CashDirectionEnum[] enums = CashDirectionEnum.values();
		for(CashDirectionEnum e : enums){
			if(e.getCode().equals(code)){
				return e.getValue();
			}
		}
		return "";
	}
}
