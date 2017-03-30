package com.centaline.trans.eloan.entity;

import java.math.BigDecimal;
import java.util.Date;

public class ToEloanRel {
    private Long pkid;

    private String eloanCode;

    private BigDecimal releaseAmount;

    private Date releaseTime;

    private Date confirmTime;

    private String confirmStatus;

    private String confirmUser;

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

    public String getEloanCode() {
        return eloanCode;
    }

    public void setEloanCode(String eloanCode) {
        this.eloanCode = eloanCode == null ? null : eloanCode.trim();
    }

    public BigDecimal getReleaseAmount() {
        return releaseAmount;
    }

    public void setReleaseAmount(BigDecimal releaseAmount) {
        this.releaseAmount = releaseAmount;
    }

    public Date getReleaseTime() {
        return releaseTime;
    }

    public void setReleaseTime(Date releaseTime) {
        this.releaseTime = releaseTime;
    }

    public Date getConfirmTime() {
        return confirmTime;
    }

    public void setConfirmTime(Date confirmTime) {
        this.confirmTime = confirmTime;
    }

    public String getConfirmStatus() {
        return confirmStatus;
    }

    public void setConfirmStatus(String confirmStatus) {
        this.confirmStatus = confirmStatus == null ? null : confirmStatus.trim();
    }

    public String getConfirmUser() {
        return confirmUser;
    }

    public void setConfirmUser(String confirmUser) {
        this.confirmUser = confirmUser == null ? null : confirmUser.trim();
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