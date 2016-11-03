package com.centaline.trans.spv.entity;

import java.math.BigDecimal;
import java.util.Date;

public class ToSpvCashFlow {
    private Long pkid;

    private String spvCode;

    private Long cashflowApplyId;

    private String direction;

    private String voucherNo;

    private String receiptNo;

    private Date receiptTime;

    private String receiver;

    private String receiverAcc;

    private String receiverBank;

    private String payer;

    private String payerAcc;

    private String payerBank;

    private String flowCondition;

    private BigDecimal amount;

    private String status;

    private Date closeTime;

    private Date inputTime;

    private String isDeleted;

    private Date createTime;

    private String createBy;

    private Date updateTime;

    private String updateBy;
    
    //对应申请的用途
    private String usage; 
    
    //总监审批人
    private String applyAuditorName;

    //财务初审人
    private String ftPreAuditorName;

    //财务复审人
    private String ftPostAuditorName;
    
    //附件ID组合
    private String attachIdArr;
    
    //文件名组合
    private String commentArr;
    
    private String createByName;

    public Long getPkid() {
        return pkid;
    }

    public void setPkid(Long pkid) {
        this.pkid = pkid;
    }

    public String getSpvCode() {
        return spvCode;
    }

    public void setSpvCode(String spvCode) {
        this.spvCode = spvCode == null ? null : spvCode.trim();
    }

    public Long getCashflowApplyId() {
        return cashflowApplyId;
    }

    public void setCashflowApplyId(Long cashflowApplyId) {
        this.cashflowApplyId = cashflowApplyId;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction == null ? null : direction.trim();
    }

    public String getVoucherNo() {
        return voucherNo;
    }

    public void setVoucherNo(String voucherNo) {
        this.voucherNo = voucherNo == null ? null : voucherNo.trim();
    }

    public String getReceiptNo() {
        return receiptNo;
    }

    public void setReceiptNo(String receiptNo) {
        this.receiptNo = receiptNo == null ? null : receiptNo.trim();
    }

    public Date getReceiptTime() {
        return receiptTime;
    }

    public void setReceiptTime(Date receiptTime) {
        this.receiptTime = receiptTime;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver == null ? null : receiver.trim();
    }

    public String getReceiverAcc() {
        return receiverAcc;
    }

    public void setReceiverAcc(String receiverAcc) {
        this.receiverAcc = receiverAcc == null ? null : receiverAcc.trim();
    }

    public String getReceiverBank() {
        return receiverBank;
    }

    public void setReceiverBank(String receiverBank) {
        this.receiverBank = receiverBank == null ? null : receiverBank.trim();
    }

    public String getPayer() {
        return payer;
    }

    public void setPayer(String payer) {
        this.payer = payer == null ? null : payer.trim();
    }

    public String getPayerAcc() {
        return payerAcc;
    }

    public void setPayerAcc(String payerAcc) {
        this.payerAcc = payerAcc == null ? null : payerAcc.trim();
    }

    public String getPayerBank() {
        return payerBank;
    }

    public void setPayerBank(String payerBank) {
        this.payerBank = payerBank == null ? null : payerBank.trim();
    }

    public String getFlowCondition() {
        return flowCondition;
    }

    public void setFlowCondition(String flowCondition) {
        this.flowCondition = flowCondition == null ? null : flowCondition.trim();
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public Date getCloseTime() {
        return closeTime;
    }

    public void setCloseTime(Date closeTime) {
        this.closeTime = closeTime;
    }

    public Date getInputTime() {
        return inputTime;
    }

    public void setInputTime(Date inputTime) {
        this.inputTime = inputTime;
    }

    public String getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(String isDeleted) {
        this.isDeleted = isDeleted == null ? null : isDeleted.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy == null ? null : createBy.trim();
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy == null ? null : updateBy.trim();
    }

	public String getUsage() {
		return usage;
	}

	public void setUsage(String usage) {
		this.usage = usage;
	}

	public String getApplyAuditorName() {
		return applyAuditorName;
	}

	public void setApplyAuditorName(String applyAuditorName) {
		this.applyAuditorName = applyAuditorName;
	}

	public String getFtPreAuditorName() {
		return ftPreAuditorName;
	}

	public void setFtPreAuditorName(String ftPreAuditorName) {
		this.ftPreAuditorName = ftPreAuditorName;
	}

	public String getFtPostAuditorName() {
		return ftPostAuditorName;
	}

	public void setFtPostAuditorName(String ftPostAuditorName) {
		this.ftPostAuditorName = ftPostAuditorName;
	}

	public String getAttachIdArr() {
		return attachIdArr;
	}

	public void setAttachIdArr(String attachIdArr) {
		this.attachIdArr = attachIdArr;
	}

	public String getCommentArr() {
		return commentArr;
	}

	public void setCommentArr(String commentArr) {
		this.commentArr = commentArr;
	}

	public String getCreateByName() {
		return createByName;
	}

	public void setCreateByName(String createByName) {
		this.createByName = createByName;
	}
	

}