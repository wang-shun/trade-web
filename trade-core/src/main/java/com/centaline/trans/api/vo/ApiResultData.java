package com.centaline.trans.api.vo;

/**
 * 通用API返回结果对象
 * T为返回的结果对象
 * @author yinchao
 * @date 2017/9/14
 */
public class ApiResultData<T> {
	private boolean success;
	private String message;
	private T data;

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}
}
