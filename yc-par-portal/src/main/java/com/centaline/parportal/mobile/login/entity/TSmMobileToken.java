package com.centaline.parportal.mobile.login.entity;

import java.util.Date;

public class TSmMobileToken {
	
	/**
	 * token
	 */
	private String token;

	/**
	 * 员工Id
	 */
	private String userid;

	/**
	 * 过期时间
	 */
	private Date expdate;

	/**
	 * 是否有效  isexp=0 有效，isexp=1 无效
	 */
	private Integer isexp;

	/**
	 * 过期后使用次数
	 */
	private Integer expaccess;

	/**
	 * 过期token
	 */
	private String pretoken;
	
	/**
	 * 对应dbo.SYS_XAPP_SESSION表的sessionId 取登录/切岗用户信息
	 */
	private String sessionId;
	
	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token == null ? null : token.trim();
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid == null ? null : userid.trim();
	}

	public Date getExpdate() {
		return expdate;
	}

	public void setExpdate(Date expdate) {
		this.expdate = expdate;
	}

	public Integer getIsexp() {
		return isexp;
	}

	public void setIsexp(Integer isexp) {
		this.isexp = isexp;
	}

	public Integer getExpaccess() {
		return expaccess;
	}

	public void setExpaccess(Integer expaccess) {
		this.expaccess = expaccess;
	}

	public String getPretoken() {
		return pretoken;
	}

	public void setPretoken(String pretoken) {
		this.pretoken = pretoken == null ? null : pretoken.trim();
	}
}