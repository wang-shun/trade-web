package com.centaline.trans.task.entity;

import java.math.BigDecimal;
import java.util.Date;
/**审税*/
public class ToTax {
    private Long pkid;

    private String caseCode;

    private Date taxTime;

    private String isUniqueHome;

    private String holdYear;

    private String comment;
    
    private BigDecimal houseHodingTax;

    private BigDecimal personalIncomeTax;

    private BigDecimal businessTax;

    private BigDecimal contractTax;

    private BigDecimal landIncrementTax;
    
    private String createBy;

	private String updateBy;
	
	private Date updateTime;
	
	private Date createTime;
	
	private String isActive;

    public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	public BigDecimal getHouseHodingTax() {
		return houseHodingTax;
	}

	public void setHouseHodingTax(BigDecimal houseHodingTax) {
		this.houseHodingTax = houseHodingTax;
	}

	public BigDecimal getPersonalIncomeTax() {
		return personalIncomeTax;
	}

	public void setPersonalIncomeTax(BigDecimal personalIncomeTax) {
		this.personalIncomeTax = personalIncomeTax;
	}

	public BigDecimal getBusinessTax() {
		return businessTax;
	}

	public void setBusinessTax(BigDecimal businessTax) {
		this.businessTax = businessTax;
	}

	public BigDecimal getContractTax() {
		return contractTax;
	}

	public void setContractTax(BigDecimal contractTax) {
		this.contractTax = contractTax;
	}

	public BigDecimal getLandIncrementTax() {
		return landIncrementTax;
	}

	public void setLandIncrementTax(BigDecimal landIncrementTax) {
		this.landIncrementTax = landIncrementTax;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public String getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
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

    public Date getTaxTime() {
        return taxTime;
    }

    public void setTaxTime(Date taxTime) {
        this.taxTime = taxTime;
    }

    public String getIsUniqueHome() {
        return isUniqueHome;
    }

    public void setIsUniqueHome(String isUniqueHome) {
        this.isUniqueHome = isUniqueHome == null ? null : isUniqueHome.trim();
    }

    public String getHoldYear() {
        return holdYear;
    }

    public void setHoldYear(String holdYear) {
        this.holdYear = holdYear == null ? null : holdYear.trim();
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment == null ? null : comment.trim();
    }
}