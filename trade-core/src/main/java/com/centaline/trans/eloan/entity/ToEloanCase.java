package com.centaline.trans.eloan.entity;

import java.math.BigDecimal;
import java.util.Date;

public class ToEloanCase {
	private Long pkid;

	private String eloanCode;

	private String caseCode;

	private String loanSrvCode;

	private String finOrgCode;

	private String custName;

	private String custPhone;

	private Date applyTime;

	private BigDecimal applyAmount;

	private Integer month;

	private BigDecimal signAmount;

	private Date signTime;

	private String excutorId;

	private String excutorTeam;

	private String excutorDistrict;

	private String zjName;

	private String zjCode;

	private String coName;

	private String coCode;

	private Double coPart;

	private String pdCode;

	private String pdName;

	private Double pdPart;

	private Date applyConfTime;

	private String applyConfUser;

	private Date signConfTime;

	private String signConfUser;

	private String isRelFinish;

	private Date createTime;

	private String createBy;

	private Date updateTime;

	private String updateBy;

	private String taskId;

	private String processInstanceId;
	
	private BigDecimal chargeAmount;   //手续费
	
	private String remark;   // 情况说明
	
	
	public BigDecimal getChargeAmount() {
		return chargeAmount;
	}

	public void setChargeAmount(BigDecimal chargeAmount) {
		this.chargeAmount = chargeAmount;
	}

	public String getRemark() {
		return remark;
	}
	
	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Long getPkid() {
		return pkid;
	}

	public void setPkid(Long pkid) {
		this.pkid = pkid;
	}

	public String getEloanCode() {
		return eloanCode;
	}

	public void setEloanCode(String eloanCode) {
		this.eloanCode = eloanCode == null ? null : eloanCode.trim();
	}

	public String getCaseCode() {
		return caseCode;
	}

	public void setCaseCode(String caseCode) {
		this.caseCode = caseCode == null ? null : caseCode.trim();
	}

	public String getLoanSrvCode() {
		return loanSrvCode;
	}

	public void setLoanSrvCode(String loanSrvCode) {
		this.loanSrvCode = loanSrvCode == null ? null : loanSrvCode.trim();
	}

	public String getFinOrgCode() {
		return finOrgCode;
	}

	public void setFinOrgCode(String finOrgCode) {
		this.finOrgCode = finOrgCode == null ? null : finOrgCode.trim();
	}

	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName == null ? null : custName.trim();
	}

	public String getCustPhone() {
		return custPhone;
	}

	public void setCustPhone(String custPhone) {
		this.custPhone = custPhone == null ? null : custPhone.trim();
	}

	public Date getApplyTime() {
		return applyTime;
	}

	public void setApplyTime(Date applyTime) {
		this.applyTime = applyTime;
	}

	public BigDecimal getApplyAmount() {
		return applyAmount;
	}

	public void setApplyAmount(BigDecimal applyAmount) {
		this.applyAmount = applyAmount;
	}

	public Integer getMonth() {
		return month;
	}

	public void setMonth(Integer month) {
		this.month = month;
	}

	public BigDecimal getSignAmount() {
		return signAmount;
	}

	public void setSignAmount(BigDecimal signAmount) {
		this.signAmount = signAmount;
	}

	public Date getSignTime() {
		return signTime;
	}

	public void setSignTime(Date signTime) {
		this.signTime = signTime;
	}

	public String getExcutorId() {
		return excutorId;
	}

	public void setExcutorId(String excutorId) {
		this.excutorId = excutorId == null ? null : excutorId.trim();
	}

	public String getExcutorTeam() {
		return excutorTeam;
	}

	public void setExcutorTeam(String excutorTeam) {
		this.excutorTeam = excutorTeam == null ? null : excutorTeam.trim();
	}

	public String getExcutorDistrict() {
		return excutorDistrict;
	}

	public void setExcutorDistrict(String excutorDistrict) {
		this.excutorDistrict = excutorDistrict == null ? null : excutorDistrict.trim();
	}

	public String getZjName() {
		return zjName;
	}

	public void setZjName(String zjName) {
		this.zjName = zjName == null ? null : zjName.trim();
	}

	public String getZjCode() {
		return zjCode;
	}

	public void setZjCode(String zjCode) {
		this.zjCode = zjCode == null ? null : zjCode.trim();
	}

	public String getCoName() {
		return coName;
	}

	public void setCoName(String coName) {
		this.coName = coName == null ? null : coName.trim();
	}

	public String getCoCode() {
		return coCode;
	}

	public void setCoCode(String coCode) {
		this.coCode = coCode == null ? null : coCode.trim();
	}

	public Double getCoPart() {
		return coPart;
	}

	public void setCoPart(Double coPart) {
		this.coPart = coPart;
	}

	public String getPdCode() {
		return pdCode;
	}

	public void setPdCode(String pdCode) {
		this.pdCode = pdCode == null ? null : pdCode.trim();
	}

	public String getPdName() {
		return pdName;
	}

	public void setPdName(String pdName) {
		this.pdName = pdName == null ? null : pdName.trim();
	}

	public Double getPdPart() {
		return pdPart;
	}

	public void setPdPart(Double pdPart) {
		this.pdPart = pdPart;
	}

	public Date getApplyConfTime() {
		return applyConfTime;
	}

	public void setApplyConfTime(Date applyConfTime) {
		this.applyConfTime = applyConfTime;
	}

	public String getApplyConfUser() {
		return applyConfUser;
	}

	public void setApplyConfUser(String applyConfUser) {
		this.applyConfUser = applyConfUser == null ? null : applyConfUser.trim();
	}

	public Date getSignConfTime() {
		return signConfTime;
	}

	public void setSignConfTime(Date signConfTime) {
		this.signConfTime = signConfTime;
	}

	public String getSignConfUser() {
		return signConfUser;
	}

	public void setSignConfUser(String signConfUser) {
		this.signConfUser = signConfUser == null ? null : signConfUser.trim();
	}

	public String getIsRelFinish() {
		return isRelFinish;
	}

	public void setIsRelFinish(String isRelFinish) {
		this.isRelFinish = isRelFinish == null ? null : isRelFinish.trim();
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
		this.updateBy = updateBy == null ? null : updateBy.trim();
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

}