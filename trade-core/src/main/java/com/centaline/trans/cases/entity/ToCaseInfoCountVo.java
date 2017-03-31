package com.centaline.trans.cases.entity;

import java.util.Date;

public class ToCaseInfoCountVo {

	/*创建日期*/
	private String createTime;
	/*用户id*/
	private String userId;
	/*接单数*/
	private Integer countJDS=0;
	/*签约数*/
	private Integer countQYS=0;
	/*过户数*/
	private Integer countGHS=0;
	/*结案数*/
	private Integer countJAS=0;
	/*组织名称*/
	private String orgName;
	
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public Integer getCountJDS() {
		return countJDS;
	}
	public void setCountJDS(Integer countJDS) {
		this.countJDS = countJDS;
	}
	public Integer getCountQYS() {
		return countQYS;
	}
	public void setCountQYS(Integer countQYS) {
		this.countQYS = countQYS;
	}
	public Integer getCountGHS() {
		return countGHS;
	}
	public void setCountGHS(Integer countGHS) {
		this.countGHS = countGHS;
	}
	public Integer getCountJAS() {
		return countJAS;
	}
	public void setCountJAS(Integer countJAS) {
		this.countJAS = countJAS;
	}
	
	
}
