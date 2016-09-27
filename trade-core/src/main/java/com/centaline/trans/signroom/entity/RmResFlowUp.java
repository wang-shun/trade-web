package com.centaline.trans.signroom.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 签约室跟进表(T_RM_RES_FLOW_UP)
 * 
 * @author zhoujp7
 *
 */
public class RmResFlowUp implements Serializable {

	private static final long serialVersionUID = 8212776903795002682L;
	private Long pkid;
	private String comment;// 跟进内容
	private Long resId;// 预约单ID
	private Date createTime;// 创建时间
	private String createBy;// 创建人
	private Date updateTime;// 更新时间
	private String updateBy;// 更新人

	public Long getPkid() {
		return pkid;
	}

	public void setPkid(Long pkid) {
		this.pkid = pkid;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Long getResId() {
		return resId;
	}

	public void setResId(Long resId) {
		this.resId = resId;
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
		this.createBy = createBy;
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
		this.updateBy = updateBy;
	}

}
