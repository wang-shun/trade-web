package com.centaline.trans.common.enums;

/**
 * 
 * <p>Project: 案件状态</p>
 * <p>Description: </p>
 * <p>Copyright (c) 2015 wanggh.All Rights Reserved.</p>
 * @author Wanggh</a>
 */
public enum CaseStatusEnum {
	WFD("30001001", "未分单"),
	YFD("30001002", "已分单"),
	YQY("30001003", "已签约"),
	YGH("30001004", "已过户"),
	YLZ("30001005", "已领证");
    
    private String name;

    private String code;

    private CaseStatusEnum(String code, String name) {
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
        for (CaseStatusEnum cte : CaseStatusEnum.values()) {
            if (code.equalsIgnoreCase(cte.getCode()))
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
