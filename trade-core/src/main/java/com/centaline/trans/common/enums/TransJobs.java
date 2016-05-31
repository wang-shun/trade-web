package com.centaline.trans.common.enums;

public enum TransJobs {
	TJYGW("consultant", "交易顾问"), TJYZL("TeamAssistant", "交易助理"), TJYZG(
			"Manager", "交易主管"), TQYMS("yucui_secretary", "区域秘书"), TFHJL(
			"JFHJL", "分行经理"), TZJL("GeneralManager", "誉萃总经理"), TZJ("director",
			"誉萃总监"), YCPRODUCT("yucui_product", "产品研发"),TSJYZG("Senior_Manager","高级交易主管"),
	YCADM("yucui_admin","誉萃管理员");

	private String name;

	private String code;

	private TransJobs(String code, String name) {
		this.name = name;
		this.code = code;
	}

	/**
	 * 根据code获取Name
	 * 
	 * @param code
	 * @return
	 */
	public static String getName(String code) {
		for (TransJobs cte : TransJobs.values()) {
			if (code.equalsIgnoreCase(cte.getCode()))
				return cte.getCode();
		}
		return null;
	}
	
	public static String getCode(String name) {
		for (TransJobs cte : TransJobs.values()) {
			if (cte.getName().equalsIgnoreCase(name))
				return cte.getCode();
		}
		return null;
	}

	/**
	 * Getter method for property <tt>name</tt>.
	 * 
	 * @return property value of name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Setter method for property <tt>name</tt>.
	 * 
	 * @param name
	 *            value to be assigned to property name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Getter method for property <tt>code</tt>.
	 * 
	 * @return property value of code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * Setter method for property <tt>code</tt>.
	 * 
	 * @param code
	 *            value to be assigned to property code
	 */
	public void setCode(String code) {
		this.code = code;
	}
}
