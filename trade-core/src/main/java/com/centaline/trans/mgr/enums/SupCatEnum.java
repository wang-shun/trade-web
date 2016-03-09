package com.centaline.trans.mgr.enums;

import org.apache.commons.lang3.StringUtils;

public enum SupCatEnum {

	FINANCE_LOAN_SUPPLIER("3", "金融贷款供应商"), MONEY_SUPPLIER("2", "资金监管供应商"), EVA_SUPPLIER(
			"1", "评估供应商"), LOAN_SUPPLIER("0", "按揭贷款供应商"), PRF_SUPPLIER("5",
			"公积金贷款供应商");

	private String code;
	private String value;

	SupCatEnum(String code, String value) {
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
		SupCatEnum[] enums = SupCatEnum.values();
		for (SupCatEnum e : enums) {
			if (e.getCode().equals(code)) {
				return e.getValue();
			}
		}
		return "";
	}
}
