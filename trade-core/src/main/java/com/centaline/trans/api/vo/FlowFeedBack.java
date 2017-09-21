package com.centaline.trans.api.vo;

import com.aist.uam.auth.remote.vo.SessionUser;
import com.aist.uam.userorg.remote.vo.User;
import com.centaline.trans.common.enums.CcaiFlowEnum;
import com.centaline.trans.common.enums.CcaiFlowResultEnum;

import java.sql.Timestamp;

/**
 * 向CCAI反馈 审批结果模型
 * @author yinchao
 * @date 2017/9/21
 */
public class FlowFeedBack {
	private SessionUser user;
	private CcaiFlowResultEnum result;
	private String comment;
	private Timestamp approveTime;
	public FlowFeedBack(SessionUser user, CcaiFlowResultEnum result, String comment) {
		this(user,result,comment,new Timestamp(System.currentTimeMillis()));
	}
	public FlowFeedBack(SessionUser user, CcaiFlowResultEnum result, String comment, Timestamp approveTime) {
		this.user = user;
		this.result = result;
		this.comment = comment;
		this.approveTime = approveTime;
	}

	public SessionUser getUser() {
		return user;
	}

	public CcaiFlowResultEnum getResult() {
		return result;
	}

	public String getComment() {
		return comment;
	}

	public Timestamp getApproveTime() {
		return approveTime;
	}
}
