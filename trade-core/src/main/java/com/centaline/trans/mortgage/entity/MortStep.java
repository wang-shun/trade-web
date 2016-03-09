package com.centaline.trans.mortgage.entity;

public class MortStep {
    private Long pkid;

    private String caseCode;

    private String isMainLoanBank;

    private Integer step;

    public Long getPkid() {
        return pkid;
    }

    public void setPkid(Long pkid) {
        this.pkid = pkid;
    }

    public String getCaseCode() {
        return caseCode;
    }

    public void setCaseCode(String caseCode) {
        this.caseCode = caseCode == null ? null : caseCode.trim();
    }

    public String getIsMainLoanBank() {
        return isMainLoanBank;
    }

    public void setIsMainLoanBank(String isMainLoanBank) {
        this.isMainLoanBank = isMainLoanBank == null ? null : isMainLoanBank.trim();
    }

    public Integer getStep() {
        return step;
    }

    public void setStep(Integer step) {
        this.step = step;
    }
}