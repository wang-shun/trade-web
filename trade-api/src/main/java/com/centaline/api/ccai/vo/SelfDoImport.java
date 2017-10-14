package com.centaline.api.ccai.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

/**
 * 自办评估和自办贷款通用导入类
 * @author yinchao
 * @date 2017/9/23
 */
@ApiModel("自办申请信息")
public class SelfDoImport extends AbstractBaseImport{
	@ApiModelProperty(value = "成交报告编号", required = true, position = 1)
	private String ccaiCode;
	@ApiModelProperty(value = "申请类型", required = true, position = 2,example = "自办评估",
		allowableValues = "自办评估,自办贷款")
	private String type;
	@ApiModelProperty(value = "申请人域账号", required = true, position = 3)
	private String userName;
	@ApiModelProperty(value = "申请人名称", required = true, position = 4)
	private String realName;
	@ApiModelProperty(value = "申请人所属部门HROC", required = true, position = 5)
	private String grpCode;
	@ApiModelProperty(value = "申请人所属部门名称", required = true, position = 6)
	private String grpName;
	@ApiModelProperty(value = "申请时间", required = true,example = "1503460440000", dataType = "integer", position = 7)
	private Date applyTime;
	@ApiModelProperty(value = "状态", required = true, position = 8)
	private String status;
	@ApiModelProperty(value = "审批记录", required = true, position = 9)
	private List<TaskInfo> tasks;

	@NotBlank(message = "成交报告编号不能为空")
	public String getCcaiCode() {
		return ccaiCode;
	}

	public void setCcaiCode(String ccaiCode) {
		this.ccaiCode = ccaiCode;
	}
	@NotBlank(message = "自办类型不能为空")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	@NotBlank(message = "申请人域账号不能为空")
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	@NotBlank(message = "申请人名称不能为空")
	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}
	@NotBlank(message = "申请人所属部门HROC不能为空")
	public String getGrpCode() {
		return grpCode;
	}

	public void setGrpCode(String grpCode) {
		this.grpCode = grpCode;
	}
	@NotBlank(message = "申请人所属部门名称不能为空")
	public String getGrpName() {
		return grpName;
	}

	public void setGrpName(String grpName) {
		this.grpName = grpName;
	}
	@NotNull(message = "申请时间不能为空")
	public Date getApplyTime() {
		return applyTime;
	}

	public void setApplyTime(Date applyTime) {
		this.applyTime = applyTime;
	}
	@NotBlank(message = "状态不能为空")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	@NotEmpty(message = "审批环节不能为空")
	public List<TaskInfo> getTasks() {
		return tasks;
	}

	public void setTasks(List<TaskInfo> tasks) {
		this.tasks = tasks;
	}
}
