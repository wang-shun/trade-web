package com.centaline.trans.cases.vo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class EditCaseDetailVO {
	/**共有属性*/
    private String caseCode;
    
    /*业务单信息*/
	private Date createTime;  	/*业务单创建时间 to_case*/
	private Date resDate;		/*分单日期 TO_CASE_INFO*/
	private Date approveTime;	/*结案时间T_TO_CLOSE*/
	
	/**TgGuestInfo 客户信息*/
	/**上家*/
    private List<Long> pkidUp;
	private List<String> guestNameUp;
    private List<String> guestPhoneUp;
    /**下家*/
    private List<Long> pkidDown;
    private List<String> guestNameDown;
    private List<String> guestPhoneDown;
    /*上下家删除的数据*/
    private List<Long> guestPkid;
    
    /**ToPropertyInfo 物业信息*/
    private Long propertyPkid;
    private String propertyAddr;
    private Integer totalFloor;
    private Integer locateFloor;
    private Double square;
    private String finishYear;
    private String propertyType;
    
    /**TO_PAYMENT 付款方式*/
    /**首付款*/
    private Long initPayPkid;
    private Date initPayTime;
    private String initPayType;
    private BigDecimal initAmount;
    /**二次付款*/
    private Long secPayPkid;
    private Date secPayTime;
    private String secPayType;
    private BigDecimal secAmount;
    /**尾款付款*/
    private Long lastPayPkid;
    private Date lastPayTime;
    private String lastPayType;
    private BigDecimal lastAmount;
    /**装修补偿款*/
    private Long compensatePayPkid;
    private Date compensatePayTime;
    private String compensatePayType;
    private BigDecimal compensateAmount;
    
    /**签约表*/
    private Long signPkid;
    private Date realConTime;
    private String isHukou;
    private String isConCert;
    private BigDecimal conPrice;		/*合同价*/
    private BigDecimal realPrice;		/*成交价*/
    
    /*贷款结清*/
    private Long lcid;
    private Date loanCloseCode;				/*还款时间*/
    private String closeType;				/*款项来源*/
    private BigDecimal uncloseMoney;		/*抵押金额*/
    
    /*过户*/
    private Long ghid;
    private Date realHtTime;
    private BigDecimal houseHodingTax;
    private BigDecimal personalIncomeTax;
    private BigDecimal businessTax;
    private BigDecimal contractTax;
    private BigDecimal landIncrementTax;
    
    /*审税*/
    private Long taxid;
    private Date taxTime;
    private String isUniqueHome;
    private String holdYear;
    
    /*查限购*/
    private Date realPlsTime;
    
    /*领证*/
    private Date realPropertyGetTime;
    
    /*贷款信息*/
    private Long mpkid;						/**/
    
    private Date signDate;					/*签约时间*/
    private Date apprDate;					/*批贷时间*/
    private String mortType;				/*贷款类型*/
    private BigDecimal mortTotalAmount;		/*贷款总额*/

    private BigDecimal comAmount;			/*商贷金额*/
    private Integer comYear;				/*商贷年限*/
    private BigDecimal prfAmount;			/*公积金贷款金额*/
    private Integer prfYear;				/*公积金贷款年限*/

    private BigDecimal comDiscount;			/*商贷利率折扣*/
    private String isDelegateYucui;			/*按揭贷款类型  是否自办*/
    private String lendWay;					/*放款方式*/
    private Date tazhengArrDate;			/*它证送达时间*/

    private String custCode;				/*客户编号 主贷人*/
    private String custName;
    private Date prfApplyDate;				/*公积金贷款申请时间*/
    private Date lendDate;					/*放款时间*/
    private String loanerName;				/*信贷员姓名*/

    private String custCompany;				/*客户单位*/
    private Integer houseNum;				/*认定套数*/
    private String loanerPhone;				/*信贷员电话*/
    private String finOrgName;				/*评估公司*/

    private String lastLoanBank;			/*最终贷款银行*/
    private String finOrgCode;				/*贷款机构*/
    
	public Long getMpkid() {
		return mpkid;
	}

	public void setMpkid(Long mpkid) {
		this.mpkid = mpkid;
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

	public BigDecimal getMortTotalAmount() {
		return mortTotalAmount;
	}

	public void setMortTotalAmount(BigDecimal mortTotalAmount) {
		this.mortTotalAmount = mortTotalAmount;
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

	public BigDecimal getComDiscount() {
		return comDiscount;
	}

	public void setComDiscount(BigDecimal comDiscount) {
		this.comDiscount = comDiscount;
	}

	public String getMortType() {
		return mortType;
	}

	public void setMortType(String mortType) {
		this.mortType = mortType;
	}

	public String getLendWay() {
		return lendWay;
	}

	public void setLendWay(String lendWay) {
		this.lendWay = lendWay;
	}

	public Date getTazhengArrDate() {
		return tazhengArrDate;
	}

	public void setTazhengArrDate(Date tazhengArrDate) {
		this.tazhengArrDate = tazhengArrDate;
	}

	public String getCustCode() {
		return custCode;
	}

	public void setCustCode(String custCode) {
		this.custCode = custCode;
	}

	public Date getPrfApplyDate() {
		return prfApplyDate;
	}

	public void setPrfApplyDate(Date prfApplyDate) {
		this.prfApplyDate = prfApplyDate;
	}

	public Date getLendDate() {
		return lendDate;
	}

	public void setLendDate(Date lendDate) {
		this.lendDate = lendDate;
	}

	public String getLoanerName() {
		return loanerName;
	}

	public void setLoanerName(String loanerName) {
		this.loanerName = loanerName;
	}

	public String getCustCompany() {
		return custCompany;
	}

	public void setCustCompany(String custCompany) {
		this.custCompany = custCompany;
	}

	public Integer getHouseNum() {
		return houseNum;
	}

	public void setHouseNum(Integer houseNum) {
		this.houseNum = houseNum;
	}

	public String getLoanerPhone() {
		return loanerPhone;
	}

	public void setLoanerPhone(String loanerPhone) {
		this.loanerPhone = loanerPhone;
	}

	public String getLastLoanBank() {
		return lastLoanBank;
	}

	public void setLastLoanBank(String lastLoanBank) {
		this.lastLoanBank = lastLoanBank;
	}

	public String getFinOrgCode() {
		return finOrgCode;
	}

	public void setFinOrgCode(String finOrgCode) {
		this.finOrgCode = finOrgCode;
	}

	public String getCaseCode() {
		return caseCode;
	}

	public void setCaseCode(String caseCode) {
		this.caseCode = caseCode;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getResDate() {
		return resDate;
	}

	public void setResDate(Date resDate) {
		this.resDate = resDate;
	}

	public Date getApproveTime() {
		return approveTime;
	}

	public void setApproveTime(Date approveTime) {
		this.approveTime = approveTime;
	}

	public Long getPropertyPkid() {
		return propertyPkid;
	}

	public void setPropertyPkid(Long propertyPkid) {
		this.propertyPkid = propertyPkid;
	}

	public String getPropertyAddr() {
		return propertyAddr;
	}

	public void setPropertyAddr(String propertyAddr) {
		this.propertyAddr = propertyAddr;
	}

	public Integer getTotalFloor() {
		return totalFloor;
	}

	public void setTotalFloor(Integer totalFloor) {
		this.totalFloor = totalFloor;
	}

	public Integer getLocateFloor() {
		return locateFloor;
	}

	public void setLocateFloor(Integer locateFloor) {
		this.locateFloor = locateFloor;
	}

	public Double getSquare() {
		return square;
	}

	public void setSquare(Double square) {
		this.square = square;
	}

	public String getFinishYear() {
		return finishYear;
	}

	public void setFinishYear(String finishYear) {
		this.finishYear = finishYear;
	}

	public String getPropertyType() {
		return propertyType;
	}

	public void setPropertyType(String propertyType) {
		this.propertyType = propertyType;
	}

	public Long getInitPayPkid() {
		return initPayPkid;
	}

	public void setInitPayPkid(Long initPayPkid) {
		this.initPayPkid = initPayPkid;
	}

	public Date getInitPayTime() {
		return initPayTime;
	}

	public void setInitPayTime(Date initPayTime) {
		this.initPayTime = initPayTime;
	}

	public String getInitPayType() {
		return initPayType;
	}

	public void setInitPayType(String initPayType) {
		this.initPayType = initPayType;
	}

	public BigDecimal getInitAmount() {
		return initAmount;
	}

	public void setInitAmount(BigDecimal initAmount) {
		this.initAmount = initAmount;
	}

	public Long getSecPayPkid() {
		return secPayPkid;
	}

	public void setSecPayPkid(Long secPayPkid) {
		this.secPayPkid = secPayPkid;
	}

	public Date getSecPayTime() {
		return secPayTime;
	}

	public void setSecPayTime(Date secPayTime) {
		this.secPayTime = secPayTime;
	}

	public String getSecPayType() {
		return secPayType;
	}

	public void setSecPayType(String secPayType) {
		this.secPayType = secPayType;
	}

	public BigDecimal getSecAmount() {
		return secAmount;
	}

	public void setSecAmount(BigDecimal secAmount) {
		this.secAmount = secAmount;
	}

	public Long getLastPayPkid() {
		return lastPayPkid;
	}

	public void setLastPayPkid(Long lastPayPkid) {
		this.lastPayPkid = lastPayPkid;
	}

	public Date getLastPayTime() {
		return lastPayTime;
	}

	public void setLastPayTime(Date lastPayTime) {
		this.lastPayTime = lastPayTime;
	}

	public String getLastPayType() {
		return lastPayType;
	}

	public void setLastPayType(String lastPayType) {
		this.lastPayType = lastPayType;
	}

	public BigDecimal getLastAmount() {
		return lastAmount;
	}

	public void setLastAmount(BigDecimal lastAmount) {
		this.lastAmount = lastAmount;
	}

	public Long getCompensatePayPkid() {
		return compensatePayPkid;
	}

	public void setCompensatePayPkid(Long compensatePayPkid) {
		this.compensatePayPkid = compensatePayPkid;
	}

	public Date getCompensatePayTime() {
		return compensatePayTime;
	}

	public void setCompensatePayTime(Date compensatePayTime) {
		this.compensatePayTime = compensatePayTime;
	}

	public String getCompensatePayType() {
		return compensatePayType;
	}

	public void setCompensatePayType(String compensatePayType) {
		this.compensatePayType = compensatePayType;
	}

	public BigDecimal getCompensateAmount() {
		return compensateAmount;
	}

	public void setCompensateAmount(BigDecimal compensateAmount) {
		this.compensateAmount = compensateAmount;
	}

	public Long getSignPkid() {
		return signPkid;
	}

	public void setSignPkid(Long signPkid) {
		this.signPkid = signPkid;
	}

	public Date getRealConTime() {
		return realConTime;
	}

	public void setRealConTime(Date realConTime) {
		this.realConTime = realConTime;
	}

	public String getIsHukou() {
		return isHukou;
	}

	public void setIsHukou(String isHukou) {
		this.isHukou = isHukou;
	}

	public String getIsConCert() {
		return isConCert;
	}

	public void setIsConCert(String isConCert) {
		this.isConCert = isConCert;
	}

	public BigDecimal getConPrice() {
		return conPrice;
	}

	public void setConPrice(BigDecimal conPrice) {
		this.conPrice = conPrice;
	}

	public BigDecimal getRealPrice() {
		return realPrice;
	}

	public void setRealPrice(BigDecimal realPrice) {
		this.realPrice = realPrice;
	}

	public Long getLcid() {
		return lcid;
	}

	public void setLcid(Long lcid) {
		this.lcid = lcid;
	}

	public Date getLoanCloseCode() {
		return loanCloseCode;
	}

	public void setLoanCloseCode(Date loanCloseCode) {
		this.loanCloseCode = loanCloseCode;
	}

	public String getCloseType() {
		return closeType;
	}

	public void setCloseType(String closeType) {
		this.closeType = closeType;
	}

	public BigDecimal getUncloseMoney() {
		return uncloseMoney;
	}

	public void setUncloseMoney(BigDecimal uncloseMoney) {
		this.uncloseMoney = uncloseMoney;
	}

	public Long getGhid() {
		return ghid;
	}

	public void setGhid(Long ghid) {
		this.ghid = ghid;
	}

	public Date getRealHtTime() {
		return realHtTime;
	}

	public void setRealHtTime(Date realHtTime) {
		this.realHtTime = realHtTime;
	}

	public BigDecimal getHouseHodingTax() {
		return houseHodingTax;
	}

	public void setHouseHodingTax(BigDecimal houseHodingTax) {
		this.houseHodingTax = houseHodingTax;
	}

	public BigDecimal getPersonalIncomeTax() {
		return personalIncomeTax;
	}

	public void setPersonalIncomeTax(BigDecimal personalIncomeTax) {
		this.personalIncomeTax = personalIncomeTax;
	}

	public BigDecimal getBusinessTax() {
		return businessTax;
	}

	public void setBusinessTax(BigDecimal businessTax) {
		this.businessTax = businessTax;
	}

	public BigDecimal getContractTax() {
		return contractTax;
	}

	public void setContractTax(BigDecimal contractTax) {
		this.contractTax = contractTax;
	}

	public BigDecimal getLandIncrementTax() {
		return landIncrementTax;
	}

	public void setLandIncrementTax(BigDecimal landIncrementTax) {
		this.landIncrementTax = landIncrementTax;
	}

	public Long getTaxid() {
		return taxid;
	}

	public void setTaxid(Long taxid) {
		this.taxid = taxid;
	}

	public Date getTaxTime() {
		return taxTime;
	}

	public void setTaxTime(Date taxTime) {
		this.taxTime = taxTime;
	}

	public String getIsUniqueHome() {
		return isUniqueHome;
	}

	public void setIsUniqueHome(String isUniqueHome) {
		this.isUniqueHome = isUniqueHome;
	}

	public String getHoldYear() {
		return holdYear;
	}

	public void setHoldYear(String holdYear) {
		this.holdYear = holdYear;
	}

	public Date getRealPlsTime() {
		return realPlsTime;
	}

	public void setRealPlsTime(Date realPlsTime) {
		this.realPlsTime = realPlsTime;
	}

	public Date getRealPropertyGetTime() {
		return realPropertyGetTime;
	}

	public void setRealPropertyGetTime(Date realPropertyGetTime) {
		this.realPropertyGetTime = realPropertyGetTime;
	}

	public String getIsDelegateYucui() {
		return isDelegateYucui;
	}

	public void setIsDelegateYucui(String isDelegateYucui) {
		this.isDelegateYucui = isDelegateYucui;
	}

	public String getFinOrgName() {
		return finOrgName;
	}

	public void setFinOrgName(String finOrgName) {
		this.finOrgName = finOrgName;
	}

	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	public List<Long> getPkidUp() {
		return pkidUp;
	}

	public void setPkidUp(List<Long> pkidUp) {
		this.pkidUp = pkidUp;
	}

	public List<String> getGuestNameUp() {
		return guestNameUp;
	}

	public void setGuestNameUp(List<String> guestNameUp) {
		this.guestNameUp = guestNameUp;
	}

	public List<String> getGuestPhoneUp() {
		return guestPhoneUp;
	}

	public void setGuestPhoneUp(List<String> guestPhoneUp) {
		this.guestPhoneUp = guestPhoneUp;
	}

	public List<Long> getPkidDown() {
		return pkidDown;
	}

	public void setPkidDown(List<Long> pkidDown) {
		this.pkidDown = pkidDown;
	}

	public List<String> getGuestNameDown() {
		return guestNameDown;
	}

	public void setGuestNameDown(List<String> guestNameDown) {
		this.guestNameDown = guestNameDown;
	}

	public List<String> getGuestPhoneDown() {
		return guestPhoneDown;
	}

	public void setGuestPhoneDown(List<String> guestPhoneDown) {
		this.guestPhoneDown = guestPhoneDown;
	}

	public List<Long> getGuestPkid() {
		return guestPkid;
	}

	public void setGuestPkid(List<Long> guestPkid) {
		this.guestPkid = guestPkid;
	}
    
    
}
