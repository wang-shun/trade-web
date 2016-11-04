package com.centaline.trans.income.entity;

import java.math.BigDecimal;
import java.util.Date;

public class TsIncomeStatistics {
	private Long pkid;

	private String caseCode;

	private String incomeCat;

	private String incomeItem;

	private BigDecimal incomeAmount;

	private Date importTime;

	private Integer importNo;

	private Date incomeBelongDay;

	private String finCaseCode;

	private String finCode;

	private String isChange;

	private String orgId;

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

	public String getIncomeCat() {
		return incomeCat;
	}

	public void setIncomeCat(String incomeCat) {
		this.incomeCat = incomeCat == null ? null : incomeCat.trim();
	}

	public String getIncomeItem() {
		return incomeItem;
	}

	public void setIncomeItem(String incomeItem) {
		this.incomeItem = incomeItem == null ? null : incomeItem.trim();
	}

	public BigDecimal getIncomeAmount() {
		return incomeAmount;
	}

	public void setIncomeAmount(BigDecimal incomeAmount) {
		this.incomeAmount = incomeAmount;
	}

	public Date getImportTime() {
		return importTime;
	}

	public void setImportTime(Date importTime) {
		this.importTime = importTime;
	}

	public Integer getImportNo() {
		return importNo;
	}

	public void setImportNo(Integer importNo) {
		this.importNo = importNo;
	}

	public Date getIncomeBelongDay() {
		return incomeBelongDay;
	}

	public void setIncomeBelongDay(Date incomeBelongDay) {
		this.incomeBelongDay = incomeBelongDay;
	}

	public String getFinCaseCode() {
		return finCaseCode;
	}

	public void setFinCaseCode(String finCaseCode) {
		this.finCaseCode = finCaseCode == null ? null : finCaseCode.trim();
	}

	public String getFinCode() {
		return finCode;
	}

	public void setFinCode(String finCode) {
		this.finCode = finCode == null ? null : finCode.trim();
	}

	public String getIsChange() {
		return isChange;
	}

	public void setIsChange(String isChange) {
		this.isChange = isChange == null ? null : isChange.trim();
	}

	/**
	 * @return the orgId
	 */
	public String getOrgId() {
		return orgId;
	}

	/**
	 * @param orgId the orgId to set
	 */
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
}