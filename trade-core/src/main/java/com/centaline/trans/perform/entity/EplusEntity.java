package com.centaline.trans.perform.entity;

import java.util.Date;

public class EplusEntity {
	private Long pkid;
	private String participantName;
	private String participantId;
	private String jobId;
	private String orgId;
	private Integer orders;
	private Integer kpiOrders;
	private Date belongMonth;
	private Date createTime;
	
	private Boolean isCalculated;
	private Boolean isDeleted;
	private Integer guohuOrder;
	private Double orderRate;

	public Boolean getIsCalculated() {
		return isCalculated;
	}

	public void setIsCalculated(Boolean isCalculated) {
		this.isCalculated = isCalculated;
	}

	public Boolean getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	

	public Double getOrderRate() {
		return orderRate;
	}

	public void setOrderRate(Double orderRate) {
		this.orderRate = orderRate;
	}

	public Long getPkid() {
		return pkid;
	}

	public void setPkid(Long pkid) {
		this.pkid = pkid;
	}

	public String getParticipantName() {
		return participantName;
	}

	public void setParticipantName(String participantName) {
		this.participantName = participantName;
	}

	public String getParticipantId() {
		return participantId;
	}

	public void setParticipantId(String participantId) {
		this.participantId = participantId;
	}

	public String getJobId() {
		return jobId;
	}

	public void setJobId(String jobId) {
		this.jobId = jobId;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public Integer getOrders() {
		return orders;
	}

	public void setOrders(Integer orders) {
		this.orders = orders;
	}

	public Integer getKpiOrders() {
		return kpiOrders;
	}

	public void setKpiOrders(Integer kpiOrders) {
		this.kpiOrders = kpiOrders;
	}

	public Date getBelongMonth() {
		return belongMonth;
	}

	public void setBelongMonth(Date belongMonth) {
		this.belongMonth = belongMonth;
	}

	public Integer getGuohuOrder() {
		return guohuOrder;
	}

	public void setGuohuOrder(Integer guohuOrder) {
		this.guohuOrder = guohuOrder;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}
