package com.centaline.trans.product.entity;

import java.util.Date;

public class TtsProductCategory {
    private Long pkid;

    private String prdcCode;

    private String prdcName;

    private String prdcDesc;

    private String applyCond;

    private String prdcIcon;

    private String propGroup;

    private Date createTime;

    private String createBy;

    private Date updateTime;

    private String updateBy;

    private Integer sort;

    private Integer status;

    private String ubjectCode;

    public Long getPkid() {
        return pkid;
    }

    public void setPkid(Long pkid) {
        this.pkid = pkid;
    }

    public String getPrdcCode() {
        return prdcCode;
    }

    public void setPrdcCode(String prdcCode) {
        this.prdcCode = prdcCode == null ? null : prdcCode.trim();
    }

    public String getPrdcName() {
        return prdcName;
    }

    public void setPrdcName(String prdcName) {
        this.prdcName = prdcName == null ? null : prdcName.trim();
    }

    public String getPrdcDesc() {
        return prdcDesc;
    }

    public void setPrdcDesc(String prdcDesc) {
        this.prdcDesc = prdcDesc == null ? null : prdcDesc.trim();
    }

    public String getApplyCond() {
        return applyCond;
    }

    public void setApplyCond(String applyCond) {
        this.applyCond = applyCond == null ? null : applyCond.trim();
    }

    public String getPrdcIcon() {
        return prdcIcon;
    }

    public void setPrdcIcon(String prdcIcon) {
        this.prdcIcon = prdcIcon == null ? null : prdcIcon.trim();
    }

    public String getPropGroup() {
        return propGroup;
    }

    public void setPropGroup(String propGroup) {
        this.propGroup = propGroup == null ? null : propGroup.trim();
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

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getUbjectCode() {
        return ubjectCode;
    }

    public void setUbjectCode(String ubjectCode) {
        this.ubjectCode = ubjectCode == null ? null : ubjectCode.trim();
    }
}