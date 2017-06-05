package com.centaline.trans.common.repository;

import java.util.Date;

public class ToPropertyResearchAttach {
    private Long pkid;

    private String prCode;

    private String preFileAdress;

    private String preFileCode;

    private String partCode;

    private String fileCat;

    private String fileName;

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

    public String getPrCode() {
        return prCode;
    }

    public void setPrCode(String prCode) {
        this.prCode = prCode == null ? null : prCode.trim();
    }

    public String getPreFileAdress() {
        return preFileAdress;
    }

    public void setPreFileAdress(String preFileAdress) {
        this.preFileAdress = preFileAdress == null ? null : preFileAdress.trim();
    }

    public String getPreFileCode() {
        return preFileCode;
    }

    public void setPreFileCode(String preFileCode) {
        this.preFileCode = preFileCode == null ? null : preFileCode.trim();
    }

    public String getPartCode() {
        return partCode;
    }

    public void setPartCode(String partCode) {
        this.partCode = partCode == null ? null : partCode.trim();
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