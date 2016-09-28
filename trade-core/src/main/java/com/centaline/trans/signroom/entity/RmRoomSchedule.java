package com.centaline.trans.signroom.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 签约室调度表(T_RM_ROOM_SCHEDULE)
 * 
 * @author zhoujp7
 *
 */
public class RmRoomSchedule implements Serializable {

	private static final long serialVersionUID = -5268696243080489513L;
	private Long pkid;
	private Long roomId;// 房间ID
	private Date startDate;// 开始时间
	private Date endDate;// 结束时间
	private Long resId;// 预约单ID
	private String useStatus;// 使用状态  N:空置 Y:已预约
	private int canRes;// 是否可以预约  1:可预约 0:不可预约
	private int isDelete;// 是否删除
	private Date createTime;// 创建时间
	private String createBy;// 创建人
	private Date updateTime;// 更新时间
	private String updateBy;// 更新人
	
	private String timeSlot;//时间段

	public Long getPkid() {
		return pkid;
	}

	public void setPkid(Long pkid) {
		this.pkid = pkid;
	}

	public Long getRoomId() {
		return roomId;
	}

	public void setRoomId(Long roomId) {
		this.roomId = roomId;
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

	public Long getResId() {
		return resId;
	}

	public void setResId(Long resId) {
		this.resId = resId;
	}

	public String getUseStatus() {
		return useStatus;
	}

	public void setUseStatus(String useStatus) {
		this.useStatus = useStatus;
	}

	public int getCanRes() {
		return canRes;
	}

	public void setCanRes(int canRes) {
		this.canRes = canRes;
	}

	public int getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(int isDelete) {
		this.isDelete = isDelete;
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

	public String getTimeSlot() {
		return timeSlot;
	}

	public void setTimeSlot(String timeSlot) {
		this.timeSlot = timeSlot;
	}
	
	

}
