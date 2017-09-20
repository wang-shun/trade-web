package com.centaline.trans.cases.entity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author wbzhouht
 * 新增了缴税字段
 */
public class VCaseTradeInfo {
    private String caseCode;

    private Date createTime;

    private Date resDate;
    private BigDecimal conPrice;
    private BigDecimal realPrice;

    private String isHukou;

    private String isConCert;

    private Date realConTime;

    private String payName1;

    private String payType1;

    private Date payTime1;

    private String payName2;

    private String payType2;

    private Date payTime2;

    private String payName3;

    private String payType3;

    private Date payTime3;

    private String payName4;

    private String payType4;

    private Date payTime4;

    private BigDecimal amount1;
    private BigDecimal amount2;
    private BigDecimal amount3;
    private BigDecimal amount4;
    private BigDecimal uncloseMoney;

    private String upBank;

    private String closeType;

    private Date loanCloseCode;

    private BigDecimal pricingTax;//新增缴税表中核税价

    private BigDecimal houseHodingTax;//原过户表中的房产税，现天津的没有房产税，顾可舍弃

    private BigDecimal personalIncomeTax;//新增缴税表中个人所得税

    private BigDecimal businessTax;//新增缴税表中增值税及附加税

    private BigDecimal contractTax;//新增缴税表中买方契税

    private BigDecimal landIncrementTax;//新增缴税表中土地增值税

    private Date realHtTime;//实际过户时间

    private String holdYear;//审税表中购房年数，现天津没有审税环节，顾可以废弃

    private String isUniqueHome;//审税表中是否唯一住房，现天津没有审税环节，顾可以废弃

    private Date taxTime;//审税表中实际审税时间，现天津没有审税环节，顾可以废弃

    private String houseProperty;//核价表中房屋性质，现天津没有核价环节，顾可以废弃

    private Date pricingTime;//合脚表中核价时间，现天津没有核价环节，顾可以废弃

    private BigDecimal taxPricing;//原核价表中的税务核定价，现天津没有核价环节，顾可以废弃

    private Date paymentTime;//新增缴税时间

    private Date realPlsTime;//实际查限购时间，天津因没有查限购环节，顾可废弃

    private Date realPropertyGetTime;

    private Date closeTime;

    //过户流程提交时间
    private Date guohuSubTime;
    //签约流程提交时间
    private Date transSignSubTime;
    //过户流程提交时间 和实际过户时间相差的天数
    private long guohuSubAndRealTimeDiff;
    //签约流程提交时间 和实际签约时间相差的天数
    private long transSignSubAndRealTimeDiff;

    private BigDecimal houseHodingTaxSign;

    private BigDecimal personalIncomeTaxSign;

    private BigDecimal businessTaxSign;

    private BigDecimal contractTaxSign;

    private BigDecimal landIncrementTaxSign;

    public BigDecimal getHouseHodingTaxSign() {
        return houseHodingTaxSign;
    }

    public void setHouseHodingTaxSign(BigDecimal houseHodingTaxSign) {
        this.houseHodingTaxSign = houseHodingTaxSign;
    }

    public BigDecimal getPersonalIncomeTaxSign() {
        return personalIncomeTaxSign;
    }

    public void setPersonalIncomeTaxSign(BigDecimal personalIncomeTaxSign) {
        this.personalIncomeTaxSign = personalIncomeTaxSign;
    }

    public BigDecimal getBusinessTaxSign() {
        return businessTaxSign;
    }

    public void setBusinessTaxSign(BigDecimal businessTaxSign) {
        this.businessTaxSign = businessTaxSign;
    }

    public BigDecimal getContractTaxSign() {
        return contractTaxSign;
    }

    public void setContractTaxSign(BigDecimal contractTaxSign) {
        this.contractTaxSign = contractTaxSign;
    }

    public BigDecimal getLandIncrementTaxSign() {
        return landIncrementTaxSign;
    }

    public void setLandIncrementTaxSign(BigDecimal landIncrementTaxSign) {
        this.landIncrementTaxSign = landIncrementTaxSign;
    }

    public String getCaseCode() {
        return caseCode;
    }

