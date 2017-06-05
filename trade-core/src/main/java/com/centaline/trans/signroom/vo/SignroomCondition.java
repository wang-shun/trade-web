package com.centaline.trans.signroom.vo;

public class SignroomCondition {

	private Long tradeCenterId; // 交易中心id

	private String selDate; // 日期

	private String startDateTime; // 预约开始时间

	private String endDateTime; // 预约结束时间

	public Long getTradeCenterId() {
		return tradeCenterId;
	}

	public void setTradeCenterId(Long tradeCenterId) {
		this.tradeCenterId = tradeCenterId;
	}

	public String getSelDate() {
		return selDate;
	}

	public void setSelDate(String selDate) {
		this.selDate = selDate;
	}

	public String getStartDateTime() {
		return startDateTime;
	}

	public void setStartDateTime(String startDateTime) {
		this.startDateTime = startDateTime;
	}

	public String getEndDateTime() {
		return endDateTime;
	}

	public void setEndDateTime(String endDateTime) {
		this.endDateTime = endDateTime;
	}

}
