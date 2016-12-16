package com.centaline.trans.common.entity;

public class CaseMergerParameter {
	
	private String id;
	
	private String pkId;
    
	private String caseCode;
	
	private String propertyAddr;
	
	private String agentName;
	
	private String agentPhone;
	
	private String agentOrgName;
	
	private String seller;
	
	private String buyer;
	
	private String mergePkid;
	
	private String type;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getMergePkid() {
		return mergePkid;
	}

	public void setMergePkid(String mergePkid) {
		this.mergePkid = mergePkid;
	}

	public String getPkId() {
		return pkId;
	}

	public void setPkId(String pkId) {
		this.pkId = pkId;
	}

	public String getCaseCode() {
		return caseCode;
	}

	public void setCaseCode(String caseCode) {
		this.caseCode = caseCode;
	}

	public String getPropertyAddr() {
		return propertyAddr;
	}

	public void setPropertyAddr(String propertyAddr) {
		this.propertyAddr = propertyAddr;
	}

	public String getAgentName() {
		return agentName;
	}

	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}

	public String getAgentPhone() {
		return agentPhone;
	}

	public void setAgentPhone(String agentPhone) {
		this.agentPhone = agentPhone;
	}

	public String getAgentOrgName() {
		return agentOrgName;
	}

	public void setAgentOrgName(String agentOrgName) {
		this.agentOrgName = agentOrgName;
	}

	public String getSeller() {
		return seller;
	}

	public void setSeller(String seller) {
		this.seller = seller;
	}

	public String getBuyer() {
		return buyer;
	}

	public void setBuyer(String buyer) {
		this.buyer = buyer;
	}

}