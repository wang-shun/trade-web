package com.centaline.trans.ransom.entity;

/**
 * 环节顺序，计划时间修改用
 * @author wbwumf
 *
 *2017年10月26日
 */
public class RansomPartOrderVo {
	
	/**
	 * 环节
	 */
	private String partCode;
	
	/**
	 * 顺序
	 */
	private Integer order;
	
	/**
	 * 环节名
	 */
	private String name;

	public RansomPartOrderVo(String partCode, Integer order) {
		this.partCode = partCode;
		this.order = order;
	}

	

	public RansomPartOrderVo(String partCode, String name,Integer order) {
		this.partCode = partCode;
		this.order = order;
		this.name = name;
	}



	public String getPartCode() {
		return partCode;
	}

	public void setPartCode(String partCode) {
		this.partCode = partCode;
	}

	public Integer getOrder() {
		return order;
	}

	public void setOrder(Integer order) {
		this.order = order;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
}
