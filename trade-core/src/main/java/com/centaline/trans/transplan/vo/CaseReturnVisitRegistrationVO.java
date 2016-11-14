package com.centaline.trans.transplan.vo;

public class CaseReturnVisitRegistrationVO {

	private Long planHistoryId;//交易计划变更历史表ID
    private String visitRemark;//回访标记
    private String content;//回访跟进
    private String createTime;//创建时间
    private String createBy;//创建者
	public Long getPlanHistoryId() {
		return planHistoryId;
	}
	public void setPlanHistoryId(Long planHistoryId) {
		this.planHistoryId = planHistoryId;
	}
	public String getVisitRemark() {
		return visitRemark;
	}
	public void setVisitRemark(String visitRemark) {
		this.visitRemark = visitRemark;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getCreateBy() {
		return createBy;
	}
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
    
}
