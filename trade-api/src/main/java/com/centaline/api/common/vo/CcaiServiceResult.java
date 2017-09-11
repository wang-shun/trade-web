package com.centaline.api.common.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * ccai服务使用返回相关服务的操作结果
 * @author yinchao
 * @since 2017-8-9
 */
@ApiModel("接口响应结果")
public class CcaiServiceResult {
	@ApiModelProperty(value="操作结果编码",required = true,example = "00",
			allowableValues = "00-成功,99-失败",position = 1)
	private String code;
	@ApiModelProperty(value="操作结果true成功 false失败",required = true,position = 0)
	private boolean success;
	@ApiModelProperty(value="响应消息",position = 2)
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
