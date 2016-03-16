package com.centaline.trans.task.entity;

import java.util.List;

import com.aist.uam.userorg.remote.vo.User;


public class ToPropertyResearchVo {
	private String caseCode;
	private String prCat;
	private String district;
	private String districtId;
	private String username;
	
	/**显示提示电话List*/
	private List<User>userList;

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

	public List<User> getUserList() {
		return userList;
	}

	public void setUserList(List<User> userList) {
		this.userList = userList;
	}

	public String getDistrictId() {
		return districtId;
	}

	public void setDistrictId(String districtId) {
		this.districtId = districtId;
	}

}