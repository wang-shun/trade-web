package com.centaline.trans.award.entity;

import java.math.BigDecimal;
import java.util.Date;

public class TsConsultantAwardBaseConfig {
    private Long pkid;

    private String jobCode;

    private String srvItemCode;

    private String srvItemName;

    private BigDecimal srvFee;

    private Date createTime;

    private String createBy;

    private Date updateTime;

    private String updateBy;

    public Long getPkid() {
        return pkid;
    }

    public void setPkid(Long pkid) {
        this.pkid = pkid;
    }

    public BigDecimal getSrvFee() {
        return srvFee;
    }

    public void setSrvFee(BigDecimal srvFee) {
        this.srvFee = srvFee;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy == null ? null : createBy.trim();
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy == null ? null : updateBy.trim();
    }

	public String getJobCode() {
		return jobCode;
	}

	public void setJobCode(String jobCode) {
		this.jobCode = jobCode;
	}

	public String getSrvItemCode() {
		return srvItemCode;
	}

	public void setSrvItemCode(String srvItemCode) {
		this.srvItemCode = srvItemCode;
	}

	public String getSrvItemName() {
		return srvItemName;
	}

	public void setSrvItemName(String srvItemName) {
		this.srvItemName = srvItemName;
	}
    
    
}