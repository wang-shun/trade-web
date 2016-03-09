package com.centaline.trans.task.entity;

import java.util.Date;

public class ToPropertyResearch {
	private Long pkid;

	private String caseCode;

	private String partCode;

	private String prCat;

	private String prAppliant;

	private String prExecutor;

	private Date prApplyTime;

	private Date prAccpetTime;

	private Date prCompleteTime;

	private String prStatus;

	private String prChannel;
	private String samePRCode;

	private Integer isSuccess;

	private String unSuccessReason;
	private String prCode;

	private String prDistrictId;

	public Integer getIsSuccess() {
		return isSuccess;
	}

	public void setIsSuccess(Integer isSuccess) {
		this.isSuccess = isSuccess;
	}

	public String getUnSuccessReason() {
		return unSuccessReason;
	}

	public void setUnSuccessReason(String unSuccessReason) {
		this.unSuccessReason = unSuccessReason;
	}

	public String getPartCode() {
		return partCode;
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

	public String getPartTime() {
		return partCode;
	}

	public void setPartCode(String partCode) {
		this.partCode = partCode == null ? null : partCode.trim();
	}

	public String getPrCat() {
		return prCat;
	}

	public void setPrCat(String prCat) {
		this.prCat = prCat == null ? null : prCat.trim();
	}

	public String getPrAppliant() {
		return prAppliant;
	}

	public void setPrAppliant(String prAppliant) {
		this.prAppliant = prAppliant == null ? null : prAppliant.trim();
	}

	public String getPrExecutor() {
		return prExecutor;
	}

	public void setPrExecutor(String prExecutor) {
		this.prExecutor = prExecutor == null ? null : prExecutor.trim();
	}

	public Date getPrApplyTime() {
		return prApplyTime;
	}

	public void setPrApplyTime(Date prApplyTime) {
		this.prApplyTime = prApplyTime;
	}

	public Date getPrAccpetTime() {
		return prAccpetTime;
	}

	public void setPrAccpetTime(Date prAccpetTime) {
		this.prAccpetTime = prAccpetTime;
	}

	public Date getPrCompleteTime() {
		return prCompleteTime;
	}

	public void setPrCompleteTime(Date prCompleteTime) {
		this.prCompleteTime = prCompleteTime;
	}

	public String getPrStatus() {
		return prStatus;
	}

	public void setPrStatus(String prStatus) {
		this.prStatus = prStatus == null ? null : prStatus.trim();
	}

	public String getPrChannel() {
		return prChannel;
	}

	public void setPrChannel(String prChannel) {
		this.prChannel = prChannel == null ? null : prChannel.trim();
	}

	public String getPrCode() {
		return prCode;
	}

	public void setPrCode(String prCode) {
		this.prCode = prCode;
	}

	public String getSamePRCode() {
		return samePRCode;
	}

	public void setSamePRCode(String samePRCode) {
		this.samePRCode = samePRCode;
	}

	/**
	 * @return the prDistrictId
	 */
	public String getPrDistrictId() {
		return prDistrictId;
	}

	/**
	 * @param prDistrictId the prDistrictId to set
	 */
	public void setPrDistrictId(String prDistrictId) {
		this.prDistrictId = prDistrictId;
	}
}