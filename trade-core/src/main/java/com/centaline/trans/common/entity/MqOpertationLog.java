package com.centaline.trans.common.entity;

import java.util.Date;

public class MqOpertationLog {
    private Long pkid;

    private String queueName;

    private String message;

    private String opertation;

    private String status;

    private String errmsg;

    private Date opertationTime;

    private Date createTime;

    private String createBy;

    public Long getPkid() {
        return pkid;
    }

    public void setPkid(Long pkid) {
        this.pkid = pkid;
    }

    public String getQueueName() {
        return queueName;
    }

    public void setQueueName(String queueName) {
        this.queueName = queueName == null ? null : queueName.trim();
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message == null ? null : message.trim();
    }

    public String getOpertation() {
        return opertation;
    }

    public void setOpertation(String opertation) {
        this.opertation = opertation == null ? null : opertation.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg == null ? null : errmsg.trim();
    }

    public Date getOpertationTime() {
        return opertationTime;
    }

    public void setOpertationTime(Date opertationTime) {
        this.opertationTime = opertationTime;
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
}