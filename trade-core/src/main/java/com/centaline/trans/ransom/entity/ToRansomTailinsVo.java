package com.centaline.trans.ransom.entity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 尾款信息
 * @author wbwumf
 *
 *2017年10月10日
 */
public class ToRansomTailinsVo {
	
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
	
	private Date createTime;
	
	private String createUser;

	/**
	 * 备注
	 */
	private String remark;
	
	private Date updateTime;
	
	private String updateUser;

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

	@Override
	public String toString() {
		return "AddRansomForm [pkid=" + pkid + ", ransomCode=" + ransomCode + ", caseCode=" + caseCode + ", finOrgCode="
				+ finOrgCode + ", signTime=" + signTime + ", planTime=" + planTime + ", mortgageType=" + mortgageType
				+ ", diyaType=" + diyaType + ", loanMoney=" + loanMoney + ", restMoney=" + restMoney + ", createTime="
				+ createTime + ", createUser=" + createUser + ", remark=" + remark + ", updateTime=" + updateTime
				+ ", updateUser=" + updateUser + "]";
	}


}
