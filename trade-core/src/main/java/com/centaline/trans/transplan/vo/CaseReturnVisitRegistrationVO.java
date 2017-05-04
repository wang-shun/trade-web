package com.centaline.trans.transplan.vo;

public class CaseReturnVisitRegistrationVO {

	private Long batchId;//交易计划变更批次ID
    private String visitRemark;//回访标记
    private String content;//回访跟进
    private String createTime;//创建时间
    private String createBy;//创建者
	
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
	public Long getBatchId() {
		return batchId;
	}
	public void setBatchId(Long batchId) {
		this.batchId = batchId;
	}
	
    
}
