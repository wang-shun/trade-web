package com.centaline.trans.common.entity;

public class ToReminderList {
    private Long pkid;

    private String partCode;

    private String remindItem;

    private String comment;

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

    public String getRemindItem() {
        return remindItem;
    }

    public void setRemindItem(String remindItem) {
        this.remindItem = remindItem == null ? null : remindItem.trim();
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment == null ? null : comment.trim();
    }
}