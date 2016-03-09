package com.centaline.trans.mgr.entity;

public class TsBankEvaRelationship {
    private Long pkid;

    private String bankCode;

    private String evaCompanyCode;

    public Long getPkid() {
        return pkid;
    }

    public void setPkid(Long pkid) {
        this.pkid = pkid;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode == null ? null : bankCode.trim();
    }

    public String getEvaCompanyCode() {
        return evaCompanyCode;
    }

    public void setEvaCompanyCode(String evaCompanyCode) {
        this.evaCompanyCode = evaCompanyCode == null ? null : evaCompanyCode.trim();
    }
}