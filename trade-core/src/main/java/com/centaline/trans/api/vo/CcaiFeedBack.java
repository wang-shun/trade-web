package com.centaline.trans.api.vo;

import java.sql.Timestamp;

/**
 * 用于定义CCAI需要的 反馈结果数据结构
 * @author yinchao
 * @date 2017/9/21
 */
public class CcaiFeedBack {
	//CCAI流程ID
	private String applyId;
	//审批人域账号
	private String userName;
	//审批人姓名
	private String realname;
	//审批人所属部门HROC CODE
	private String grpCode;
	//审批人所属部门名称
	private String grpName;
	/**
	 * 审批步骤
	 * @see com.centaline.trans.common.enums.CcaiTaskEnum
	 */
	private String activeName;
	/**
	 * 流程类型
	 * @see com.centaline.trans.common.enums.CcaiFlowEnum
	 */
	private int workFlowType;
	/**
	 * 审批结果
	 * @see com.centaline.trans.common.enums.CcaiFlowResultEnum
	 */
	private int result;
	/**
	 * 审批意见
	 */
	private String comment;
	/**
	 * 审批时间
	 */
	private Timestamp approveTime;

	public String getApplyId() {
		return applyId;
	}

	public void setApplyId(String applyId) {
		this.applyId = applyId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}

	public String getGrpCode() {
		return grpCode;
	}

	public void setGrpCode(String grpCode) {
		this.grpCode = grpCode;
	}

	public String getGrpName() {
		return grpName;
	}

	public void setGrpName(String grpName) {
		this.grpName = grpName;
	}

	public String getActiveName() {
		return activeName;
	}

	public void setActiveName(String activeName) {
		this.activeName = activeName;
	}

	public int getWorkFlowType() {
		return workFlowType;
	}

	public void setWorkFlowType(int workFlowType) {
		this.workFlowType = workFlowType;
	}

	public int getResult() {
		return result;
	}

	public void setResult(int result) {
		this.result = result;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Timestamp getApproveTime() {
		return approveTime;
	}

	public void setApproveTime(Timestamp approveTime) {
		this.approveTime = approveTime;
	}
}
