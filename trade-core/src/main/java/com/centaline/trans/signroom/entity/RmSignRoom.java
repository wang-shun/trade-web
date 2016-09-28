package com.centaline.trans.signroom.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 签约室(T_RM_SIGN_ROOM)
 * 
 * @author zhoujp7
 *
 */
public class RmSignRoom implements Serializable {

	private static final long serialVersionUID = 2713653054466978616L;

	private Long pkid;
	private String roomType;// 房间类型 0；普通房间 1：机动房间
	private String roomNo;// 房间编号
	private String tradeCenter;// 交易中心
	private Long tradeCenterId;// 交易中心ID
	private int numbeOfAccommodatePeople;// 可容纳人数
	private String remark;// 备注
	private Date closeTime;// 关闭时间
	private String roomStatus;// 房间状态 0:关闭 1:开放
	private int isDelete;// 是否删除
	private Date createTime;// 创建时间
	private String createBy;// 创建人
	private Date updateTime;// 更新时间
	private String updateBy;// 更新人
	
	private List<RmRoomSchedule> rmRoomSchedules;//签约室对应的排期
	
	private Long stragegyPkid;//策略值pkid
	private Long stragegyWeekVal;//策略值
	private Map weeks;//测量值对应选中的星期几
	private String districtName;//所属贵宾服务部
	
	

	public Long getPkid() {
		return pkid;
	}

	public void setPkid(Long pkid) {
		this.pkid = pkid;
	}

	public String getRoomType() {
		return roomType;
	}

	public void setRoomType(String roomType) {
		this.roomType = roomType;
	}

	public String getRoomNo() {
		return roomNo;
	}

	public void setRoomNo(String roomNo) {
		this.roomNo = roomNo;
	}

	public String getTradeCenter() {
		return tradeCenter;
	}

	public void setTradeCenter(String tradeCenter) {
		this.tradeCenter = tradeCenter;
	}

	public Long getTradeCenterId() {
		return tradeCenterId;
	}

	public void setTradeCenterId(Long tradeCenterId) {
		this.tradeCenterId = tradeCenterId;
	}

	public int getNumbeOfAccommodatePeople() {
		return numbeOfAccommodatePeople;
	}

	public void setNumbeOfAccommodatePeople(int numbeOfAccommodatePeople) {
		this.numbeOfAccommodatePeople = numbeOfAccommodatePeople;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Date getCloseTime() {
		return closeTime;
	}

	public void setCloseTime(Date closeTime) {
		this.closeTime = closeTime;
	}

	public String getRoomStatus() {
		return roomStatus;
	}

	public void setRoomStatus(String roomStatus) {
		this.roomStatus = roomStatus;
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

	public List<RmRoomSchedule> getRmRoomSchedules() {
		return rmRoomSchedules;
	}

	public void setRmRoomSchedules(List<RmRoomSchedule> rmRoomSchedules) {
		this.rmRoomSchedules = rmRoomSchedules;
	}

	public Long getStragegyPkid() {
		return stragegyPkid;
	}

	public void setStragegyPkid(Long stragegyPkid) {
		this.stragegyPkid = stragegyPkid;
	}

	public Long getStragegyWeekVal() {
		return stragegyWeekVal;
	}

	public void setStragegyWeekVal(Long stragegyWeekVal) {
		this.stragegyWeekVal = stragegyWeekVal;
	}

	public Map getWeeks() {
		return weeks;
	}

	public void setWeeks(Map weeks) {
		this.weeks = weeks;
	}

	public String getDistrictName() {
		return districtName;
	}

	public void setDistrictName(String districtName) {
		this.districtName = districtName;
	}

	
	
	
	
	

}
