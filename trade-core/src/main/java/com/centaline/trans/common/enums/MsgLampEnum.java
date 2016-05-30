package com.centaline.trans.common.enums;

/**
 * 消息类型
 * @ClassName: MsgLampEnum
 * @Description: TODO
 * @author wanggh
 * @date 2015年10月10日
 *
 */
public enum MsgLampEnum {
	RED("redlamp_reminder", "红灯提醒"),
	YELLOW("yellow_lamp", "黄灯提醒"),
	REDMANAGER("redlamp_reminder_manager", "红灯提醒-经理"),
	CASECLOSE("case_close","结案提醒"),
	PRICING("xunjia_response", "询价反馈"),
	MFOLLOW("followup_reminder", "跟进提醒"),
	MWORK("work_template", "作业提醒"),
	REPORT("eval_report_response", "评估报告反馈"),
	APPROVE_RESULT_REMINDER("approve_result_reminder","审批结果提醒");
    
    private String name;

    private String code;

    /**
     * 
     * 创建一个新的实例 MsgLampEnum.
     *
     * @param code
     * @param name
     */
    private MsgLampEnum(String code, String name) {
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
        for (MsgLampEnum cte : MsgLampEnum.values()) {
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
