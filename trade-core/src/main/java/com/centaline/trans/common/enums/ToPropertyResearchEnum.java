package com.centaline.trans.common.enums;

public enum ToPropertyResearchEnum {
	
	PROPERTY_RESEARCH_LETTER("property_research_letter", "附件编码"),
	PROPERTY_RESEARCH("property_research", "环节编码"),
	FILE_TYPE("jpg","文件类型");
    
    private String name;

    private String code;

    private ToPropertyResearchEnum(String code, String name) {
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
        for (ToPropertyResearchEnum cte : ToPropertyResearchEnum.values()) {
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

