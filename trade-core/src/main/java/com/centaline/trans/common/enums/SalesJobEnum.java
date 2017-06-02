
package com.centaline.trans.common.enums;

public enum SalesJobEnum {
	
	JWYGW("JWYGW", "物业顾问"), 
	JFHJL("JFHJL", "分行经理"),
	JFHMS("JFHMS", "分行秘书"),
	JQYJL("JQYJL", "区域经理"),
	JQYZJ("JQYZJ", "区域总监"),
	JQYDS("JQYDS", "区域董事"),
	JFZJL("JFZJL", "副总经理"),
	JPKZY("JPKZY","品控专员");
	
	private String code;

    private String name;

    private SalesJobEnum(String code, String name) {
        this.code = code;
        this.name = name;
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
     * @param name value to be assigned to property name
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
     * @param code value to be assigned to property code
     */
    public void setCode(String code) {
        this.code = code;
    }
}
