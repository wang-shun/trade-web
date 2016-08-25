package com.centaline.trans.api.service;

public class SalesApiResponse {
	
	
	private boolean success;
	private String code;
	private String message;
	public boolean getSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	@Override
	public String toString() {
		return "SalesApiResponse [success=" + success + ", code=" + code + ", message=" + message + "]";
	}
}
