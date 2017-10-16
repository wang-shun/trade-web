package com.centaline.trans.ransom.entity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 赎楼列表
 * @author wbwumf
 *
 *2017年10月9日
 */
public class ToRansomCaseVo {
	
    private Long pkid;

    /**
     * 赎楼单编号
     */
    private String ransomCode;

    /**
     * 案件编号
     */
    private String caseCode;

    /**
     * 案件状态
     */
    private String ransomStatus;

    /**
     * 赎楼环节
     */
    private String ransomProperty;

    /**
     * 合作机构编码
     */
    private String comOrgCode;

    /**
     * 借款总金额
     */
    private BigDecimal borroMoney;

    /**
     * 主贷人姓名
     */
    private String borrowerName;

    /**
     * 主贷人电话
     */
    private String borrowerTel;

    /**
     * 是否中止
     */
    private String isstop;

    /**
     * 中止类型
     */
    private String stopType;

    /**
     * 中止原因
     */
    private String stopReason;

    /**
     * 受理时间
     */
    private Date acceptTime;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 创建人 
     */
    private String createUser;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 更新人
     */
    private String updateUser;

    /**
     * 尾款机构ID
     */
    private String finOrgId;

    /**
     * 备注
     */
    private String remark;
    
    public Long getPkid() {
        return pkid;
    }

    public void setPkid(Long pkid) {
        this.pkid = pkid;
    }

    public String getRansomCode() {
        return ransomCode;
    }

    public void setRansomCode(String ransomCode) {
        this.ransomCode = ransomCode == null ? null : ransomCode.trim();
    }

    public String getCaseCode() {
        return caseCode;
    }

    public void setCaseCode(String caseCode) {
        this.caseCode = caseCode == null ? null : caseCode.trim();
    }

    public String getRansomStatus() {
        return ransomStatus;
    }

    public void setRansomStatus(String ransomStatus) {
        this.ransomStatus = ransomStatus == null ? null : ransomStatus.trim();
    }

    public String getRansomProperty() {
        return ransomProperty;
    }

    public void setRansomProperty(String ransomProperty) {
        this.ransomProperty = ransomProperty == null ? null : ransomProperty.trim();
    }

    public String getComOrgCode() {
        return comOrgCode;
    }

    public void setComOrgCode(String comOrgCode) {
        this.comOrgCode = comOrgCode == null ? null : comOrgCode.trim();
    }

    public BigDecimal getBorroMoney() {
        return borroMoney;
    }

    public void setBorroMoney(BigDecimal borroMoney) {
        this.borroMoney = borroMoney;
    }

    public String getBorrowerName() {
        return borrowerName;
    }

    public void setBorrowerName(String borrowerName) {
        this.borrowerName = borrowerName == null ? null : borrowerName.trim();
    }

    public String getBorrowerTel() {
        return borrowerTel;
    }

    public void setBorrowerTel(String borrowerTel) {
        this.borrowerTel = borrowerTel == null ? null : borrowerTel.trim();
    }

    public String getIsstop() {
        return isstop;
    }

    public void setIsstop(String isstop) {
        this.isstop = isstop == null ? null : isstop.trim();
    }

    public String getStopType() {
        return stopType;
    }

    public void setStopType(String stopType) {
        this.stopType = stopType == null ? null : stopType.trim();
    }

    public String getStopReason() {
        return stopReason;
    }

    public void setStopReason(String stopReason) {
        this.stopReason = stopReason == null ? null : stopReason.trim();
    }

    public Date getAcceptTime() {
        return acceptTime;
    }

    public void setAcceptTime(Date acceptTime) {
        this.acceptTime = acceptTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser == null ? null : createUser.trim();
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser == null ? null : updateUser.trim();
    }

    public String getFinOrgId() {
        return finOrgId;
    }

    public void setFinOrgId(String finOrgId) {
        this.finOrgId = finOrgId == null ? null : finOrgId.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

}