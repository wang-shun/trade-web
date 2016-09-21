package com.centaline.trans.signroom.entity;

/**
 * 交易中心信息
 * 
 * @author yinjf2
 *
 */
public class TradeCenter {
	private Long pkid; // 交易中心标识

	private String orgId; // 贵宾服务部id

	private String orgName; // 贵宾服务部

	private String centerName; // 交易中心名

	private String centerAddress; // 交易中心地址

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
		this.orgId = orgId == null ? null : orgId.trim();
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName == null ? null : orgName.trim();
	}

	public String getCenterName() {
		return centerName;
	}

	public void setCenterName(String centerName) {
		this.centerName = centerName == null ? null : centerName.trim();
	}

	public String getCenterAddress() {
		return centerAddress;
	}

	public void setCenterAddress(String centerAddress) {
		this.centerAddress = centerAddress == null ? null : centerAddress
				.trim();
	}
}