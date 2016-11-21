package com.centaline.trans.eloan.entity;

import java.util.Date;

public class LoanAgent {
	private Long pkid;
	private String caseCode;
	private String loanSrvCode;
	private String finOrgCode;
	private String custName;
	private String custPhone;
	private Double loanAmount;
	private Double actualAmount;
	private Integer month;
	private String zjName;
	private String zjCode;
	private String coCode;
	private String coName;
	private Double awardPer;
	private String applyStatus;
	private Date confirmTime;
	private String confirmStatus;
	private Date lastExceedExportTime;
	private Date applyTime;
	private Date signTime;
	private Double signAmount;
	// 放款时间
	private Date releaseTime;
	// 进帐对帐时间
	private Date incomeConfirmTime;
	private Date incomeArriveTime;
	private String executorTeam;
	private String executorId;

	private String pdName;
	private String pdCode;
	// 造成财务编号
	private String finCaseCode;
	private Date createTime;
	//创建人
	private String creatorId;

	private Double pdPar;
	/** 运算参数 */
	// 每次操作用户(传递参数用)
	private String optUser;

	private Long tempServId;
	private String result;

	/**
	 * @return the pkid
	 */
	public Long getPkid() {
		return pkid;
	}

	/**
	 * @param pkid
	 *            the pkid to set
	 */
	public void setPkid(Long pkid) {
		this.pkid = pkid;
	}

	/**
	 * @return the caseCode
	 */
	public String getCaseCode() {
		return caseCode;
	}

	/**
	 * @param caseCode
	 *            the caseCode to set
	 */
	public void setCaseCode(String caseCode) {
		this.caseCode = caseCode;
	}

	/**
	 * @return the loanSrvCode
	 */
	public String getLoanSrvCode() {
		return loanSrvCode;
	}

	/**
	 * @param loanSrvCode
	 *            the loanSrvCode to set
	 */
	public void setLoanSrvCode(String loanSrvCode) {
		this.loanSrvCode = loanSrvCode;
	}

	/**
	 * @return the finOrgCode
	 */
	public String getFinOrgCode() {
		return finOrgCode;
	}

	/**
	 * @param finOrgCode
	 *            the finOrgCode to set
	 */
	public void setFinOrgCode(String finOrgCode) {
		this.finOrgCode = finOrgCode;
	}

	/**
	 * @return the custName
	 */
	public String getCustName() {
		return custName;
	}

	/**
	 * @param custName
	 *            the custName to set
	 */
	public void setCustName(String custName) {
		this.custName = custName;
	}

	/**
	 * @return the custPhone
	 */
	public String getCustPhone() {
		return custPhone;
	}

	/**
	 * @param custPhone
	 *            the custPhone to set
	 */
	public void setCustPhone(String custPhone) {
		this.custPhone = custPhone;
	}

	/**
	 * @return the loanAmount
	 */
	public Double getLoanAmount() {
		return loanAmount;
	}

	/**
	 * @param loanAmount
	 *            the loanAmount to set
	 */
	public void setLoanAmount(Double loanAmount) {
		this.loanAmount = loanAmount;
	}

	/**
	 * @return the actualAmount
	 */
	public Double getActualAmount() {
		return actualAmount;
	}

	/**
	 * @param actualAmount
	 *            the actualAmount to set
	 */
	public void setActualAmount(Double actualAmount) {
		this.actualAmount = actualAmount;
	}

	/**
	 * @return the month
	 */
	public Integer getMonth() {
		return month;
	}

	/**
	 * @param month
	 *            the month to set
	 */
	public void setMonth(Integer month) {
		this.month = month;
	}

	/**
	 * @return the zjName
	 */
	public String getZjName() {
		return zjName;
	}

	/**
	 * @param zjName
	 *            the zjName to set
	 */
	public void setZjName(String zjName) {
		this.zjName = zjName;
	}

	/**
	 * @return the zjCode
	 */
	public String getZjCode() {
		return zjCode;
	}

	/**
	 * @param zjCode
	 *            the zjCode to set
	 */
	public void setZjCode(String zjCode) {
		this.zjCode = zjCode;
	}

	/**
	 * @return the coCode
	 */
	public String getCoCode() {
		return coCode;
	}

	/**
	 * @param coCode
	 *            the coCode to set
	 */
	public void setCoCode(String coCode) {
		this.coCode = coCode;
	}

	/**
	 * @return the coName
	 */
	public String getCoName() {
		return coName;
	}

	/**
	 * @param coName
	 *            the coName to set
	 */
	public void setCoName(String coName) {
		this.coName = coName;
	}

	/**
	 * @return the awardPre
	 */
	public Double getAwardPer() {
		return awardPer;
	}

	/**
	 * @param awardPre
	 *            the awardPre to set
	 */
	public void setAwardPer(Double awardPer) {
		this.awardPer = awardPer;
	}

	/**
	 * @return the applyStatus
	 */
	public String getApplyStatus() {
		return applyStatus;
	}

	/**
	 * @param applyStatus
	 *            the applyStatus to set
	 */
	public void setApplyStatus(String applyStatus) {
		this.applyStatus = applyStatus;
	}

	/**
	 * @return the comfirmTime
	 */
	public Date getConfirmTime() {
		return confirmTime;
	}

