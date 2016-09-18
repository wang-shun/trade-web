package com.centaline.trans.signroom.entity;

import java.io.Serializable;

/**
 * 签约中心表(T_RM_TRADE_CENTER)
 * 
 * @author zhoujp7
 *
 */
public class RmTradeCenter implements Serializable {

	private static final long serialVersionUID = 7557099224615946633L;
	private Long pkid;
	private String orgId;// 归属贵宾服务中心ID
	private String orgName;// 归属贵宾服务中心
	private String centerName;// 交易中心名
	private String centerAddress;// 中心地址

	public Long getPkid() {
		return pkid;
	}

	public void setPkid(Long pkid) {
		this.pkid = pkid;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getCenterName() {
		return centerName;
	}

	public void setCenterName(String centerName) {
		this.centerName = centerName;
	}

	public String getCenterAddress() {
		return centerAddress;
	}

	public void setCenterAddress(String centerAddress) {
		this.centerAddress = centerAddress;
	}

}
