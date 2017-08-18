package com.centaline.trans.cases.entity;

import java.util.Date;

/**
 * CCAI案件导入附件信息
 * @author yinchao
 *
 */
public class CcaiImportAttachment {
	private String id;
	private String name;
	private String type;
	private String url;
	private Date uploadTime;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Date getUploadTime() {
		return uploadTime;
	}
	public void setUploadTime(Date uploadTime) {
		this.uploadTime = uploadTime;
	}
	@Override
	public String toString() {
		return "CcaiImportAttachment [id=" + id + ", name=" + name + ", type=" + type + ", url=" + url + ", uploadTime="
				+ uploadTime + "]";
	}
}
