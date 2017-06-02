package com.centaline.trans.common.enums;

public enum TransDictEnum {

	TAJZT("30001", "案件状态"),
	//TAJSJ("30002", "案件状态"),
	TAJXZ("30003", "案件性质"),
	TFWBM("yu_serv_cat_code_tree", "服务编码表"),
	TLSFX("yu_cash_direction", "流水方向"),
	TDKLX("30016", "贷款类型"),
	TAJSJ("30005", "案件时间项"),
	TGSXJ("30006", "客户上下家"),
	TGFNS("hold_year", "购房年数"),
	TPARTCODE("part_code", "环节编码"),
	TWYZF("is_unique_home", "是否是唯一住房"),
	THKQK("hukou_remain", "户口情况"),
	THTGZ("gongzheng_need", "合同公证"),
	TFWXZ("house_property", "房屋性质"),
	TBMCC("UAM_DEP_TYPE_HIER", "部门层次"),
	TWJBH("file_type_code", "文件编号"),
	TFKFS("30015", "付款方式"),
	TCDXM("30009", "产调项目"),
	TLENDWAY("30017", "放款方式"),
	TSQZT("yu_eplus_status","申请状态"),
	TSYLB("yu_income_cat","收益类别"),
	THKFS("LoanCloseMethod", "还款方式");
    private String name;

    private String code;

    private TransDictEnum(String code, String name) {
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
        for (TransDictEnum cte : TransDictEnum.values()) {
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
