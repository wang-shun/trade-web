
package com.centaline.trans.common.enums;

public enum SalesDepTypeEnum {
	
	BUSIBA("BUSIBA","大区"),
	BUSIWZ("BUSIWZ","战区"),
	BUSISWZ("BUSISWZ","小战区"),
	BUSISAR("BUSISAR","片区"),
	BUSISSH("BUSISSH","店"),
	BUSISGRP("BUSISGRP","店组");
	
	private String code;

    private String name;

    private SalesDepTypeEnum(String code, String name) {
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
