package com.centaline.trans.wechat.pojo;

/**
 * 企业号部门信息
 * 
 * @author liyufeng
 * @date   20141029
 */

public class WeixinDepartment {
	// 部门id
	private int id;
	// 部门名称
	private String name;
	// 在父部门的次序
	private int order;
	// 父部门id
	private int parentid;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getOrder() {
		return order;
	}
	public void setOrder(int order) {
		this.order = order;
	}
	public int getParentid() {
		return parentid;
	}
	public void setParentid(int parentid) {
		this.parentid = parentid;
	}

	
}
