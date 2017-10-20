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
 * 评估费发票申请导入对象
 *
 * @author yinchao
 * @date 2017/9/23
 */
@ApiModel("评估费发票申请信息")
public class EvalInvoiceImport extends AbstractBaseImport {

	@ApiModelProperty(value = "成交报告编号", required = true, position = 1)
	private String ccaiCode;
	@ApiModelProperty(value = "开票名称类型", example = "单位", allowableValues = "个人,单位", required = true, position = 2)
	private String nameType;
	@ApiModelProperty(value = "发票种类", example = "增值税专用发票", allowableValues = "增值税普通发票,增值税专用发票", required = true, position = 3)
	private String invoiceType;
	@ApiModelProperty(value = "开票抬头", example = "XXXXX公司", required = true, position = 4)
	private String invoiceHeader;
	@ApiModelProperty(value = "税号", example = "99999999X", position = 5)
	private String companyNumber;
	@ApiModelProperty(value = "单位地址", required = true, position = 6)
	private String companyAddress;
	@ApiModelProperty(value = "电话", required = true, position = 7)
	private String mobile;
	@ApiModelProperty(value = "开户银行", position = 8)
	private String bankName;
	@ApiModelProperty(value = "银行账户", position = 9)
	private String bankAccount;
	@ApiModelProperty(value = "发票金额", required = true, position = 10)
	private BigDecimal invoiceAmount;
	@ApiModelProperty(value = "申请人域账号", required = true, position = 11)
	private String applyUserName;
	@ApiModelProperty(value = "申请人名称", required = true, position = 12)
	private String applyRealName;
	@ApiModelProperty(value = "申请时间", required = true, example = "1503460440000", dataType = "integer", position = 13)
	private Date applyDate;
	@ApiModelProperty(value = "备注", required = true, position = 14)
	private String remark;
	@ApiModelProperty(value = "审批记录", required = true, position = 15)
	List<TaskInfo> tasks;

	@NotBlank(message = "成交报告编号不能为空")
	public String getCcaiCode() {
		return ccaiCode;
	}

	public void setCcaiCode(String ccaiCode) {
		this.ccaiCode = ccaiCode;
	}

	@NotBlank(message = "发票名称类型不能为空")
	public String getNameType() {
		return nameType;
	}

	public void setNameType(String nameType) {
		this.nameType = nameType;
	}

	@NotBlank(message = "发票类型不能为空")
	public String getInvoiceType() {
		return invoiceType;
	}

	public void setInvoiceType(String invoiceType) {
		this.invoiceType = invoiceType;
	}

	@NotBlank(message = "发票抬头不能为空")
	public String getInvoiceHeader() {
		return invoiceHeader;
	}

	public String getCompanyNumber() {
		return companyNumber;
	}

	public void setCompanyNumber(String companyNumber) {
		this.companyNumber = companyNumber;
	}

	@NotBlank(message = "公司地址不能为空")
	public String getCompanyAddress() {
		return companyAddress;
	}

	public void setCompanyAddress(String companyAddress) {
		this.companyAddress = companyAddress;
	}

	@NotBlank(message = "电话不能为空")
	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getBankAccount() {
		return bankAccount;
	}

	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}

	public void setInvoiceHeader(String invoiceHeader) {
		this.invoiceHeader = invoiceHeader;
	}

	@NotNull(message = "发票金额不能为空")
	public BigDecimal getInvoiceAmount() {
		return invoiceAmount;
	}

	public void setInvoiceAmount(BigDecimal invoiceAmount) {
		this.invoiceAmount = invoiceAmount;
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

	@NotNull(message = "发票申请时间不能为空")
	public Date getApplyDate() {
		return applyDate;
	}

	public void setApplyDate(Date applyDate) {
		this.applyDate = applyDate;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@NotEmpty(message = "审批历史不能为空")
	public List<TaskInfo> getTasks() {
		return tasks;
	}

	public void setTasks(List<TaskInfo> tasks) {
		this.tasks = tasks;
	}

	@Override
	public String toString() {
		return "EvalInvoiceImport{" +
				"ccaiCode='" + ccaiCode + '\'' +
				", nameType='" + nameType + '\'' +
				", invoiceType='" + invoiceType + '\'' +
				", invoiceHeader='" + invoiceHeader + '\'' +
				", invoiceAmount=" + invoiceAmount +
				", applyUserName='" + applyUserName + '\'' +
				", applyRealName='" + applyRealName + '\'' +
				", applyDate=" + applyDate +
				", remark='" + remark + '\'' +
				", tasks=" + tasks +
				'}';
	}
}
