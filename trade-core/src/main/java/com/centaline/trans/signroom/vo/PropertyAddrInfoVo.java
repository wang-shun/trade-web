package com.centaline.trans.signroom.vo;

import java.io.Serializable;

/**
 * 产证地址信息
 * 
 * @author yinjf2
 *
 */
public class PropertyAddrInfoVo implements Serializable {

	private String value; // 案件编号

	private String label; // 产证地址

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

}
