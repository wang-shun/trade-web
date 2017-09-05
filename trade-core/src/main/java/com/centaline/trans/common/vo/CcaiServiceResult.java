package com.centaline.trans.common.vo;
/**
 * ccai服务使用返回相关服务的操作结果
 * @author yinchao
 * @since 2017-8-9
 */
public class CcaiServiceResult {
	/** 操作结果编码*/
	private String code;
	/** 操作结果 true成功 false失败*/
	private boolean success;
	/** 操作消息 */
	private String message;
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
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
		return "CcaiServiceResult [code=" + code + ", success=" + success + ", message=" + message + "]";
	}
}
