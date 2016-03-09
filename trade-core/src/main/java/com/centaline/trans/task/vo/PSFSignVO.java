package com.centaline.trans.task.vo;

import java.math.BigDecimal;
import java.util.Date;

public class PSFSignVO {
	/*流程引擎*/
	private String taskId;
	private String processInstanceId;
	
	private String partCode;
	
	/*按揭贷款表*/
	private Long pkid;					
    private String caseCode;				/*交易单编号*/
    private String mortType;				/*按揭贷款类型*/
    private BigDecimal mortTotalAmount;		/*贷款总额*/
    private String finOrgCode;				/*贷款机构   xx银行*/
    private String custCode;				/*客户编号id*/
    private BigDecimal prfAmount;			/*公积金金额*/
    private Integer prfYear;				/*公积金年限*/
    private Date signDate;					/*签约时间*/
    private String remark;					/*备注*/
    /*客户表*/
    private Long guestPkid;
    private String workUnit;				/*工作单位*/
    
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
	public String getMortType() {
		return mortType;
	}
	public void setMortType(String mortType) {
		this.mortType = mortType;
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
		this.finOrgCode = finOrgCode;
	}
	public String getCustCode() {
		return custCode;
	}
	public void setCustCode(String custCode) {
		this.custCode = custCode;
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
	public Date getSignDate() {
		return signDate;
	}
	public void setSignDate(Date signDate) {
		this.signDate = signDate;
	}
	public Long getGuestPkid() {
		return guestPkid;
	}
	public void setGuestPkid(Long guestPkid) {
		this.guestPkid = guestPkid;
	}
	public String getWorkUnit() {
		return workUnit;
	}
	public void setWorkUnit(String workUnit) {
		this.workUnit = workUnit;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
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

}
