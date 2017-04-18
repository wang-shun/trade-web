package com.centaline.trans.common.enums;

public enum WorkFlowStatus {
	/**有效**/
	ACTIVE("0", "有效"),
	/**非正常结束**/
	TERMINATE("2","非正常结束"),
	/**爆单**/
	BAODAN("3","爆单"),
	/**完成**/
	COMPLETE("4","完成");
	
	private String name;
	private String code;

	private WorkFlowStatus(String code, String name) {
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
