package com.centaline.trans.transplan.vo;

import java.util.Date;

public class TransPlanVO {
	/*流程引擎*/
	private String taskId;
	private String processInstanceId;

	private String caseCode;
	private String partCode;
    /**审税天津废弃
	private Long pkidTr;
    private String partCodeTr;
    private Date estPartTimeTr;
    */
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
    
    /**缴税Pt*/
    private Long pkidPt;
    private String partCodePt;
    private Date estPartTimePt;
    
    /**商贷面签Cs*/
    private Long pkidCs;
    private String partCodeCs;
    private Date estPartTimeCs;
    
    /**商贷出评估报告Br*/
    private Long pkidBr;
    private String partCodeBr;
    private Date estPartTimeBr;
    
    /**商贷批贷完成Cc*/
    private Long pkidCc;
    private String partCodeCc;
    private Date estPartTimeCc;
    
    /**公积金贷款预约申请Pa*/
    private Long pkidPa;
    private String partCodePa;
    private Date estPartTimePa;
    
    /**公积金面签Pfs*/
	private Long pkidPfs;
	private String partCodePfs;
	private Date estPartTimePfs;
	
	 /**公积金批贷完成Pfc*/
	private Long pkidPfc;
	private String partCodePfc;
	private Date estPartTimePfc;
    
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
	/**天津废弃
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
	}*/
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
	
	public Long getPkidPt() {
		return pkidPt;
	}
	public void setPkidPt(Long pkidPt) {
		this.pkidPt = pkidPt;
	}
	public String getPartCodePt() {
		return partCodePt;
	}
	public void setPartCodePt(String partCodePt) {
		this.partCodePt = partCodePt;
	}
	public Date getEstPartTimePt() {
		return estPartTimePt;
	}
	public void setEstPartTimePt(Date estPartTimePt) {
		this.estPartTimePt = estPartTimePt;
	}
	public Long getPkidCs() {
		return pkidCs;
	}
	public void setPkidCs(Long pkidCs) {
		this.pkidCs = pkidCs;
	}
	public String getPartCodeCs() {
		return partCodeCs;
	}
	public void setPartCodeCs(String partCodeCs) {
		this.partCodeCs = partCodeCs;
	}
	public Date getEstPartTimeCs() {
		return estPartTimeCs;
	}
	public void setEstPartTimeCs(Date estPartTimeCs) {
		this.estPartTimeCs = estPartTimeCs;
	}
	public Long getPkidBr() {
		return pkidBr;
	}
	public void setPkidBr(Long pkidBr) {
		this.pkidBr = pkidBr;
	}
	public String getPartCodeBr() {
		return partCodeBr;
	}
	public void setPartCodeBr(String partCodeBr) {
		this.partCodeBr = partCodeBr;
	}
	public Date getEstPartTimeBr() {
		return estPartTimeBr;
	}
	public void setEstPartTimeBr(Date estPartTimeBr) {
		this.estPartTimeBr = estPartTimeBr;
	}
	public Long getPkidCc() {
		return pkidCc;
	}
	public void setPkidCc(Long pkidCc) {
		this.pkidCc = pkidCc;
	}
	public String getPartCodeCc() {
		return partCodeCc;
	}
	public void setPartCodeCc(String partCodeCc) {
		this.partCodeCc = partCodeCc;
	}
	public Date getEstPartTimeCc() {
		return estPartTimeCc;
	}
	public void setEstPartTimeCc(Date estPartTimeCc) {
		this.estPartTimeCc = estPartTimeCc;
	}
	public Long getPkidPa() {
		return pkidPa;
	}
	public void setPkidPa(Long pkidPa) {
		this.pkidPa = pkidPa;
	}
	public String getPartCodePa() {
		return partCodePa;
	}
	public void setPartCodePa(String partCodePa) {
		this.partCodePa = partCodePa;
	}
	public Date getEstPartTimePa() {
		return estPartTimePa;
	}
	public void setEstPartTimePa(Date estPartTimePa) {
		this.estPartTimePa = estPartTimePa;
	}
	public Long getPkidPfs() {
		return pkidPfs;
	}
	public void setPkidPfs(Long pkidPfs) {
		this.pkidPfs = pkidPfs;
	}
	public String getPartCodePfs() {
		return partCodePfs;
	}
	public void setPartCodePfs(String partCodePfs) {
		this.partCodePfs = partCodePfs;
	}
	public Date getEstPartTimePfs() {
		return estPartTimePfs;
	}
	public void setEstPartTimePfs(Date estPartTimePfs) {
		this.estPartTimePfs = estPartTimePfs;
	}
	public Long getPkidPfc() {
		return pkidPfc;
	}
	public void setPkidPfc(Long pkidPfc) {
		this.pkidPfc = pkidPfc;
	}
	public String getPartCodePfc() {
		return partCodePfc;
	}
	public void setPartCodePfc(String partCodePfc) {
		this.partCodePfc = partCodePfc;
	}
	public Date getEstPartTimePfc() {
		return estPartTimePfc;
	}
	public void setEstPartTimePfc(Date estPartTimePfc) {
		this.estPartTimePfc = estPartTimePfc;
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
