package com.centaline.trans.common.entity;

import java.util.Date;

public class ToCcaiAttachment {
    private Long pkid;

    private String caseCode;

    private String ccaiFileid;

    private String ccaiCode;

    private String fileCat;

    private String fileName;

    private String createBy;

    private Date updateTime;

    private String updateBy;

    private Date createTime;

    private String available;

    private Date uploadTime;

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

    public String getCcaiFileid() {
        return ccaiFileid;
    }

    public void setCcaiFileid(String ccaiFileid) {
        this.ccaiFileid = ccaiFileid == null ? null : ccaiFileid.trim();
    }

    public String getCcaiCode() {
        return ccaiCode;
    }

    public void setCcaiCode(String ccaiCode) {
        this.ccaiCode = ccaiCode == null ? null : ccaiCode.trim();
    }

    public String getFileCat() {
        return fileCat;
    }

    public void setFileCat(String fileCat) {
        this.fileCat = fileCat == null ? null : fileCat.trim();
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName == null ? null : fileName.trim();
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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getAvailable() {
        return available;
    }

    public void setAvailable(String available) {
        this.available = available == null ? null : available.trim();
    }

    public Date getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(Date uploadTime) {
        this.uploadTime = uploadTime;
    }
}