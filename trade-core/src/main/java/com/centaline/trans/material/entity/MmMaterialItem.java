package com.centaline.trans.material.entity;

import java.util.Date;

public class MmMaterialItem {
    private Long pkid;

    private String caseCode;

    private String itemCode;

    private String itemName;
    //物品类别
    private String itemCategory;

    private Date itemInputTime;

    private Date itemOutputTime;

    private Date actionPreDate;

    private Date itemBackTime;

    private String itemResource;

    private String itemManager;

    private String itemAddrCode;

    private String itemBusinessRemark;

    private String itemRemark;
    //物品状态
    private String itemStatus;

    private String createBy;

    private Date createTime;

    private String updateBy;

    private Date updateTime;

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

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode == null ? null : itemCode.trim();
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName == null ? null : itemName.trim();
    }

    public String getItemCategory() {
        return itemCategory;
    }

    public void setItemCategory(String itemCategory) {
        this.itemCategory = itemCategory == null ? null : itemCategory.trim();
    }

    public Date getItemInputTime() {
        return itemInputTime;
    }

    public void setItemInputTime(Date itemInputTime) {
        this.itemInputTime = itemInputTime;
    }

    public Date getItemOutputTime() {
        return itemOutputTime;
    }

    public void setItemOutputTime(Date itemOutputTime) {
        this.itemOutputTime = itemOutputTime;
    }

    public Date getActionPreDate() {
        return actionPreDate;
    }

    public void setActionPreDate(Date actionPreDate) {
        this.actionPreDate = actionPreDate;
    }

    public Date getItemBackTime() {
        return itemBackTime;
    }

    public void setItemBackTime(Date itemBackTime) {
        this.itemBackTime = itemBackTime;
    }

    public String getItemResource() {
        return itemResource;
    }

    public void setItemResource(String itemResource) {
        this.itemResource = itemResource == null ? null : itemResource.trim();
    }

    public String getItemManager() {
        return itemManager;
    }

    public void setItemManager(String itemManager) {
        this.itemManager = itemManager == null ? null : itemManager.trim();
    }

    public String getItemAddrCode() {
        return itemAddrCode;
    }

    public void setItemAddrCode(String itemAddrCode) {
        this.itemAddrCode = itemAddrCode == null ? null : itemAddrCode.trim();
    }

    public String getItemBusinessRemark() {
        return itemBusinessRemark;
    }

    public void setItemBusinessRemark(String itemBusinessRemark) {
        this.itemBusinessRemark = itemBusinessRemark == null ? null : itemBusinessRemark.trim();
    }

    public String getItemRemark() {
        return itemRemark;
    }

    public void setItemRemark(String itemRemark) {
        this.itemRemark = itemRemark == null ? null : itemRemark.trim();
    }

    public String getItemStatus() {
        return itemStatus;
    }

    public void setItemStatus(String itemStatus) {
        this.itemStatus = itemStatus == null ? null : itemStatus.trim();
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy == null ? null : createBy.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy == null ? null : updateBy.trim();
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}