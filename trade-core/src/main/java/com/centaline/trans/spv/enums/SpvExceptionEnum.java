package com.centaline.trans.spv.enums;

import org.apache.commons.lang3.StringUtils;

public enum SpvExceptionEnum {

	SPV_CODE_EXIST("01","监管协议编号已存在！");

	private String code;
	private String value;
	
	SpvExceptionEnum(String code,String value){
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
		SpvExceptionEnum[] enums = SpvExceptionEnum.values();
		for(SpvExceptionEnum e : enums){
			if(e.getCode().equals(code)){
				return e.getValue();
			}
		}
		return "";
	}
}
