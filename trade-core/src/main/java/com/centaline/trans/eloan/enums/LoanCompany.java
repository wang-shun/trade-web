package com.centaline.trans.eloan.enums;

import org.apache.commons.lang3.StringUtils;

public enum LoanCompany {
	AJD("W0001", "安家贷", "AJD"), 
	JYD("W0002", "居易贷", "JY"), 
	SYD("W0003", "搜易贷","SY"), 
	JSY("W0004", "及时雨", "JS"),
	
	GSYH("102290003752", "中国工商银行股份有限公司上海市友谊路支行","GSYH"), 
	NSYH("322290012088", "上海农商银行白丽分理处", "NSYH");
	private String code;
	private String value;
	private String caseValue;

	LoanCompany(String code, String value, String caseValue) {
		this.code = code;
		this.value = value;
		this.caseValue = caseValue;
	}

	public String getCode() {
		return this.code;
	}

	public String getValue() {
		return this.value;
	}

	public String getCaseValue() {
		return this.caseValue;
	}

	public static String getCaseValueByCode(String code) {
		if (StringUtils.isBlank(code)) {
			//返回其他
			return "qt";
		}
		LoanCompany[] enums = LoanCompany.values();
		for (LoanCompany e : enums) {
			if (e.getCode().equals(code)) {
				return e.getCaseValue();
			}
		}
		return "";
	}
}
