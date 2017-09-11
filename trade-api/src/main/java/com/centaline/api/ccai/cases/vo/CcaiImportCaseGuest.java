package com.centaline.api.ccai.cases.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotBlank;

/**
 * CCAI导入案件客户信息
 *
 * @author yinchao
 */
@ApiModel("案件客户信息")
public class CcaiImportCaseGuest {
	@ApiModelProperty(value = "唯一确认客户ID", required = true, position = 0)
	private String id;
	@ApiModelProperty(value = "客户类型", required = true, example = "30006001",
			allowableValues = "30006001-卖家,30006002-买家", position = 1)
	private String position;
	@ApiModelProperty(value = "客户名称", required = true, position = 2)
	private String name;
	@ApiModelProperty(value = "客户手机号", required = true, position = 3)
	private String mobile;
	@ApiModelProperty(value = "证件类型", example = "身份证",
			allowableValues = "身份证,暂住证,军官证,护照,港澳台同胞通行证,营业执照,户口本,结婚证,其他",
			position = 4)
	private String certType;
	@ApiModelProperty(value = "证件编码", position = 5)
	private String certCode;

	@NotBlank(message = "ID不能为空")
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@NotBlank(message = "类型不能为空")
	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	@NotBlank(message = "名称不能为空")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@NotBlank(message = "手机号不能为空")
	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getCertType() {
		return certType;
	}

	public void setCertType(String certType) {
		this.certType = certType;
	}

	public String getCertCode() {
		return certCode;
	}

	public void setCertCode(String certCode) {
		this.certCode = certCode;
	}

	@Override
	public String toString() {
		return "CcaiImportCaseGuest [id=" + id + ", position=" + position + ", name=" + name + ", mobile=" + mobile
				+ ", certType=" + certType + ", certCode=" + certCode + "]";
	}
}
