package com.centaline.trans.evaPricing.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
/**
 * 询价VO
 * @author wbcaiyx
 *
 */
public class ToEvaPricingVo {
	
	private Long pkid;
	//案件编号
	private String caseCode;
	//询价类型
	private String evaType;
	//原购入价
	private BigDecimal originPrice;
	//贷款银行
	private String loanBank;
	//评估公司ID
	private String finorgId;
	//评估公司
	private String evaCompany;
	//单价
	private BigDecimal unitPrice;
	//询价值
	private BigDecimal totalPrice;
	//询价时间
	private Date evalTime;
	//期望评估价 
	private BigDecimal expectPrice;
	//询价发起人ID
	private String ariserId;
	//询价发起人
	private String ariserName;
	//发起人所属部门
	private String ariserOrgName;
	//产证地址
	private String propertyAddr;
	//创建时间
	private Date createTime;
	//完成时间
	private Date completeTime;
	//询价编号
	private String evaCode;
	//产证地址
	private String residenceName;
	//层高
	private Integer floor;
	//总楼高
	private Integer totalFloor;
	//面积
	private BigDecimal area;
	//竣工年限
	private String completeYear;
	//房屋类型
	private String propType;
	//室
	private Integer room;
	//厅
	private Integer hall;
	//卫
	private Integer toilet;
	//房龄
	private Integer houseAge;
	//ccai成交报告编号 
	private String ctmCode;
	//评估公司集合
	private List<String> finorgIds;
	//记录提交还是保存
	private Integer isSubmit;
	
	public String getCaseCode() {
		return caseCode;
	}

	public void setCaseCode(String caseCode) {
		this.caseCode = caseCode;
	}

	public String getEvaType() {
		return evaType;
	}

	public void setEvaType(String evaType) {
		this.evaType = evaType;
	}

	public BigDecimal getOriginPrice() {
		return originPrice;
	}

	public void setOriginPrice(BigDecimal originPrice) {
		this.originPrice = originPrice;
	}

	public String getLoanBank() {
		return loanBank;
	}

	public void setLoanBank(String loanBank) {
		this.loanBank = loanBank;
	}

	public String getFinorgId() {
		return finorgId;
	}

	public void setFinorgId(String finorgId) {
		this.finorgId = finorgId;
	}

	public String getEvaCompany() {
		return evaCompany;
	}

	public void setEvaCompany(String evaCompany) {
		this.evaCompany = evaCompany;
	}

	public BigDecimal getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(BigDecimal unitPrice) {
		this.unitPrice = unitPrice;
	}

	public BigDecimal getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(BigDecimal totalPrice) {
		this.totalPrice = totalPrice;
	}

	public Date getEvalTime() {
		return evalTime;
	}

	public void setEvalTime(Date evalTime) {
		this.evalTime = evalTime;
	}

	public BigDecimal getExpectPrice() {
		return expectPrice;
	}

	public void setExpectPrice(BigDecimal expectPrice) {
		this.expectPrice = expectPrice;
	}

	public String getAriserId() {
		return ariserId;
	}

	public void setAriserId(String ariserId) {
		this.ariserId = ariserId;
	}

	public String getAriserName() {
		return ariserName;
	}

	public void setAriserName(String ariserName) {
		this.ariserName = ariserName;
	}

	public String getPropertyAddr() {
		return propertyAddr;
	}

	public void setPropertyAddr(String propertyAddr) {
		this.propertyAddr = propertyAddr;
	}

	public String getEvaCode() {
		return evaCode;
	}

	public void setEvaCode(String evaCode) {
		this.evaCode = evaCode;
	}

	public String getResidenceName() {
		return residenceName;
	}

	public void setResidenceName(String residenceName) {
		this.residenceName = residenceName;
	}

	public Integer getFloor() {
		return floor;
	}

	public void setFloor(Integer floor) {
		this.floor = floor;
	}

	public Integer getTotalFloor() {
		return totalFloor;
	}

	public void setTotalFloor(Integer totalFloor) {
		this.totalFloor = totalFloor;
	}

	public BigDecimal getArea() {
		return area;
	}

	public void setArea(BigDecimal area) {
		this.area = area;
	}

	public String getCompleteYear() {
		return completeYear;
	}

	public void setCompleteYear(String completeYear) {
		this.completeYear = completeYear;
	}

	public String getPropType() {
		return propType;
	}

	public void setPropType(String propType) {
		this.propType = propType;
	}

	public Integer getRoom() {
		return room;
	}

	public void setRoom(Integer room) {
		this.room = room;
	}

	public Integer getHall() {
		return hall;
	}

	public void setHall(Integer hall) {
		this.hall = hall;
	}

	public Integer getToilet() {
		return toilet;
	}

	public void setToilet(Integer toilet) {
		this.toilet = toilet;
	}

	public Integer getHouseAge() {
		return houseAge;
	}

	public void setHouseAge(Integer houseAge) {
		this.houseAge = houseAge;
	}

	public String getAriserOrgName() {
		return ariserOrgName;
	}

	public void setAriserOrgName(String ariserOrgName) {
		this.ariserOrgName = ariserOrgName;
	}

	public String getCtmCode() {
		return ctmCode;
	}

	public void setCtmCode(String ctmCode) {
		this.ctmCode = ctmCode;
	}

	public List<String> getFinorgIds() {
		return finorgIds;
	}

	public void setFinorgIds(List<String> finorgIds) {
		this.finorgIds = finorgIds;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getCompleteTime() {
		return completeTime;
	}

	public void setCompleteTime(Date completeTime) {
		this.completeTime = completeTime;
	}

	public Long getPkid() {
		return pkid;
	}

	public void setPkid(Long pkid) {
		this.pkid = pkid;
	}

	public Integer getIsSubmit() {
		return isSubmit;
	}

	public void setIsSubmit(Integer isSubmit) {
		this.isSubmit = isSubmit;
	}
	
}
