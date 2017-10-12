package com.centaline.api.ccai.vo;

import java.io.Serializable;

/**
 * 案件消息队列使用消息对象
 * 用于启动案件流程 和 发送再次审核边界消息
 * @author yinchao
 * @date 2017/9/26
 */
public class MQCaseMessage  implements Serializable {

	public static final String STARTFLOW_TYPE = "START";
	public static final String UPDATEFLOW_TYPE = "UPDATE";
	
	/**
	 * 自办贷款/评估审批
	 */
	public static final String STARTFLOW_LOANANDASSE_TYPE = "START_LOANANDASSE";

	private String caseCode;
	private String type;

	public MQCaseMessage() {}

	public MQCaseMessage(String caseCode, String type) {
		this.caseCode = caseCode;
		this.type = type;
	}

	public String getCaseCode() {
		return caseCode;
	}

	public void setCaseCode(String caseCode) {
		this.caseCode = caseCode;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "MQCaseMessage{" +
				"caseCode='" + caseCode + '\'' +
				", type='" + type + '\'' +
				'}';
	}
}
