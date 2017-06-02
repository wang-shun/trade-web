package com.centaline.trans.subject.entity;

import java.util.Date;
/**
 * 应收科目 
 * @author jjm
 *
 */
public class ComSubject {
	/**
	 * 主键
	 */
    private Long pkid;
    /**
     * 父节点ID
     */
    private Long parentId;
    /**
     * 应收科目代码
     */
    private String subjectCode;
    /**
     * 应收科目名称
     */
    private String subjectName;

    private String createBy;

    private Date createTime;

    private String updateBy;

    private Date updateTime;
    /**
     * 是否有效
     */
    private String isActive;
    /**
     * 无效时间
     */
    private Date inactiveTime;

    public Long getPkid() {
        return pkid;
    }

    public void setPkid(Long pkid) {
        this.pkid = pkid;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getSubjectCode() {
        return subjectCode;
    }

    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode == null ? null : subjectCode.trim();
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName == null ? null : subjectName.trim();
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

    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive == null ? null : isActive.trim();
    }

    public Date getInactiveTime() {
        return inactiveTime;
    }

    public void setInactiveTime(Date inactiveTime) {
        this.inactiveTime = inactiveTime;
    }
}