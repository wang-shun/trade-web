package com.centaline.scheduler.sync.service;


import com.centaline.scheduler.sync.enums.SyncResultType;
/**
 * 同步结果类
 * 成功则会忽略message
 * @author yinchao
 *
 */
public class SyncResult {
	private SyncResultType result;
	private String message;
	public SyncResult(SyncResultType result, String message) {
		this.result = result;
		this.message = message;
	}
	public SyncResultType getResult() {
		return result;
	}
	public String getMessage() {
		return message;
	}
}
