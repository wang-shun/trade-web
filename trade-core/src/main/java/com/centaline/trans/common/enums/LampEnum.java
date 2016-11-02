package com.centaline.trans.common.enums;
/**
 * 案件状态
 * <p>Project: Credo's Base</p>
 * <p>Description: </p>
 * <p>Copyright (c) 2015 LionCredo.All Rights Reserved.</p>
 * @author <a href="zhaoqianjava@foxmail.com">LionCredo</a>
 */
public enum LampEnum {
	GREEN("-1", "2"),//绿灯
	YELLOW("1", "1"),//黄灯
	RED("3", "0"); //红灯
    
    private String name;

    private String code;

    private LampEnum(String code, String name) {
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
        for (LampEnum cte : LampEnum.values()) {
            if (code.equalsIgnoreCase(cte.getCode()))
                return cte.getCode();
        }
        return null;
    }
    /**
     * 根据code获取Name
     * 
     * @param code
     * @return
     */
    @SuppressWarnings("null")
	public static String[] getCodes() {
    	LampEnum[] lampEnums = LampEnum.values();
    	String[] reStrings = new String[lampEnums.length];
        for (int i=0;i<lampEnums.length;i++) {
        	LampEnum lampEnum = lampEnums[i];
        	reStrings[i]=lampEnum.getCode();
        }
        return reStrings;
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
