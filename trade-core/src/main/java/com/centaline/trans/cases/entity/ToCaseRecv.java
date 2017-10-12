package com.centaline.trans.cases.entity;

import java.util.Date;
/**
 * @author xiefei1
 * @since 2017年9月4日 下午1:50:41 
 * @description 接单跟进
 */
public class ToCaseRecv {
    private String caseCode;

    private Date estEvlApplyTime;

    private Date estSignTime;

    private Date estF2fSignTime;

    private String purchaseHouseNo;

    private String loanHouseNo;

    private String marriageStatus;

    private String familyStatus;

    private String evalByWho;

    private String huji;

    private String societySecuretyYears;

    private String taxSecuretyYears;

    private String houseSubsidy;

    private String loanWarn;

    private String houseFrom;

    private String mortInfo;

    private String oriPrice;

    public String getCaseCode() {
        return caseCode;
    }

    public void setCaseCode(String caseCode) {
        this.caseCode = caseCode == null ? null : caseCode.trim();
    }

    public Date getEstEvlApplyTime() {
        return estEvlApplyTime;
    }

    public void setEstEvlApplyTime(Date estEvlApplyTime) {
        this.estEvlApplyTime = estEvlApplyTime;
    }

    public Date getEstSignTime() {
        return estSignTime;
    }

    public void setEstSignTime(Date estSignTime) {
        this.estSignTime = estSignTime;
    }

    public Date getEstF2fSignTime() {
        return estF2fSignTime;
    }

    public void setEstF2fSignTime(Date estF2fSignTime) {
        this.estF2fSignTime = estF2fSignTime;
    }

    public String getPurchaseHouseNo() {
        return purchaseHouseNo;
    }

    public void setPurchaseHouseNo(String purchaseHouseNo) {
        this.purchaseHouseNo = purchaseHouseNo == null ? null : purchaseHouseNo.trim();
    }

    public String getLoanHouseNo() {
        return loanHouseNo;
    }

    public void setLoanHouseNo(String loanHouseNo) {
        this.loanHouseNo = loanHouseNo == null ? null : loanHouseNo.trim();
    }

    public String getMarriageStatus() {
        return marriageStatus;
    }

    public void setMarriageStatus(String marriageStatus) {
        this.marriageStatus = marriageStatus == null ? null : marriageStatus.trim();
    }

    public String getFamilyStatus() {
        return familyStatus;
    }

    public void setFamilyStatus(String familyStatus) {
        this.familyStatus = familyStatus == null ? null : familyStatus.trim();
    }

    public String getEvalByWho() {
        return evalByWho;
    }

    public void setEvalByWho(String evalByWho) {
        this.evalByWho = evalByWho == null ? null : evalByWho.trim();
    }

    public String getHuji() {
        return huji;
    }

    public void setHuji(String huji) {
        this.huji = huji == null ? null : huji.trim();
    }

    public String getSocietySecuretyYears() {
        return societySecuretyYears;
    }

    public void setSocietySecuretyYears(String societySecuretyYears) {
        this.societySecuretyYears = societySecuretyYears == null ? null : societySecuretyYears.trim();
    }

    public String getTaxSecuretyYears() {
        return taxSecuretyYears;
    }

    public void setTaxSecuretyYears(String taxSecuretyYears) {
        this.taxSecuretyYears = taxSecuretyYears == null ? null : taxSecuretyYears.trim();
    }

    public String getHouseSubsidy() {
        return houseSubsidy;
    }

    public void setHouseSubsidy(String houseSubsidy) {
        this.houseSubsidy = houseSubsidy == null ? null : houseSubsidy.trim();
    }

    public String getLoanWarn() {
        return loanWarn;
    }

    public void setLoanWarn(String loanWarn) {
        this.loanWarn = loanWarn == null ? null : loanWarn.trim();
    }

    public String getHouseFrom() {
        return houseFrom;
    }

    public void setHouseFrom(String houseFrom) {
        this.houseFrom = houseFrom == null ? null : houseFrom.trim();
    }

    public String getMortInfo() {
        return mortInfo;
    }

    public void setMortInfo(String mortInfo) {
        this.mortInfo = mortInfo == null ? null : mortInfo.trim();
    }

    public String getOriPrice() {
        return oriPrice;
    }

    public void setOriPrice(String oriPrice) {
        this.oriPrice = oriPrice == null ? null : oriPrice.trim();
    }
}