
package com.centaline.trans.common.enums;

public enum TaxEnum {
	/**
	 * 有效
	 */
	YX("1", "YX"), 
	/**
	 * 无效
	 */
	WX("0", "WX");
	
	
	private String code;

    private String name;

    private TaxEnum(String code, String name) {
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
