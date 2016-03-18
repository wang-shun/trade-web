package com.centaline.trans.common.enums;
/**
 * 案件状态
 * <p>Project: Credo's Base</p>
 * <p>Description: </p>
 * <p>Copyright (c) 2015 LionCredo.All Rights Reserved.</p>
 * @author <a href="zhaoqianjava@foxmail.com">LionCredo</a>
 */
public enum DepTypeEnum {
	TYCZB("yucui_headquarter", "誉萃总部 "),
	TYCTEAM("yucui_team", "组别"),
	TYCQY("yucui_district", "区域"),
	BUSIAR("BUSIAR","片区");
    
    private String name;

    private String code;

    private DepTypeEnum(String code, String name) {
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
        for (DepTypeEnum cte : DepTypeEnum.values()) {
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
