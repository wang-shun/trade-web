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

	private String scheduleId; // 签约室安排id

	private Long roomId; // 房间id

	private String roomNo; // 房间号

	private String orgId; // 所在机构id

	private int numberOfPeople; // 房间容纳人数

	private Date startDate; // 开始预约日期

	private Date endDate; // 结束预约日期

	private String selDate; // 预约日期

	private String bespeakTime; // 预约时间段

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
