

/**
 * 添加字段
 * 日期           说明                                                       	作者
 * 2017 9 06 添加主贷人单位  priCreditUnit    lujian
 * 2017 9 07 添加是否补件      isPatch   1是0否  lujian
 * 2017 9 07 买方材料用于接收    buyPatch     lujian
 * 2017 9 07 卖方材料用于接收    sellPatch    lujian
 * 2017 9 07 买方材料用于存储    buyPatchStr  lujian
 * 2017 9 07 卖方材料用于存储    sellPatchStr  lujian
 * 2017 9 07 买方补件时间      patchTimeBuy   	lujian
 * 2017 9 07 卖方补件时间      patchTimeSell   lujian
 * 2017 9 08 实际审批完成时间 apprCompleTime lujian
 * 2017 9 08 出具借款合同时间 loanContraTime lujian
 * 2017 9 12 贷款银行code  bank_type        lujian
 * 2017 9 13 预约结果        bookResult        lujian
 * 
 * 2017 9 13   预约人      bookPerson         lujian
 * 2017 9 13   共贷人      coLender           lujian         
 */

package com.centaline.trans.mortgage.entity;

import java.math.BigDecimal;
import java.util.Date;

import com.centaline.trans.eval.entity.ToEval;
import com.centaline.trans.mgr.entity.ToSupDocu;
import com.fasterxml.jackson.annotation.JsonFormat;

public class ToMortgage {
    private Long       pkid;

    private String     caseCode;

    private String     mortType;

    private BigDecimal mortTotalAmount;

    private String     finOrgCode;

    private String     custCode;

    private BigDecimal comAmount;

    private Integer    comYear;

    private BigDecimal comDiscount;

    private BigDecimal prfAmount;

    private Integer    prfYear;

    private String     lendWay;

    private Integer    houseNum;

    private String     isLoanerArrive;

    private String     partCode;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private Date       signDate;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private Date       apprDate;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private Date       tazhengArrDate;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private Date       lendDate;

    private String     custCompany;

    private String     ifReportBeforeLend;

    private String     loanerName;

    private String     loanerPhone;

    private String     loanerOrgCode;

    private String     loanerOrgId;
    
    private String     loanerId;
    private String     dispachUserId;

    private String     loanerProcessInstCode;
    private String     bankLevel;
    
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private Date       dispachTime;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private Date       bankApproveTime;

    private String     remark;

    private String     lastLoanBank;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private Date       prfApplyDate;

    private String     ifRequireReconsider;

    private ToSupDocu  toSupDocu;
    /**
     * 评估费
     */
    private ToEval toEval;

    private String     isDelegateYucui;

    private String     isMainLoanBank;

    private String     custName;

    private String     selfDelReason;

    private String     tmpBankReason;

    private String     isActive;
    
    /* 是否商贷 */
    private String     formCommLoan;
    /** 推荐函编号 */
    private String     recLetterNo;

    /** 申请贷款流失原因 */
    private String     loanLostApplyReason;

    /** 贷款流失确认函编号 */
    private String     loanLostConfirmCode;

    private String     loanAgent;

    private String     loanAgentTeam;

    private String     isTmpBank;
    private String     tmpBankUpdateByStr;
    private String     tmpBankUpdateBy;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private Date       createTime;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private Date       updateTime;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private Date       tmpBankUpdateTime;
    private String     createBy;
    private String     updateBy;
    private String     bankName;

    private String     parentBankName;

    private String     tmpBankStatus;

    private String     tmpBankRejectReason;

    /*贷款在银行内部的状态--由信贷员填写*/
    private String     stateInBank;
    
    /**
	 * 预定签约地点
	 */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
	private Date resSignTime;
	/**
	 * 预定签约地点
	 */
	private String resSignAddr;
	
	//主贷人单位
	private String priCreditUnit;
	
	/**
	 * 是否补件
	 * @return
	 */
	private String isPatch;
	/**
	 * 买方材料
	 * 用于接收
	 */
	private String [] buyPatch;
	/**
	 * 卖方材料
	 * 用于接收
	 * @return
	 */
	private String [] sellPatch;
	
	/**
	 * 买方材料
	 * 用于存储
	 */
	private String buyPatchStr;
	/**
	 * 卖方材料
	 * 用于存储
	 */
	private String sellPatchStr;
	/**
	 * 买方补件时间
	 * @return
	 */
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
	private Date patchTimeBuy;
	
