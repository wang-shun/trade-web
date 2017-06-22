package com.centaline.trans.performance.enums;

import org.apache.commons.lang3.StringUtils;

public enum ReceivablePerfForm {
	EVA_FEE("EVA_FEE", "评估费"), SERVICE_FEE("SERVICE_FEE", "服务费"), ELOAN("ELOAN", "E+贷款"), LOAN("LOAN", "按揭贷款");

	private String code;
	private String value;

	ReceivablePerfForm(String code, String value) {
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
		PerfGoalStatus[] enums = PerfGoalStatus.values();
		for (PerfGoalStatus e : enums) {
			if (e.getCode().equals(code)) {
				return e.getValue();
			}
		}
		return "";
	}
}
