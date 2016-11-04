package com.centaline.trans.task.entity;

import java.math.BigDecimal;
import java.util.Date;
/**签约*/
public class ToSign {
    private Long pkid;

    private String partCode;

    private String caseCode;

    private Date realConTime;

    private BigDecimal conPrice;

    private BigDecimal realPrice;

    private String isHukou;

    private String isConCert;

    private String comment;

    public Long getPkid() {
        return pkid;
    }

    public void setPkid(Long pkid) {
        this.pkid = pkid;
    }

    public String getPartCode() {
        return partCode;
    }

    public void setPartCode(String partCode) {
        this.partCode = partCode == null ? null : partCode.trim();
    }

    public String getCaseCode() {
        return caseCode;
    }

    public void setCaseCode(String caseCode) {
        this.caseCode = caseCode == null ? null : caseCode.trim();
    }

    public Date getRealConTime() {
        return realConTime;
    }

    public void setRealConTime(Date realConTime) {
        this.realConTime = realConTime;
    }

    public BigDecimal getConPrice() {
        return conPrice;
    }

    public void setConPrice(BigDecimal conPrice) {
        this.conPrice = conPrice;
    }

    public BigDecimal getRealPrice() {
        return realPrice;
    }

    public void setRealPrice(BigDecimal realPrice) {
        this.realPrice = realPrice;
    }

    public String getIsHukou() {
        return isHukou;
    }

    public void setIsHukou(String isHukou) {
        this.isHukou = isHukou == null ? null : isHukou.trim();
    }

    public String getIsConCert() {
        return isConCert;
    }

    public void setIsConCert(String isConCert) {
        this.isConCert = isConCert == null ? null : isConCert.trim();
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment == null ? null : comment.trim();
    }
}