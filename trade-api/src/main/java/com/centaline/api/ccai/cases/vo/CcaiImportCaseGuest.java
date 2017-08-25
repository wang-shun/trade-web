package com.centaline.api.ccai.cases.vo;

import org.hibernate.validator.constraints.NotBlank;

/**
 * CCAI导入案件客户信息
 * @author yinchao
 *
 */
public class CcaiImportCaseGuest {
	/**客户ID*/
	private String id;
	/**客户类型 上家30006001 下家30006002*/
	private String position;
	/**客户名称*/
	private String name;
	/**客户手机号*/
	private String mobile;
	/**客户身份证件类型*/
	private String certType;
	/**客户身份证件编码*/
	private String certCode;
	@NotBlank(message="客户ID不能为空")
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	@NotBlank(message="客户类型不能为空")
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	@NotBlank(message="客户名称不能为空")
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@NotBlank(message="客户手机号不正确")
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
