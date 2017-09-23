package com.centaline.api.ccai.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * CCAI生产的最终
 * 评估返利报告信息
 *
 * @author yinchao
 * @date 2017/9/22
 */
@ApiModel("评估返利报告信息")
public class EvalRebeatReportImport extends EvalRebeatImport {
	@ApiModelProperty(value = "评估公司名称", required = true, position = 3)
	private String evalCompany;
	@ApiModelProperty(value = "评估公司ID", required = true, position = 4)
	private String evalCompanyId;
	@ApiModelProperty(value = "评估价", required = true, position = 5)
	private BigDecimal evalPrice;
	@ApiModelProperty(value = "评估费收据,多个“,”分割", example = "12345,33333,44444", required = true, position = 6)
	private String evalRecept;
	@ApiModelProperty(value = "中原分成金额", required = true, position = 9)
	private BigDecimal centaComAmount;
	@ApiModelProperty(value = "评估公司分成金额", required = true, position = 10)
	private BigDecimal evalComAmount;
	@ApiModelProperty(value = "评估报告生成时间", example = "1503460440000", required = true, position = 12)
	private Date createTime;

	@NotBlank(message = "评估公司名称不能为空")
	public String getEvalCompany() {
		return evalCompany;
	}

	public void setEvalCompany(String evalCompany) {
		this.evalCompany = evalCompany;
	}

	@NotBlank(message = "评估公司ID不能为空")
	public String getEvalCompanyId() {
		return evalCompanyId;
	}

	public void setEvalCompanyId(String evalCompanyId) {
		this.evalCompanyId = evalCompanyId;
	}

	@NotNull(message = "评估价不能为空")
	public BigDecimal getEvalPrice() {
		return evalPrice;
	}

	public void setEvalPrice(BigDecimal evalPrice) {
		this.evalPrice = evalPrice;
	}

	@NotBlank(message = "评估费收据不能为空")
	public String getEvalRecept() {
		return evalRecept;
	}

	public void setEvalRecept(String evalRecept) {
		this.evalRecept = evalRecept;
	}

	@NotNull(message = "中原分成金额不能为空")
	public BigDecimal getCentaComAmount() {
		return centaComAmount;
	}

	public void setCentaComAmount(BigDecimal centaComAmount) {
		this.centaComAmount = centaComAmount;
	}

	@NotNull(message = "评估公司分成金额不能为空")
	public BigDecimal getEvalComAmount() {
		return evalComAmount;
	}

	public void setEvalComAmount(BigDecimal evalComAmount) {
		this.evalComAmount = evalComAmount;
	}

	@NotNull(message = "报告生成时间不能为空")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Override
	public String toString() {
		return "EvalRebeatReportImport{" +
				"evalCompany='" + evalCompany + '\'' +
				", evalCompanyId='" + evalCompanyId + '\'' +
				", evalPrice=" + evalPrice +
				", evalRecept='" + evalRecept + '\'' +
				", centaComAmount=" + centaComAmount +
				", evalComAmount=" + evalComAmount +
				", createTime=" + createTime +
				'}';
	}
}
