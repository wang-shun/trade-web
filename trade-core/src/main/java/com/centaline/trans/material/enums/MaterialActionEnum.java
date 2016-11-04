package com.centaline.trans.material.enums;

import org.apache.commons.lang3.StringUtils;

public enum MaterialActionEnum {
	
	IN("in","入库"),
	OUT("out","借用"),
	RETURN("return","归还"),
	REFUND("refund","退还");
	
	private String code;
	private String value;
	
	MaterialActionEnum(String code,String value){
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
		MaterialActionEnum[] enums = MaterialActionEnum.values();
		for(MaterialActionEnum e : enums){
			if(e.getCode().equals(code)){
				return e.getValue();
			}
		}
		return "";
	}
}
