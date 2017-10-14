package com.centaline.trans.common.enums;

/**
 * @Description:评估单流程性质状态
 * @author：jinwl6
 * @date:2017年10月12日
 * @version:
 */
public enum EvalPropertyEnum {
	
	PGYX("61001001", "有效"),
	PGGQ("61001002", "挂起"),
	PGBD("61001003", "爆单"),
	PGBH("61001004", "驳回");
    
    private String name;

    private String code;

    private EvalPropertyEnum(String code, String name) {
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
