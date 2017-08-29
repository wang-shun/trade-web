package com.centaline.trans.common.enums;

/**
 * 
 * <p>Project: 案件状态</p>
 * <p>Description: </p>
 * <p>Copyright (c) 2015 wanggh.All Rights Reserved.</p>
 * @author Wanggh</a>
 */
public enum CaseStatusEnum {
	//TODO 案件状态需要根据需求说明书进行调整 by:yinchao 2017-8-22
	/** 未分单 **/
	WJD("30001001", "未接单"),
	/** 已分单 **/
	YJD("30001002", "已接单"),
	/** 已签约 **/
	YWY("30001003", "已网约"),
	/** 已过户 **/
	YGH("30001004", "已过户"),
	/** 已领证 **/
	YLTZ("30001005", "已领他证"),
	/** 已终止 **/
	YZZ("30001007", "已终止"),
	/** 权证经理审核未通过 驳回ccai **/
	BHCCAI("30001008", "驳回CCAI");
    
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
