package com.centaline.trans.spv.vo;

import java.math.BigDecimal;

public class SpvRecordedsVOItem {
	//付款人姓名
	private String payerName;
	//付款人银行卡号
	private String payerAcc;
	//付款人银行名称
	private String payerBank;
	//贷记凭证编号
	private String receiptNo;
	//入账金额
	private BigDecimal payerAmount;
	//转账凭证
	private String voucherNo;
	//文件id
	private String fileId;
	//文件名
	private String fileName;
	
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getFileId() {
		return fileId;
	}
	public void setFileId(String fileId) {
		this.fileId = fileId;
	}
	public String getPayerName() {
		return payerName;
	}
	public void setPayerName(String payerName) {
		this.payerName = payerName;
	}
	public String getPayerAcc() {
		return payerAcc;
	}
	public void setPayerAcc(String payerAcc) {
		this.payerAcc = payerAcc;
	}
	public String getPayerBank() {
		return payerBank;
	}
	public void setPayerBank(String payerBank) {
		this.payerBank = payerBank;
	}
	public String getReceiptNo() {
		return receiptNo;
	}
	public void setReceiptNo(String receiptNo) {
		this.receiptNo = receiptNo;
	}
	
	public BigDecimal getPayerAmount() {
		return payerAmount;
	}
	public void setPayerAmount(BigDecimal payerAmount) {
		this.payerAmount = payerAmount;
	}
	public String getVoucherNo() {
		return voucherNo;
	}
	public void setVoucherNo(String voucherNo) {
		this.voucherNo = voucherNo;
	}
	
}
