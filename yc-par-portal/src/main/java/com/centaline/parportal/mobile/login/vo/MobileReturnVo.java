package com.centaline.parportal.mobile.login.vo;

import java.io.Serializable;

/**
 * mobile返回对象
 * 
 * @author jjm
 * 
 */
public class MobileReturnVo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7262247494170737647L;
	private boolean isSuccess;
	private String msg;
	private String empId;
	private String token;
	private Object content;

	/**
	 * @return the isSuccess
	 */
	public boolean getIsSuccess() {
		return isSuccess;
	}

	/**
	 * @param isSuccess
	 *            the isSuccess to set
	 */
	public void setSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}

	/**
	 * @return the msg
	 */
	public String getMsg() {
		return msg;
	}

	/**
	 * @param msg
	 *            the msg to set
	 */
	public void setMsg(String msg) {
		this.msg = msg;
	}

	/**
	 * @return the empId
	 */
	public String getEmpId() {
		return empId;
	}

	/**
	 * @param empId
	 *            the empId to set
	 */
	public void setEmpId(String empId) {
		this.empId = empId;
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
	 * @return the content
	 */
	public Object getContent() {
		return content;
	}

	/**
	 * @param content
	 *            the content to set
	 */
	public void setContent(Object content) {
		this.content = content;
	}
}
