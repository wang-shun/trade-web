package com.centaline.api.ccai.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 代理费折评估费同步字段
 * @author yinchao
 * @date 2017/10/20
 */
@ApiModel("代理费折评估费申请信息")
public class Brokerage2EvalFee extends AbstractBaseImport {
	@ApiModelProperty(value = "成交报告编号", required = true, position = 1)
	private String ccaiCode;
	@ApiModelProperty(value = "评估费实收", required = true, dataType = "number", position = 2)
	private BigDecimal evalRealCharges;
	@ApiModelProperty(value = "评估费应收", required = true, dataType = "number", position = 3)
	private BigDecimal evalDueCharges;
	@ApiModelProperty(value = "中介费实收", required = true, dataType = "number", position = 4)
	private BigDecimal brokerageRealCharges;
	@ApiModelProperty(value = "中介费应收", required = true, dataType = "number", position = 5)
	private BigDecimal brokerageCharges;
	@ApiModelProperty(value = "申请事由", required = true, dataType = "number", position = 6)
	private String reason;
	@ApiModelProperty(value = "备注", position = 7)
	private String remark;
	@ApiModelProperty(value = "申请人域账号", required = true, position = 8)
	private String applyUserName;
	@ApiModelProperty(value = "申请人名称", required = true, position = 9)
	private String applyRealName;
	@ApiModelProperty(value = "申请时间", required = true, example = "1503460440000", dataType = "integer", position = 10)
	private Date applyDate;
	@NotBlank(message = "成交单编号不能为空")
	public String getCcaiCode() {
		return ccaiCode;
	}

	public void setCcaiCode(String ccaiCode) {
		this.ccaiCode = ccaiCode;
	}
	@NotNull(message = "评估费实收不能为空")
	public BigDecimal getEvalRealCharges() {
		return evalRealCharges;
	}

	public void setEvalRealCharges(BigDecimal evalRealCharges) {
		this.evalRealCharges = evalRealCharges;
	}
	@NotNull(message = "评估费应收不能为空")
	public BigDecimal getEvalDueCharges() {
		return evalDueCharges;
	}

	public void setEvalDueCharges(BigDecimal evalDueCharges) {
		this.evalDueCharges = evalDueCharges;
	}
	@NotNull(message = "中介费实收不能为空")
	public BigDecimal getBrokerageRealCharges() {
		return brokerageRealCharges;
	}

	public void setBrokerageRealCharges(BigDecimal brokerageRealCharges) {
		this.brokerageRealCharges = brokerageRealCharges;
	}
	@NotNull(message = "中介费应收不能为空")
	public BigDecimal getBrokerageCharges() {
		return brokerageCharges;
	}

	public void setBrokerageCharges(BigDecimal brokerageCharges) {
		this.brokerageCharges = brokerageCharges;
	}
	@NotBlank(message = "申请事由")
	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	@NotBlank(message = "申请人域账号不能为空")
	public String getApplyUserName() {
		return applyUserName;
	}

	public void setApplyUserName(String applyUserName) {
		this.applyUserName = applyUserName;
	}
	@NotBlank(message = "申请人名称不能为空")
	public String getApplyRealName() {
		return applyRealName;
	}

	public void setApplyRealName(String applyRealName) {
		this.applyRealName = applyRealName;
	}
	@NotNull(message = "申请时间不能为空")
	public Date getApplyDate() {
		return applyDate;
	}

	public void setApplyDate(Date applyDate) {
		this.applyDate = applyDate;
	}
}
