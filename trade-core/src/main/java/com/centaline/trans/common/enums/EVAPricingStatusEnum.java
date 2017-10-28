package com.centaline.trans.common.enums;

public enum EVAPricingStatusEnum {
	
	APPY("0","申请"),
	COMPLETE("1","完成"),
	INVALID("2","无效");
	
	private EVAPricingStatusEnum(String code, String name){
		this.code = code;
		this.name = name;
	}
	
	private String code;
    private String name;
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
    
    

}
