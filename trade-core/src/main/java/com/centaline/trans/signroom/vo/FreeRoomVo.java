package com.centaline.trans.signroom.vo;

/**
 * 闲置房间查询条件对象
 * 
 * @author yinjf2
 *
 */
public class FreeRoomVo {

	private Long tradeCenterId; // 交易中心标识

	private int numberOfParticipants; // 参与人数

	private String startDate; // 开始预约时间

	private String endDate; // 结束预约时间

	private Long resId; // 预约单id

	private String scheduleId; // 签约室安排id

	private String resOrgId; // 归属组织ID

	private String signingCenter; // 签约中心

	public String getResOrgId() {
		return resOrgId;
	}

	public void setResOrgId(String resOrgId) {
		this.resOrgId = resOrgId;
	}

	public String getSigningCenter() {
		return signingCenter;
	}

	public void setSigningCenter(String signingCenter) {
		this.signingCenter = signingCenter;
	}

	public String getScheduleId() {
		return scheduleId;
	}

	public void setScheduleId(String scheduleId) {
		this.scheduleId = scheduleId;
	}

	public Long getResId() {
		return resId;
	}

	public void setResId(Long resId) {
		this.resId = resId;
	}

	public int getNumberOfParticipants() {
		return numberOfParticipants;
	}

	public void setNumberOfParticipants(int numberOfParticipants) {
		this.numberOfParticipants = numberOfParticipants;
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

	public Long getTradeCenterId() {
		return tradeCenterId;
	}

	public void setTradeCenterId(Long tradeCenterId) {
		this.tradeCenterId = tradeCenterId;
	}

}
