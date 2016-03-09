package com.centaline.trans.loan.entity;

import java.util.Date;

public class LoanStatusChange {
	private Long pkid;
	private Long loanId;
	private String stFrom;
	private String stTo;
	private Date changeDate;
	private String changeUser;
	private String isConfirm;

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
	 * @return the loanId
	 */
	public Long getLoanId() {
		return loanId;
	}

	/**
	 * @param loanId
	 *            the loanId to set
	 */
	public void setLoanId(Long loanId) {
		this.loanId = loanId;
	}

	/**
	 * @return the stFrom
	 */
	public String getStFrom() {
		return stFrom;
	}

	/**
	 * @param stFrom
	 *            the stFrom to set
	 */
	public void setStFrom(String stFrom) {
		this.stFrom = stFrom;
	}

	/**
	 * @return the stTo
	 */
	public String getStTo() {
		return stTo;
	}

	/**
	 * @param stTo
	 *            the stTo to set
	 */
	public void setStTo(String stTo) {
		this.stTo = stTo;
	}

	/**
	 * @return the changeDate
	 */
	public Date getChangeDate() {
		return changeDate;
	}

	/**
	 * @param changeDate
	 *            the changeDate to set
	 */
	public void setChangeDate(Date changeDate) {
		this.changeDate = changeDate;
	}

	/**
	 * @return the changeUser
	 */
	public String getChangeUser() {
		return changeUser;
	}

	/**
	 * @param changeUser
	 *            the changeUser to set
	 */
	public void setChangeUser(String changeUser) {
		this.changeUser = changeUser;
	}

	public String getIsConfirm() {
		return isConfirm;
	}

	public void setIsConfirm(String isConfirm) {
		this.isConfirm = isConfirm;
	}

}
