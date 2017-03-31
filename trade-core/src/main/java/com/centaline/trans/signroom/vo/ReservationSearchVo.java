package com.centaline.trans.signroom.vo;

/**
 * 签约室预约查询条件
 * 
 * @author yinjf2
 *
 */
public class ReservationSearchVo {

	private Long tradeCenterId; // 交易中心标识

	private String startTime; // 预约开始时间

	private String endTime; // 预约结束时间

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

	public Long getTradeCenterId() {
		return tradeCenterId;
	}

	public void setTradeCenterId(Long tradeCenterId) {
		this.tradeCenterId = tradeCenterId;
	}

}
