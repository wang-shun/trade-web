package com.centaline.trans.api.vo;

/**
 * 采用继承的方式
 * 减少数据的一个层级
 * @author yinchao
 * @date 2017/9/14
 */
public abstract class ApiResultData {
	private boolean success;
	private String message;

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

	@Override
	public String toString() {
		return "ApiResultData{" +
				"success=" + success +
				", message='" + message + '\'' +
				'}';
	}
}
