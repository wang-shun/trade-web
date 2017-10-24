package com.centaline.trans.ransom.entity;

import java.util.Date;

/**
 * 赎楼环节顺序Vo
 * @author wbwumf
 *
 *2017年10月23日
 */
public class ToRansomPartOrderVo {
	
	private String partCode;
	
	private Integer order;

	public ToRansomPartOrderVo(String partCode, Integer order) {
		super();
		this.partCode = partCode;
		this.order = order;
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
	

}
