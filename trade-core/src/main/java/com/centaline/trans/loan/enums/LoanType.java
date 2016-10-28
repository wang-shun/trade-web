package com.centaline.trans.loan.enums;

import org.apache.commons.lang3.StringUtils;

public enum LoanType {
	ZY_XD("30004008", "消费贷");
	private String code;
	private String value;

	LoanType(String code, String value) {
		this.code = code;
		this.value = value;
	}

	public String getCode() {
		return this.code;
	}

	public String getValue() {
		return this.value;
	}

	public static String getValueByCode(String code) {
		if (StringUtils.isBlank(code)) {
			return "";
		}
		LoanType[] enums = LoanType.values();
		for (LoanType e : enums) {
			if (e.getCode().equals(code)) {
				return e.getValue();
			}
		}
		return "";
	}
}
