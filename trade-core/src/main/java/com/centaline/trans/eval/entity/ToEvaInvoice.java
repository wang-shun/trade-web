package com.centaline.trans.eval.entity;

import java.math.BigDecimal;
import java.util.Date;

public class ToEvaInvoice {
    private Long pkid;

    private String caseCode;

    private String evaCode;

    private String invoiceType;

    private String invoiceHeader;

    private BigDecimal invoiceAmount;

    private String invoiceAddress;

    private String status;

    private String taxNum;

    private Date applyDate;
    //预计开票完成时间
    private Date toFinshDate;

    private Date bankOpenedDate;

    private String bankAccount;

    private Date billTime;

    private String evaProcessId;
    
    private String evaCompanyId;
    
    private String evaCompanyName;

    public String getEvaCompanyId() {
		return evaCompanyId;
	}

	public void setEvaCompanyId(String evaCompanyId) {
		this.evaCompanyId = evaCompanyId;
	}

	public String getEvaCompanyName() {
		return evaCompanyName;
	}

	public void setEvaCompanyName(String evaCompanyName) {
		this.evaCompanyName = evaCompanyName;
	}

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

    public String getEvaCode() {
        return evaCode;
    }

    public void setEvaCode(String evaCode) {
        this.evaCode = evaCode == null ? null : evaCode.trim();
    }

    public String getInvoiceType() {
        return invoiceType;
    }

    public void setInvoiceType(String invoiceType) {
        this.invoiceType = invoiceType == null ? null : invoiceType.trim();
    }

    public String getInvoiceHeader() {
        return invoiceHeader;
    }

    public void setInvoiceHeader(String invoiceHeader) {
        this.invoiceHeader = invoiceHeader == null ? null : invoiceHeader.trim();
    }

    public BigDecimal getInvoiceAmount() {
        return invoiceAmount;
    }

    public void setInvoiceAmount(BigDecimal invoiceAmount) {
        this.invoiceAmount = invoiceAmount;
    }

    public String getInvoiceAddress() {
        return invoiceAddress;
    }

    public void setInvoiceAddress(String invoiceAddress) {
        this.invoiceAddress = invoiceAddress == null ? null : invoiceAddress.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getTaxNum() {
        return taxNum;
    }

    public void setTaxNum(String taxNum) {
        this.taxNum = taxNum == null ? null : taxNum.trim();
    }

    public Date getApplyDate() {
        return applyDate;
    }

    public void setApplyDate(Date applyDate) {
        this.applyDate = applyDate;
    }

    public Date getToFinshDate() {
        return toFinshDate;
    }

    public void setToFinshDate(Date toFinshDate) {
        this.toFinshDate = toFinshDate;
    }

    public Date getBankOpenedDate() {
        return bankOpenedDate;
    }

    public void setBankOpenedDate(Date bankOpenedDate) {
        this.bankOpenedDate = bankOpenedDate;
    }

    public String getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount == null ? null : bankAccount.trim();
    }

    public Date getBillTime() {
        return billTime;
    }

    public void setBillTime(Date billTime) {
        this.billTime = billTime;
    }

    public String getEvaProcessId() {
        return evaProcessId;
    }

    public void setEvaProcessId(String evaProcessId) {
        this.evaProcessId = evaProcessId == null ? null : evaProcessId.trim();
    }
}