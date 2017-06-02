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