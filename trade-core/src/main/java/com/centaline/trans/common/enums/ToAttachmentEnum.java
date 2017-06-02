package com.centaline.trans.common.enums;
/**
 * 待办事项
 * @author aisliaohail
 */
public enum ToAttachmentEnum {
	CASECLOSETHIRDAPPROVE("CaseCloseThirdApprove", "归档确认和结案审核"),
	CASECLOSESECONDAPPROVE("CaseCloseSecondApprove", "归档确认和结案审核"),
	CASECLOSEFIRSTAPPROVE("CaseCloseFirstApprove", "归档确认和结案审核"),
	
	CASECLOSE("CaseClose", "结案归档"),
	LOANRELEASE("LoanRelease", "放款"),
	EVAREPORTARISE("EvaReportArise", "发起评估"),
	
	HOUSEBOOKGET("HouseBookGet", "领证"),
	GUOHUAPPROVE("GuohuApprove", "过户审批"),
	GUOHU("Guohu", "过户"),
	
	OFFLINEEVA("OfflineEva", "线下评估报告发起"),
	INVALIDCASEAPPROVE("InvalidCaseApprove", "无效审批"),
	SELFLOANAPPROVE("SelfLoanApprove", "自办贷款审批"),
	
	LOANLOSTAPPROVEGENERALMANAGER("LoanlostApproveGeneralManager", "流失审批（总经理）"),
	LOANLOSTAPPROVEDIRECTOR("LoanlostApproveDirector", "流失审批（总监）"),
	LOANLOSTAPPROVEMANAGER("LoanlostApproveManager", "流失审批（主管）"),
	
	PSFAPPROVE("PSFApprove", "纯公积金贷款审批"),
	PSFSIGN("PSFSign", "纯公积金贷款签约"),
	PSFAPPLY("PSFApply", "纯公积金贷款申请"),
	
	MORTGAGESELECT("MortgageSelect","贷款需求选择"),
	COMLOANPROCESS("ComLoanProcess", "商业贷款办理"),
	LOANCLOSE("LoanClose", "贷款结清"),
	TAXREVIEW("TaxReview", "审税"),
	
	PRICING("Pricing", "核价"),
	PURCHASELIMIT("PurchaseLimit", "查限购"),
	TRANSPLANFILLING("TransPlanFilling", "填写交易计划"),
	
	TRANSSIGN("TransSign", "签约"),
	FIRSTFOLLOW("FirstFollow", "首次跟进");
	
    private String name;

    private String code;

    private ToAttachmentEnum(String code, String name) {
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
        for (ToAttachmentEnum cte : ToAttachmentEnum.values()) {
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
