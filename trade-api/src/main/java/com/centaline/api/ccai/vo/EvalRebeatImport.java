package com.centaline.api.ccai.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * CCAI评估费返利申请信息
 *
 * @author yinchao
 * @date 2017-9-22
 */
@ApiModel("评估费返利申请信息")
public class EvalRebeatImport extends AbstractBaseImport {
	@ApiModelProperty(value = "CCAI评估返利报告编号", required = true, position = 1)
	private String ccaiCode;
	@ApiModelProperty(value = "对应成交单编号", required = true, position = 6)
	private String oringinCcaiCode;
	@ApiModelProperty(value = "评估价",required = true,position =7)
	private BigDecimal evalPrice;
	@ApiModelProperty(value = "评估费实收", required = true, dataType = "number", position = 8)
	private BigDecimal evalRealCharges;
	@ApiModelProperty(value = "评估费应收", required = true, dataType = "number", position = 9)
	private BigDecimal evalDueCharges;
	@ApiModelProperty(value = "申请人域账号", required = true , position = 10)
	private String applyUserName;
	@ApiModelProperty(value = "申请人名称", required = true , position = 11)
	private String applyRealName;
	@ApiModelProperty(value = "申请时间", required = true, example = "1503460440000", dataType = "integer", position = 12)
	private Date inputTime;
	@ApiModelProperty(value = "审批记录", required = true , position = 16)
	List<TaskInfo> tasks;

	@NotBlank(message = "评估返利报告编号不能为空")
	public String getCcaiCode() {
		return ccaiCode;
	}

	public void setCcaiCode(String ccaiCode) {
		this.ccaiCode = ccaiCode;
	}

	@NotBlank(message = "成交单编号不能为空")
	public String getOringinCcaiCode() {
		return oringinCcaiCode;
	}

	public void setOringinCcaiCode(String oringinCcaiCode) {
		this.oringinCcaiCode = oringinCcaiCode;
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

	@NotNull(message = "申请时间不能为空")
	public Date getInputTime() {
		return inputTime;
	}

	public void setInputTime(Date inputTime) {
		this.inputTime = inputTime;
	}
	@NotNull(message = "评估价不能为空")
	public BigDecimal getEvalPrice() {
		return evalPrice;
	}

	public void setEvalPrice(BigDecimal evalPrice) {
		this.evalPrice = evalPrice;
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
	@NotEmpty(message = "审批记录不能为空")
	public List<TaskInfo> getTasks() {
		return tasks;
	}

	public void setTasks(List<TaskInfo> tasks) {
		this.tasks = tasks;
	}
}
