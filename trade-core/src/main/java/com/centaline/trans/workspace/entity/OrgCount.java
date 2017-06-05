package com.centaline.trans.workspace.entity;

import java.io.Serializable;

public class OrgCount implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -861337232274696495L;
	private String orgName;
	private Integer count;
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
}
