package com.centaline.trans.task.vo;

import java.util.Date;

public class OfflineEvaVO {

    private String finOrgName;				/*评估公司*/
    private String lastLoanBank;			/*最终贷款银行*/
    private String reportType;				/*报告类型*/
    private String propertyAddr;			/*物业地址*/
    private String loanerName;				/*信贷员姓名*/
    private String loanerPhone;				/*信贷员电话*/
    private Double expectedPrice;			/*期望评估价*/
    private Integer totalFloor;				/*层高*/
    private Integer locateFloor;			/*总层高*/
    private Double square;					/*面积*/
    private String comment;					/*备注*/
    private Long pkid;						/*报告类评估id*/
    private String status;					/*报告类评估当前状态*/
    private Date reportResponseTime;			/*反馈时间*/
    
	public String getFinOrgName() {
		return finOrgName;
	}
	public void setFinOrgName(String finOrgName) {
		this.finOrgName = finOrgName;
	}
	public String getLastLoanBank() {
		return lastLoanBank;
	}
	public void setLastLoanBank(String lastLoanBank) {
		this.lastLoanBank = lastLoanBank;
	}
	public String getReportType() {
		return reportType;
	}
	public void setReportType(String reportType) {
		this.reportType = reportType;
	}
	public String getPropertyAddr() {
		return propertyAddr;
	}
	public void setPropertyAddr(String propertyAddr) {
		this.propertyAddr = propertyAddr;
	}
	public String getLoanerName() {
		return loanerName;
	}
	public void setLoanerName(String loanerName) {
		this.loanerName = loanerName;
	}
	public String getLoanerPhone() {
		return loanerPhone;
	}
	public void setLoanerPhone(String loanerPhone) {
		this.loanerPhone = loanerPhone;
	}
	public Double getExpectedPrice() {
		return expectedPrice;
	}
	public void setExpectedPrice(Double expectedPrice) {
		this.expectedPrice = expectedPrice;
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
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public Long getPkid() {
		return pkid;
	}
	public void setPkid(Long pkid) {
		this.pkid = pkid;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Date getReportResponseTime() {
		return reportResponseTime;
	}
	public void setReportResponseTime(Date reportResponseTime) {
		this.reportResponseTime = reportResponseTime;
	}
    
    
}
