package com.centaline.trans.engine.bean;

public class RestStatus {
	private int statusCode;
	private boolean success;
	private String returnMsg;

	/**
	 * @return the returnMsg
	 */
	public String getReturnMsg() {
		return returnMsg;
	}

	/**
	 * @param returnMsg
	 *            the returnMsg to set
	 */
	public void setReturnMsg(String returnMsg) {
		this.returnMsg = returnMsg;
	}

	/**
	 * @return the statusCode
	 */
	public int getStatusCode() {
		return statusCode;
	}

	/**
	 * @param statusCode
	 *            the statusCode to set
	 */
	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	/**
	 * @return the success
	 */
	public boolean isSuccess() {
		return success;
	}

	/**
	 * @param success
	 *            the success to set
	 */
	public void setSuccess(boolean success) {
		this.success = success;
	}
}
