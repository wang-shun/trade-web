package com.centaline.trans.signroom.vo;

import java.util.Date;

/**
 * 临时分配过渡类
 * @author zhoujp7
 *
 */
public class ReservationInfoVo {

	private String resNo; // 预约编号

	private String resType; // 预约类型 0:经纪人预约,1:管理员临时预约

	private String resPersonOrgId; // 预约人组织id

	private String resPersonId; // 预约人id

	private String resStatus; // 预约状态(预约中:0,使用中:1,使用完:2,已过期:3,已取消:4)

	private String scheduleId; // 签约室安排id

	private String caseCode; // 案件编号

	private String propertyAddress; // 产证地址

	private String signingCenter; // 签约中心(交易中心)

	private Integer numberOfParticipants; // 参与人数

	private String transactItemCode; // 办理事项编号(签合同:contract,办贷款:doLoan,E+贷款:Eloan)

	private String specialRequirement; // 特殊需求

	private Date createTime; // 创建时间

	private String createBy; // 创建人

	private Date updateTime; // 更新时间

	private String updateBy; // 更新人
	
	private Long roomId;//房间编号
	
	private String startDate;//房间排期开始时间
	
	private String endDate;//房间排期结束时间

	public String getResNo() {
		return resNo;
	}

	public void setResNo(String resNo) {
		this.resNo = resNo;
	}

	public String getResType() {
		return resType;
	}

	public void setResType(String resType) {
		this.resType = resType;
	}

	public String getResPersonOrgId() {
		return resPersonOrgId;
	}

	public void setResPersonOrgId(String resPersonOrgId) {
		this.resPersonOrgId = resPersonOrgId;
	}

	public String getResPersonId() {
		return resPersonId;
	}

	public void setResPersonId(String resPersonId) {
		this.resPersonId = resPersonId;
	}

	public String getResStatus() {
		return resStatus;
	}

	public void setResStatus(String resStatus) {
		this.resStatus = resStatus;
	}

	public String getScheduleId() {
		return scheduleId;
	}

	public void setScheduleId(String scheduleId) {
		this.scheduleId = scheduleId;
	}

	public String getCaseCode() {
		return caseCode;
	}

	public void setCaseCode(String caseCode) {
		this.caseCode = caseCode;
	}

	public String getPropertyAddress() {
		return propertyAddress;
	}

	public void setPropertyAddress(String propertyAddress) {
		this.propertyAddress = propertyAddress;
	}

	public String getSigningCenter() {
		return signingCenter;
	}

	public void setSigningCenter(String signingCenter) {
		this.signingCenter = signingCenter;
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
		this.transactItemCode = transactItemCode;
	}

	public String getSpecialRequirement() {
		return specialRequirement;
	}

	public void setSpecialRequirement(String specialRequirement) {
		this.specialRequirement = specialRequirement;
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
		this.createBy = createBy;
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
		this.updateBy = updateBy;
	}

	public Long getRoomId() {
		return roomId;
	}

	public void setRoomId(Long roomId) {
		this.roomId = roomId;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	
	
	

}
