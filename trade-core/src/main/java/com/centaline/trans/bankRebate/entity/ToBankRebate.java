package com.centaline.trans.bankRebate.entity;

import java.math.BigDecimal;
import java.util.Date;

public class ToBankRebate {
    private Long pkid;

    private String caseCode;

    private Date applyTime;

    private String guaranteeCompany;

    private String companyAccount;

    private BigDecimal rebateTotal;

    private String applyPerson;

    private String status;

    private String comment;

    private String guaranteeCompId;

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

    public Date getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(Date applyTime) {
        this.applyTime = applyTime;
    }

    public String getGuaranteeCompany() {
        return guaranteeCompany;
    }

    public void setGuaranteeCompany(String guaranteeCompany) {
        this.guaranteeCompany = guaranteeCompany == null ? null : guaranteeCompany.trim();
    }

    public String getCompanyAccount() {
        return companyAccount;
    }

    public void setCompanyAccount(String companyAccount) {
        this.companyAccount = companyAccount == null ? null : companyAccount.trim();
    }

    public BigDecimal getRebateTotal() {
        return rebateTotal;
    }

    public void setRebateTotal(BigDecimal rebateTotal) {
        this.rebateTotal = rebateTotal;
    }

    public String getApplyPerson() {
        return applyPerson;
    }

    public void setApplyPerson(String applyPerson) {
        this.applyPerson = applyPerson == null ? null : applyPerson.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment == null ? null : comment.trim();
    }

    public String getGuaranteeCompId() {
        return guaranteeCompId;
    }

    public void setGuaranteeCompId(String guaranteeCompId) {
        this.guaranteeCompId = guaranteeCompId == null ? null : guaranteeCompId.trim();
    }
}