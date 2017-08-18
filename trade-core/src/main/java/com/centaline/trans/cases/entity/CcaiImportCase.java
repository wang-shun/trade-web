package com.centaline.trans.cases.entity;

import java.util.Date;
import java.util.List;
/**
 * CCAI案件导入信息
 * 包含 基本信息、物业信息、客户信息
 * @author yinchao
 *
 */
public class CcaiImportCase {
	/**CCAI系统中案件ID*/
	private String ccaiId;
	/**CCAI系统中案件CODE*/
	private String ccaiCode;
	/**交易类型*/
	private String tradeType;
	/**付款类型*/
	private String payType;
	/**CCAI系统中案件创建时间*/
	private Date  createTime;
	/**CCAI系统中案件修改时间*/
	private Date updateTime;
	/**导入案件基本信息*/
	private List<CcaiImportCaseInfo> participants;
	/**导入案件物业信息*/
	private CcaiImportCaseProperty property;
	/**导入案件客户信息 包含上下家*/
	private List<CcaiImportCaseGuest> guests;
	/**导入案件附件信息 */
	private List<CcaiImportAttachment> attachments;
	/**城市信息*/
	private String city;
	public String getCcaiId() {
		return ccaiId;
	}
	public void setCcaiId(String ccaiId) {
		this.ccaiId = ccaiId;
	}
	public String getCcaiCode() {
		return ccaiCode;
	}
	public void setCcaiCode(String ccaiCode) {
		this.ccaiCode = ccaiCode;
	}
	public String getTradeType() {
		return tradeType;
	}
	public void setTradeType(String tradeType) {
		this.tradeType = tradeType;
	}
	public String getPayType() {
		return payType;
	}
	public void setPayType(String payType) {
		this.payType = payType;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public List<CcaiImportCaseInfo> getParticipants() {
		return participants;
	}
	public void setParticipants(List<CcaiImportCaseInfo> participants) {
		this.participants = participants;
	}
	public CcaiImportCaseProperty getProperty() {
		return property;
	}
	public void setProperty(CcaiImportCaseProperty property) {
		this.property = property;
	}
	public List<CcaiImportCaseGuest> getGuests() {
		return guests;
	}
	public void setGuests(List<CcaiImportCaseGuest> guests) {
		this.guests = guests;
	}
	public List<CcaiImportAttachment> getAttachments() {
		return attachments;
	}
	public void setAttachments(List<CcaiImportAttachment> attachments) {
		this.attachments = attachments;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	@Override
	public String toString() {
		return "CcaiImportCase [ccaiId=" + ccaiId + ", ccaiCode=" + ccaiCode + ", tradeType=" + tradeType + ", payType="
				+ payType + ", createTime=" + createTime + ", updateTime=" + updateTime + ", participants="
				+ participants + ", property=" + property + ", guests=" + guests + ", attachments=" + attachments
				+ ", city=" + city + "]";
	}
}
