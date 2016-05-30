package com.centaline.trans.report.entity;

import java.sql.Timestamp;

/**
 * 案件处理统计
 * 
 * @author maximum-wong
 *
 */
public class CaseProcessStatis {

	private Long pkid;
	private String caseCode; // 案件编号
	private String teamId; // 所属组织
	private String districtId; // 所属贵宾服务部
	private Timestamp receivedTime; // 接单时间
	private String receivedUser; // 接单人
	private String receivedTeamId; // 接单人组别
	private String receivedDistrictId;// 接单人所属贵宾服务部
	private Timestamp signTime;// 签约时间
	private String signUser; // 签约人
	private String signTeamId; // 签约人组织
	private String signDistrictId; // 签约人贵宾服务部
	private Timestamp houseTranferTime;// 过户时间
	private String houseTranferUser;// 过户人
	private String houseTranferTeamId;// 过户组别
	private String houseTranferDistrictId;// 过户贵宾服务部
	private Timestamp closeTime;// 结案时间
	private String closeUser;// 结案用户
	private String closeTeamId;// 结案店组
	private String closeDistrictId;// 结案贵宾服务部
	private Timestamp createTime;// 创建时间
	private String createBy;// 创建人
	private Timestamp updateTime;// 更新时间
	private String updateBy;// 更新人

	public Long getPkid() {
		return pkid;
	}

	public void setPkid(Long pkid) {
		this.pkid = pkid;
	}

	public String getCaseCode() {
		return caseCode;
	}

	public void setCaseCode(String caseCode) {
		this.caseCode = caseCode;
	}

	public String getTeamId() {
		return teamId;
	}

	public void setTeamId(String teamId) {
		this.teamId = teamId;
	}

	public String getDistrictId() {
		return districtId;
	}

	public void setDistrictId(String districtId) {
		this.districtId = districtId;
	}

	public Timestamp getReceivedTime() {
		return receivedTime;
	}

	public void setReceivedTime(Timestamp receivedTime) {
		this.receivedTime = receivedTime;
	}

	public String getReceivedUser() {
		return receivedUser;
	}

	public void setReceivedUser(String receivedUser) {
		this.receivedUser = receivedUser;
	}

	public String getReceivedTeamId() {
		return receivedTeamId;
	}

	public void setReceivedTeamId(String receivedTeamId) {
		this.receivedTeamId = receivedTeamId;
	}

	public String getReceivedDistrictId() {
		return receivedDistrictId;
	}

	public void setReceivedDistrictId(String receivedDistrictId) {
		this.receivedDistrictId = receivedDistrictId;
	}

	public Timestamp getSignTime() {
		return signTime;
	}

	public void setSignTime(Timestamp signTime) {
		this.signTime = signTime;
	}

	public String getSignUser() {
		return signUser;
	}

	public void setSignUser(String signUser) {
		this.signUser = signUser;
	}

	public String getSignTeamId() {
		return signTeamId;
	}

	public void setSignTeamId(String signTeamId) {
		this.signTeamId = signTeamId;
	}

	public String getSignDistrictId() {
		return signDistrictId;
	}

	public void setSignDistrictId(String signDistrictId) {
		this.signDistrictId = signDistrictId;
	}

	public Timestamp getHouseTranferTime() {
		return houseTranferTime;
	}

	public void setHouseTranferTime(Timestamp houseTranferTime) {
		this.houseTranferTime = houseTranferTime;
	}

	public String getHouseTranferUser() {
		return houseTranferUser;
	}

	public void setHouseTranferUser(String houseTranferUser) {
		this.houseTranferUser = houseTranferUser;
	}

	public String getHouseTranferTeamId() {
		return houseTranferTeamId;
	}

	public void setHouseTranferTeamId(String houseTranferTeamId) {
		this.houseTranferTeamId = houseTranferTeamId;
	}

	public String getHouseTranferDistrictId() {
		return houseTranferDistrictId;
	}

	public void setHouseTranferDistrictId(String houseTranferDistrictId) {
		this.houseTranferDistrictId = houseTranferDistrictId;
	}

	public Timestamp getCloseTime() {
		return closeTime;
	}

	public void setCloseTime(Timestamp closeTime) {
		this.closeTime = closeTime;
	}

	public String getCloseUser() {
		return closeUser;
	}

	public void setCloseUser(String closeUser) {
		this.closeUser = closeUser;
	}

	public String getCloseTeamId() {
		return closeTeamId;
	}

	public void setCloseTeamId(String closeTeamId) {
		this.closeTeamId = closeTeamId;
	}

	public String getCloseDistrictId() {
		return closeDistrictId;
	}

	public void setCloseDistrictId(String closeDistrictId) {
		this.closeDistrictId = closeDistrictId;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public Timestamp getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

	public String getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

}

/*
 * <result column="CASE_CODE" jdbcType="VARCHAR" property="caseCode" /> <result
 * column="TEAM_ID" jdbcType="VARCHAR" property="teamId" /> <result
 * column="DISTRICT_ID" jdbcType="VARCHAR" property="districtId" /> <result
 * column="RECEIVED_TIME" jdbcType="TIMESTAMP" property="receivedTime" />
 * <result column="RECEIVED_USER" jdbcType="VARCHAR" property="receivedUser" />
 * <result column="RECEIVED_TEAM_ID" jdbcType="VARCHAR"
 * property="receivedTeamId" /> <result column="RECEIVED_DISTRICT_ID"
 * jdbcType="VARCHAR" property="receivedDistrictId" /> <result
 * column="SIGN_TIME" jdbcType="TIMESTAMP" property="signTime" /> <result
 * column="SIGN_USER" jdbcType="VARCHAR" property="signUser" /> <result
 * column="SIGN_TEAM_ID" jdbcType="VARCHAR" property="signteamId" /> <result
 * column="SIGN_DISTRICT_ID" jdbcType="VARCHAR" property="signDistrictId" />
 * <result column="HOUSE_TRANFER_TIME" jdbcType="TIMESTAMP"
 * property="houseTranferTime" /> <result column="HOUSE_TRANFER_USER"
 * jdbcType="VARCHAR" property="houseTranferUser" /> <result
 * column="HOUSE_TRANFER_TEAM_ID" jdbcType="VARCHAR"
 * property="houseTranferTeamId" /> <result column="HOUSE_TRANFER_DISTRICT_ID"
 * jdbcType="VARCHAR" property="houseTranferDistrictId" /> <result
 * column="CLOSE_TIME" jdbcType="TIMESTAMP" property="closeTime" /> <result
 * column="CLOSE_USER" jdbcType="VARCHAR" property="closeUser"/> <result
 * column="CLOSE_TEAM_ID" jdbcType="VARCHAR" property="closeTeamId"/> <result
 * column="CLOSE_DISTRICT_ID" jdbcType="VARCHAR" property="closeDistrictId"/>
 * <result column="CREATE_TIME" jdbcType="TIMESTAMP" property="createTime"/>
 * <result column="CREATE_BY" jdbcType="VARCHAR" property="createBy"/> <result
 * column="UPDATE_TIME" jdbcType="TIMESTAMP" property="upTIMESTAMP"/> <result
 * column="UPDATE_BY" jdbcType="VARCHAR" property="updateBy"/>
 */