package com.centaline.api.ccai.vo;

import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 评估费发票申请导入对象
 * TODO 天津CCAI目前是走的线下，然后进行调佣没有线上流程
 * @author yinchao
 * @date 2017/9/23
 */
public class EvalInvoiceImport extends AbstractBaseImport{
	@ApiModelProperty(value = "评估报告编号", required = true, position = 1)
	private String ccaiCode;
	@ApiModelProperty(value = "开票抬头", required = true, position = 2)
	private String invoiceHeader;
	@ApiModelProperty(value = "申请时间", required = true, position = 3)
	private Date  applyDate;
	@ApiModelProperty(value = "评估公司名称", required = true, position = 4)
	private String evalCompany;
	@ApiModelProperty(value = "评估公司ID", required = true, position = 5)
	private String evalCompanyId;
	@ApiModelProperty(value = "开票抬头", required = true, position = 2)
	private String evalComContacts;
	@ApiModelProperty(value = "发票金额", required = true, position = 6)
	private BigDecimal invoiceAmount;
	@ApiModelProperty(value = "发票种类", required = true, position = 2)
	private String invoiceType;
}
