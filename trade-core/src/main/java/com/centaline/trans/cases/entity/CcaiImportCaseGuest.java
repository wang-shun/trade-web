package com.centaline.trans.cases.entity;
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
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
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
