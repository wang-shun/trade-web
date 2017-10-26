package com.centaline.trans.ransom.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author wbwumf
 *
 */
public class ToRansomFormVo {
	
	private Long pkid;
	
	/**
	 * 赎楼单编号
	 */
	private String ransomCode;
	
	/**
	 * 案件编号
	 */
	private String caseCode;
	
	/**
	 * 尾款机构编号
	 */
	private String finOrgCode;
	
	/**
	 * 主贷人
	 */
	private String borrowerName;
	
	/**
	 * 电话
	 */
	private String borrowerTel;
	
	/**
	 * 受理时间
	 */
	private Date signTime;
	
	/**
	 * 计划申请时间
	 */
	private Date planTime;
	
	/**
	 * 尾款类型
	 */
	private String mortgageType;
	
	/**
	 * 抵押类型
	 */
	private String diyaType;
	
	/**
	 * 贷款金额
	 */
	private BigDecimal loanMoney;
	
	/**
	 * 剩余金额
	 */
	private BigDecimal restMoney;
	
	/**
	 * 借款总金额
	 */
	private BigDecimal borroMoney;
	
	private Date createTime;
	
	private String createUser;

	/**
	 * 备注
	 */
	private String remark;
	
	private Date updateTime;
	
	private String updateUser;
	//陪同还贷时间
	private Date repayTime;
	//合作机构
	private String comOrgName;
	//经办人
	private String handler;
	//放款金额
	private BigDecimal repayMoney;
	//利息
	private BigDecimal interest;
	//回款结清时间
	private Date paymentTime;
	//案件环节及状态
	private List<ToRansomCaseVo> casePartStatus;
	//案件计划时间
	private List<ToRansomPlanVo> planTimes;
	//当前任务环节及顺序
	private List<RansomPartOrderVo> partOrders;
	//抵押数量
	private Integer diyaNum;
	
	private String ransomStatus;
	//案件所有的环节
	private List<RansomPartOrderVo> allPartCodes;
	//二抵时，一抵的环节
	private List<RansomPartOrderVo> onePartCodes;
	//二抵时，二抵的环节
	private List<RansomPartOrderVo> twoPartCodes;
	
	public Long getPkid() {
		return pkid;
	}

	public void setPkid(Long pkid) {
		this.pkid = pkid;
	}

	public String getRansomCode() {
		return ransomCode;
	}

	public void setRansomCode(String ransomCode) {
		this.ransomCode = ransomCode;
	}

	public String getCaseCode() {
		return caseCode;
	}

	public void setCaseCode(String caseCode) {
		this.caseCode = caseCode;
	}

	public String getFinOrgCode() {
		return finOrgCode;
	}

	public void setFinOrgCode(String finOrgCode) {
		this.finOrgCode = finOrgCode;
	}

	public Date getSignTime() {
		return signTime;
	}

	public void setSignTime(Date signTime) {
		this.signTime = signTime;
	}

	public Date getPlanTime() {
		return planTime;
	}

	public void setPlanTime(Date planTime) {
		this.planTime = planTime;
	}

	public String getMortgageType() {
		return mortgageType;
	}

	public void setMortgageType(String mortgageType) {
		this.mortgageType = mortgageType;
	}

	public String getDiyaType() {
		return diyaType;
	}

	public void setDiyaType(String diyaType) {
		this.diyaType = diyaType;
	}

	public BigDecimal getLoanMoney() {
		return loanMoney;
	}

	public void setLoanMoney(BigDecimal loanMoney) {
		this.loanMoney = loanMoney;
	}

	public BigDecimal getRestMoney() {
		
		return restMoney;
	}

	public void setRestMoney(BigDecimal restMoney) {
		this.restMoney = restMoney;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	public String getBorrowerName() {
		return borrowerName;
	}

	public void setBorrowerName(String borrowerName) {
		this.borrowerName = borrowerName;
	}

	public BigDecimal getBorroMoney() {
		return borroMoney;
	}

	public void setBorroMoney(BigDecimal borroMoney) {
		this.borroMoney = borroMoney;
	}

	public String getBorrowerTel() {
		return borrowerTel;
	}

	public void setBorrowerTel(String borrowerTel) {
		this.borrowerTel = borrowerTel;
	}

	
	public Date getRepayTime() {
		return repayTime;
	}

	public void setRepayTime(Date repayTime) {
		this.repayTime = repayTime;
	}

	
	public String getComOrgName() {
		return comOrgName;
	}

	public void setComOrgName(String comOrgName) {
		this.comOrgName = comOrgName;
	}

	
	
	public String getHandler() {
		return handler;
	}

	public void setHandler(String handler) {
		this.handler = handler;
	}

	public BigDecimal getRepayMoney() {
		return repayMoney;
	}

	public void setRepayMoney(BigDecimal repayMoney) {
		this.repayMoney = repayMoney;
	}

	public BigDecimal getInterest() {
		return interest;
	}

	public void setInterest(BigDecimal interest) {
		this.interest = interest;
	}

	public Date getPaymentTime() {
		return paymentTime;
	}

	public void setPaymentTime(Date paymentTime) {
		this.paymentTime = paymentTime;
	}

	public List<ToRansomCaseVo> getCasePartStatus() {
		return casePartStatus;
	}

	public void setCasePartStatus(List<ToRansomCaseVo> casePartStatus) {
		this.casePartStatus = casePartStatus;
	}

	public List<ToRansomPlanVo> getPlanTimes() {
		return planTimes;
	}

	public void setPlanTimes(List<ToRansomPlanVo> planTimes) {
		this.planTimes = planTimes;
	}

	

	public List<RansomPartOrderVo> getPartOrders() {
		return partOrders;
	}

	public void setPartOrders(List<RansomPartOrderVo> partOrders) {
		this.partOrders = partOrders;
	}

	public Integer getDiyaNum() {
		return diyaNum;
	}

	public void setDiyaNum(Integer diyaNum) {
		this.diyaNum = diyaNum;
	}

	public String getRansomStatus() {
		return ransomStatus;
	}

	public void setRansomStatus(String ransomStatus) {
		this.ransomStatus = ransomStatus;
	}

	public List<RansomPartOrderVo> getAllPartCodes() {
		return allPartCodes;
	}

	public void setAllPartCodes(List<RansomPartOrderVo> allPartCodes) {
		this.allPartCodes = allPartCodes;
	}

	public List<RansomPartOrderVo> getOnePartCodes() {
		return onePartCodes;
	}

	public void setOnePartCodes(List<RansomPartOrderVo> onePartCodes) {
		this.onePartCodes = onePartCodes;
	}

	public List<RansomPartOrderVo> getTwoPartCodes() {
		return twoPartCodes;
	}

	public void setTwoPartCodes(List<RansomPartOrderVo> twoPartCodes) {
		this.twoPartCodes = twoPartCodes;
	}

	@Override
	public String toString() {
		return "ToRansomFormVo [pkid=" + pkid + ", ransomCode=" + ransomCode + ", caseCode=" + caseCode
				+ ", finOrgCode=" + finOrgCode + ", borrowerName=" + borrowerName + ", borrowerTel=" + borrowerTel
				+ ", signTime=" + signTime + ", planTime=" + planTime + ", mortgageType=" + mortgageType + ", diyaType="
				+ diyaType + ", loanMoney=" + loanMoney + ", restMoney=" + restMoney + ", borroMoney=" + borroMoney
				+ ", createTime=" + createTime + ", createUser=" + createUser + ", remark=" + remark + ", updateTime="
				+ updateTime + ", updateUser=" + updateUser + "]";
	}

}
