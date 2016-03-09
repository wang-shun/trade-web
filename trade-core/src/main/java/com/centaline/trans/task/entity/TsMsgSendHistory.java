package com.centaline.trans.task.entity;

import java.util.Date;

public class TsMsgSendHistory {
    private Long pkid;

    private String caseCode;

    private String partCode;

    private Date sendTime;

    private String msgCat;

    private String senderId;

    private String receiverName;

    private String receiverPhone;

    private String receiverCat;

    private String isSuccess;

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

    public String getPartCode() {
        return partCode;
    }

    public void setPartCode(String partCode) {
        this.partCode = partCode == null ? null : partCode.trim();
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    public String getMsgCat() {
        return msgCat;
    }

    public void setMsgCat(String msgCat) {
        this.msgCat = msgCat == null ? null : msgCat.trim();
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId == null ? null : senderId.trim();
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName == null ? null : receiverName.trim();
    }

    public String getReceiverPhone() {
        return receiverPhone;
    }

    public void setReceiverPhone(String receiverPhone) {
        this.receiverPhone = receiverPhone == null ? null : receiverPhone.trim();
    }

    public String getReceiverCat() {
        return receiverCat;
    }

    public void setReceiverCat(String receiverCat) {
        this.receiverCat = receiverCat == null ? null : receiverCat.trim();
    }

    public String getIsSuccess() {
        return isSuccess;
    }

    public void setIsSuccess(String isSuccess) {
        this.isSuccess = isSuccess == null ? null : isSuccess.trim();
    }
}