package com.centaline.trans.workspace.entity;

import java.util.List;

public class WorkSpace {
	private String userId;
	private List<String> orgs;
	private String orgId;
	// 月份
	private String mo;

	private String username;

	private String rankCat;
	private String rankType;
	
	private Integer color;
	
	private String rankDuration="MONTH";
	private Integer size=5;
	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * @param userId
	 *            the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * @return the orgs
	 */
	public List<String> getOrgs() {
		return orgs;
	}

	/**
	 * @param orgs
	 *            the orgs to set
	 */
	public void setOrgs(List<String> orgs) {
		this.orgs = orgs;
	}

	/**
	 * @return the mo
	 */
	public String getMo() {
		return mo;
	}

	/**
	 * @param mo
	 *            the mo to set
	 */
	public void setMo(String mo) {
		this.mo = mo;
	}

	/**
	 * @return the orgId
	 */
	public String getOrgId() {
		return orgId;
	}

	/**
	 * @param orgId
	 *            the orgId to set
	 */
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	/**
	 * @return the rankCat
	 */
	public String getRankCat() {
		return rankCat;
	}

	/**
	 * @param rankCat
	 *            the rankCat to set
	 */
	public void setRankCat(String rankCat) {
		this.rankCat = rankCat;
	}

	/**
	 * @return the rankType
	 */
	public String getRankType() {
		return rankType;
	}

	/**
	 * @param rankType
	 *            the rankType to set
	 */
	public void setRankType(String rankType) {
		this.rankType = rankType;
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

	public Integer getColor() {
		return color;
	}

	public void setColor(Integer color) {
		this.color = color;
	}

	public String getRankDuration() {
		return rankDuration;
	}

	public void setRankDuration(String rankDuration) {
		this.rankDuration = rankDuration;
	}

	public Integer getSize() {
		return size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}

}
