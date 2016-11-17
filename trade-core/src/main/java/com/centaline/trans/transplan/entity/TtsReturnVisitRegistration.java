package com.centaline.trans.transplan.entity;

import java.util.Date;

/**
 * T_TS_RETURN_VISIT_REGISTRATION<回访跟进表>
 * @author zhoujp7
 *
 */
public class TtsReturnVisitRegistration {
    private Long pkid;

    private String visitRemark;//回访跟进标记

    private String content;//跟进内容

    private String createTime;
    private Date crtTime;

    private String createBy;

    private Long batchId;//交易计划变更批次ID

    public Long getPkid() {
        return pkid;
    }

    public void setPkid(Long pkid) {
        this.pkid = pkid;
    }

    public String getVisitRemark() {
        return visitRemark;
    }

    public void setVisitRemark(String visitRemark) {
        this.visitRemark = visitRemark == null ? null : visitRemark.trim();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy == null ? null : createBy.trim();
    }

    public Long getBatchId() {
        return batchId;
    }

    public void setBatchId(Long batchId) {
        this.batchId = batchId;
    }

	public Date getCrtTime() {
		return crtTime;
	}

	public void setCrtTime(Date crtTime) {
		this.crtTime = crtTime;
	}
    
}