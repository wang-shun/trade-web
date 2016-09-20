package com.centaline.trans.common.enums;

/**
 * 签约室预约预约状态
 * @author zhoujp7
 *
 */
public enum ReservationStatusEnum {
	QYSYYZ("0","预约中"),
	QYSYGQ("1","已过期"),
	QYSYSY("2","已使用"),
	QYSSYZ("3","使用中"),
	QYSYQX("4","已取消");

	private String name;

	private String code;

	private ReservationStatusEnum(String code, String name) {
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
		for (ReservationStatusEnum cte : ReservationStatusEnum.values()) {
			if (code.equalsIgnoreCase(cte.getCode()))
				return cte.getCode();
		}
		return null;
	}
	
	public static String getCode(String name) {
		for (ReservationStatusEnum cte : ReservationStatusEnum.values()) {
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
