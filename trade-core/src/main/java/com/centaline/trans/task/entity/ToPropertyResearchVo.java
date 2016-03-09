package com.centaline.trans.task.entity;

public class ToPropertyResearchVo {
	private String caseCode;
	private String prCat;
	private String district;
	private String username;

	/* 物业地址 */
	private String propertyAddr;

	public String getPropertyAddr() {
		return propertyAddr;
	}

	public void setPropertyAddr(String propertyAddr) {
		this.propertyAddr = propertyAddr;
	}

	/**
	 * @return the prCat
	 */
	public String getPrCat() {
		return prCat;
	}

	/**
	 * @param prCat
	 *            the prCat to set
	 */
	public void setPrCat(String prCat) {
		this.prCat = prCat;
	}

	/**
	 * @return the district
	 */
	public String getDistrict() {
		return district;
	}

	/**
	 * @param district
	 *            the district to set
	 */
	public void setDistrict(String district) {
		this.district = district;
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username
	 *            the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the caseCode
	 */
	public String getCaseCode() {
		return caseCode;
	}

	/**
	 * @param caseCode the caseCode to set
	 */
	public void setCaseCode(String caseCode) {
		this.caseCode = caseCode;
	}

}