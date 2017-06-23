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

    private BigDecimal houseHodingTax;

    private BigDecimal personalIncomeTax;

    private BigDecimal businessTax;

    private BigDecimal contractTax;

    private BigDecimal landIncrementTax;

    private String houseQuantity;		/*是否首套 0:首套 1：二套 2：多套*/

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

    public String getHouseQuantity() {
        return houseQuantity;
    }

    public void setHouseQuantity(String houseQuantity) {
        this.houseQuantity = houseQuantity;
    }
}