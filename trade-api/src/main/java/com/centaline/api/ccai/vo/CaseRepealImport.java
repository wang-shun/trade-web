package com.centaline.api.ccai.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

/**
 * 成交报告撤单导入对象
 * @author yinchao
 * @date 2017/10/10
 */
@ApiModel("成交报告撤单信息")
public class CaseRepealImport {
	@ApiModelProperty(value = "CCAI成交报告编号", required = true, position = 1)
	private String ccaiCode;
	@ApiModelProperty(value = "发起时间", required = true, example = "1503460440000", dataType = "integer", position = 4)
	private Date createTime;
	@ApiModelProperty(value = "审批记录", required = true, position = 9)
	List<TaskInfo> tasks;
	@ApiModelProperty(value = "信息对应的城市行政区划编码", required = true, example = "120000",
			allowableValues = "110000-北京,120000-天津", position = 99)
	private String city;
	@NotBlank(message = "成交报告编号不能为空")
	public String getCcaiCode() {
		return ccaiCode;
	}

	public void setCcaiCode(String ccaiCode) {
		this.ccaiCode = ccaiCode;
	}
	@NotNull(message = "发起申请时间不能为空")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	@NotEmpty(message = "审批信息不能为null")
	public List<TaskInfo> getTasks() {
		return tasks;
	}

	public void setTasks(List<TaskInfo> tasks) {
		this.tasks = tasks;
	}
	@NotBlank(message = "城市不能为空")
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}
}
