package com.centaline.trans.task.vo;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class ToAppRecordInfoVO {

	private Long pkid;
	
	private String  applyUserName; // 审批人域账号 ,
	
	private String applyRealName; // 审批人名称 ,
	
	private String level ; //审批人级别 ,
	
	private Date dealTime ;// 审批时间 ,
	
	private Integer result ;// 审批结果 = ['-1', '0', '1']integerEnum:-1, 0, 1,
	
	private String comment ; // 审批意见
	
	private Long  selfAppInfoId; //关联自办申请信息表
	
	private String visitResult; //回访结果
	
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
	private Date visitTime;//回访时间
	
	private Date createTime; //创建时间
	
	
	private String caseCode;
	private String taskId;
	private String processInstanceId;
	private String processDefinitionId;
	public Long getPkid() {
		return pkid;
	}
	public void setPkid(Long pkid) {
		this.pkid = pkid;
	}
	public String getApplyUserName() {
		return applyUserName;
	}
	public void setApplyUserName(String applyUserName) {
		this.applyUserName = applyUserName;
	}
	public String getApplyRealName() {
		return applyRealName;
	}
	public void setApplyRealName(String applyRealName) {
		this.applyRealName = applyRealName;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	public Date getDealTime() {
		return dealTime;
	}
	public void setDealTime(Date dealTime) {
		this.dealTime = dealTime;
	}
	public Integer getResult() {
		return result;
	}
	public void setResult(Integer result) {
		this.result = result;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public Long getSelfAppInfoId() {
		return selfAppInfoId;
	}
	public void setSelfAppInfoId(Long selfAppInfoId) {
		this.selfAppInfoId = selfAppInfoId;
	}
	public String getVisitResult() {
		return visitResult;
	}
	public void setVisitResult(String visitResult) {
		this.visitResult = visitResult;
	}
	public Date getVisitTime() {
		return visitTime;
	}
	public void setVisitTime(Date visitTime) {
		this.visitTime = visitTime;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getCaseCode() {
		return caseCode;
	}
	public void setCaseCode(String caseCode) {
		this.caseCode = caseCode;
	}
	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	public String getProcessInstanceId() {
		return processInstanceId;
	}
	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}
	public String getProcessDefinitionId() {
		return processDefinitionId;
	}
	public void setProcessDefinitionId(String processDefinitionId) {
		this.processDefinitionId = processDefinitionId;
	}
	
	
	
	

}
