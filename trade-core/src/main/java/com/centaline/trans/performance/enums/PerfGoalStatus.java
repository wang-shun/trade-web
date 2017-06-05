package com.centaline.trans.performance.enums;

import org.apache.commons.lang3.StringUtils;

public enum PerfGoalStatus {

	NOTSET(null,"未设定"),
	UNSUBMITTED("0","未提交"),
	SUBMITTED("1","已提交");

	private String code;
	private String value;
	
	PerfGoalStatus(String code,String value){
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
		PerfGoalStatus[] enums = PerfGoalStatus.values();
		for(PerfGoalStatus e : enums){
			if(e.getCode().equals(code)){
				return e.getValue();
			}
		}
		return "";
	}
}