    public void setCaseCode(String caseCode) {
        this.caseCode = caseCode == null ? null : caseCode.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getResDate() {
        return resDate;
    }

    public void setResDate(Date resDate) {
        this.resDate = resDate;
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

    public Date getRealConTime() {
        return realConTime;
    }

    public void setRealConTime(Date realConTime) {
        this.realConTime = realConTime;
    }

    public String getPayName1() {
        return payName1;
    }

    public void setPayName1(String payName1) {
        this.payName1 = payName1 == null ? null : payName1.trim();
    }

    public String getPayType1() {
        return payType1;
    }

    public void setPayType1(String payType1) {
        this.payType1 = payType1 == null ? null : payType1.trim();
    }

    public Date getPayTime1() {
        return payTime1;
    }

    public void setPayTime1(Date payTime1) {
        this.payTime1 = payTime1;
    }

    public String getPayName2() {
        return payName2;
    }

    public void setPayName2(String payName2) {
        this.payName2 = payName2 == null ? null : payName2.trim();
    }

    public String getPayType2() {
        return payType2;
    }

    public void setPayType2(String payType2) {
        this.payType2 = payType2 == null ? null : payType2.trim();
    }

    public Date getPayTime2() {
        return payTime2;
    }

    public void setPayTime2(Date payTime2) {
        this.payTime2 = payTime2;
    }

    public String getPayName3() {
        return payName3;
    }

    public void setPayName3(String payName3) {
        this.payName3 = payName3 == null ? null : payName3.trim();
    }

    public String getPayType3() {
        return payType3;
    }

    public void setPayType3(String payType3) {
        this.payType3 = payType3 == null ? null : payType3.trim();
    }

    public Date getPayTime3() {
        return payTime3;
    }

    public void setPayTime3(Date payTime3) {
        this.payTime3 = payTime3;
    }

    public String getPayName4() {
        return payName4;
    }

    public void setPayName4(String payName4) {
        this.payName4 = payName4 == null ? null : payName4.trim();
    }

    public String getPayType4() {
        return payType4;
    }

    public void setPayType4(String payType4) {
        this.payType4 = payType4 == null ? null : payType4.trim();
    }

    public Date getPayTime4() {
        return payTime4;
    }

    public void setPayTime4(Date payTime4) {
        this.payTime4 = payTime4;
    }

    public BigDecimal getUncloseMoney() {
        return uncloseMoney;
    }

    public BigDecimal getConPrice() {
        return conPrice;
    }

    public BigDecimal getRealPrice() {
        return realPrice;
    }

    public BigDecimal getAmount1() {
        return amount1;
    }

    public BigDecimal getAmount2() {
        return amount2;
    }

    public BigDecimal getAmount3() {
        return amount3;
    }

    public BigDecimal getAmount4() {
        return amount4;
    }

    public void setConPrice(BigDecimal conPrice) {
        this.conPrice = conPrice;
    }

    public void setRealPrice(BigDecimal realPrice) {
        this.realPrice = realPrice;
    }

    public void setAmount1(BigDecimal amount1) {
        this.amount1 = amount1;
    }

    public void setAmount2(BigDecimal amount2) {
        this.amount2 = amount2;
    }

    public void setAmount3(BigDecimal amount3) {
        this.amount3 = amount3;
    }

    public void setAmount4(BigDecimal amount4) {
        this.amount4 = amount4;
    }

    public void setUncloseMoney(BigDecimal uncloseMoney) {
        this.uncloseMoney = uncloseMoney;
    }

    public String getCloseType() {
        return closeType;
    }

    public void setCloseType(String closeType) {
        this.closeType = closeType == null ? null : closeType.trim();
    }

    public Date getLoanCloseCode() {
        return loanCloseCode;
    }

    public void setLoanCloseCode(Date loanCloseCode) {
        this.loanCloseCode = loanCloseCode;
    }

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

    public Date getRealHtTime() {
        return realHtTime;
    }

    public void setRealHtTime(Date realHtTime) {
        this.realHtTime = realHtTime;
    }

    public String getHoldYear() {
        return holdYear;
    }

    public void setHoldYear(String holdYear) {
        this.holdYear = holdYear == null ? null : holdYear.trim();
    }

    public String getIsUniqueHome() {
        return isUniqueHome;
    }

    public void setIsUniqueHome(String isUniqueHome) {
        this.isUniqueHome = isUniqueHome == null ? null : isUniqueHome.trim();
    }

    public Date getTaxTime() {
        return taxTime;
    }

    public void setTaxTime(Date taxTime) {
        this.taxTime = taxTime;
    }

    public String getHouseProperty() {
        return houseProperty;
    }

    public void setHouseProperty(String houseProperty) {
        this.houseProperty = houseProperty == null ? null : houseProperty.trim();
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

    public Date getRealPlsTime() {
        return realPlsTime;
    }

    public void setRealPlsTime(Date realPlsTime) {
        this.realPlsTime = realPlsTime;
    }

    public Date getRealPropertyGetTime() {
        return realPropertyGetTime;
    }

    public void setRealPropertyGetTime(Date realPropertyGetTime) {
        this.realPropertyGetTime = realPropertyGetTime;
    }

    public Date getCloseTime() {
        return closeTime;
    }

    public void setCloseTime(Date closeTime) {
        this.closeTime = closeTime;
    }

    public String getUpBank() {
        return upBank;
    }

    public void setUpBank(String upBank) {
        this.upBank = upBank;
    }

    public Date getGuohuSubTime() {
        return guohuSubTime;
    }

    public void setGuohuSubTime(Date guohuSubTime) {
        this.guohuSubTime = guohuSubTime;
    }

    public Date getTransSignSubTime() {
        return transSignSubTime;
    }

    public void setTransSignSubTime(Date transSignSubTime) {
        this.transSignSubTime = transSignSubTime;
    }

    public long getGuohuSubAndRealTimeDiff() {
        return guohuSubAndRealTimeDiff;
    }

    public void setGuohuSubAndRealTimeDiff(long guohuSubAndRealTimeDiff) {
        this.guohuSubAndRealTimeDiff = guohuSubAndRealTimeDiff;
    }

    public long getTransSignSubAndRealTimeDiff() {
        return transSignSubAndRealTimeDiff;
    }

    public void setTransSignSubAndRealTimeDiff(long transSignSubAndRealTimeDiff) {
        this.transSignSubAndRealTimeDiff = transSignSubAndRealTimeDiff;
    }

    public BigDecimal getPricingTax() {
        return pricingTax;
    }

    public void setPricingTax(BigDecimal pricingTax) {
        this.pricingTax = pricingTax;
    }

    public Date getPaymentTime() {
        return paymentTime;
    }

    public void setPaymentTime(Date paymentTime) {
        this.paymentTime = paymentTime;
    }
}