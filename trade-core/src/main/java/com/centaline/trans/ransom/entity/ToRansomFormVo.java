package com.centaline.trans.ransom.entity;

import java.math.BigDecimal;
import java.util.Date;

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
