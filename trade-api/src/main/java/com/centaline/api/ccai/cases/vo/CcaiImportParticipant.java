package com.centaline.api.ccai.cases.vo;

import com.centaline.api.validate.group.NormalGroup;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotBlank;

import com.centaline.api.enums.CaseSyncParticipantEnum;

/**
 * 需要导入的CCAI案件基本信息
 * 主要包含
 * 成交经纪人信息
 * 成交部门信息
 * 成交部门管理人信息
 * 业务部门信息
 * 业务部门管理人信息
 * 业务权证信息
 * 战区负责人信息
 *
 * @author yinchao
 */
@ApiModel("案件参与人信息")
public class CcaiImportParticipant {
	/**
	 * 案件基本信息 类型
	 *
	 * @see CaseSyncParticipantEnum
	 */
	@ApiModelProperty(value = "指派ID,修改时唯一匹配人员,秘书可为空", required = true, position = 0)
	private String assignId;
	@ApiModelProperty(value = "参与人职务", required = true, example = "agent",
			allowableValues = "agent-成交经纪人,secretary-分行秘书,warrant-过户权证,loan-贷款权证,financial-金融权证",
			position = 0)
	private String position;
	@ApiModelProperty(value = "参与人域账号/工号", required = true, position = 1)
	private String userName;
	@ApiModelProperty(value = "参与人名称", required = true, position = 2)
	private String realName;
	@ApiModelProperty(value = "参与人手机号", required = true, position = 3)
	private String mobile;
	@ApiModelProperty(value = "参与人所在部门HROC编码", required = true, position = 4)
	private String grpCode;
	@ApiModelProperty(value = "参与人所在部门名称", required = true, position = 5)
	private String grpName;
	@ApiModelProperty(value = "部门主管域账号/工号", required = true, position = 6)
	private String grpMgrUserName;
	@ApiModelProperty(value = "部门主管名称", required = true, position = 7)
	private String grpMgrRealName;
	@ApiModelProperty(value = "部门主管手机号", required = true, position = 8)
	private String grpMgrMobile;
	@NotBlank(message = "指派ID不能为空",groups = {NormalGroup.class})
	public String getAssignId() {
		return assignId;
	}

	public void setAssignId(String assignId) {
		this.assignId = assignId;
	}

	@NotBlank(message = "类型不能为空")
	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}
	@NotBlank(message = "域账号/工号不能为空")
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	@NotBlank(message = "名称不能为空")
	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	@NotBlank(message = "部门HROC不能为空",groups = {NormalGroup.class})
	public String getGrpCode() {
		return grpCode;
	}

	public void setGrpCode(String grpCode) {
		this.grpCode = grpCode;
	}
	@NotBlank(message = "部门名称不能为空",groups = {NormalGroup.class})
	public String getGrpName() {
		return grpName;
	}

	public void setGrpName(String grpName) {
		this.grpName = grpName;
	}
	@NotBlank(message = "部门主管域账号/工号不能为空",groups = {NormalGroup.class})
	public String getGrpMgrUserName() {
		return grpMgrUserName;
	}

	public void setGrpMgrUserName(String grpMgrUserName) {
		this.grpMgrUserName = grpMgrUserName;
	}

	public String getGrpMgrRealName() {
		return grpMgrRealName;
	}

	public void setGrpMgrRealName(String grpMgrRealName) {
		this.grpMgrRealName = grpMgrRealName;
	}

	public String getGrpMgrMobile() {
		return grpMgrMobile;
	}

	public void setGrpMgrMobile(String grpMgrMobile) {
		this.grpMgrMobile = grpMgrMobile;
	}

	@Override
	public String toString() {
		return "CcaiImportParticipant [position=" + position + ", userName=" + userName + ", realName=" + realName
				+ ", mobile=" + mobile + ", grpCode=" + grpCode + ", grpName=" + grpName + ", grpMgrUserName="
				+ grpMgrUserName + ", grpMgrRealName=" + grpMgrRealName + ", grpMgrMobile=" + grpMgrMobile + "]";
	}
}
