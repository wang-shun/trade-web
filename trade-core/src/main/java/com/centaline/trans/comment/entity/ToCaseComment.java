package com.centaline.trans.comment.entity;

import java.util.Date;

public class ToCaseComment {
    private Long pkid;

    private String caseCode;

    private String srvCode;

    private String comment;

    private Date createTime;

    private String createBy;
    
    private String createByShow;

    private Date updateTime;

    private String updateBy;
    
    private String creatorOrgId;
    
    private String creatorOrgIdShow;
    
    private String source;
    
    private String type;
    
    private String parentId;
    
    private String bizCode;

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

    public String getSrvCode() {
        return srvCode;
    }

    public void setSrvCode(String srvCode) {
        this.srvCode = srvCode == null ? null : srvCode.trim();
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment == null ? null : comment.trim();
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

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy == null ? null : updateBy.trim();
    }

	public String getCreatorOrgId() {
		return creatorOrgId;
	}

	public void setCreatorOrgId(String creatorOrgId) {
		this.creatorOrgId = creatorOrgId;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getBizCode() {
		return bizCode;
	}

	public void setBizCode(String bizCode) {
		this.bizCode = bizCode;
	}

	public String getCreateByShow() {
		return createByShow;
	}

	public void setCreateByShow(String createByShow) {
		this.createByShow = createByShow;
	}

	public String getCreatorOrgIdShow() {
		return creatorOrgIdShow;
	}

	public void setCreatorOrgIdShow(String creatorOrgIdShow) {
		this.creatorOrgIdShow = creatorOrgIdShow;
	}
}