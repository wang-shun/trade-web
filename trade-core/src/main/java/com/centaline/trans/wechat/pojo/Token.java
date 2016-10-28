package com.centaline.trans.wechat.pojo;

import java.util.Date;

/**
 * 凭证
 * 
 * @author liyufeng
 * @date 20141015
 */
public class Token {
	// 接口访问凭证
	private String accessToken;
	// 凭证有效期，单位：秒
	private int expiresIn;
	// 过期时间
	private Date expiresDate;

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public int getExpiresIn() {
		return expiresIn;
	}

	public void setExpiresIn(int expiresIn) {
		this.expiresIn = expiresIn;
	}

	/**
	 * @return the expiresDate
	 */
	public Date getExpiresDate() {
		return expiresDate;
	}

	/**
	 * @param expiresDate
	 *            the expiresDate to set
	 */
	public void setExpiresDate(Date expiresDate) {
		this.expiresDate = expiresDate;
	}
}
