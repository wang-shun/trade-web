package com.centaline.trans.common.enums;

public enum AwardStatusEnum {
	/**未发放**/
	WEIFAFANG("0", "未发放"),
	/**已发放**/
	YIFAFANG("1","已发放"),
	/**特殊原因**/
	WENTI("2","特殊原因");

	
	private String name;
	private String code;

	private AwardStatusEnum(String code, String name) {
		this.setName(name);
		this.setCode(code);
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
