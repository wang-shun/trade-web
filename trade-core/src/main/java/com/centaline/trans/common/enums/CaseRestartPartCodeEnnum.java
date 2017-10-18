package com.centaline.trans.common.enums;

/**
 * 案件重启环节
 * @author wbcaiyx
 *
 */
public enum CaseRestartPartCodeEnnum {

	
	CASE_RESTART_APPLY("caseRestartApply","案件重启申请"),
	CASE_RESTART_APPROVE("caseRestartApprove","案件重启审批");
	
	private String code;
	private String name;
	
	 private CaseRestartPartCodeEnnum(String code, String name) {
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
        for (CaseRestartPartCodeEnnum csc : CaseRestartPartCodeEnnum.values()) {
            if (code.equalsIgnoreCase(csc.getCode()))
                return csc.getCode();
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
