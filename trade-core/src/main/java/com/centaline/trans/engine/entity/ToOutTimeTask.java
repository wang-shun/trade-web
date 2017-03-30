package com.centaline.trans.engine.entity;

import java.util.Date;

public class ToOutTimeTask {
    private String userId;

    private String caseCode;
    private String instCode;
    private String userName;
    private String userRealName;
    private String partCode;
    private String partName;
    private String propertyAddr;
    private Date estPartTime;

	private Long planId;
	private String managerId;
	private String servManagerId;
	private String orgId;
	private String servOrgId;
    private String managerName;
    private String servManagerName;
	private Integer dateLamp;
	
    public String getManagerId() {
		return managerId;
	}

	public String getServManagerId() {
		return servManagerId;
	}

	public String getOrgId() {
		return orgId;
	}

	public String getServOrgId() {
		return servOrgId;
	}

	public String getManagerName() {
		return managerName;
	}

	public String getServManagerName() {
		return servManagerName;
	}

	public void setManagerId(String managerId) {
		this.managerId = managerId;
	}

	public void setServManagerId(String servManagerId) {
		this.servManagerId = servManagerId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public void setServOrgId(String servOrgId) {
		this.servOrgId = servOrgId;
	}

	public void setManagerName(String managerName) {
		this.managerName = managerName;
	}

	public void setServManagerName(String servManagerName) {
		this.servManagerName = servManagerName;
	}

	public String getUserId() {
		return userId;
	}

	public String getUserName() {
		return userName;
	}

	public String getPartCode() {
		return partCode;
	}

	public String getPartName() {
		return partName;
	}

	public String getPropertyAddr() {
		return propertyAddr;
	}

	public Date getEstPartTime() {
		return estPartTime;
	}

	public Integer getDateLamp() {
		return dateLamp;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setPartCode(String partCode) {
		this.partCode = partCode;
	}

	public void setPartName(String partName) {
		this.partName = partName;
	}

	public void setPropertyAddr(String propertyAddr) {
		this.propertyAddr = propertyAddr;
	}

	public void setEstPartTime(Date estPartTime) {
		this.estPartTime = estPartTime;
	}

	public void setDateLamp(Integer dateLamp) {
		this.dateLamp = dateLamp;
	}

    

    public String getCaseCode() {
        return caseCode;
    }

    public void setCaseCode(String caseCode) {
        this.caseCode = caseCode == null ? null : caseCode.trim();
    }

    public String getInstCode() {
        return instCode;
    }

    public void setInstCode(String instCode) {
        this.instCode = instCode == null ? null : instCode.trim();
    }

	public Long getPlanId() {
		return planId;
	}

	public void setPlanId(Long planId) {
		this.planId = planId;
	}

	public String getUserRealName() {
		return userRealName;
	}

	public void setUserRealName(String userRealName) {
		this.userRealName = userRealName;
	}

}