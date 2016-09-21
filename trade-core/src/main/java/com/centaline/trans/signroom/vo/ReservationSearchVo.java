package com.centaline.trans.signroom.vo;

/**
 * 签约室预约查询条件
 * 
 * @author yinjf2
 *
 */
public class ReservationSearchVo {

	private String orgId; // 机构标识

	private String startTime; // 预约开始时间

	private String endTime; // 预约结束时间

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

}
