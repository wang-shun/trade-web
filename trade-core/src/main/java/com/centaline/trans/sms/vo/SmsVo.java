package com.centaline.trans.sms.vo;

import java.util.List;

public class SmsVo {
	/* 内容 */
	private String content;
	/* 电话 */
	private String phone;
	/* 用户标识 */
	private String userFlag;
	/* 用户姓名 */
	private String userName;
	private List<String> templateCodes;

	/**
	 * @return the content
	 */
	public String getContent() {
		return content;
	}

	/**
	 * @param content
	 *            the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * @return the phone
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * @param phone
	 *            the phone to set
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * @return the templateCodes
	 */
	public List<String> getTemplateCodes() {
		return templateCodes;
	}

	/**
	 * @param templateCodes
	 *            the templateCodes to set
	 */
	public void setTemplateCodes(List<String> templateCodes) {
		this.templateCodes = templateCodes;
	}

	/**
	 * @return the userFlag
	 */
	public String getUserFlag() {
		return userFlag;
	}

	/**
	 * @param userFlag
	 *            the userFlag to set
	 */
	public void setUserFlag(String userFlag) {
		this.userFlag = userFlag;
	}

	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName
	 *            the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

}
