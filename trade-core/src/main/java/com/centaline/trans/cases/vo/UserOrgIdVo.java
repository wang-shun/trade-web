package com.centaline.trans.cases.vo;

import java.util.List;

import com.aist.uam.userorg.remote.vo.User;

public class UserOrgIdVo {

	private List<User> userList;
	private String orgId;
	
	
	public List<User> getUserList() {
		return userList;
	}
	public void setUserList(List<User> userList) {
		this.userList = userList;
	}
	public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	
}
