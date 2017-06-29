package com.centaline.trans.performance.entity;

import java.math.BigDecimal;
import java.util.Date;

public class ReceivablePerf {
	private Long pkid;
	/**
	 * 业绩标准
	 */
	private Long perfId;
	/**
	 * 业绩编号
	 *
	 */
	private String sharesCode;
	/**
	 * 业绩分拆比例
	 */
	private BigDecimal sharesRate;
	/**
	 * 案件编号
	 */
	private String caseCode;
	/**
	 * 业务编号
	 */
	private String bizCode;
	/**
	 * 业务PKID
	 */
	private String bizPkid;
	/**
	 * 业绩科目
	 */
	private String subject;
	/**
	 * 
	 * 角色类型
	 */
	private String roleType;
	/**
	 * 业绩基数
	 * 
	 * 
	 */
	private BigDecimal shareBase;
	
	/**
	 * 业绩金额
	 * 
	 * 
	 */
	private BigDecimal shareAmount;
	/**
	 * 业绩人
	 * 
	 */
	private String userId;
	/**
	 * 业绩人组织
	 */
	private String teamId;
	/**
	 * 助理
	 */
	private String assistant;
	/**
	 * 主管
	 */
	private String manager;
	/**
	 * 高级主管
	 */
	private String seniorManager;
	/**
	 * 贵宾服务部
	 */
	private String district;
	/**
	 * 总监
	 */
	private String director;
	/**
	 * 公司
	 */
	private String company;
	/**
	 * 总经理
	 */
	private String gerneralManager;
	/**
	 * 业绩时间
	 */
	private Date shareTime;
	/**
	 * 贷款放款时间
	 */
	private Date loanReleaseTime;
	/**
	 * 贷款审批时间
	 */
	private Date loanApprovalTime;
	/**
	 * 贷款利率
	 */
	private BigDecimal loanRate;
	/**
	 * 成本利率
	 */
	private BigDecimal costRate;
	/**
	 * 业绩状态
	 */
	private String status;
	/**
	 * 变更原因
	 */
	private String changeReason;
	/**
	 * 备注
	 */
	private String remark;
	/**
	 * 创建人
	 */
	private String createBy;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 更新人
	 */
	private String updateBy;
	/**
	 * 更新时间
	 */
	private Date updateTime;
	/**
	 * 是否删除
	 */
	private Integer isDeleted;

	public Long getPkid() {
		return pkid;
	}

	public void setPkid(Long pkid) {
		this.pkid = pkid;
	}

	public Long getPerfId() {
		return perfId;
	}

	public void setPerfId(Long perfId) {
		this.perfId = perfId;
	}

	public String getSharesCode() {
		return sharesCode;
	}

	public void setSharesCode(String sharesCode) {
		this.sharesCode = sharesCode == null ? null : sharesCode.trim();
	}

	public BigDecimal getSharesRate() {
		return sharesRate;
	}

	public void setSharesRate(BigDecimal sharesRate) {
		this.sharesRate = sharesRate;
	}

	public String getCaseCode() {
		return caseCode;
	}

	public void setCaseCode(String caseCode) {
		this.caseCode = caseCode == null ? null : caseCode.trim();
	}

	public String getBizCode() {
		return bizCode;
	}

	public void setBizCode(String bizCode) {
		this.bizCode = bizCode == null ? null : bizCode.trim();
	}

	public String getBizPkid() {
		return bizPkid;
	}

	public void setBizPkid(String bizPkid) {
		this.bizPkid = bizPkid == null ? null : bizPkid.trim();
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject == null ? null : subject.trim();
	}

	public String getRoleType() {
		return roleType;
	}

	public void setRoleType(String roleType) {
		this.roleType = roleType == null ? null : roleType.trim();
	}

	public BigDecimal getShareBase() {
		return shareBase;
	}

	public void setShareBase(BigDecimal shareBase) {
		this.shareBase = shareBase;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId == null ? null : userId.trim();
	}

	public String getTeamId() {
		return teamId;
	}

	public void setTeamId(String teamId) {
		this.teamId = teamId == null ? null : teamId.trim();
	}

	public String getAssistant() {
		return assistant;
	}

	public void setAssistant(String assistant) {
		this.assistant = assistant == null ? null : assistant.trim();
	}

	public String getManager() {
		return manager;
	}

	public void setManager(String manager) {
		this.manager = manager == null ? null : manager.trim();
	}

	public String getSeniorManager() {
		return seniorManager;
	}

	public void setSeniorManager(String seniorManager) {
		this.seniorManager = seniorManager == null ? null : seniorManager.trim();
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district == null ? null : district.trim();
	}

	public String getDirector() {
		return director;
	}

	public void setDirector(String director) {
		this.director = director == null ? null : director.trim();
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company == null ? null : company.trim();
	}

	public String getGerneralManager() {
		return gerneralManager;
	}

	public void setGerneralManager(String gerneralManager) {
		this.gerneralManager = gerneralManager == null ? null : gerneralManager.trim();
	}

	public Date getShareTime() {
		return shareTime;
	}

	public void setShareTime(Date shareTime) {
		this.shareTime = shareTime;
	}

	public Date getLoanReleaseTime() {
		return loanReleaseTime;
	}

	public void setLoanReleaseTime(Date loanReleaseTime) {
		this.loanReleaseTime = loanReleaseTime;
	}

	public Date getLoanApprovalTime() {
		return loanApprovalTime;
	}

	public void setLoanApprovalTime(Date loanApprovalTime) {
		this.loanApprovalTime = loanApprovalTime;
	}

	public BigDecimal getLoanRate() {
		return loanRate;
	}

	public void setLoanRate(BigDecimal loanRate) {
		this.loanRate = loanRate;
	}

	public BigDecimal getCostRate() {
		return costRate;
	}

	public void setCostRate(BigDecimal costRate) {
		this.costRate = costRate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status == null ? null : status.trim();
	}

	public String getChangeReason() {
		return changeReason;
	}

	public void setChangeReason(String changeReason) {
		this.changeReason = changeReason == null ? null : changeReason.trim();
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark == null ? null : remark.trim();
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy == null ? null : createBy.trim();
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy == null ? null : updateBy.trim();
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Integer getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Integer isDeleted) {
		this.isDeleted = isDeleted;
	}

	public BigDecimal getShareAmount() {
		return shareAmount;
	}

	public void setShareAmount(BigDecimal shareAmount) {
		this.shareAmount = shareAmount;
	}
}