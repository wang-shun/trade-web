package com.centaline.trans.workspace.entity;

public class WorkLoad {
	private String userId;
	private String userName;
	// 任务数
	private Integer yCount;
	private Integer tCount;

	// 任务数
	private String yCountStr;
	private String tCountStr;

	// 任务名
	private String name;

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
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName
	 *            the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * @return the yCount
	 */
	public Integer getyCount() {
		return yCount;
	}

	/**
	 * @param yCount
	 *            the yCount to set
	 */
	public void setyCount(Integer yCount) {
		this.yCount = yCount;
	}

	/**
	 * @return the tCount
	 */
	public Integer gettCount() {
		return tCount;
	}

	/**
	 * @param tCount
	 *            the tCount to set
	 */
	public void settCount(Integer tCount) {
		this.tCount = tCount;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the yCountStr
	 */
	public String getyCountStr() {
		return yCountStr;
	}

	/**
	 * @param yCountStr
	 *            the yCountStr to set
	 */
	public void setyCountStr(String yCountStr) {
		this.yCountStr = yCountStr;
	}

	/**
	 * @return the tCountStr
	 */
	public String gettCountStr() {
		return tCountStr;
	}

	/**
	 * @param tCountStr
	 *            the tCountStr to set
	 */
	public void settCountStr(String tCountStr) {
		this.tCountStr = tCountStr;
	}

}
