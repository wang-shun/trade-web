package com.centaline.trans.mortgage.enums;

import org.apache.commons.lang3.StringUtils;

import com.centaline.trans.mgr.enums.SupCatEnum;

public enum ReportType {

	PREREPORT("1","预估单"),
	INQUIRYREPORT("2","询价单"),
	REPORT("3","评估报告");
	
	private String code;
	private String value;
	
	ReportType(String code,String value){
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
		ReportType[] enums = ReportType.values();
		for(ReportType e : enums){
			if(e.getCode().equals(code)){
				return e.getValue();
			}
		}
		return "";
	}
}
