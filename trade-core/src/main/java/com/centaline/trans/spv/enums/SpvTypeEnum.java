package com.centaline.trans.spv.enums;

import org.apache.commons.lang3.StringUtils;

public enum SpvTypeEnum {

	ANXINBAO("30004003","安心宝"),
	DAIFU("30004004","代收代付");

	private String code;
	private String value;
	
	SpvTypeEnum(String code,String value){
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
		SpvTypeEnum[] enums = SpvTypeEnum.values();
		for(SpvTypeEnum e : enums){
			if(e.getCode().equals(code)){
				return e.getValue();
			}
		}
		return "";
	}
}
