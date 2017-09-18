package com.centaline.scheduler.sync.enums;
/**
 * 同步结果枚举类
 * @author yinchao
 *
 */
public enum SyncResultType {
	SUCCESS(0,"成功"),FAILURE(-1,"失败");
	
	private String name;
	private int code = -1;
	private SyncResultType(int code,String name) {
		this.name = name;
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
}
