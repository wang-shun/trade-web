package com.centaline.trans.common.enums;

import java.util.HashMap;

public enum MsgCatagoryEnum {
	LOSS("10064001", "止损类"), 
	DEAL("10064002", "成交类"),
	NEWS("10064003", "提醒类"),
	WORK("MSG_YU_WORK", "作业提醒"),
	STOPLOSS("MSG_YU_STOPLOSS", "案件止损提醒"),
	RESPON("MSG_YU_RESPONSE", "反馈提醒");
	
	HashMap<String, String> aot = new HashMap<String, String>();
	
    private String code;

    private String name;

    private MsgCatagoryEnum(String code, String name) {
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
        for (MsgCatagoryEnum cte : MsgCatagoryEnum.values()) {
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
