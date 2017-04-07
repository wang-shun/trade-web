package com.centaline.trans.workspace.entity;

import java.io.Serializable;

public class UserLightCount implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8351231790748127178L;
	private String eCode;
	private String realName;
	private Integer count;
	public String geteCode() {
		return eCode;
	}
	public void seteCode(String eCode) {
		this.eCode = eCode;
	}
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
}
