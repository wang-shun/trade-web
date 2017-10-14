package com.centaline.trans.api.vo;

import java.math.BigDecimal;

/**
 *  CCAI银行返利申请 明细信息
 * @author yinchao
 * @date 2017/10/14
 */
public class CcaiBrokageBackDetail {
	//CCAI成交报告编号
	private String ReportNo;
	// 返利总金额
	private BigDecimal ReturnMoney;
	// 权证分成金额 过户和贷款合计
	private BigDecimal CertMoney;
	// 业务分成金额
	private BigDecimal BizMoney;
	// 返利银行名称
	private String BankName;

	public String getReportNo() {
		return ReportNo;
	}

	public void setReportNo(String reportNo) {
		ReportNo = reportNo;
	}

	public BigDecimal getReturnMoney() {
		return ReturnMoney;
	}

	public void setReturnMoney(BigDecimal returnMoney) {
		ReturnMoney = returnMoney;
	}

	public BigDecimal getCertMoney() {
		return CertMoney;
	}

	public void setCertMoney(BigDecimal certMoney) {
		CertMoney = certMoney;
	}

	public BigDecimal getBizMoney() {
		return BizMoney;
	}

	public void setBizMoney(BigDecimal bizMoney) {
		BizMoney = bizMoney;
	}

	public String getBankName() {
		return BankName;
	}

	public void setBankName(String bankName) {
		BankName = bankName;
	}
}
