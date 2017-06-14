package com.centaline.trans.common.enums;

/**
 * Created by caoyuan7 on 2017/6/5.
 */
public enum OldActivitiFormKey {


    ToHouseTransfer("Guohu", "ToHouseTransfer"),
    PurchaseLimit("PurchaseLimit","purchaseLimit"),
    Pricing("Pricing","pricing"),
    TaxReview("TaxReview","taxReview"),
    HouseBookGet("HouseBookGet","houseBookGet"),
    LoanClose("LoanClose","closeLoan"),
    InvalidCaseApprove("InvalidCaseApprove","invalidCaseApprove"),
    LoanlostApproveManager("LoanlostApproveManager","loanlostApproveManager"),
    LoanlostApproveDirector("LoanlostApproveDirector","loanlostApproveDirector"),
    LoanlostApproveGeneralManager("LoanlostApproveGeneralManager","loanlostApproveGeneralManager"),
    LoanlostApproveSeniorManager("LoanlostApproveSeniorManager","loanlostApproveSeniorManager"),
    CaseCloseFirstApprove("CaseCloseFirstApprove","caselostApprove/first"),
    CaseCloseSecondApprove("CaseCloseSecondApprove","caselostApprove/second"),
    PSFApply("PSFApply","PSFApply"),
    PSFApprove("PSFApprove","PSFApprove"),
    PSFSign("PSFSign","PSFSign"),
    LoanRelease("LoanRelease","loanRelease"),
    TransPlanFilling("TransPlanFilling","transPlan"),
    MortgageSelect("MortgageSelect","mortgageSelect"),
    NOT_FOUND("", "未知的枚举类型");


    OldActivitiFormKey(String taskDefinitionKey, String formKey) {
        this.taskDefinitionKey = taskDefinitionKey;
        this.formKey = formKey;
    }

    public static OldActivitiFormKey from(String taskDefinitionKey) {
        for (OldActivitiFormKey oldActivitiFormKey : OldActivitiFormKey.values()) {
            if (oldActivitiFormKey.getTaskDefinitionKey().equals(taskDefinitionKey)) {
                return oldActivitiFormKey;
            }
        }
        return NOT_FOUND;
    }

    private String taskDefinitionKey;

    private String formKey;


    public String getTaskDefinitionKey() {
        return taskDefinitionKey;
    }

    public void setTaskDefinitionKey(String taskDefinitionKey) {
        this.taskDefinitionKey = taskDefinitionKey;
    }

    public String getFormKey() {
        return formKey;
    }

    public void setFormKey(String formKey) {
        this.formKey = formKey;
    }
}
