package com.centaline.trans.common.enums;
/**
 * 案件状态
 * <p>Project: Credo's Base</p>
 * <p>Description: </p>
 * <p>Copyright (c) 2015 LionCredo.All Rights Reserved.</p>
 * @author <a href="zhaoqianjava@foxmail.com">LionCredo</a>
 */
public enum CasePropertyEnum {
	TPWX("30003001", "无效案件"),
	TPJA("30003002", "结案案件"),
	TPZT("30003003", "在途案件"),
	TPGQ("30003004", "挂起案件"),
	TPBD("30003005", "爆单案件");
    
    private String name;

    private String code;

    private CasePropertyEnum(String code, String name) {
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
        for (CasePropertyEnum cte : CasePropertyEnum.values()) {
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
