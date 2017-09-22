package com.centaline.trans.common.enums;

/**
 * CCAI 流程枚举
 * 用于配置流程环节枚举 作为父级使用
 * @author yinchao
 * @date 2017/9/21
 */
public enum CcaiFlowEnum {
	TRADE("成交报告审批",0),
	EVA_REBATE("评估返利",1),
	EVA_REFUND("评估退费",2),
	BANK_REBATE("银行返利",3),
	MANAGER_COMMISSION_CHANGE("调佣流程",4),
	CHANGETO_CUSTOMER_MORTGAGE("变更自办贷款",5),
	CHANGETO_CUSTOMER_EVA("变更自办评估",6);
	private String name;
	private int code;

	CcaiFlowEnum(String name, int code) {
		this.name = name;
		this.code = code;
	}

	public String getName() {
		return name;
	}
	public int getCode() {
		return code;
	}

}
