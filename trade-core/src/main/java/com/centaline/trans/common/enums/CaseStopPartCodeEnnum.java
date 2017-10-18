package com.centaline.trans.common.enums;

/**
 * 案件爆单环节
 * @author wbcaiyx
 *
 */
public enum CaseStopPartCodeEnnum {

	
	CASE_STOP_APPLY("caseStopApply","案件爆单申请"),
	CASE_STOP_APPROVE("caseStopApprove","案件爆单审批");
	
	private String code;
	private String name;
	
	 private CaseStopPartCodeEnnum(String code, String name) {
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
        for (CaseStopPartCodeEnnum csc : CaseStopPartCodeEnnum.values()) {
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