	/**
	 * 卖方补件时间
	 * @return
	 */
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
	private Date patchTimeSell;
	/**
	 * 实际审批完成时间
	 */
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
	private Date apprCompleTime;
	/**
	 * 出具借款合同时间
	 */
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
	private Date loanContraTime;
	
	/**
	 * 贷款银行
	 */
	private String bank_type;
	
	/**
	 * 预约结果
	 */
	private String bookResult;
	
	private String bookPerson;
	/**
	 * 共贷人
	 */
	private String coLender;
	
    public String getCoLender() {
		return coLender;
	}

	public void setCoLender(String coLender) {
		this.coLender = coLender;
	}

	public String getBookResult() {
		return bookResult;
	}

	public void setBookResult(String bookResult) {
		this.bookResult = bookResult;
	}

	public String getBookPerson() {
		return bookPerson;
	}

	public void setBookPerson(String bookPerson) {
		this.bookPerson = bookPerson;
	}

	public String getBank_type() {
		return bank_type;
	}

	public void setBank_type(String bank_type) {
		this.bank_type = bank_type;
	}

	public Date getApprCompleTime() {
		return apprCompleTime;
	}

	public void setApprCompleTime(Date apprCompleTime) {
		this.apprCompleTime = apprCompleTime;
	}

	public Date getLoanContraTime() {
		return loanContraTime;
	}

	public void setLoanContraTime(Date loanContraTime) {
		this.loanContraTime = loanContraTime;
	}

	public String getPriCreditUnit() {
		return priCreditUnit;
	}

	public void setPriCreditUnit(String priCreditUnit) {
		this.priCreditUnit = priCreditUnit;
	}

	public Long getPkid() {
        return pkid;
    }

    public void setPkid(Long pkid) {
        this.pkid = pkid;
    }

    public String getCaseCode() {
        return caseCode;
    }

    public void setCaseCode(String caseCode) {
        this.caseCode = caseCode == null ? null : caseCode.trim();
    }

    public String getMortType() {
        return mortType;
    }

    public void setMortType(String mortType) {
        this.mortType = mortType == null ? null : mortType.trim();
    }

    public BigDecimal getMortTotalAmount() {
        return mortTotalAmount;
    }

    public void setMortTotalAmount(BigDecimal mortTotalAmount) {
        this.mortTotalAmount = mortTotalAmount;
    }

    public String getFinOrgCode() {
        return finOrgCode;
    }

    public void setFinOrgCode(String finOrgCode) {
        this.finOrgCode = finOrgCode == null ? null : finOrgCode.trim();
    }

    public String getCustCode() {
        return custCode;
    }

    public void setCustCode(String custCode) {
        this.custCode = custCode == null ? null : custCode.trim();
    }

    public BigDecimal getComAmount() {
        return comAmount;
    }

    public void setComAmount(BigDecimal comAmount) {
        this.comAmount = comAmount;
    }

    public Integer getComYear() {
        return comYear;
    }

    public void setComYear(Integer comYear) {
        this.comYear = comYear;
    }

    public BigDecimal getComDiscount() {
        return comDiscount;
    }

    public void setComDiscount(BigDecimal comDiscount) {
        this.comDiscount = comDiscount;
    }

    public BigDecimal getPrfAmount() {
        return prfAmount;
    }

    public void setPrfAmount(BigDecimal prfAmount) {
        this.prfAmount = prfAmount;
    }

    public Integer getPrfYear() {
        return prfYear;
    }

    public void setPrfYear(Integer prfYear) {
        this.prfYear = prfYear;
    }

    public String getLendWay() {
        return lendWay;
    }

    public void setLendWay(String lendWay) {
        this.lendWay = lendWay == null ? null : lendWay.trim();
    }

    public Integer getHouseNum() {
        return houseNum;
    }

    public void setHouseNum(Integer houseNum) {
        this.houseNum = houseNum;
    }

    public String getIsLoanerArrive() {
        return isLoanerArrive;
    }

    public void setIsLoanerArrive(String isLoanerArrive) {
        this.isLoanerArrive = isLoanerArrive == null ? null : isLoanerArrive.trim();
    }

    public Date getSignDate() {
        return signDate;
    }

    public void setSignDate(Date signDate) {
        this.signDate = signDate;
    }

    public Date getApprDate() {
        return apprDate;
    }

    public void setApprDate(Date apprDate) {
        this.apprDate = apprDate;
    }

    public Date getTazhengArrDate() {
        return tazhengArrDate;
    }

