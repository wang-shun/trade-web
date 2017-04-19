package com.centaline.trans.eloan.vo;

import java.io.Serializable;

import com.aist.uam.auth.remote.vo.SessionUser;

/**
 * E+贷款案件前台传值对象
 * 
 * @author yinjf2
 *
 */
public class ELoanVo implements Serializable {

	private String eLoanCode; // E+金融编号

	private String isPass; // 是否接单(true:接单,false:打回)

	private String taskId; // 任务id

	private String stateInBank; // 状态编码

	private String caseCode; // 案件编号

	private String type; // 类型

	private String comment; // 跟进备注信息

	private SessionUser user; // 当前用户信息

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public SessionUser getUser() {
		return user;
	}

	public void setUser(SessionUser user) {
		this.user = user;
	}

	public String getIsPass() {
		return isPass;
	}

	public void setIsPass(String isPass) {
		this.isPass = isPass;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getStateInBank() {
		return stateInBank;
	}

	public void setStateInBank(String stateInBank) {
		this.stateInBank = stateInBank;
	}

	public String getCaseCode() {
		return caseCode;
	}

	public void setCaseCode(String caseCode) {
		this.caseCode = caseCode;
	}

	public String geteLoanCode() {
		return eLoanCode;
	}

	public void seteLoanCode(String eLoanCode) {
		this.eLoanCode = eLoanCode;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

}
