package com.centaline.trans.eloan.entity;

import java.math.BigDecimal;
import java.util.Date;

public class ToEloanLoaner {
    private Long pkid;

    private String eloanCode;

    private String caseCode;

    private String receiveCode;

    private String custName;

    private String custPhone;

    private String houAddress;

    private Date applyTime;

    private BigDecimal applyAmount;

    private Integer month;

    private String excutorId;

    private String excutorTeam;

    private String excutorDistrict;

    private String flowStatus;

    private String loanerStatus;

    private String loanerDesc;

    private String sendId;

    private String sendName;

    private Date receiveTime;

    private Date sendTime;

    private String receiveId;

    private String receiveName;

    private String rejectId;

    private String rejectName;

    private Date rejectTime;

    private String cancleId;

    private String cancleName;

    private Date cancleTime;

    private String approvalId;

    private String approvalName;

    private Date approvalTime;

    private Date createTime;

    private String createBy;

    private Date updateTime;

    private Date updateBy;

    public Long getPkid() {
        return pkid;
    }

    public void setPkid(Long pkid) {
        this.pkid = pkid;
    }

    public String getEloanCode() {
        return eloanCode;
    }

    public void setEloanCode(String eloanCode) {
        this.eloanCode = eloanCode == null ? null : eloanCode.trim();
    }

    public String getCaseCode() {
        return caseCode;
    }

    public void setCaseCode(String caseCode) {
        this.caseCode = caseCode == null ? null : caseCode.trim();
    }

    public String getReceiveCode() {
        return receiveCode;
    }

    public void setReceiveCode(String receiveCode) {
        this.receiveCode = receiveCode == null ? null : receiveCode.trim();
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName == null ? null : custName.trim();
    }

    public String getCustPhone() {
        return custPhone;
    }

    public void setCustPhone(String custPhone) {
        this.custPhone = custPhone == null ? null : custPhone.trim();
    }

    public String getHouAddress() {
        return houAddress;
    }

    public void setHouAddress(String houAddress) {
        this.houAddress = houAddress == null ? null : houAddress.trim();
    }

    public Date getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(Date applyTime) {
        this.applyTime = applyTime;
    }

    public BigDecimal getApplyAmount() {
        return applyAmount;
    }

    public void setApplyAmount(BigDecimal applyAmount) {
        this.applyAmount = applyAmount;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public String getExcutorId() {
        return excutorId;
    }

    public void setExcutorId(String excutorId) {
        this.excutorId = excutorId == null ? null : excutorId.trim();
    }

    public String getExcutorTeam() {
        return excutorTeam;
    }

    public void setExcutorTeam(String excutorTeam) {
        this.excutorTeam = excutorTeam == null ? null : excutorTeam.trim();
    }

    public String getExcutorDistrict() {
        return excutorDistrict;
    }

    public void setExcutorDistrict(String excutorDistrict) {
        this.excutorDistrict = excutorDistrict == null ? null : excutorDistrict.trim();
    }

    public String getFlowStatus() {
        return flowStatus;
    }

    public void setFlowStatus(String flowStatus) {
        this.flowStatus = flowStatus == null ? null : flowStatus.trim();
    }

    public String getLoanerStatus() {
        return loanerStatus;
    }

    public void setLoanerStatus(String loanerStatus) {
        this.loanerStatus = loanerStatus == null ? null : loanerStatus.trim();
    }

    public String getLoanerDesc() {
        return loanerDesc;
    }

    public void setLoanerDesc(String loanerDesc) {
        this.loanerDesc = loanerDesc == null ? null : loanerDesc.trim();
    }

    public String getSendId() {
        return sendId;
    }

    public void setSendId(String sendId) {
        this.sendId = sendId == null ? null : sendId.trim();
    }

    public String getSendName() {
        return sendName;
    }

    public void setSendName(String sendName) {
        this.sendName = sendName == null ? null : sendName.trim();
    }

    public Date getReceiveTime() {
        return receiveTime;
    }

    public void setReceiveTime(Date receiveTime) {
        this.receiveTime = receiveTime;
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    public String getReceiveId() {
        return receiveId;
    }

    public void setReceiveId(String receiveId) {
        this.receiveId = receiveId == null ? null : receiveId.trim();
    }

    public String getReceiveName() {
        return receiveName;
    }

    public void setReceiveName(String receiveName) {
        this.receiveName = receiveName == null ? null : receiveName.trim();
    }

    public String getRejectId() {
        return rejectId;
    }

    public void setRejectId(String rejectId) {
        this.rejectId = rejectId == null ? null : rejectId.trim();
    }

    public String getRejectName() {
        return rejectName;
    }

    public void setRejectName(String rejectName) {
        this.rejectName = rejectName == null ? null : rejectName.trim();
    }

    public Date getRejectTime() {
        return rejectTime;
    }

    public void setRejectTime(Date rejectTime) {
        this.rejectTime = rejectTime;
    }

    public String getCancleId() {
        return cancleId;
    }

    public void setCancleId(String cancleId) {
        this.cancleId = cancleId == null ? null : cancleId.trim();
    }

    public String getCancleName() {
        return cancleName;
    }

    public void setCancleName(String cancleName) {
        this.cancleName = cancleName == null ? null : cancleName.trim();
    }

    public Date getCancleTime() {
        return cancleTime;
    }

    public void setCancleTime(Date cancleTime) {
        this.cancleTime = cancleTime;
    }

    public String getApprovalId() {
        return approvalId;
    }

    public void setApprovalId(String approvalId) {
        this.approvalId = approvalId == null ? null : approvalId.trim();
    }

    public String getApprovalName() {
        return approvalName;
    }

    public void setApprovalName(String approvalName) {
        this.approvalName = approvalName == null ? null : approvalName.trim();
    }

    public Date getApprovalTime() {
        return approvalTime;
    }

    public void setApprovalTime(Date approvalTime) {
        this.approvalTime = approvalTime;
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

    public Date getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(Date updateBy) {
        this.updateBy = updateBy;
    }
}