package com.centaline.trans.signroom.entity;

import java.util.Date;

/**
 * 预约取号信息
 * 
 * @author yinjf2
 *
 */
public class Reservation {

	private Long pkid;

	private String resNo; // 预约编号

	private String resType; // 预约类型 0:经纪人预约,1:管理员临时预约

	private Date checkInTime; // 签到时间

	private Date checkedOutTime; // 签出时间

	private String resPersonOrgId; // 预约人组织id

	private String resPersonId; // 预约人id

	private String resStatus; // 预约状态(预约中:0,使用中:1,使用完:2,已过期:3,已取消:4)

	private String scheduleId; // 签约室安排id

	private String caseCode; // 案件编号

	private String propertyAddress; // 产证地址

	private String signingCenter; // 签约中心(交易中心)

	private Long signingCenterId; // 签约中心标识

	private Integer numberOfParticipants; // 参与人数

	private String transactItemCode; // 办理事项编号(签合同:contract,办贷款:doLoan,E+贷款:Eloan)

	private String specialRequirement; // 特殊需求

	private Date createTime; // 创建时间

	private String createBy; // 创建人

	private Date updateTime; // 更新时间

	private String updateBy; // 更新人

	public Long getSigningCenterId() {
		return signingCenterId;
	}

	public void setSigningCenterId(Long signingCenterId) {
		this.signingCenterId = signingCenterId;
	}

	public Long getPkid() {
		return pkid;
	}

	public void setPkid(Long pkid) {
		this.pkid = pkid;
	}

	public String getResNo() {
		return resNo;
	}

	public void setResNo(String resNo) {
		this.resNo = resNo == null ? null : resNo.trim();
	}

	public String getResType() {
		return resType;
	}

	public void setResType(String resType) {
		this.resType = resType == null ? null : resType.trim();
	}

	public Date getCheckInTime() {
		return checkInTime;
	}

	public void setCheckInTime(Date checkInTime) {
		this.checkInTime = checkInTime;
	}

	public Date getCheckedOutTime() {
		return checkedOutTime;
	}

	public void setCheckedOutTime(Date checkedOutTime) {
		this.checkedOutTime = checkedOutTime;
	}

	public String getResPersonOrgId() {
		return resPersonOrgId;
	}

	public void setResPersonOrgId(String resPersonOrgId) {
		this.resPersonOrgId = resPersonOrgId == null ? null : resPersonOrgId
				.trim();
	}

	public String getResPersonId() {
		return resPersonId;
	}

	public void setResPersonId(String resPersonId) {
		this.resPersonId = resPersonId == null ? null : resPersonId.trim();
	}

	public String getResStatus() {
		return resStatus;
	}

	public void setResStatus(String resStatus) {
		this.resStatus = resStatus == null ? null : resStatus.trim();
	}

	public String getScheduleId() {
		return scheduleId;
	}

	public void setScheduleId(String scheduleId) {
		this.scheduleId = scheduleId == null ? null : scheduleId.trim();
	}

	public String getCaseCode() {
		return caseCode;
	}

	public void setCaseCode(String caseCode) {
		this.caseCode = caseCode == null ? null : caseCode.trim();
	}

	public String getPropertyAddress() {
		return propertyAddress;
	}

	public void setPropertyAddress(String propertyAddress) {
		this.propertyAddress = propertyAddress == null ? null : propertyAddress
				.trim();
	}

	public String getSigningCenter() {
		return signingCenter;
	}

	public void setSigningCenter(String signingCenter) {
		this.signingCenter = signingCenter == null ? null : signingCenter
				.trim();
	}

	public Integer getNumberOfParticipants() {
		return numberOfParticipants;
	}

	public void setNumberOfParticipants(Integer numberOfParticipants) {
		this.numberOfParticipants = numberOfParticipants;
	}

	public String getTransactItemCode() {
		return transactItemCode;
	}

	public void setTransactItemCode(String transactItemCode) {
		this.transactItemCode = transactItemCode == null ? null
				: transactItemCode.trim();
	}

	public String getSpecialRequirement() {
		return specialRequirement;
	}

	public void setSpecialRequirement(String specialRequirement) {
		this.specialRequirement = specialRequirement == null ? null
				: specialRequirement.trim();
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy == null ? null : createBy.trim();
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy == null ? null : updateBy.trim();
	}

}