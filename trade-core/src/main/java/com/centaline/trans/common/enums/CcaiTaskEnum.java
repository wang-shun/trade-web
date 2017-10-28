package com.centaline.trans.common.enums;
/**
 * CCAI 流程环节枚举
 * 用于流程反馈时使用
 * @author yinchao
 * @date 2017/9/21
 */
public enum CcaiTaskEnum {
	//trade 流程环节
	TRADE_WARRANT_MANAGER("权证审核",CcaiFlowEnum.TRADE),
	TRADE_WARRANT_TRANSFER("权证过户",CcaiFlowEnum.TRADE),
	TRADE_ACCESS_BROKERAGE("允许结佣",CcaiFlowEnum.TRADE),
	//评估退费 流程环节
	EVAL_REFUND_MANAGER("权证经理审批",CcaiFlowEnum.EVAL_REFUND),
	EVAL_REFUND_MAJORDOMO("权证总监审批",CcaiFlowEnum.EVAL_REFUND),
	//银行返利 流程环节
	BANK_REBATE_MANAGER("权证经理审核",CcaiFlowEnum.BANK_REBATE),
	//经理调佣 流程环节
	COMMISSION_CHANGE_MANAGER("权证经理审核",CcaiFlowEnum.MANAGER_COMMISSION_CHANGE),
	//变更自办评估 流程环节
	EVAL_CUSTOMER_MANAGER("权证经理审批",CcaiFlowEnum.CHANGETO_CUSTOMER_EVAL),
	EVAL_CUSTOMER_MAJORDOMO("权证总监审批",CcaiFlowEnum.CHANGETO_CUSTOMER_EVAL),
	//变更自办贷款 流程环节
	MORTGAGE_CUSTOMER_MANAGER("权证经理审批",CcaiFlowEnum.CHANGETO_CUSTOMER_MORTGAGE),
	MORTGAGE_CUSTOMER_MAJORDOMO("权证总监审批",CcaiFlowEnum.CHANGETO_CUSTOMER_MORTGAGE);

	private String name;
	private CcaiFlowEnum parent;

	CcaiTaskEnum(String name, CcaiFlowEnum parent) {
		this.name = name;
		this.parent = parent;
	}

	public String getName() {
		return name;
	}

	public CcaiFlowEnum getParent() {
		return parent;
	}
}
