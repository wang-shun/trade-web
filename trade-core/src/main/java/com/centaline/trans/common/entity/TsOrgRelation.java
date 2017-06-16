package com.centaline.trans.common.entity;

import java.util.Date;

public class TsOrgRelation {
	
	private Long 		pkid;
	
	private String 		type;
	
	private String 		originOrgId;
	
	private String 		targetOrgId;
	
	private String      isDeleted;
	
	private String 		available;
	
	private Date 		createTime;
	
	private String 		createBy;
	
	private Date 		updateTime;
	
	private String 		updateBy;
	
	public static final String IS_DELETE  		= "1";
	public static final String NOT_DELETE 		= "0";
	public static final String IS_AVAILABLE 	= "1";
	public static final String NOT_AVAILABLE 	= "0";
	public static final String TYPE 			= "front_back";
	
	
	public Long getPkid() {
		return pkid;
	}
	public void setPkid(Long pkid) {
		this.pkid = pkid;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getOriginOrgId() {
		return originOrgId;
	}
	public void setOriginOrgId(String originOrgId) {
		this.originOrgId = originOrgId;
	}
	public String getTargetOrgId() {
		return targetOrgId;
	}
	public void setTargetOrgId(String targetOrgId) {
		this.targetOrgId = targetOrgId;
	}
	public String getIsDeleted() {
		return isDeleted;
	}
	public void setIsDeleted(String isDeleted) {
		this.isDeleted = isDeleted;
	}
	public String getAvailable() {
		return available;
	}
	public void setAvailable(String available) {
		this.available = available;
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
