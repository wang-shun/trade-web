package com.centaline.api.ccai.vo;

import com.centaline.api.validate.group.FeedTrueGroup;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

/**
 * 银行返利CCAI反馈信息
 *
 * @author yinchao
 * @date 2017/10/16
 */
@ApiModel("银行返利财务审批结果反馈")
public class BankRebeatFeedBack {
	@ApiModelProperty(value = "银行返利申请ID", required = true, position = 0)
	private String backID;
	@ApiModelProperty(value = "银行返利流程实例ID", required = true, position = 1)
	private String applyId;
	@ApiModelProperty(value = "审批人域账号", required = true, position = 2)
	private String userName;
	@ApiModelProperty(value = "审批人姓名", required = true, position = 3)
	private String realName;
	@ApiModelProperty(value = "审批人所属部门HROC", required = true, position = 4, example = "022A003")
	private String grpCode;
	@ApiModelProperty(value = "审批人所属部门名称", required = true, position = 5, example = "财务部")
	private String grpName;
	@ApiModelProperty(value = "审批结果", required = true, position = 8, example = "1",
			allowableValues = "-1,0,1")//-1-拒绝,0-驳回,1-通过
	private int result;
	@ApiModelProperty(value = "审批意见", required = false, position = 9)
	private String comment;
	@ApiModelProperty(value = "审批时间", required = true, position = 10, example = "1505990444260")
	private Date approveTime;

	@ApiModelProperty(value = "财务审批通过后，新的返利报告关联关系", position = 10)
	private List<ReportRelation> relation;

	@ApiModelProperty(value = "信息对应的城市行政区划编码", required = true, example = "120000",
			allowableValues = "110000-北京,120000-天津", position = 11)
	private String city;

	@NotBlank(message = "银行返利ID不能为空")
	public String getBackID() {
		return backID;
	}

	public void setBackID(String backID) {
		this.backID = backID;
	}

	@NotBlank(message = "流程实例ID不能为空")
	public String getApplyId() {
		return applyId;
	}

	public void setApplyId(String applyId) {
		this.applyId = applyId;
	}

	@NotBlank(message = "审批人域账号不能为空")
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@NotBlank(message = "审批人姓名不能为空")
	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	@NotBlank(message = "审批人所属部门HROC不能为空")
	public String getGrpCode() {
		return grpCode;
	}

	public void setGrpCode(String grpCode) {
		this.grpCode = grpCode;
	}

	@NotBlank(message = "审批人所属部门名称不能为空")
	public String getGrpName() {
		return grpName;
	}

	public void setGrpName(String grpName) {
		this.grpName = grpName;
	}

	@Range(min = -1, max = 1, message = "审批结果仅支持-1-1")
	public int getResult() {
		return result;
	}

	public void setResult(int result) {
		this.result = result;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	@NotNull(message = "审批时间不能为空")
	public Date getApproveTime() {
		return approveTime;
	}

	public void setApproveTime(Date approveTime) {
		this.approveTime = approveTime;
	}

	@NotBlank(message = "城市不能为空")
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	//财务审批结果为true时
	@NotNull(message = "关联关系不能为空", groups = {FeedTrueGroup.class})
	public List<ReportRelation> getRelation() {
		return relation;
	}

	public void setRelation(List<ReportRelation> relation) {
		this.relation = relation;
	}
}
