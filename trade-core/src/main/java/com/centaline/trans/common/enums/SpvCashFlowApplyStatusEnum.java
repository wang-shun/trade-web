package com.centaline.trans.common.enums;
/**
 * 案件出入账状态
 */
public enum SpvCashFlowApplyStatusEnum {
	/**入账*/
	APPLY("00", "入账起草"),
	DIRECTORADUIT("01", "总监审批"),
	FINANCEADUIT("02", "财务审核"),
	AUDITCOMPLETED("03", "审核完成"),
	
	/**出账*/
	OUTDRAFT("10","出账起草状态"),
	OUTINPROGRESS("11","出账审核中状态"),
	OUTDIRECTORADUIT("12","出账总监审批状态"),
	OUTFINANCEADUIT("13", "出账财务初审状态"),
	OUTFINANCE2ADUIT("14", "出账财务复审状态"),
	OUTAUDITCOMPLETED("15", "出账审核完成状态");
    
    private String name;

    private String code;

    private SpvCashFlowApplyStatusEnum(String code, String name) {
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
        for (SpvCashFlowApplyStatusEnum cte : SpvCashFlowApplyStatusEnum.values()) {
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
