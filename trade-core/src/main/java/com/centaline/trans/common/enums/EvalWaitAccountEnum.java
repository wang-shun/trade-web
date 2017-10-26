package com.centaline.trans.common.enums;
/**
 * @Description:评估待结算案件状态
 * @author wbwangxj
 *
 */
public enum EvalWaitAccountEnum {
	WTJ("0","未提交"),
    YBH("1","已驳回"),
    WXJS("2","无需结算"),
	WJS("3","未结算"),
    YJS("4","已结算总监审批"),
    ZJSP("5","已提交总监"),
	CWSP("6","已提交财务审批");
	
	private String code;
    private String name;
    
	private EvalWaitAccountEnum(String code, String name) {
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
	        for (EvalWaitAccountEnum cte : EvalWaitAccountEnum.values()) {
	            if (code.equalsIgnoreCase(cte.getCode()))
	                return cte.getCode();
	        }
	        return null;
	    }

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	 
	 
}
