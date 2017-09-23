package com.centaline.api.ccai.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;
import java.util.Date;

/**
 * CCAI导入评估退费申请流程信息 (退报告)
 * @author yinchao
 * @date 2017/9/22
 */
@ApiModel("评估费退费申请信息")
public class EvalRefundImport extends AbstractBaseImport {
	@ApiModelProperty(value = "CCAI成交报告编号", required = true, position = 1)
	private String ccaiCode;
	@ApiModelProperty(value = "退费类别", required = true, position = 2)
	private String refundKinds;
	@ApiModelProperty(value = "申请人域账号", required = true, position = 3)
	private String userName;
	@ApiModelProperty(value = "申请人名称", required = true, position = 4)
	private String realName;
	@ApiModelProperty(value = "申请人所属分行HROC", required = true, position = 5)
	private String grpCode;
	@ApiModelProperty(value = "申请人所属分行名称", required = true, position = 6)
	private String grpName;
	@ApiModelProperty(value = "申请时间", required = true,example = "1503460440000", dataType = "integer", position = 7)
	private Date applyTime;
	@ApiModelProperty(value = "退款金额", required = true, position = 8)
	private BigDecimal refundAmount;
	@ApiModelProperty(value = "退款对象", required = true, position = 9)
	private String refundTarget;
	@ApiModelProperty(value = "退款原因", required = true, position = 10)
	private String refundCause;
	@ApiModelProperty(value = "评估公司名称", required = true, position = 11)
	private String evalCompany;
	@ApiModelProperty(value = "评估公司ID", required = true, position = 12)
	private String evalCompanyId;
	@ApiModelProperty(value = "预计退款时间", required = true, example = "1503460440000", dataType = "integer", position = 13)
	private Date toRefundTime;

	public String getCcaiCode() {
		return ccaiCode;
	}

	public void setCcaiCode(String ccaiCode) {
		this.ccaiCode = ccaiCode;
	}

	public String getRefundKinds() {
		return refundKinds;
	}

	public void setRefundKinds(String refundKinds) {
		this.refundKinds = refundKinds;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getGrpCode() {
		return grpCode;
	}

	public void setGrpCode(String grpCode) {
		this.grpCode = grpCode;
	}

	public String getGrpName() {
		return grpName;
	}

	public void setGrpName(String grpName) {
		this.grpName = grpName;
	}

	public Date getApplyTime() {
		return applyTime;
	}

	public void setApplyTime(Date applyTime) {
		this.applyTime = applyTime;
	}

	public BigDecimal getRefundAmount() {
		return refundAmount;
	}

	public void setRefundAmount(BigDecimal refundAmount) {
		this.refundAmount = refundAmount;
	}

	public String getRefundTarget() {
		return refundTarget;
	}

	public void setRefundTarget(String refundTarget) {
		this.refundTarget = refundTarget;
	}

	public String getRefundCause() {
		return refundCause;
	}

	public void setRefundCause(String refundCause) {
		this.refundCause = refundCause;
	}

	public String getEvalCompany() {
		return evalCompany;
	}

	public void setEvalCompany(String evalCompany) {
		this.evalCompany = evalCompany;
	}

	public String getEvalCompanyId() {
		return evalCompanyId;
	}

	public void setEvalCompanyId(String evalCompanyId) {
		this.evalCompanyId = evalCompanyId;
	}

	public Date getToRefundTime() {
		return toRefundTime;
	}

	public void setToRefundTime(Date toRefundTime) {
		this.toRefundTime = toRefundTime;
	}
}
