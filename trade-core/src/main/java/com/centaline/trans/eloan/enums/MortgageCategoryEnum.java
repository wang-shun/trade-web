package com.centaline.trans.eloan.enums;

public enum MortgageCategoryEnum {
	PROPERTY_CARD("propertyCard", "产权证"),
	OTHER_CARD("otherCard", "他证"),
	CARDED("carded", "身份证"),
	BANKCARD("bankCard", "银行卡");
    
    private String name;

    private String code;

    private MortgageCategoryEnum(String code, String name) {
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
        for (MortgageCategoryEnum riskType : MortgageCategoryEnum.values()) {
            if (code.equalsIgnoreCase(riskType.getCode()))
                return riskType.getName();
        }
        return null;
    }
    
    public static String getCode(String name) {
        for (MortgageCategoryEnum riskType : MortgageCategoryEnum.values()) {
            if (name.equalsIgnoreCase(riskType.getName()))
                return riskType.getCode();
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
