package com.centaline.api.ccai.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotBlank;

/**
 * CCAI中财务审批通过后
 * 新生成的银行返利报告单与原报告单的对应关系以及CCAI流程对应关系
 * @author yinchao
 * @date 2017/10/16
 */
@ApiModel("银行返利报告关联关系")
public class ReportRelation {
	@ApiModelProperty(value = "原成交报告编号", required = true, position = 0)
	private String ccaiCode;
	@ApiModelProperty(value = "银行返利报告审批流程实例ID", required = true, position = 1)
	private String applyId;
	@ApiModelProperty(value = "返利报告编号", required = true, position = 2)
	private String rebateReportCode;
	@NotBlank(message = "原成交报告编号不能为空")
	public String getCcaiCode() {
		return ccaiCode;
	}

	public void setCcaiCode(String ccaiCode) {
		this.ccaiCode = ccaiCode;
	}
	@NotBlank(message = "返利流程实例ID不能为空")
	public String getApplyId() {
		return applyId;
	}

	public void setApplyId(String applyId) {
		this.applyId = applyId;
	}
	@NotBlank(message = "返利报告编号不能为空")
	public String getRebateReportCode() {
		return rebateReportCode;
	}

	public void setRebateReportCode(String rebateReportCode) {
		this.rebateReportCode = rebateReportCode;
	}
}
