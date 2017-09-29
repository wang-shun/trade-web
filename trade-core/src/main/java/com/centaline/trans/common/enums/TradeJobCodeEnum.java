package com.centaline.trans.common.enums;

/**
 * 交易岗位编码枚举
 * @author yinchao
 * @date 2017/9/28
 */
public enum TradeJobCodeEnum {
	ASSISTANT("QZNQ","内勤助理"),LOAN("DKQZ","贷款权证"),
	WARRANT("GHQZ","过户权证"),MANAGER("QZJL","权证经理"),
	FINANCIAL("JRQZ","金融权证");
	private String code;

	private String name;

	private TradeJobCodeEnum(String code, String name) {
		this.code = code;
		this.name = name;
	}

	public static String getName(String code) {
		for (CaseParticipantEnum sde : CaseParticipantEnum.values()) {
			if (code.equalsIgnoreCase(sde.getCode())) {
				return sde.getName();
			}
		}
		return null;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}
}
