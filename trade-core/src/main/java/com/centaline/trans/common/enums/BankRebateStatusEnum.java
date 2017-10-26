package com.centaline.trans.common.enums;

/**
 * 银行返利状态
 * @author yinchao
 * @date 2017/10/26
 */
public enum BankRebateStatusEnum {

	NOT_SUBMIT("未提交","0"),
	SUBMIT("已提交","1"),
	BACK("财务驳回","-1"),
	FINISH("完成","9");

	private String name;
	private String code;

	BankRebateStatusEnum(String name, String code) {
		this.name = name;
		this.code = code;
	}

	public String getName() {
		return name;
	}
	public String getCode() {
		return code;
	}
}
