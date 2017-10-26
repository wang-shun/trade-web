package com.centaline.trans.bankRebate.entity;

import java.math.BigDecimal;
/**
 * 
 * @author wbwangxj
 *
 */
public class ToBankRebateInfo {
    private Long pkid;

    private String caseCode;

    private String ccaiCode;

    private String guaranteeCompId;

    private String bankName;

    private BigDecimal rebateMoney;

    private BigDecimal rebateWarrant;

    private BigDecimal rebateBusiness;

    private Long caseId;

    private String applyId;

    private String reportCode;

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
        this.caseCode = caseCode;
    }

    public String getCcaiCode() {
        return ccaiCode;
    }

    public void setCcaiCode(String ccaiCode) {
        this.ccaiCode = ccaiCode;
    }

    public String getGuaranteeCompId() {
        return guaranteeCompId;
    }

    public void setGuaranteeCompId(String guaranteeCompId) {
        this.guaranteeCompId = guaranteeCompId;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public BigDecimal getRebateMoney() {
        return rebateMoney;
    }

    public void setRebateMoney(BigDecimal rebateMoney) {
        this.rebateMoney = rebateMoney;
    }

    public BigDecimal getRebateWarrant() {
        return rebateWarrant;
    }

    public void setRebateWarrant(BigDecimal rebateWarrant) {
        this.rebateWarrant = rebateWarrant;
    }

    public BigDecimal getRebateBusiness() {
        return rebateBusiness;
    }

    public void setRebateBusiness(BigDecimal rebateBusiness) {
        this.rebateBusiness = rebateBusiness;
    }

    public Long getCaseId() {
        return caseId;
    }

    public void setCaseId(Long caseId) {
        this.caseId = caseId;
    }

    public String getApplyId() {
        return applyId;
    }

    public void setApplyId(String applyId) {
        this.applyId = applyId;
    }

    public String getReportCode() {
        return reportCode;
    }

    public void setReportCode(String reportCode) {
        this.reportCode = reportCode;
    }
}