    public void setTazhengArrDate(Date tazhengArrDate) {
        this.tazhengArrDate = tazhengArrDate;
    }

    public Date getLendDate() {
        return lendDate;
    }

    public void setLendDate(Date lendDate) {
        this.lendDate = lendDate;
    }

    public String getCustCompany() {
        return custCompany;
    }

    public void setCustCompany(String custCompany) {
        this.custCompany = custCompany == null ? null : custCompany.trim();
    }

    public String getIfReportBeforeLend() {
        return ifReportBeforeLend;
    }

    public void setIfReportBeforeLend(String ifReportBeforeLend) {
        this.ifReportBeforeLend = ifReportBeforeLend == null ? null : ifReportBeforeLend.trim();
    }

    public String getLoanerName() {
        return loanerName;
    }

    public void setLoanerName(String loanerName) {
        this.loanerName = loanerName == null ? null : loanerName.trim();
    }

    public String getLoanerPhone() {
        return loanerPhone;
    }

    public void setLoanerPhone(String loanerPhone) {
        this.loanerPhone = loanerPhone == null ? null : loanerPhone.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getLastLoanBank() {
        return lastLoanBank;
    }

    public void setLastLoanBank(String lastLoanBank) {
        this.lastLoanBank = lastLoanBank;
    }

    public Date getPrfApplyDate() {
        return prfApplyDate;
    }

    public void setPrfApplyDate(Date prfApplyDate) {
        this.prfApplyDate = prfApplyDate;
    }

    public String getIfRequireReconsider() {
        return ifRequireReconsider;
    }

    public void setIfRequireReconsider(String ifRequireReconsider) {
        this.ifRequireReconsider = ifRequireReconsider;
    }

    public ToSupDocu getToSupDocu() {
        return toSupDocu;
    }

    public void setToSupDocu(ToSupDocu toSupDocu) {
        this.toSupDocu = toSupDocu;
    }

    public String getIsDelegateYucui() {
        return isDelegateYucui;
    }

    public void setIsDelegateYucui(String isDelegateYucui) {
        this.isDelegateYucui = isDelegateYucui;
    }

    public String getIsMainLoanBank() {
        return isMainLoanBank;
    }

    public void setIsMainLoanBank(String isMainLoanBank) {
        this.isMainLoanBank = isMainLoanBank;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getPartCode() {
        return partCode;
    }

    public void setPartCode(String partCode) {
        this.partCode = partCode;
    }

    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }

    public String getSelfDelReason() {
        return selfDelReason;
    }

    public void setSelfDelReason(String selfDelReason) {
        this.selfDelReason = selfDelReason;
    }

    public String getFormCommLoan() {
        return formCommLoan;
    }

    public void setFormCommLoan(String formCommLoan) {
        this.formCommLoan = formCommLoan;
    }

    public String getRecLetterNo() {
        return recLetterNo;
    }

    public void setRecLetterNo(String recLetterNo) {
        this.recLetterNo = recLetterNo;
    }

    public String getIsTmpBank() {
        return isTmpBank;
    }

    public void setIsTmpBank(String isTmpBank) {
        this.isTmpBank = isTmpBank;
    }

    public String getTmpBankUpdateBy() {
        return tmpBankUpdateBy;
    }

    public void setTmpBankUpdateBy(String tmpBankUpdateBy) {
        this.tmpBankUpdateBy = tmpBankUpdateBy;
    }

    public Date getTmpBankUpdateTime() {
        return tmpBankUpdateTime;
    }

    public void setTmpBankUpdateTime(Date tmpBankUpdateTime) {
        this.tmpBankUpdateTime = tmpBankUpdateTime;
    }

    public String getTmpBankReason() {
        return tmpBankReason;
    }

    public void setTmpBankReason(String tmpBankReason) {
        this.tmpBankReason = tmpBankReason;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getParentBankName() {
        return parentBankName;
    }

    public void setParentBankName(String parentBankName) {
        this.parentBankName = parentBankName;
    }

    public String getTmpBankUpdateByStr() {
        return tmpBankUpdateByStr;
    }

    public void setTmpBankUpdateByStr(String tmpBankUpdateByStr) {
        this.tmpBankUpdateByStr = tmpBankUpdateByStr;
    }

    public String getTmpBankStatus() {
        return tmpBankStatus;
    }

    public void setTmpBankStatus(String tmpBankStatus) {
        this.tmpBankStatus = tmpBankStatus;
    }

    public String getTmpBankRejectReason() {
        return tmpBankRejectReason;
    }

    public void setTmpBankRejectReason(String tmpBankRejectReason) {
        this.tmpBankRejectReason = tmpBankRejectReason;
    }

    public String getLoanLostApplyReason() {
        return loanLostApplyReason;
    }

    public void setLoanLostApplyReason(String loanLostApplyReason) {
        this.loanLostApplyReason = loanLostApplyReason;
    }

    public String getLoanAgent() {
        return loanAgent;
    }

    public void setLoanAgent(String loanAgent) {
        this.loanAgent = loanAgent;
    }

    public String getLoanAgentTeam() {
        return loanAgentTeam;
    }

    public void setLoanAgentTeam(String loanAgentTeam) {
        this.loanAgentTeam = loanAgentTeam;
    }

    public String getLoanLostConfirmCode() {
        return loanLostConfirmCode;
    }

    public void setLoanLostConfirmCode(String loanLostConfirmCode) {
        this.loanLostConfirmCode = loanLostConfirmCode;
    }

    public String getLoanerId() {
        return loanerId;
    }

    public void setLoanerId(String loanerId) {
        this.loanerId = loanerId;
    }

    public String getLoanerOrgCode() {
        return loanerOrgCode;
    }

    public void setLoanerOrgCode(String loanerOrgCode) {
        this.loanerOrgCode = loanerOrgCode;
    }

    public String getLoanerOrgId() {
        return loanerOrgId;
    }

    public void setLoanerOrgId(String loanerOrgId) {
        this.loanerOrgId = loanerOrgId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

	public String getDispachUserId() {
		return dispachUserId;
	}

	public void setDispachUserId(String dispachUserId) {
		this.dispachUserId = dispachUserId;
	}


	public Date getDispachTime() {
		return dispachTime;
	}

	public void setDispachTime(Date dispachTime) {
		this.dispachTime = dispachTime;
	}

	public Date getBankApproveTime() {
		return bankApproveTime;
	}

	public void setBankApproveTime(Date bankApproveTime) {
		this.bankApproveTime = bankApproveTime;
	}

	public String getLoanerProcessInstCode() {
		return loanerProcessInstCode;
	}

	public void setLoanerProcessInstCode(String loanerProcessInstCode) {
		this.loanerProcessInstCode = loanerProcessInstCode;
	}

	public String getBankLevel() {
		return bankLevel;
	}

	public void setBankLevel(String bankLevel) {
		this.bankLevel = bankLevel;
	}

	public String getStateInBank() {
		return stateInBank;
	}

	public void setStateInBank(String stateInBank) {
		this.stateInBank = stateInBank;
	}

	public ToEval getToEval() {
		return toEval;
	}

	public void setToEval(ToEval toEval) {
		this.toEval = toEval;
	}

	public String getResSignAddr() {
		return resSignAddr;
	}

	public void setResSignAddr(String resSignAddr) {
		this.resSignAddr = resSignAddr;
	}

	public Date getResSignTime() {
		return resSignTime;
	}

	public void setResSignTime(Date resSignTime) {
		this.resSignTime = resSignTime;
	}

	public String getIsPatch() {
		return isPatch;
	}

	public void setIsPatch(String isPatch) {
		this.isPatch = isPatch;
	}

	public String[] getBuyPatch() {
		return buyPatch;
	}

	public void setBuyPatch(String[] buyPatch) {
		this.buyPatch = buyPatch;
	}

	public String[] getSellPatch() {
		return sellPatch;
	}

	public void setSellPatch(String[] sellPatch) {
		this.sellPatch = sellPatch;
	}

	public String getBuyPatchStr() {
		return buyPatchStr;
	}

	public void setBuyPatchStr(String buyPatchStr) {
		this.buyPatchStr = buyPatchStr;
	}

	public String getSellPatchStr() {
		return sellPatchStr;
	}

	public void setSellPatchStr(String sellPatchStr) {
		this.sellPatchStr = sellPatchStr;
	}

	public Date getPatchTimeBuy() {
		return patchTimeBuy;
	}

	public void setPatchTimeBuy(Date patchTimeBuy) {
		this.patchTimeBuy = patchTimeBuy;
	}

	public Date getPatchTimeSell() {
		return patchTimeSell;
	}

	public void setPatchTimeSell(Date patchTimeSell) {
		this.patchTimeSell = patchTimeSell;
	}
	
}