package com.centaline.api.ccai.vo;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * CCAI案件导入信息
 * 包含 基本信息、物业信息、客户信息
 *
 * @author yinchao
 */
@ApiModel("案件信息")
public class CaseImport extends AbstractBaseImport {
	@ApiModelProperty(value = "CCAI成交报告ID", required = true, position = 0)
	private String ccaiId;
	@ApiModelProperty(value = "CCAI成交报告编号", required = true, position = 1)
	private String ccaiCode;
	@ApiModelProperty(value = "交易类型", required = true, example = "0",
			allowableValues = "0-有产证正常过户,1-使用权转让,2-改底单,3-等产证过户,5-公产房,6-企业产,7-还迁协议",
			position = 2)
	private String tradeType;
	@ApiModelProperty(value = "付款类型", required = true, example = "自办贷款",
			allowableValues = "自办贷款,公积金贷款,一次性,按揭贷款,公积金（组合）贷款,抵押贷款",
			position = 3)
	private String payType;
	@ApiModelProperty(value = "业绩上报时间", required = true, example = "1503460440000", dataType = "integer", position = 5)
	private Date reportTime;
	@ApiModelProperty(value = "成交报告创建时间", required = true, example = "1503460440000", dataType = "integer", position = 6)
	private Date createTime;
	@ApiModelProperty(value = "成交报告修改时间", required = true, example = "1503460440000", dataType = "integer", position = 7)
	private Date updateTime;
	@ApiModelProperty(value = "成交报告参与人员信息", required = true, position = 8)
	private List<CaseParticipantImport> participants;
	@ApiModelProperty(value = "成交报告物业信息", required = true, position = 9)
	private CasePropertyImport property;
	@ApiModelProperty(value = "成交报告客户信息，包含买家和卖家", required = true, position = 10)
	private List<CaseGuestImport> guests;
	@ApiModelProperty(value = "成交报告附件信息", required = true, position = 11)
	private List<CaseAttachmentImport> attachments;

	@NotBlank(message = "成交报告ID不能为空")
	public String getCcaiId() {
		return ccaiId;
	}

	public void setCcaiId(String ccaiId) {
		this.ccaiId = ccaiId;
	}

	@NotBlank(message = "成交报告编号不能为空")
	public String getCcaiCode() {
		return ccaiCode;
	}

	public void setCcaiCode(String ccaiCode) {
		this.ccaiCode = ccaiCode;
	}

	@NotBlank(message = "交易类型不能为空")
	public String getTradeType() {
		return tradeType;
	}

	public void setTradeType(String tradeType) {
		this.tradeType = tradeType;
	}

	@NotBlank(message = "付款类型不能为空")
	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	@NotNull(message = "业绩上报时间不能为空")
	public Date getReportTime() {
		return reportTime;
	}

	public void setReportTime(Date reportTime) {
		this.reportTime = reportTime;
	}
	@NotNull(message = "创建时间不能为空")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	@NotNull(message = "修改时间不能为空")
	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	@NotEmpty(message = "案件参与人不能为空")
	public List<CaseParticipantImport> getParticipants() {
		return participants;
	}

	public void setParticipants(List<CaseParticipantImport> participants) {
		this.participants = participants;
	}

	@NotNull(message = "房源信息不能为空")
	public CasePropertyImport getProperty() {
		return property;
	}

	public void setProperty(CasePropertyImport property) {
		this.property = property;
	}

	@NotEmpty(message = "客户信息不能为空")
	public List<CaseGuestImport> getGuests() {
		return guests;
	}

	public void setGuests(List<CaseGuestImport> guests) {
		this.guests = guests;
	}

	public List<CaseAttachmentImport> getAttachments() {
		return attachments;
	}

	public void setAttachments(List<CaseAttachmentImport> attachments) {
		this.attachments = attachments;
	}

	@Override
	public String toString() {
		return "CaseImport{" +
				"ccaiId='" + ccaiId + '\'' +
				", ccaiCode='" + ccaiCode + '\'' +
				", tradeType='" + tradeType + '\'' +
				", payType='" + payType + '\'' +
				", reportTime=" + reportTime +
				", createTime=" + createTime +
				", updateTime=" + updateTime +
				", participants=" + participants +
				", property=" + property +
				", guests=" + guests +
				", attachments=" + attachments +
				'}';
	}
}
