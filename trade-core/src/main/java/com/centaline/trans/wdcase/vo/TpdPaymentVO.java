package com.centaline.trans.wdcase.vo;

import java.math.BigDecimal;
import java.util.Date;

public class TpdPaymentVO {


    private BigDecimal paymentAmount;

    private String payer;

    private Date paymentDate;

    private String receiptPic;
    /**
     * 流水合计金额
     */
    private BigDecimal allAmount;

	public BigDecimal getAllAmount() {
		return allAmount;
	}

	public void setAllAmount(BigDecimal allAmount) {
		this.allAmount = allAmount;
	}

	public BigDecimal getPaymentAmount() {
		return paymentAmount;
	}

	public void setPaymentAmount(BigDecimal paymentAmount) {
		this.paymentAmount = paymentAmount;
	}

	public String getPayer() {
		return payer;
	}

	public void setPayer(String payer) {
		this.payer = payer;
	}

	public Date getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(Date paymentDate) {
		this.paymentDate = paymentDate;
	}

	public String getReceiptPic() {
		return receiptPic;
	}

	public void setReceiptPic(String receiptPic) {
		this.receiptPic = receiptPic;
	}
}