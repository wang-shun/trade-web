package com.centaline.api.ccai.cases.vo;

import org.hibernate.validator.constraints.NotBlank;

import com.centaline.api.enums.CaseSyncParticipantEnum;

/**
 * 需要导入的CCAI案件基本信息
 * 主要包含
 * 成交经纪人信息
 * 成交部门信息
 * 成交部门管理人信息
 * 业务部门信息
 * 业务部门管理人信息
 * 业务权证信息
 * 战区负责人信息
 * 
 * @author yinchao
 *
 */
public class CcaiImportCaseInfo {
	/** 案件基本信息 类型 
	 * 	@see CaseSyncParticipantEnum
	 * */
	private String position;
	/**办理人域账号*/
	private String userName;
	/**办理人名称*/
	private String realName;
	/**办理人手机号*/
	private String mobile;
	/**对应部门HROC编码*/
	private String grpCode;
	/**对应部门名称*/
	private String grpName;
	/**部门管理人域账号*/
	private String grpMgrUserName;
	/**部门管理人名称*/
	private String grpMgrRealName;
	/**部门管理人手机号*/
	private String grpMgrMobile;
	@NotBlank(message="参与人类型不能为空")
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getGrpCode() {
		return grpCode;
	}
	public void setGrpCode(String grpCode) {
		this.grpCode = grpCode;
	}
	public String getGrpName() {
		return grpName;
	}
	public void setGrpName(String grpName) {
		this.grpName = grpName;
	}
	public String getGrpMgrUserName() {
		return grpMgrUserName;
	}
	public void setGrpMgrUserName(String grpMgrUserName) {
		this.grpMgrUserName = grpMgrUserName;
	}
	public String getGrpMgrRealName() {
		return grpMgrRealName;
	}
	public void setGrpMgrRealName(String grpMgrRealName) {
		this.grpMgrRealName = grpMgrRealName;
	}
	public String getGrpMgrMobile() {
		return grpMgrMobile;
	}
	public void setGrpMgrMobile(String grpMgrMobile) {
		this.grpMgrMobile = grpMgrMobile;
	}
	@Override
	public String toString() {
		return "CcaiImportCaseInfo [position=" + position + ", userName=" + userName + ", realName=" + realName
				+ ", mobile=" + mobile + ", grpCode=" + grpCode + ", grpName=" + grpName + ", grpMgrUserName="
				+ grpMgrUserName + ", grpMgrRealName=" + grpMgrRealName + ", grpMgrMobile=" + grpMgrMobile + "]";
	}
}
