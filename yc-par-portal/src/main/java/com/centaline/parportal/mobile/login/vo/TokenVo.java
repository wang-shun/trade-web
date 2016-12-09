package com.centaline.parportal.mobile.login.vo;

import java.io.Serializable;
import java.util.Date;

import com.aist.uam.auth.remote.vo.SessionUser;

public class TokenVo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6465038795928508228L;
	private String token;
	//private MobileUserVo mobileUser;
	private String userId;
	private Date lastUpdate;// 最后更新时间
	private Date expDate;// 失效时间
	private Integer isExp = 0;// 是否过期
	private Integer expAccess;// 失效后访问次数
	private String preToken;// 上一个Token的ID

	private SessionUser sessionUser; //登录用户
	private String sessionId; //绑定sso中sessionId
	/**
	 * @return the lastUpdate
	 */
	public Date getLastUpdate() {
		return lastUpdate;
	}

	/**
	 * @param lastUpdate
	 *            the lastUpdate to set
	 */
	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	/**
	 * @return the expAccess
	 */
	public Integer getExpAccess() {
		return expAccess;
	}

	/**
	 * @param expAccess
	 *            the expAccess to set
	 */
	public void setExpAccess(Integer expAccess) {
		this.expAccess = expAccess;
	}

	/**
	 * @return the preToken
	 */
	public String getPreToken() {
		return preToken;
	}

	/**
	 * @param preToken
	 *            the preToken to set
	 */
	public void setPreToken(String preToken) {
		this.preToken = preToken;
	}

	/**
	 * @return the token
	 */
	public String getToken() {
		return token;
	}

	/**
	 * @param token
	 *            the token to set
	 */
	public void setToken(String token) {
		this.token = token;
	}

	/**
	 * @return the mobileUser
	 *//*
	public MobileUserVo getMobileUser() {
		return mobileUser;
	}

	*//**
	 * @param mobileUser
	 *            the mobileUser to set
	 *//*
	public void setMobileUser(MobileUserVo mobileUser) {
		this.mobileUser = mobileUser;
	}*/
	
	public SessionUser getSessionUser() {
		return sessionUser;
	}

	public void setSessionUser(SessionUser sessionUser) {
		this.sessionUser = sessionUser;
	}
	
	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	/**
	 * @return the expDate
	 */
	public Date getExpDate() {
		return expDate;
	}

	/**
	 * @param expDate
	 *            the expDate to set
	 */
	public void setExpDate(Date expDate) {
		this.expDate = expDate;
	}

	/**
	 * @return the isExp
	 */
	public Integer getIsExp() {
		return isExp;
	}

	/**
	 * @param isExp
	 *            the isExp to set
	 */
	public void setIsExp(Integer isExp) {
		this.isExp = isExp;
	}

	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * @param userId
	 *            the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

}
