package com.centaline.trans.signroom.vo;

import java.util.Date;

/**
 * 闲置房间信息
 * 
 * @author yinjf2
 *
 */
public class FreeRoomInfo {

	private String isSuccess; // 是否预约成功标志(预约成功:true,预约失败:false,无闲置房间:noRoom)

	private String result; // 返回的结果信息

	private String scheduleId; // 签约室安排id

	private Long roomId; // 房间id

	private String roomNo; // 房间号

	private String resNo; // 预约编号

	private String orgId; // 所在机构id

	private int numberOfPeople; // 房间容纳人数

	private Date startDate; // 开始预约日期

	private Date endDate; // 结束预约日期

	private String selDate; // 预约日期

	private String bespeakTime; // 预约时间段

	private String useStatus; // 使用状态

	private String resStatus; // 预约状态

	private Date checkInTime; // 签到时间

	private Date checkOutTime; // 签退时间

	private Date createTime; // 预约信息创建时间

	public String getUseStatus() {
		return useStatus;
	}

	public void setUseStatus(String useStatus) {
		this.useStatus = useStatus;
	}

	public String getResStatus() {
		return resStatus;
	}

	public void setResStatus(String resStatus) {
		this.resStatus = resStatus;
	}

	public Date getCheckInTime() {
		return checkInTime;
	}

	public void setCheckInTime(Date checkInTime) {
		this.checkInTime = checkInTime;
	}

	public Date getCheckOutTime() {
		return checkOutTime;
	}

	public void setCheckOutTime(Date checkOutTime) {
		this.checkOutTime = checkOutTime;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getResNo() {
		return resNo;
	}

	public void setResNo(String resNo) {
		this.resNo = resNo;
	}

	public String getIsSuccess() {
		return isSuccess;
	}

	public void setIsSuccess(String isSuccess) {
		this.isSuccess = isSuccess;
	}

	public String getSelDate() {
		return selDate;
	}

	public void setSelDate(String selDate) {
		this.selDate = selDate;
	}

	public String getBespeakTime() {
		return bespeakTime;
	}

	public void setBespeakTime(String bespeakTime) {
		this.bespeakTime = bespeakTime;
	}

	public Long getRoomId() {
		return roomId;
	}

	public void setRoomId(Long roomId) {
		this.roomId = roomId;
	}

	public String getScheduleId() {
		return scheduleId;
	}

	public void setScheduleId(String scheduleId) {
		this.scheduleId = scheduleId;
	}

	public String getRoomNo() {
		return roomNo;
	}

	public void setRoomNo(String roomNo) {
		this.roomNo = roomNo;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public int getNumberOfPeople() {
		return numberOfPeople;
	}

	public void setNumberOfPeople(int numberOfPeople) {
		this.numberOfPeople = numberOfPeople;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

}
