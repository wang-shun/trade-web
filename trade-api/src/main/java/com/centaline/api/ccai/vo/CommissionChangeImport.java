package com.centaline.api.ccai.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;

/**
 * 分行经理调佣申请导入
 * @author yinchao
 * @date 2017/9/23
 */
@ApiModel("调佣申请信息")
public class CommissionChangeImport extends AbstractBaseImport {
	@ApiModelProperty(value = "成交报告编号", required = true, position = 1)
	private String ccaiCode;
	@ApiModelProperty(value = "调佣事项", required = true, position = 2)
	private String item;
	@ApiModelProperty(value = "调佣类型", required = true, position = 3)
	private String type;
	@ApiModelProperty(value = "调佣对象", required = true, position = 4)
	private String target;
	@ApiModelProperty(value = "调整前佣金", required = true, position = 5)
	private BigDecimal oldCommission;
	@ApiModelProperty(value = "调整后佣金", required = true, position = 6)
	private BigDecimal newCommission;
	@ApiModelProperty(value = "调佣事由", required = true, position = 0)
	private String cause;

	public String getCcaiCode() {
		return ccaiCode;
	}

	public void setCcaiCode(String ccaiCode) {
		this.ccaiCode = ccaiCode;
	}

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public BigDecimal getOldCommission() {
		return oldCommission;
	}

	public void setOldCommission(BigDecimal oldCommission) {
		this.oldCommission = oldCommission;
	}

	public BigDecimal getNewCommission() {
		return newCommission;
	}

	public void setNewCommission(BigDecimal newCommission) {
		this.newCommission = newCommission;
	}

	public String getCause() {
		return cause;
	}

	public void setCause(String cause) {
		this.cause = cause;
	}
}
