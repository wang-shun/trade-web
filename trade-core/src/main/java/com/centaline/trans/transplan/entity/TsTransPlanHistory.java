package com.centaline.trans.transplan.entity;

import java.util.Date;

public class TsTransPlanHistory {
    private Long pkid;

    private String caseCode;

    private String partCode;

    private Date oldEstPartTime;

    private Date changeTime;

    private String changerId;

    private String changeReason;

    private Date newEstPartTime;
    
    private Long batchId;//交易计划变更批次ID add by zhoujp
    /**
     * 新增发起环节编码和审批结果 by wbzhouht
     */
    private String appverPartCode;//发起环节编码

    private Integer auditResult;//审批结果0待审核1审核通过2审核不通过

    public String getAppverPartCode() {
        return appverPartCode;
    }

    public void setAppverPartCode(String appverPartCode) {
        this.appverPartCode = appverPartCode;
    }

    public Integer getAuditResult() {
        return auditResult;
    }

    public void setAuditResult(Integer auditResult) {
        this.auditResult = auditResult;
    }

    public Long getPkid() {
        return pkid;
    }

    public void setPkid(Long pkid) {
        this.pkid = pkid;
    }

    public String getCaseCode() {
        return caseCode;
    }

    public void setCaseCode(String caseCode) {
        this.caseCode = caseCode == null ? null : caseCode.trim();
    }

    public String getPartCode() {
        return partCode;
    }

    public void setPartCode(String partCode) {
        this.partCode = partCode == null ? null : partCode.trim();
    }

    public Date getOldEstPartTime() {
        return oldEstPartTime;
    }

    public void setOldEstPartTime(Date oldEstPartTime) {
        this.oldEstPartTime = oldEstPartTime;
    }

    public Date getChangeTime() {
        return changeTime;
    }

    public void setChangeTime(Date changeTime) {
        this.changeTime = changeTime;
    }

    public String getChangerId() {
        return changerId;
    }

    public void setChangerId(String changerId) {
        this.changerId = changerId == null ? null : changerId.trim();
    }

    public String getChangeReason() {
        return changeReason;
    }

    public void setChangeReason(String changeReason) {
        this.changeReason = changeReason == null ? null : changeReason.trim();
    }

    public Date getNewEstPartTime() {
        return newEstPartTime;
    }

    public void setNewEstPartTime(Date newEstPartTime) {
        this.newEstPartTime = newEstPartTime;
    }

	public Long getBatchId() {
		return batchId;
	}

	public void setBatchId(Long batchId) {
		this.batchId = batchId;
	}
	
    
}