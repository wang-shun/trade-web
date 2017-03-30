package com.centaline.trans.common.enums;

/**
 * 
 * <p>Project: 案件合流状态</p>
 * @author hejf10
 */
public enum CaseMergeStatusEnum {
	/** CTM 导入  INPUT 自录   MERGE 合流  PROCESS 合流申请中**/
	CTM("CTM", "导入"),
	/** CTM 导入  INPUT 自录   MERGE 合流  PROCESS 合流申请中**/
	INPUT("INPUT", "自录"),
	/** CTM 导入  INPUT 自录   MERGE 合流  PROCESS 合流申请中**/
	MERGE("MERGE", "合流"),
	/** CTM 导入  INPUT 自录   MERGE 合流  PROCESS 合流申请中**/
	PROCESS("PROCESS", "合流申请中"),
	/** APPLY_STATUS 0：申请   1：已确认   2：拒绝**/
	APPLYSTATUS0("0","申请"),
	/** APPLY_STATUS 0：申请   1：已确认   2：拒绝**/
	APPLYSTATUS1("1","已确认"),
	/** APPLY_STATUS 0：申请   1：已确认   2：拒绝**/
	APPLYSTATUS2("2","拒绝"),
	/** OPERATOR 1：合并  2 ：拆解**/
	OPERATOR1("1","合并"),
	/** OPERATOR 1：合并  2 ：拆解**/
	OPERATOR2("2","拆解"),
	/** APPLY_DIRECTION 0 : 自录发起 合CTM      1：CTM发起 合自录**/
	APPLY_DIRECTION0("0","自录发起 合CTM"),
	/** APPLY_DIRECTION 0 : 自录发起 合CTM      1：CTM发起 合自录**/
	APPLY_DIRECTION1("1","CTM发起 合自录"),
	/** 30003007 合流案件 **/
	CASE_PROPERTY3("30003007","合流案件"),
	/** 1确认 0驳回 **/
	TYPE1("1","确认"),
	/** 1确认 0驳回 **/
	TYPE0("0","驳回");
    
    private String name;

    private String code;

    private CaseMergeStatusEnum(String code, String name) {
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
        for (CaseMergeStatusEnum cte : CaseMergeStatusEnum.values()) {
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
