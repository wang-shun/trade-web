package com.centaline.trans.signroom.entity;

import java.util.Date;

/**
 * 交易中心值班表
 * @author zhoujp7
 *
 */
public class TradeCenterSchedule {
	private Long pkid; 
	private String dutyOfficer; //值班人员ID
	private String dutyDate; //值班日期
	private Long tradeCenterId;//交易中心ID
	private String dutyType;//值班类型
	private Date createTime;// 创建时间
	private String createBy;// 创建人
	private Date updateTime;// 更新时间
	private String updateBy;// 更新人

	public Long getPkid() {
		return pkid;
	}

	public void setPkid(Long pkid) {
		this.pkid = pkid;
	}

	public String getDutyOfficer() {
		return dutyOfficer;
	}

	public void setDutyOfficer(String dutyOfficer) {
		this.dutyOfficer = dutyOfficer;
	}

	public String getDutyDate() {
		return dutyDate;
	}

	public void setDutyDate(String dutyDate) {
		this.dutyDate = dutyDate;
	}

	public Long getTradeCenterId() {
		return tradeCenterId;
	}

	public void setTradeCenterId(Long tradeCenterId) {
		this.tradeCenterId = tradeCenterId;
	}

	public String getDutyType() {
		return dutyType;
	}

	public void setDutyType(String dutyType) {
		this.dutyType = dutyType;
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

	
}