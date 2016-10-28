package com.centaline.trans.mortgage.entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class ToEguPricing {
    private Long pkid;

    private String caseCode;

    private String evaCode;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date ariseTime;
    
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date responseTime;

    private String status;

    private String result;

    private Double unitPrice;

    private Double totalPrice;

    private String isFinal;

    private String applyCode;

    private String comfirmSeq;
    
    private String finOrgCode;
    
    private String isMainLoanBank;
    
    private Double expectRate;
    
    private String ariserId;

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

    public Date getAriseTime() {
        return ariseTime;
    }

    public void setAriseTime(Date ariseTime) {
        this.ariseTime = ariseTime;
    }

    public Date getResponseTime() {
        return responseTime;
    }

    public void setResponseTime(Date responseTime) {
        this.responseTime = responseTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result == null ? null : result.trim();
    }

    public Double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getIsFinal() {
        return isFinal;
    }

    public void setIsFinal(String isFinal) {
        this.isFinal = isFinal == null ? null : isFinal.trim();
    }

    public String getApplyCode() {
        return applyCode;
    }

    public void setApplyCode(String applyCode) {
        this.applyCode = applyCode == null ? null : applyCode.trim();
    }

    public String getComfirmSeq() {
        return comfirmSeq;
    }

    public void setComfirmSeq(String comfirmSeq) {
        this.comfirmSeq = comfirmSeq == null ? null : comfirmSeq.trim();
    }

	public String getFinOrgCode() {
		return finOrgCode;
	}

	public void setFinOrgCode(String finOrgCode) {
		this.finOrgCode = finOrgCode;
	}

	public String getIsMainLoanBank() {
		return isMainLoanBank;
	}

	public void setIsMainLoanBank(String isMainLoanBank) {
		this.isMainLoanBank = isMainLoanBank;
	}

	public Double getExpectRate() {
		return expectRate;
	}

	public void setExpectRate(Double expectRate) {
		this.expectRate = expectRate;
	}

	public String getAriserId() {
		return ariserId;
	}

	public void setAriserId(String ariserId) {
		this.ariserId = ariserId;
	}

}
