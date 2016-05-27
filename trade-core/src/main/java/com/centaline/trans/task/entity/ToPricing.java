package com.centaline.trans.task.entity;

import java.math.BigDecimal;
import java.util.Date;
/**核价*/
public class ToPricing {
    private Long pkid;

    private String partCode;

    private String caseCode;

    private Date pricingTime;

    private BigDecimal taxPricing;

    private String houseProperty;

    private String commet;

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

    public Date getPricingTime() {
        return pricingTime;
    }

    public void setPricingTime(Date pricingTime) {
        this.pricingTime = pricingTime;
    }

    public BigDecimal getTaxPricing() {
        return taxPricing;
    }

    public void setTaxPricing(BigDecimal taxPricing) {
        this.taxPricing = taxPricing;
    }

    public String getHouseProperty() {
        return houseProperty;
    }

    public void setHouseProperty(String houseProperty) {
        this.houseProperty = houseProperty == null ? null : houseProperty.trim();
    }

    public String getCommet() {
        return commet;
    }

    public void setCommet(String commet) {
        this.commet = commet == null ? null : commet.trim();
    }
}