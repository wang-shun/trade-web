package com.centaline.trans.mortgage.entity;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class ToMortLoaner {
	private Long pkid;

	private String custName;

	private String caseCode;

	private String receiveCode;

	private String houAddress;

	private BigDecimal mortTotalAmount;

	private BigDecimal comAmount;

	private Integer comYear;

	private BigDecimal comDiscount;

	private BigDecimal prfAmount;

	private Integer prfYear;

	private String flowStatus;

	private String mortPkid;

	private String loanerStatus;

	private String loanerDesc;
	
	private String mortType;
	
	private String isMainLoanBankProcess;

	private String sendId;

	private String sendName;

	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
	private Date sendTime;

	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
	private Date receiveTime;

	private String receiveId;

	private String receiveName;

	private String rejectId;

	private String rejectName;

	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
	private Date rejectTime;

	private String cancleId;

	private String cancleName;

	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
	private Date cancleTime;

	private String approvalId;

	private String approvalName;

	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
	private Date approvalTime;

	private String loanerId;

	private String loanerName;

	private String loanerPhone;

	private String loanerOrgId;

	private String loanerOrgCode;

	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
	private Date createTime;

	private String createBy;

	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
	private Date updateTime;

	private String updateBy;

	public Long getPkid() {
		return pkid;
	}

	public void setPkid(Long pkid) {
		this.pkid = pkid;
	}

	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName == null ? null : custName.trim();
	}

	public String getCaseCode() {
		return caseCode;
	}

	public void setCaseCode(String caseCode) {
		this.caseCode = caseCode == null ? null : caseCode.trim();
	}

	public String getReceiveCode() {
		return receiveCode;
	}

	public void setReceiveCode(String receiveCode) {
		this.receiveCode = receiveCode == null ? null : receiveCode.trim();
	}

	public String getHouAddress() {
		return houAddress;
	}

	public void setHouAddress(String houAddress) {
		this.houAddress = houAddress == null ? null : houAddress.trim();
	}

	public BigDecimal getMortTotalAmount() {
		return mortTotalAmount;
	}

	public void setMortTotalAmount(BigDecimal mortTotalAmount) {
		this.mortTotalAmount = mortTotalAmount;
	}

	public BigDecimal getComAmount() {
		return comAmount;
	}

	public void setComAmount(BigDecimal comAmount) {
		this.comAmount = comAmount;
	}

	public Integer getComYear() {
		return comYear;
	}	
	
	
	public String getMortType() {
		return mortType;
	}

	public void setMortType(String mortType) {
		this.mortType = mortType;
	}

	public String getIsMainLoanBankProcess() {
		return isMainLoanBankProcess;
	}

	public void setIsMainLoanBankProcess(String isMainLoanBankProcess) {
		this.isMainLoanBankProcess = isMainLoanBankProcess;
	}

	public void setComYear(Integer comYear) {
		this.comYear = comYear;
	}

	public BigDecimal getComDiscount() {
		return comDiscount;
	}

	public void setComDiscount(BigDecimal comDiscount) {
		this.comDiscount = comDiscount;
	}

	public BigDecimal getPrfAmount() {
		return prfAmount;
	}

	public void setPrfAmount(BigDecimal prfAmount) {
		this.prfAmount = prfAmount;
	}

	public Integer getPrfYear() {
		return prfYear;
	}

	public void setPrfYear(Integer prfYear) {
		this.prfYear = prfYear;
	}

	public String getFlowStatus() {
		return flowStatus;
	}

	public void setFlowStatus(String flowStatus) {
		this.flowStatus = flowStatus == null ? null : flowStatus.trim();
	}

	public String getMortPkid() {
		return mortPkid;
	}

	public void setMortPkid(String mortPkid) {
		this.mortPkid = mortPkid;
	}

	public String getLoanerStatus() {
		return loanerStatus;
	}

	public void setLoanerStatus(String loanerStatus) {
		this.loanerStatus = loanerStatus == null ? null : loanerStatus.trim();
	}

	public String getLoanerDesc() {
		return loanerDesc;
	}

	public void setLoanerDesc(String loanerDesc) {
		this.loanerDesc = loanerDesc == null ? null : loanerDesc.trim();
	}

	public String getSendId() {
		return sendId;
	}

	public void setSendId(String sendId) {
		this.sendId = sendId == null ? null : sendId.trim();
	}

	public String getSendName() {
		return sendName;
	}

	public void setSendName(String sendName) {
		this.sendName = sendName == null ? null : sendName.trim();
	}

	public Date getSendTime() {
		return sendTime;
	}

	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}

	public Date getReceiveTime() {
		return receiveTime;
	}

	public void setReceiveTime(Date receiveTime) {
		this.receiveTime = receiveTime;
	}

	public String getReceiveId() {
		return receiveId;
	}

	public void setReceiveId(String receiveId) {
		this.receiveId = receiveId == null ? null : receiveId.trim();
	}

	public String getReceiveName() {
		return receiveName;
	}

	public void setReceiveName(String receiveName) {
		this.receiveName = receiveName == null ? null : receiveName.trim();
	}

	public String getRejectId() {
		return rejectId;
	}

	public void setRejectId(String rejectId) {
		this.rejectId = rejectId == null ? null : rejectId.trim();
	}

	public String getRejectName() {
		return rejectName;
	}

	public void setRejectName(String rejectName) {
		this.rejectName = rejectName == null ? null : rejectName.trim();
	}

	public Date getRejectTime() {
		return rejectTime;
	}

	public void setRejectTime(Date rejectTime) {
		this.rejectTime = rejectTime;
	}

	public String getCancleId() {
		return cancleId;
	}

	public void setCancleId(String cancleId) {
		this.cancleId = cancleId == null ? null : cancleId.trim();
	}

	public String getCancleName() {
		return cancleName;
	}

	public void setCancleName(String cancleName) {
		this.cancleName = cancleName == null ? null : cancleName.trim();
	}

	public Date getCancleTime() {
		return cancleTime;
	}

	public void setCancleTime(Date cancleTime) {
		this.cancleTime = cancleTime;
	}

	public String getApprovalId() {
		return approvalId;
	}

	public void setApprovalId(String approvalId) {
		this.approvalId = approvalId == null ? null : approvalId.trim();
	}

	public String getApprovalName() {
		return approvalName;
	}

	public void setApprovalName(String approvalName) {
		this.approvalName = approvalName == null ? null : approvalName.trim();
	}

	public Date getApprovalTime() {
		return approvalTime;
	}

	public void setApprovalTime(Date approvalTime) {
		this.approvalTime = approvalTime;
	}

	public String getLoanerId() {
		return loanerId;
	}

	public void setLoanerId(String loanerId) {
		this.loanerId = loanerId == null ? null : loanerId.trim();
	}

	public String getLoanerName() {
		return loanerName;
	}

	public void setLoanerName(String loanerName) {
		this.loanerName = loanerName == null ? null : loanerName.trim();
	}

	public String getLoanerPhone() {
		return loanerPhone;
	}

	public void setLoanerPhone(String loanerPhone) {
		this.loanerPhone = loanerPhone == null ? null : loanerPhone.trim();
	}

	public String getLoanerOrgId() {
		return loanerOrgId;
	}

	public void setLoanerOrgId(String loanerOrgId) {
		this.loanerOrgId = loanerOrgId == null ? null : loanerOrgId.trim();
	}

	public String getLoanerOrgCode() {
		return loanerOrgCode;
	}

	public void setLoanerOrgCode(String loanerOrgCode) {
		this.loanerOrgCode = loanerOrgCode == null ? null : loanerOrgCode
				.trim();
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
		this.createBy = createBy == null ? null : createBy.trim();
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
}