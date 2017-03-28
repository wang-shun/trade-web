package com.centaline.trans.common.enums;

public enum LightColorEnum {
	RED("0", "红灯"), 
	YELLOW("1", "黄灯");

    private String code;

    private String name;

    private LightColorEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }
    /**
     * 根据code获取Name
     * 
     * @param code
     * @return
     */
    public static String getName(String code) {
        for (LightColorEnum cte : LightColorEnum.values()) {
            if (code.equalsIgnoreCase(cte.getCode()))
                return cte.getName();
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
