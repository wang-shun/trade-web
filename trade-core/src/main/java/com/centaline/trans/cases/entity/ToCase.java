package com.centaline.trans.cases.entity;

import java.util.Date;

public class ToCase {
    private Long pkid;

    private String caseCode;

    private Date createTime;

    private String status;

    private String caseProperty;

    private String leadingProcessId;

    private String time;//TODO 不清楚作用，不是T_TO_CASE表中字段 by:yinchao
    
    private String orgId;
    
    private String loanReq;
    
    private Date closeTime;
    
	private String caseOrigin;
	
	private String createBy;

	private String updateBy;
	
	private Date updateTime;
	//交易助理ID
	private String assistantId;
	//交易助理名字
	private String assistantName;
	//交易助理电话
	private String assistantPhone;
	
	/********************全国交易金融系统 新增字段 begin******************************************/
	//CCAI 成交报告ID
    private String ccaiId;
    //CCAI 成交报告编号
    private String ccaiCode;
    //案件对应城市信息 国标行政区划 编码前6位
    private String city;
	/********************全国交易金融系统 新增字段 end******************************************/
	//查询条件使用
    private String startDate;
    //查询条件使用
    private String endDate;
	/*********************全国交易金融系统   弃用字段 begin***********************/
	private String ctmCode;
	 //贵宾服务部ID
    private String districtId;
	/*********************全国交易金融系统   弃用字段 end***********************/
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
		this.caseCode = caseCode;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getCaseProperty() {
		return caseProperty;
	}
	public void setCaseProperty(String caseProperty) {
		this.caseProperty = caseProperty;
	}
	public String getLeadingProcessId() {
		return leadingProcessId;
	}
	public void setLeadingProcessId(String leadingProcessId) {
		this.leadingProcessId = leadingProcessId;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	public String getLoanReq() {
		return loanReq;
	}
	public void setLoanReq(String loanReq) {
		this.loanReq = loanReq;
	}
	public Date getCloseTime() {
		return closeTime;
	}
	public void setCloseTime(Date closeTime) {
		this.closeTime = closeTime;
	}
	public String getCaseOrigin() {
		return caseOrigin;
	}
	public void setCaseOrigin(String caseOrigin) {
		this.caseOrigin = caseOrigin;
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
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public String getAssistantId() {
		return assistantId;
	}
	public void setAssistantId(String assistantId) {
		this.assistantId = assistantId;
	}
	public String getAssistantName() {
		return assistantName;
	}
	public void setAssistantName(String assistantName) {
		this.assistantName = assistantName;
	}
	public String getAssistantPhone() {
		return assistantPhone;
	}
	public void setAssistantPhone(String assistantPhone) {
		this.assistantPhone = assistantPhone;
	}
	public String getCcaiId() {
		return ccaiId;
	}
	public void setCcaiId(String ccaiId) {
		this.ccaiId = ccaiId;
	}
	public String getCcaiCode() {
		return ccaiCode;
	}
	public void setCcaiCode(String ccaiCode) {
		this.ccaiCode = ccaiCode;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getCtmCode() {
		return ctmCode;
	}
	public void setCtmCode(String ctmCode) {
		this.ctmCode = ctmCode;
	}
	public String getDistrictId() {
		return districtId;
	}
	public void setDistrictId(String districtId) {
		this.districtId = districtId;
	}
}