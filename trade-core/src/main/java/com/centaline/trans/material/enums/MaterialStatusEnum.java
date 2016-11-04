package com.centaline.trans.material.enums;

import org.apache.commons.lang3.StringUtils;

public enum MaterialStatusEnum {
	
	STAY("stay","待入库"),
	INSTOCK("instock","在库"),
	BORROW("borrow","已借用"),
	BACK("back","已退还");
	
	private String code;
	private String value;
	
	MaterialStatusEnum(String code,String value){
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
		MaterialStatusEnum[] enums = MaterialStatusEnum.values();
		for(MaterialStatusEnum e : enums){
			if(e.getCode().equals(code)){
				return e.getValue();
			}
		}
		return "";
	}
}
