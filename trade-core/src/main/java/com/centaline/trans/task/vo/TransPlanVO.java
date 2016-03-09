package com.centaline.trans.task.vo;

import java.util.Date;

public class TransPlanVO {
	/*流程引擎*/
	private String taskId;
	private String processInstanceId;

	private String caseCode;
	private String partCode;
    /**审税*/
	private Long pkidTr;
    private String partCodeTr;
    private Date estPartTimeTr;
    /**还贷*/
	private Long pkidHd;
    private String partCodeHd;
    private Date estPartTimeHd;
    /**过户*/
	private Long pkidGh;
    private String partCodeGh;
    private Date estPartTimeGh;
    /**领证*/
	private Long pkidLz;
    private String partCodeLz;
    private Date estPartTimeLz;
    
    /**放款*/
	private Long pkidFk;
    private String partCodeFk;
    private Date estPartTimeFk;
    
    /**超期未处理天数*/
	private Long day;
    /**超期未处理描述*/
	private String dayDescription;
	/**地址**/
	private String propertyAddr;
	/**执行人ID*/
    private String userId;
    /**执行人姓名*/
    private String userName;
    /**estPartTime */
    private Date estPartTime;
    private String partCodeStr;
    
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
	public String getCaseCode() {
		return caseCode;
	}
	public void setCaseCode(String caseCode) {
		this.caseCode = caseCode;
	}
	public String getPartCode() {
		return partCode;
	}
	public void setPartCode(String partCode) {
		this.partCode = partCode;
	}
	public Long getPkidTr() {
		return pkidTr;
	}
	public void setPkidTr(Long pkidTr) {
		this.pkidTr = pkidTr;
	}
	public String getPartCodeTr() {
		return partCodeTr;
	}
	public void setPartCodeTr(String partCodeTr) {
		this.partCodeTr = partCodeTr;
	}
	public Date getEstPartTimeTr() {
		return estPartTimeTr;
	}
	public void setEstPartTimeTr(Date estPartTimeTr) {
		this.estPartTimeTr = estPartTimeTr;
	}
	public Long getPkidHd() {
		return pkidHd;
	}
	public void setPkidHd(Long pkidHd) {
		this.pkidHd = pkidHd;
	}
	public String getPartCodeHd() {
		return partCodeHd;
	}
	public void setPartCodeHd(String partCodeHd) {
		this.partCodeHd = partCodeHd;
	}
	public Long getPkidGh() {
		return pkidGh;
	}
	public void setPkidGh(Long pkidGh) {
		this.pkidGh = pkidGh;
	}
	public String getPartCodeGh() {
		return partCodeGh;
	}
	public void setPartCodeGh(String partCodeGh) {
		this.partCodeGh = partCodeGh;
	}
	public Date getEstPartTimeGh() {
		return estPartTimeGh;
	}
	public void setEstPartTimeGh(Date estPartTimeGh) {
		this.estPartTimeGh = estPartTimeGh;
	}
	public Long getPkidLz() {
		return pkidLz;
	}
	public void setPkidLz(Long pkidLz) {
		this.pkidLz = pkidLz;
	}
	public String getPartCodeLz() {
		return partCodeLz;
	}
	public void setPartCodeLz(String partCodeLz) {
		this.partCodeLz = partCodeLz;
	}
	public Date getEstPartTimeLz() {
		return estPartTimeLz;
	}
	public void setEstPartTimeLz(Date estPartTimeLz) {
		this.estPartTimeLz = estPartTimeLz;
	}
	public Long getPkidFk() {
		return pkidFk;
	}
	public void setPkidFk(Long pkidFk) {
		this.pkidFk = pkidFk;
	}
	public String getPartCodeFk() {
		return partCodeFk;
	}
	public void setPartCodeFk(String partCodeFk) {
		this.partCodeFk = partCodeFk;
	}
	public Date getEstPartTimeFk() {
		return estPartTimeFk;
	}
	public void setEstPartTimeFk(Date estPartTimeFk) {
		this.estPartTimeFk = estPartTimeFk;
	}
	public Date getEstPartTimeHd() {
		return estPartTimeHd;
	}
	public void setEstPartTimeHd(Date estPartTimeHd) {
		this.estPartTimeHd = estPartTimeHd;
	}
	public Long getDay() {
		return day;
	}
	public void setDay(Long day) {
		this.day = day;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Date getEstPartTime() {
		return estPartTime;
	}
	public void setEstPartTime(Date estPartTime) {
		this.estPartTime = estPartTime;
	}
	public String getDayDescription() {
		return dayDescription;
	}
	public void setDayDescription(String dayDescription) {
		this.dayDescription = dayDescription;
	}
	public String getPropertyAddr() {
		return propertyAddr;
	}
	public void setPropertyAddr(String propertyAddr) {
		this.propertyAddr = propertyAddr;
	}
	public String getPartCodeStr() {
		return partCodeStr;
	}
	public void setPartCodeStr(String partCodeStr) {
		this.partCodeStr = partCodeStr;
	}
	
}
