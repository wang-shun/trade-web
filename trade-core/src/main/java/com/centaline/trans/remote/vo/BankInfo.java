package com.centaline.trans.remote.vo;

public class BankInfo {
	//主贷银行编号
	private String type ;
	
	//主贷银行名称
	private String name;
	
	//誉萃方支行银行ID
	private String id;
	
	//支行名称
	private String value;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
}
