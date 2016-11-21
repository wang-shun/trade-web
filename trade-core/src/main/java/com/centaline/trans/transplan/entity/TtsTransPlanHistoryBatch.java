package com.centaline.trans.transplan.entity;

import java.util.Date;

/**
 * 交易计划变更历史批次表(T_TS_TRANS_PLAN_HISTORY_BATCH)
 * @author zhoujp7
 * date:2016-11-14
 */
public class TtsTransPlanHistoryBatch {

	private long pkid;
	private String	caseCode;//案件编号
	private String partCode;//环节编码
	private Date oldEstPartTime;//原预计时间
	private Date newEstPartTime;//新预计时间
	private String changeReason;//变更原因
	private String lastVisitRemark;//最新回访跟进标记
	private String	lastContent;//最新跟进内容
	private Date createTime;//创建时间
	private String	createBy;//创建人
	private Date updateTime;//更新时间
	private String updateBy;//更新人
	private String operateFlag;//操作标记（0：手工 1：流程重置、重启）
	
	public long getPkid() {
		return pkid;
	}
	public void setPkid(long pkid) {
		this.pkid = pkid;
	}
	public String getCaseCode() {
		return caseCode;
	}
	public void setCaseCode(String caseCode) {
		this.caseCode = caseCode;
	}
	public String getPartCode() {
		return partCode;
	}
	public void setPartCode(String partCode) {
		this.partCode = partCode;
	}
	public Date getOldEstPartTime() {
		return oldEstPartTime;
	}
	public void setOldEstPartTime(Date oldEstPartTime) {
		this.oldEstPartTime = oldEstPartTime;
	}
	public Date getNewEstPartTime() {
		return newEstPartTime;
	}
	public void setNewEstPartTime(Date newEstPartTime) {
		this.newEstPartTime = newEstPartTime;
	}
	public String getLastVisitRemark() {
		return lastVisitRemark;
	}
	public void setLastVisitRemark(String lastVisitRemark) {
		this.lastVisitRemark = lastVisitRemark;
	}
	public String getLastContent() {
		return lastContent;
	}
	public void setLastContent(String lastContent) {
		this.lastContent = lastContent;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getCreateBy() {
		return createBy;
	}
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public String getUpdateBy() {
		return updateBy;
	}
	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}
	public String getOperateFlag() {
		return operateFlag;
	}
	public void setOperateFlag(String operateFlag) {
		this.operateFlag = operateFlag;
	}
	public String getChangeReason() {
		return changeReason;
	}
	public void setChangeReason(String changeReason) {
		this.changeReason = changeReason;
	}
	
	
}
