package com.centaline.api.ccai.flow.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

/**
 * CCAI提供的 流程回馈结果数据结构
 * @author yinchao
 * @date 2017/9/21
 */
@ApiModel("任务审批结果")
public class FlowFeedBack {
	@ApiModelProperty(value = "CCAI成交报告编号", required = true, position = 0)
	private String ccaiCode;
	@ApiModelProperty(value = "审批人域账号", required = true, position = 1)
	private String userName;
	@ApiModelProperty(value = "审批人姓名", required = true, position = 2)
	private String realName;
	@ApiModelProperty(value = "审批人所属部门HROC", required = true, position = 3,example = "022A003")
	private String grpCode;
	@ApiModelProperty(value = "审批人所属部门名称", required = true, position = 4,example = "财务部")
	private String grpName;
	@ApiModelProperty(value = "当前流程审批步骤", required = true, position = 5,example = "财务审批",
		allowableValues = "财务审批")
	private String activeName;
	@ApiModelProperty(value = "流程类型", required = true, position = 6,example = "3",
		allowableValues = "3,9")//3-银行返利,9-评估费结算
	private int workFlowType;
	@ApiModelProperty(value = "审批结果", required = true, position = 7,example = "1",
			allowableValues = "-1,0,1")//-1-拒绝,0-驳回,1-通过
	private int result;
	@ApiModelProperty(value = "审批意见", required = false, position = 8)
	private String comment;
	@ApiModelProperty(value = "审批时间", required = true, position = 9,example = "1505990444260")
	private Timestamp approveTime;
	@NotBlank(message = "成交报告编号不能为空")
	public String getCcaiCode() {
		return ccaiCode;
	}

	public void setCcaiCode(String ccaiCode) {
		this.ccaiCode = ccaiCode;
	}
	@NotBlank(message = "审批人域账号不能为空")
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	@NotBlank(message = "审批人姓名不能为空")
	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}
	@NotBlank(message = "审批人所属部门HROC不能为空")
	public String getGrpCode() {
		return grpCode;
	}

	public void setGrpCode(String grpCode) {
		this.grpCode = grpCode;
	}
	@NotBlank(message = "审批人所属部门名称不能为空")
	public String getGrpName() {
		return grpName;
	}

	public void setGrpName(String grpName) {
		this.grpName = grpName;
	}
	@NotBlank(message = "当前流程审批步骤不能为空")
	public String getActiveName() {
		return activeName;
	}

	public void setActiveName(String activeName) {
		this.activeName = activeName;
	}
	@Range(min=3,max=9,message = "流程类型仅支持了3-9,3为银行返利,9为评估费结算")
	public int getWorkFlowType() {
		return workFlowType;
	}

	public void setWorkFlowType(int workFlowType) {
		this.workFlowType = workFlowType;
	}
	@Range(min=-1,max=1,message = "审批结果仅支持-1-1")
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
	@NotNull(message = "审批时间不能为空")
	public Timestamp getApproveTime() {
		return approveTime;
	}

	public void setApproveTime(Timestamp approveTime) {
		this.approveTime = approveTime;
	}

	@Override
	public String toString() {
		return "FlowFeedBack{" +
				"ccaiCode='" + ccaiCode + '\'' +
				", userName='" + userName + '\'' +
				", realName='" + realName + '\'' +
				", grpCode='" + grpCode + '\'' +
				", grpName='" + grpName + '\'' +
				", activeName='" + activeName + '\'' +
				", workFlowType=" + workFlowType +
				", result=" + result +
				", comment='" + comment + '\'' +
				", approveTime=" + approveTime +
				'}';
	}
}
