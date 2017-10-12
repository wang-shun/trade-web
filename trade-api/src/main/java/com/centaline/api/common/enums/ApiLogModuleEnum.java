package com.centaline.api.common.enums;

/**
 * @author yinchao
 * @date 2017/9/22
 */
public enum ApiLogModuleEnum {
	CASE_SYNC("成交报告同步","CASESYNC"),
	CASE_UPDATE("成交报告修改","CASEUPDATE"),
	FLOW_FEEDBACK("审批反馈","FLOW_FEEDBACK"),
	EVAL_REBATE_SYNC("评估返利报告申请同步","EVAL_REBATE_SYNC"),
	EVAL_REBATE_SYNC_UPDATE("评估返利报告驳回修改同步","EVAL_REBATE_SYNC_UPDATE"),
	EVAL_REBATE_REPORT("评估返利报告同步","EVAL_REBATE_REPORT"),
	EVAL_REFUND_SYNC("评估退费申请同步","EVAL_REFUND_SYNC"),
	SELF_DO_DYNC("自办贷款/评估审批同步","SELF_DO_DYNC");

	private String name;
	private String code;

	ApiLogModuleEnum(String name, String code) {
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
