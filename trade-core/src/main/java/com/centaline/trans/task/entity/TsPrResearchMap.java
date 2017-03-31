package com.centaline.trans.task.entity;

public class TsPrResearchMap {
	private Long pkid;
	private String distName;
	private String distCode;
	private String yuDistName;
	private String yuDistCode;

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
	 * @return the distName
	 */
	public String getDistName() {
		return distName;
	}

	/**
	 * @param distName
	 *            the distName to set
	 */
	public void setDistName(String distName) {
		this.distName = distName;
	}

	/**
	 * @return the distCode
	 */
	public String getDistCode() {
		return distCode;
	}

	/**
	 * @param distCode
	 *            the distCode to set
	 */
	public void setDistCode(String distCode) {
		this.distCode = distCode;
	}

	/**
	 * @return the yuDistName
	 */
	public String getYuDistName() {
		return yuDistName;
	}

	/**
	 * @param yuDistName
	 *            the yuDistName to set
	 */
	public void setYuDistName(String yuDistName) {
		this.yuDistName = yuDistName;
	}

	/**
	 * @return the yuDistCode
	 */
	public String getYuDistCode() {
		return yuDistCode;
	}

	/**
	 * @param yuDistCode
	 *            the yuDistCode to set
	 */
	public void setYuDistCode(String yuDistCode) {
		this.yuDistCode = yuDistCode;
	}
}
