package com.centaline.trans.task.vo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


public class TransSignVO {
	
	/*流程引擎*/
	private String taskId;
	private String processInstanceId;
	
	/**共有属性*/
    private String caseCode;
    private String partCode;
	
	/**TgGuestInfo 客户信息*/
	/**上家*/
    private List<Long> pkidUp;
	private List<String> guestNameUp;
    private List<String> guestPhoneUp;
    /**下家*/
    private List<Long> pkidDown;
    private List<String> guestNameDown;
    private List<String> guestPhoneDown;
    /*上下家删除的记录id*/
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
    private String initPayName;
    private Date initPayTime;
    private String initPayType;
    private BigDecimal initAmount;
    /**二次付款*/
    private Long secPayPkid;
    private String secPayName;
    private Date secPayTime;
    private String secPayType;
    private BigDecimal secAmount;
    /**尾款付款*/
    private Long lastPayPkid;
    private String lastPayName;
    private Date lastPayTime;
    private String lastPayType;
    private BigDecimal lastAmount;
    /**装修补偿款*/
    private Long compensatePayPkid;
    private String compensatePayName;
    private Date compensatePayTime;
    private String compensatePayType;
    private BigDecimal compensateAmount;
    
    /**签约表*/
    private Long signPkid;
    private Date realConTime;
    private String isHukou;
    private String isConCert;
    private String comment;
    private BigDecimal conPrice;		/*合同价*/
    private BigDecimal realPrice;		/*成交价*/
    
    /*预估税费*/
    private Long housePkid;
    private BigDecimal houseHodingTax;       /*房产税*/
    private BigDecimal personalIncomeTax;    /*个人所得税*/
    private BigDecimal businessTax;			 /*上家营业税*/
    private BigDecimal contractTax;			 /*下家契税*/
    private BigDecimal landIncrementTax;	 /*土地增值税*/
    
    /* 首次跟进表 */
    private String isPerchaseReserachNeed;  /*是否需要查限购*/
    private String isLoanClose;  /*抵押情况*/
    
    
	public Long getPropertyPkid() {
		return propertyPkid;
	}
	public void setPropertyPkid(Long propertyPkid) {
		this.propertyPkid = propertyPkid;
	}
	public Long getInitPayPkid() {
		return initPayPkid;
	}
	public void setInitPayPkid(Long initPayPkid) {
		this.initPayPkid = initPayPkid;
	}
	public Long getSecPayPkid() {
		return secPayPkid;
	}
	public void setSecPayPkid(Long secPayPkid) {
		this.secPayPkid = secPayPkid;
	}
	public Long getLastPayPkid() {
		return lastPayPkid;
	}
	public void setLastPayPkid(Long lastPayPkid) {
		this.lastPayPkid = lastPayPkid;
	}
	public Long getCompensatePayPkid() {
		return compensatePayPkid;
	}
	public void setCompensatePayPkid(Long compensatePayPkid) {
		this.compensatePayPkid = compensatePayPkid;
	}
	public Long getSignPkid() {
		return signPkid;
	}
	public void setSignPkid(Long signPkid) {
		this.signPkid = signPkid;
	}
	public String getInitPayName() {
		return initPayName;
	}
	public void setInitPayName(String initPayName) {
		this.initPayName = initPayName;
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
	public String getSecPayName() {
		return secPayName;
	}
	public void setSecPayName(String secPayName) {
		this.secPayName = secPayName;
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
	public String getLastPayName() {
		return lastPayName;
	}
	public void setLastPayName(String lastPayName) {
		this.lastPayName = lastPayName;
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
	public String getCompensatePayName() {
		return compensatePayName;
	}
	public void setCompensatePayName(String compensatePayName) {
		this.compensatePayName = compensatePayName;
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
	public String getCaseCode() {
		return caseCode;
	}
	public void setCaseCode(String caseCode) {
		this.caseCode = caseCode;
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
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getPartCode() {
		return partCode;
	}
	public void setPartCode(String partCode) {
		this.partCode = partCode;
	}
	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	public String getProcessInstanceId() {
		return processInstanceId;
	}
	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
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
	public Long getHousePkid() {
		return housePkid;
	}
	public void setHousePkid(Long housePkid) {
		this.housePkid = housePkid;
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
	public String getIsPerchaseReserachNeed() {
		return isPerchaseReserachNeed;
	}
	public void setIsPerchaseReserachNeed(String isPerchaseReserachNeed) {
		this.isPerchaseReserachNeed = isPerchaseReserachNeed;
	}
	public String getIsLoanClose() {
		return isLoanClose;
	}
	public void setIsLoanClose(String isLoanClose) {
		this.isLoanClose = isLoanClose;
	}
	
	
}