	/**
	 * @param comfirmTime
	 *            the comfirmTime to set
	 */
	public void setConfirmTime(Date confirmTime) {
		this.confirmTime = confirmTime;
	}

	/**
	 * @return the comfirmStatus
	 */
	public String getConfirmStatus() {
		return confirmStatus;
	}

	/**
	 * @param comfirmStatus
	 *            the comfirmStatus to set
	 */
	public void setConfirmStatus(String confirmStatus) {
		this.confirmStatus = confirmStatus;
	}

	/**
	 * @return the lastExceedExportTime
	 */
	public Date getLastExceedExportTime() {
		return lastExceedExportTime;
	}

	/**
	 * @param lastExceedExportTime
	 *            the lastExceedExportTime to set
	 */
	public void setLastExceedExportTime(Date lastExceedExportTime) {
		this.lastExceedExportTime = lastExceedExportTime;
	}

	/**
	 * @return the applyTime
	 */
	public Date getApplyTime() {
		return applyTime;
	}

	/**
	 * @param applyTime
	 *            the applyTime to set
	 */
	public void setApplyTime(Date applyTime) {
		this.applyTime = applyTime;
	}

	/**
	 * @return the signTime
	 */
	public Date getSignTime() {
		return signTime;
	}

	/**
	 * @param signTime
	 *            the signTime to set
	 */
	public void setSignTime(Date signTime) {
		this.signTime = signTime;
	}

	/**
	 * @return the incomeComfirmTime
	 */
	public Date getIncomeConfirmTime() {
		return incomeConfirmTime;
	}

	/**
	 * @param incomeComfirmTime
	 *            the incomeComfirmTime to set
	 */
	public void setIncomeConfirmTime(Date incomeConfirmTime) {
		this.incomeConfirmTime = incomeConfirmTime;
	}

	/**
	 * @return the incomeArriveTime
	 */
	public Date getIncomeArriveTime() {
		return incomeArriveTime;
	}

	/**
	 * @param incomeArriveTime
	 *            the incomeArriveTime to set
	 */
	public void setIncomeArriveTime(Date incomeArriveTime) {
		this.incomeArriveTime = incomeArriveTime;
	}

	/**
	 * @return the optUser
	 */
	public String getOptUser() {
		return optUser;
	}

	/**
	 * @param optUser
	 *            the optUser to set
	 */
	public void setOptUser(String optUser) {
		this.optUser = optUser;
	}

	/**
	 * @return the releaseTime
	 */
	public Date getReleaseTime() {
		return releaseTime;
	}

	/**
	 * @param releaseTime
	 *            the releaseTime to set
	 */
	public void setReleaseTime(Date releaseTime) {
		this.releaseTime = releaseTime;
	}

	/**
	 * @return the signAmount
	 */
	public Double getSignAmount() {
		return signAmount;
	}

	/**
	 * @param signAmount
	 *            the signAmount to set
	 */
	public void setSignAmount(Double signAmount) {
		this.signAmount = signAmount;
	}

	/**
	 * @return the executorTeam
	 */
	public String getExecutorTeam() {
		return executorTeam;
	}

	/**
	 * @param executorTeam
	 *            the executorTeam to set
	 */
	public void setExecutorTeam(String executorTeam) {
		this.executorTeam = executorTeam;
	}

	/**
	 * @return the executorId
	 */
	public String getExecutorId() {
		return executorId;
	}

	/**
	 * @param executorId
	 *            the executorId to set
	 */
	public void setExecutorId(String executorId) {
		this.executorId = executorId;
	}

	/**
	 * @return the result
	 */
	public String getResult() {
		return result;
	}

	/**
	 * @param result
	 *            the result to set
	 */
	public void setResult(String result) {
		this.result = result;
	}

	/**
	 * @return the tempServId
	 */
	public Long getTempServId() {
		return tempServId;
	}

	/**
	 * @param tempServId
	 *            the tempServId to set
	 */
	public void setTempServId(Long tempServId) {
		this.tempServId = tempServId;
	}

	/**
	 * @return the pdName
	 */
	public String getPdName() {
		return pdName;
	}

	/**
	 * @param pdName
	 *            the pdName to set
	 */
	public void setPdName(String pdName) {
		this.pdName = pdName;
	}

	/**
	 * @return the pdCode
	 */
	public String getPdCode() {
		return pdCode;
	}

	/**
	 * @param pdCode
	 *            the pdCode to set
	 */
	public void setPdCode(String pdCode) {
		this.pdCode = pdCode;
	}

	/**
	 * @return the pdPar
	 */
	public Double getPdPar() {
		return pdPar;
	}

	/**
	 * @param pdPar
	 *            the pdPar to set
	 */
	public void setPdPar(Double pdPar) {
		this.pdPar = pdPar;
	}

	/**
	 * @return the finCaseCode
	 */
	public String getFinCaseCode() {
		return finCaseCode;
	}

	/**
	 * @param finCaseCode
	 *            the finCaseCode to set
	 */
	public void setFinCaseCode(String finCaseCode) {
		this.finCaseCode = finCaseCode;
	}

	/**
	 * @return the createTime
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * @param createTime
	 *            the createTime to set
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getCreatorId() {
		return creatorId;
	}

	public void setCreatorId(String creatorId) {
		this.creatorId = creatorId;
	}
}
