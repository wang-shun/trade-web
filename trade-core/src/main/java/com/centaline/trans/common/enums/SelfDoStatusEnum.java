package com.centaline.trans.common.enums;

public enum SelfDoStatusEnum {

	  DOING("0","审批中"),
	  BACK("-1","驳回"),
	  SUCCESS("1","通过");
		
    private String code;
    private String name;

    private SelfDoStatusEnum(String code, String name) {
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
        for (EvalRebateStatusEnum cte : EvalRebateStatusEnum.values()) {
            if (code.equalsIgnoreCase(cte.getCode()))
                return cte.getCode();
        }
        return null;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = name;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

