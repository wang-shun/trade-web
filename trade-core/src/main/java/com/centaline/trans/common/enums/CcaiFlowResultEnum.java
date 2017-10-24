package com.centaline.trans.common.enums;

/**
 * CCAI 流程环节反馈结果枚举
 * @author yinchao
 * @date 2017/9/21
 */
public enum CcaiFlowResultEnum {
	SUCCESS("通过",1),
	//回到前一环节
	BACK("驳回修改",2),
	SUPPLEMENT("补充资料",3),
	//终止流程
	FAILURE("拒绝",-1),
	NORMAL_BACK("通用驳回",0);

	private String name;
	private int code;

	CcaiFlowResultEnum(String name, int code) {
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
