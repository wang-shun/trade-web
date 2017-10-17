package com.centaline.trans.eval.entity;

import java.math.BigDecimal;
import java.util.Date;

public class ToEvaRefund {
    private Long pkid;

    private String caseCode;

    private String evaCode;

    private String refundKinds;

    private Date pricingTime;

    private String proposer;

    private String applyDepart;

    private Date applyTime;

    private BigDecimal refundAmount;

    private String refundTarget;

    private String refundCause;

    private String finOrgId;

    private Date toRefundTime;

    private Integer reportBackNum;

    private Date backTime;

    private String backCause;

    private BigDecimal evalRealCharges;

    private String evaProcessId;

    private String status;
    
    private String ccaiCode;
    
    private String applyId;
    
    private String proposerId;
    
    private String applyDepartCode;
    
    private String evalCompany;
    
    private String city;

    public Long getPkid() {
        return pkid;
    }

    public String getCcaiCode() {
		return ccaiCode;
	}

	public void setCcaiCode(String ccaiCode) {
		this.ccaiCode = ccaiCode == null ? null : ccaiCode.trim();
	}

	public String getApplyId() {
		return applyId;
	}

	public void setApplyId(String applyId) {
		this.applyId = applyId== null ? null : applyId.trim();
	}

	public String getProposerId() {
		return proposerId;
	}

	public void setProposerId(String proposerId) {
		this.proposerId = proposerId== null ? null : proposerId.trim();
	}

	public String getApplyDepartCode() {
		return applyDepartCode;
	}

	public void setApplyDepartCode(String applyDepartCode) {
		this.applyDepartCode = applyDepartCode== null ? null : applyDepartCode.trim();
	}

	public String getEvalCompany() {
		return evalCompany;
	}

	public void setEvalCompany(String evalCompany) {
		this.evalCompany = evalCompany== null ? null : evalCompany.trim();
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city== null ? null : city.trim();
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

    public String getRefundKinds() {
        return refundKinds;
    }

    public void setRefundKinds(String refundKinds) {
        this.refundKinds = refundKinds == null ? null : refundKinds.trim();
    }

    public Date getPricingTime() {
        return pricingTime;
    }

    public void setPricingTime(Date pricingTime) {
        this.pricingTime = pricingTime;
    }

    public String getProposer() {
        return proposer;
    }

    public void setProposer(String proposer) {
        this.proposer = proposer == null ? null : proposer.trim();
    }

    public String getApplyDepart() {
        return applyDepart;
    }

    public void setApplyDepart(String applyDepart) {
        this.applyDepart = applyDepart == null ? null : applyDepart.trim();
    }

    public Date getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(Date applyTime) {
        this.applyTime = applyTime;
    }

    public BigDecimal getRefundAmount() {
        return refundAmount;
    }

    public void setRefundAmount(BigDecimal refundAmount) {
        this.refundAmount = refundAmount;
    }

    public String getRefundTarget() {
        return refundTarget;
    }

    public void setRefundTarget(String refundTarget) {
        this.refundTarget = refundTarget == null ? null : refundTarget.trim();
    }

    public String getRefundCause() {
        return refundCause;
    }

    public void setRefundCause(String refundCause) {
        this.refundCause = refundCause == null ? null : refundCause.trim();
    }

    public String getFinOrgId() {
        return finOrgId;
    }

    public void setFinOrgId(String finOrgId) {
        this.finOrgId = finOrgId == null ? null : finOrgId.trim();
    }

    public Date getToRefundTime() {
        return toRefundTime;
    }

    public void setToRefundTime(Date toRefundTime) {
        this.toRefundTime = toRefundTime;
    }

    public Integer getReportBackNum() {
        return reportBackNum;
    }

    public void setReportBackNum(Integer reportBackNum) {
        this.reportBackNum = reportBackNum;
    }

    public Date getBackTime() {
        return backTime;
    }

    public void setBackTime(Date backTime) {
        this.backTime = backTime;
    }

    public String getBackCause() {
        return backCause;
    }

    public void setBackCause(String backCause) {
        this.backCause = backCause == null ? null : backCause.trim();
    }

    public BigDecimal getEvalRealCharges() {
        return evalRealCharges;
    }

    public void setEvalRealCharges(BigDecimal evalRealCharges) {
        this.evalRealCharges = evalRealCharges;
    }

    public String getEvaProcessId() {
        return evaProcessId;
    }

    public void setEvaProcessId(String evaProcessId) {
        this.evaProcessId = evaProcessId == null ? null : evaProcessId.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }
}