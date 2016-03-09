package com.centaline.trans.loan.entity;

import java.math.BigDecimal;
import java.util.Date;

public class ToCloseLoan {
    private Long pkid;

    private String caseCode;

    private Date loanCloseCode;

    private String closeType;

    private BigDecimal uncloseMoney;

    private String comment;
    
    private String mortgageBank;

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

    public Date getLoanCloseCode() {
        return loanCloseCode;
    }

    public void setLoanCloseCode(Date loanCloseCode) {
        this.loanCloseCode = loanCloseCode;
    }

    public String getCloseType() {
        return closeType;
    }

    public void setCloseType(String closeType) {
        this.closeType = closeType == null ? null : closeType.trim();
    }

    public BigDecimal getUncloseMoney() {
        return uncloseMoney;
    }

    public void setUncloseMoney(BigDecimal uncloseMoney) {
        this.uncloseMoney = uncloseMoney;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment == null ? null : comment.trim();
    }

	public String getMortgageBank() {
		return mortgageBank;
	}

	public void setMortgageBank(String mortgageBank) {
		this.mortgageBank = mortgageBank;
	}
}