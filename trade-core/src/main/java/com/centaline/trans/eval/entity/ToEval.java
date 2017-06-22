package com.centaline.trans.eval.entity;

import java.math.BigDecimal;
import java.util.Date;

public class ToEval {
    private Long pkid;

    private String caseCode;

    private String evaCode;

    private BigDecimal conPrice;

    private Long mortgageId;

    private BigDecimal evaFee;

    private BigDecimal serviceFee;

    private BigDecimal feeTotal;

    private BigDecimal evaFeeCost;

    private BigDecimal evaFeePerf;

    private BigDecimal serviceFeePerf;

    private String createBy;

    private Date createTime;

    private String updateBy;

    private Date updateTime;
    
    private String orgId;

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

    public BigDecimal getConPrice() {
        return conPrice;
    }

    public void setConPrice(BigDecimal conPrice) {
        this.conPrice = conPrice;
    }

    public Long getMortgageId() {
        return mortgageId;
    }

    public void setMortgageId(Long mortgageId) {
        this.mortgageId = mortgageId;
    }



    public BigDecimal getServiceFee() {
        return serviceFee;
    }

    public void setServiceFee(BigDecimal serviceFee) {
        this.serviceFee = serviceFee;
    }

    public BigDecimal getFeeTotal() {
        return feeTotal;
    }

    public void setFeeTotal(BigDecimal feeTotal) {
        this.feeTotal = feeTotal;
    }

    public BigDecimal getServiceFeePerf() {
        return serviceFeePerf;
    }

    public void setServiceFeePerf(BigDecimal serviceFeePerf) {
        this.serviceFeePerf = serviceFeePerf;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy == null ? null : createBy.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy == null ? null : updateBy.trim();
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public BigDecimal getEvaFeeCost() {
		return evaFeeCost;
	}

	public void setEvaFeeCost(BigDecimal evaFeeCost) {
		this.evaFeeCost = evaFeeCost;
	}

	public BigDecimal getEvaFeePerf() {
		return evaFeePerf;
	}

	public void setEvaFeePerf(BigDecimal evaFeePerf) {
		this.evaFeePerf = evaFeePerf;
	}

	public BigDecimal getEvaFee() {
		return evaFee;
	}

	public void setEvaFee(BigDecimal evaFee) {
		this.evaFee = evaFee;
	}
}