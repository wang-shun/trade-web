package com.centaline.api.ccai.vo;

import java.io.Serializable;

/**
 * 评估消息队列使用消息对象
 * 用于启动评估流程 和 发送再次处理边界消息 等
 * @author yinchao
 * @date 2017-10-18
 */
public class MQEvalMessage implements Serializable {

	public static final String STARTFLOW_TYPE = "START";
	public static final String UPDATEFLOW_TYPE = "UPDATE";
	/**
	 * 评估报告编号
	 */
	private String evalCode;
	/**
	 * 流程code
	 * @see com.centaline.trans.common.enums.WorkFlowEnum
	 */
	private String flowType;
	/**
	 * 操作类型
	 */
	private String type;

	public MQEvalMessage() {}

	public MQEvalMessage(String evalCode, String flowType, String type) {
		this.evalCode = evalCode;
		this.flowType = flowType;
		this.type = type;
	}

	public String getEvalCode() {
		return evalCode;
	}

	public void setEvalCode(String evalCode) {
		this.evalCode = evalCode;
	}

	public String getFlowType() {
		return flowType;
	}

	public void setFlowType(String flowType) {
		this.flowType = flowType;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "MQEvalMessage{" +
				"evalCode='" + evalCode + '\'' +
				", flowType='" + flowType + '\'' +
				", type='" + type + '\'' +
				'}';
	}
}
