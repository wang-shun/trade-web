package com.centaline.trans.performance.enums;

import org.apache.commons.lang3.StringUtils;

public enum ReceivablePerfStatus {
	/* X为临时状态 **/
	REQUESTED("REQUESTED","已经申请"), GENERATED("GENERATED","已经生成"), CANCELED( "CANCELED","已经取消");

	private String code;
	private String value;

	ReceivablePerfStatus(String code, String value) {
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
