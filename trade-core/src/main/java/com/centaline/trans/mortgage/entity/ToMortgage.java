package com.centaline.trans.mortgage.entity;

import java.math.BigDecimal;
import java.util.Date;

import com.centaline.trans.mgr.entity.ToSupDocu;
import com.fasterxml.jackson.annotation.JsonFormat;

public class ToMortgage {
    private Long pkid;

    private String caseCode;

    private String mortType;

    private BigDecimal mortTotalAmount;

    private String finOrgCode;

    private String custCode;

    private BigDecimal comAmount;

    private Integer comYear;

    private BigDecimal comDiscount;

    private BigDecimal prfAmount;

    private Integer prfYear;

    private String lendWay;

    private Integer houseNum;

    private String isLoanerArrive;
    
    private String partCode;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private Date signDate;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private Date apprDate;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private Date tazhengArrDate;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private Date lendDate;

    private String custCompany;

    private String ifReportBeforeLend;

    private String loanerName;

    private String loanerPhone;

    private String remark;
    
    private String lastLoanBank;
    
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private Date prfApplyDate;
    
    private String ifRequireReconsider;
    
    private ToSupDocu toSupDocu;
    
    private String isDelegateYucui;
    
    private String isMainLoanBank;
    
    private String custName;
    
    private String selfDelReason;
    
    private String isActive;
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

    public String getMortType() {
        return mortType;
    }

    public void setMortType(String mortType) {
        this.mortType = mortType == null ? null : mortType.trim();
    }

    public BigDecimal getMortTotalAmount() {
        return mortTotalAmount;
    }

    public void setMortTotalAmount(BigDecimal mortTotalAmount) {
        this.mortTotalAmount = mortTotalAmount;
    }

    public String getFinOrgCode() {
        return finOrgCode;
    }

    public void setFinOrgCode(String finOrgCode) {
        this.finOrgCode = finOrgCode == null ? null : finOrgCode.trim();
    }

    public String getCustCode() {
        return custCode;
    }

    public void setCustCode(String custCode) {
        this.custCode = custCode == null ? null : custCode.trim();
    }

    public BigDecimal getComAmount() {
        return comAmount;
    }

    public void setComAmount(BigDecimal comAmount) {
        this.comAmount = comAmount;
    }

    public Integer getComYear() {
        return comYear;
    }

    public void setComYear(Integer comYear) {
        this.comYear = comYear;
    }

    public BigDecimal getComDiscount() {
        return comDiscount;
    }

    public void setComDiscount(BigDecimal comDiscount) {
        this.comDiscount = comDiscount;
    }

    public BigDecimal getPrfAmount() {
        return prfAmount;
    }

    public void setPrfAmount(BigDecimal prfAmount) {
        this.prfAmount = prfAmount;
    }

    public Integer getPrfYear() {
        return prfYear;
    }

    public void setPrfYear(Integer prfYear) {
        this.prfYear = prfYear;
    }

    public String getLendWay() {
        return lendWay;
    }

    public void setLendWay(String lendWay) {
        this.lendWay = lendWay == null ? null : lendWay.trim();
    }

    public Integer getHouseNum() {
        return houseNum;
    }

    public void setHouseNum(Integer houseNum) {
        this.houseNum = houseNum;
    }

    public String getIsLoanerArrive() {
        return isLoanerArrive;
    }

    public void setIsLoanerArrive(String isLoanerArrive) {
        this.isLoanerArrive = isLoanerArrive == null ? null : isLoanerArrive.trim();
    }

    public Date getSignDate() {
        return signDate;
    }

    public void setSignDate(Date signDate) {
        this.signDate = signDate;
    }

    public Date getApprDate() {
        return apprDate;
    }

    public void setApprDate(Date apprDate) {
        this.apprDate = apprDate;
    }

    public Date getTazhengArrDate() {
        return tazhengArrDate;
    }

    public void setTazhengArrDate(Date tazhengArrDate) {
        this.tazhengArrDate = tazhengArrDate;
    }

    public Date getLendDate() {
        return lendDate;
    }

    public void setLendDate(Date lendDate) {
        this.lendDate = lendDate;
    }

    public String getCustCompany() {
        return custCompany;
    }

    public void setCustCompany(String custCompany) {
        this.custCompany = custCompany == null ? null : custCompany.trim();
    }

    public String getIfReportBeforeLend() {
        return ifReportBeforeLend;
    }

    public void setIfReportBeforeLend(String ifReportBeforeLend) {
        this.ifReportBeforeLend = ifReportBeforeLend == null ? null : ifReportBeforeLend.trim();
    }

    public String getLoanerName() {
        return loanerName;
    }

    public void setLoanerName(String loanerName) {
        this.loanerName = loanerName == null ? null : loanerName.trim();
    }

    public String getLoanerPhone() {
        return loanerPhone;
    }

    public void setLoanerPhone(String loanerPhone) {
        this.loanerPhone = loanerPhone == null ? null : loanerPhone.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

	public String getLastLoanBank() {
		return lastLoanBank;
	}

	public void setLastLoanBank(String lastLoanBank) {
		this.lastLoanBank = lastLoanBank;
	}

	public Date getPrfApplyDate() {
		return prfApplyDate;
	}

	public void setPrfApplyDate(Date prfApplyDate) {
		this.prfApplyDate = prfApplyDate;
	}

	public String getIfRequireReconsider() {
		return ifRequireReconsider;
	}

	public void setIfRequireReconsider(String ifRequireReconsider) {
		this.ifRequireReconsider = ifRequireReconsider;
	}

	public ToSupDocu getToSupDocu() {
		return toSupDocu;
	}

	public void setToSupDocu(ToSupDocu toSupDocu) {
		this.toSupDocu = toSupDocu;
	}

	public String getIsDelegateYucui() {
		return isDelegateYucui;
	}

	public void setIsDelegateYucui(String isDelegateYucui) {
		this.isDelegateYucui = isDelegateYucui;
	}

	public String getIsMainLoanBank() {
		return isMainLoanBank;
	}

	public void setIsMainLoanBank(String isMainLoanBank) {
		this.isMainLoanBank = isMainLoanBank;
	}

	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	public String getPartCode() {
		return partCode;
	}

	public void setPartCode(String partCode) {
		this.partCode = partCode;
	}

	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	public String getSelfDelReason() {
		return selfDelReason;
	}

	public void setSelfDelReason(String selfDelReason) {
		this.selfDelReason = selfDelReason;
	}
    
}