package com.centaline.scheduler.sync.enums;
/**
 * 城市枚举类
 * 
 * @author yinchao
 *
 */
public enum CityType {
	BEIJING("BEIJING","北京"),
	TIANJIN("TIANJIN","天津");
	
	private String name;
	private String code;
	private CityType(String code,String name) {
		this.name = name;
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
}
