package com.centaline.trans.eval.entity;

import java.math.BigDecimal;
/**
 * @author xiefei1
 * @since 2017年10月11日 下午3:14:04 
 * @description 调佣对象与调佣金额
 * 
 */
public class ToEvaCommPersonAmount {
    private Long pkid;

    private String caseCode;
//    四个选项：1.贷款权证；2.过户权证；3.合作人；4.分成人
    private String position;

    private String department;

    private String employeeName;

    private BigDecimal shareAmount;

    private String shareReason;

    private Integer dealCount;

    private String cooperateType;

    private String cooperatePerson;

    private String cooperateDept;

    private String cooperateManager;

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

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position == null ? null : position.trim();
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department == null ? null : department.trim();
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName == null ? null : employeeName.trim();
    }

    public BigDecimal getShareAmount() {
        return shareAmount;
    }

    public void setShareAmount(BigDecimal shareAmount) {
        this.shareAmount = shareAmount;
    }

    public String getShareReason() {
        return shareReason;
    }

    public void setShareReason(String shareReason) {
        this.shareReason = shareReason == null ? null : shareReason.trim();
    }

    public Integer getDealCount() {
        return dealCount;
    }

    public void setDealCount(Integer dealCount) {
        this.dealCount = dealCount;
    }

    public String getCooperateType() {
        return cooperateType;
    }

    public void setCooperateType(String cooperateType) {
        this.cooperateType = cooperateType == null ? null : cooperateType.trim();
    }

    public String getCooperatePerson() {
        return cooperatePerson;
    }

    public void setCooperatePerson(String cooperatePerson) {
        this.cooperatePerson = cooperatePerson == null ? null : cooperatePerson.trim();
    }

    public String getCooperateDept() {
        return cooperateDept;
    }

    public void setCooperateDept(String cooperateDept) {
        this.cooperateDept = cooperateDept == null ? null : cooperateDept.trim();
    }

    public String getCooperateManager() {
        return cooperateManager;
    }

    public void setCooperateManager(String cooperateManager) {
        this.cooperateManager = cooperateManager == null ? null : cooperateManager.trim();
    }
}