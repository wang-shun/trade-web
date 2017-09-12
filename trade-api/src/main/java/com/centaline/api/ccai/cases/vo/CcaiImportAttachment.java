package com.centaline.api.ccai.cases.vo;

import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 * CCAI案件导入附件信息
 *
 * @author yinchao
 */
@ApiModel("附件信息")
public class CcaiImportAttachment {
	@ApiModelProperty(value = "附件唯一确认ID", required = true, position = 0)
	private String id;
	@ApiModelProperty(value = "附件名称", required = true, position = 1)
	private String name;
	@ApiModelProperty(value = "附件类型", required = true, example = "jpg", position = 2)
	private String type;
	@ApiModelProperty(value = "附件完整地址", required = true, example = "http://tjccai/images/id",
			position = 3)
	private String url;
	@ApiModelProperty(value = "上传时间", required = true, example = "1503460440000", dataType = "integer", position = 4)
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
