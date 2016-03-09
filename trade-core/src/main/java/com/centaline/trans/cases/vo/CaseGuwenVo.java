package com.centaline.trans.cases.vo;

/**
 * 功能：交易顾问查询接口的 vo
 * @author zhangxb16
 */

public class CaseGuwenVo {
	
	private String guwenID;  // 顾问ID
	private String guwenName;  // 顾问姓名
	private String orgCode;  // 组织code
	private String orgName;  // 组织名称
	
	
	public String getGuwenID() {
		return guwenID;
	}
	public void setGuwenID(String guwenID) {
		this.guwenID = guwenID;
	}
	public String getGuwenName() {
		return guwenName;
	}
	public void setGuwenName(String guwenName) {
		this.guwenName = guwenName;
	}
	public String getOrgCode() {
		return orgCode;
	}
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	
	
}
