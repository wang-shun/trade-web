package com.centaline.trans.task.entity;

import java.math.BigDecimal;
import java.util.Date;
/**过户*/
public class ToHouseTransfer {
    private Long pkid;

    private String partCode;

    private String caseCode;

    private Date realHtTime;

    private BigDecimal houseHodingTax;

    private BigDecimal personalIncomeTax;

    private BigDecimal businessTax;

    private BigDecimal contractTax;

    private BigDecimal landIncrementTax;

    private String comment;

    private String  useCardPay;

    private  String accompany;//是否陪同 0:不陪同 1:陪同

    private  String accompanyReason;//陪同原因

    private  String accompanyOthersReason;//陪同其他原因

    private BigDecimal  cardPayAmount;//刷卡金额(已废弃)

    private BigDecimal ownerMatAmount;//业主垫资金额

    private Date matAmountTime;//垫资日期

    private String pawnCompanies;//典当公司

    private BigDecimal matCharges;//垫资费用

    private BigDecimal guaranteeFeeAmount;//担保费金额

    public BigDecimal getOwnerMatAmount() {
        return ownerMatAmount;
    }

    public void setOwnerMatAmount(BigDecimal ownerMatAmount) {
        this.ownerMatAmount = ownerMatAmount;
    }

    public Date getMatAmountTime() {
        return matAmountTime;
    }

    public void setMatAmountTime(Date matAmountTime) {
        this.matAmountTime = matAmountTime;
    }

    public String getPawnCompanies() {
        return pawnCompanies;
    }

    public void setPawnCompanies(String pawnCompanies) {
        this.pawnCompanies = pawnCompanies;
    }

    public BigDecimal getMatCharges() {
        return matCharges;
    }

    public void setMatCharges(BigDecimal matCharges) {
        this.matCharges = matCharges;
    }

    public BigDecimal getGuaranteeFeeAmount() {
        return guaranteeFeeAmount;
    }

    public void setGuaranteeFeeAmount(BigDecimal guaranteeFeeAmount) {
        this.guaranteeFeeAmount = guaranteeFeeAmount;
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

    public Date getRealHtTime() {
        return realHtTime;
    }

    public void setRealHtTime(Date realHtTime) {
        this.realHtTime = realHtTime;
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

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment == null ? null : comment.trim();
    }

    public String getUseCardPay() {
        return useCardPay;
    }

    public void setUseCardPay(String useCardPay) {
        this.useCardPay = useCardPay;
    }

    public BigDecimal getCardPayAmount() {
        return cardPayAmount;
    }

    public void setCardPayAmount(BigDecimal cardPayAmount) {
        this.cardPayAmount = cardPayAmount;
    }


    public String getAccompany() {
        return accompany;
    }

    public void setAccompany(String accompany) {
        this.accompany = accompany;
    }

    public String getAccompanyOthersReason() {
        return accompanyOthersReason;
    }

    public void setAccompanyOthersReason(String accompanyOthersReason) {
        this.accompanyOthersReason = accompanyOthersReason;
    }

    public String getAccompanyReason() {
        return accompanyReason;
    }

    public void setAccompanyReason(String accompanyReason) {
        this.accompanyReason = accompanyReason;
    }
}