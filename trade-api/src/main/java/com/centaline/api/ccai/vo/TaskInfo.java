package com.centaline.api.ccai.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * 通用获取审批记录环节信息
 * @author yinchao
 * @date 2017-9-23
 */
@ApiModel("审批记录信息")
public class TaskInfo {
	@ApiModelProperty(value = "审批人域账号", required = true, position = 0)
	private String applyUserName;
	@ApiModelProperty(value = "审批人名称", required = true, position = 2)
	private String applyRealName;
	@ApiModelProperty(value = "审批人级别", required = true, position = 3)
	private String level;
	@ApiModelProperty(value = "审批时间", required = true,example = "1503460440000", dataType = "integer", position = 4)
	private Date dealTime;
	@ApiModelProperty(value = "审批结果", required = true, position = 5, example = "0",
			allowableValues = "-1,0,1")
	private Integer result;
	@ApiModelProperty(value = "审批意见", position = 6)
	private String comment;
	@NotBlank(message = "审批人域账号不能为空")
	public String getApplyUserName() {
		return applyUserName;
	}

	public void setApplyUserName(String applyUserName) {
		this.applyUserName = applyUserName;
	}
	@NotBlank(message = "审批人域账号不能为空")
	public String getApplyRealName() {
		return applyRealName;
	}

	public void setApplyRealName(String applyRealName) {
		this.applyRealName = applyRealName;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}
	@NotNull(message = "处理时间不能为空")
	public Date getDealTime() {
		return dealTime;
	}

	public void setDealTime(Date dealTime) {
		this.dealTime = dealTime;
	}
	@NotNull(message = "审批结果不能为空")
	public Integer getResult() {
		return result;
	}

	public void setResult(Integer result) {
		this.result = result;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
}