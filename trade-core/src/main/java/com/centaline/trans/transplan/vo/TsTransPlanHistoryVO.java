package com.centaline.trans.transplan.vo;

import java.util.Date;

public class TsTransPlanHistoryVO {
	private Long pkid;

    private String caseCode;

    private String partCode;

    private String oldEstPartTime;

    private Date changeTime;

    private String changerId;

    private String changeReason;

    private String newEstPartTime;
    
    private Long batchId;//交易计划变更批次ID add by zhoujp

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
		this.caseCode = caseCode;
	}

	public String getPartCode() {
		return partCode;
	}

	public void setPartCode(String partCode) {
		this.partCode = partCode;
	}

	public String getOldEstPartTime() {
		return oldEstPartTime;
	}

	public void setOldEstPartTime(String oldEstPartTime) {
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
		this.changerId = changerId;
	}

	public String getChangeReason() {
		return changeReason;
	}

	public void setChangeReason(String changeReason) {
		this.changeReason = changeReason;
	}

	public String getNewEstPartTime() {
		return newEstPartTime;
	}

	public void setNewEstPartTime(String newEstPartTime) {
		this.newEstPartTime = newEstPartTime;
	}

	public Long getBatchId() {
		return batchId;
	}

	public void setBatchId(Long batchId) {
		this.batchId = batchId;
	}
    
}
