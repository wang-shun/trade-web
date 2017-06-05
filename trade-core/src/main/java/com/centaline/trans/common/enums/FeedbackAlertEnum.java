package com.centaline.trans.common.enums;

public enum FeedbackAlertEnum {
	
	ASSESS_REPORT_FEEDBACK("4", "评估报告反馈"),
	ENQUIRY_RESULT_FEEDBACK("3", "询价结果反馈"),
	EXAM_APPR_RESULT_FEEDBACK("2", "审批结果反馈"),
	PROPERTY_PROCESS("1", "产调受理"),
	PROPERTY_SUCCESS("0", "产调完毕");
	
    
    private String name;

    private String code;

    private FeedbackAlertEnum(String code, String name) {
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
        for (FeedbackAlertEnum cte : FeedbackAlertEnum.values()) {
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
