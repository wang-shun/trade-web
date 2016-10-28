package com.centaline.trans.spv.entity;

import java.math.BigDecimal;
import java.util.Date;

public class ToSpvDeDetail {
    private Long pkid;

    private Long deId;

    private String deCondCode;

    private String deAddition;

    private BigDecimal deAmount;

    private Long payeeAccountId;

    private String isDeleted;
    
    //存储account表账户类型
    private String payeeAccountType;

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

    public Long getDeId() {
        return deId;
    }

    public void setDeId(Long deId) {
        this.deId = deId;
    }

    public String getDeCondCode() {
        return deCondCode;
    }

    public void setDeCondCode(String deCondCode) {
        this.deCondCode = deCondCode == null ? null : deCondCode.trim();
    }

    public String getDeAddition() {
        return deAddition;
    }

    public void setDeAddition(String deAddition) {
        this.deAddition = deAddition == null ? null : deAddition.trim();
    }

    public BigDecimal getDeAmount() {
        return deAmount;
    }

    public void setDeAmount(BigDecimal deAmount) {
        this.deAmount = deAmount;
    }

    public Long getPayeeAccountId() {
        return payeeAccountId;
    }

    public void setPayeeAccountId(Long payeeAccountId) {
        this.payeeAccountId = payeeAccountId;
    }

    public String getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(String isDeleted) {
        this.isDeleted = isDeleted == null ? null : isDeleted.trim();
    }
 
    public String getPayeeAccountType() {
		return payeeAccountType;
	}

	public void setPayeeAccountType(String payeeAccountType) {
		this.payeeAccountType = payeeAccountType;
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
}