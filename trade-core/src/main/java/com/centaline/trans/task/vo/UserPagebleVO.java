package com.centaline.trans.task.vo;

import com.aist.common.quickQuery.bo.JQGridParam;

public class UserPagebleVO extends JQGridParam {
	private String username;

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username
	 *            the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}
}
