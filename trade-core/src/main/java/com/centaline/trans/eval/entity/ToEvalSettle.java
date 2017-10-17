package com.centaline.trans.eval.entity;

import java.math.BigDecimal;
import java.util.Date;

public class ToEvalSettle {
    private Long pkid;

    private String caseCode;

    private String evaCode;

    private String feeChangeReason;

    private String finOrgId;

    private BigDecimal settleFee;

    private String settleAddReason;

    private String status;

    private Date settleTime;

    private String settleNotReason;

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

    public String getFeeChangeReason() {
        return feeChangeReason;
    }

    public void setFeeChangeReason(String feeChangeReason) {
        this.feeChangeReason = feeChangeReason == null ? null : feeChangeReason.trim();
    }

    public String getFinOrgId() {
        return finOrgId;
    }

    public void setFinOrgId(String finOrgId) {
        this.finOrgId = finOrgId == null ? null : finOrgId.trim();
    }

    public BigDecimal getSettleFee() {
        return settleFee;
    }

    public void setSettleFee(BigDecimal settleFee) {
        this.settleFee = settleFee;
    }

    public String getSettleAddReason() {
        return settleAddReason;
    }

    public void setSettleAddReason(String settleAddReason) {
        this.settleAddReason = settleAddReason == null ? null : settleAddReason.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public Date getSettleTime() {
        return settleTime;
    }

    public void setSettleTime(Date settleTime) {
        this.settleTime = settleTime;
    }

    public String getSettleNotReason() {
        return settleNotReason;
    }

    public void setSettleNotReason(String settleNotReason) {
        this.settleNotReason = settleNotReason == null ? null : settleNotReason.trim();
    }
}