package com.centaline.trans.task.entity;

import java.util.List;

import com.aist.uam.userorg.remote.vo.User;

public class ToPropertyResearchVo {
	private String caseCode;
	private String prCat;
	private String district;
	private String districtId;
	private String username;
	private String prCode;
	private String prChannel;

	private String prApplyOrgId;
	private String prApplyOrgName;
	private String prCostOrgId;
	private String prCostOrgName;
	private String prCostOrgMgr;
	
	private String agentCode;
	
	private String appliant;
	
	//记录产调申请人的组织
	private String prApplyDepId;

	private String prApplyDepName;

	
	public String getAgentCode(){
		return this.agentCode;
	}
	public void setAgentCode(String agentCode){
		this.agentCode=agentCode;
	}

	public String getPrApplyOrgId() {
		return prApplyOrgId;
	}

	public void setPrApplyOrgId(String prApplyOrgId) {
		this.prApplyOrgId = prApplyOrgId;
	}

	public String getPrApplyOrgName() {
		return prApplyOrgName;
	}

	public void setPrApplyOrgName(String prApplyOrgName) {
		this.prApplyOrgName = prApplyOrgName;
	}

	public String getPrCostOrgId() {
		return prCostOrgId;
	}

	public void setPrCostOrgId(String prCostOrgId) {
		this.prCostOrgId = prCostOrgId;
	}

	public String getPrCostOrgName() {
		return prCostOrgName;
	}

	public void setPrCostOrgName(String prCostOrgName) {
		this.prCostOrgName = prCostOrgName;
	}

	public String getPrCostOrgMgr() {
		return prCostOrgMgr;
	}

	public void setPrCostOrgMgr(String prCostOrgMgr) {
		this.prCostOrgMgr = prCostOrgMgr;
	}

	/** 显示提示电话List */
	private List<User> userList;

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
	 * @param caseCode
	 *            the caseCode to set
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

	public String getPrCode() {
		return prCode;
	}

	public void setPrCode(String prCode) {
		this.prCode = prCode;
	}

	public String getPrChannel() {
		return prChannel;
	}

	public void setPrChannel(String prChannel) {
		this.prChannel = prChannel;
	}

	public String getAppliant() {
		return appliant;
	}

	public void setAppliant(String appliant) {
		this.appliant = appliant;
	}
	
	public String getPrApplyDepId() {
		return prApplyDepId;
	}
	public void setPrApplyDepId(String prApplyDepId) {
		this.prApplyDepId = prApplyDepId;
	}
	public String getPrApplyDepName() {
		return prApplyDepName;
	}
	public void setPrApplyDepName(String prApplyDepName) {
		this.prApplyDepName = prApplyDepName;
	}

}