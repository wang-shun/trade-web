package com.centaline.api.ccai.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotBlank;

/**
 * 基础导入类
 * 定义CCAI需要提供的共用基础信息部分
 * @author yinchao
 * @date 2017/9/22
 */
@ApiModel("基础流程相关导入信息")
public abstract class AbstractBaseImport {
	@ApiModelProperty(value = "CCAI流程实例ID", required = true, position = 0)
	private String applyId;
	@ApiModelProperty(value = "信息对应的城市行政区划编码", required = true, example = "120000",
			allowableValues = "110000-北京,120000-天津", position = 99)
	private String city;
	@NotBlank(message = "CCAI流程实例ID不能为空")
	public String getApplyId() {
		return applyId;
	}

	public void setApplyId(String applyId) {
		this.applyId = applyId;
	}
	@NotBlank(message = "城市不能为空")
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}
}
