package com.centaline.trans.signroom.vo;

public class ReservationVo {

	private String resType; // 预约类型 0:经纪人预约,1:管理员临时预约

	private String resPersonId; // 预约人id

	private String scheduleId; // 签约室安排id(表T_RM_ROOM_SCHEDULE的pkid)

	private String resId; // 预约单id(表T_RM_SIGN_ROOM的pkid)

	private String caseCode; // 案件编号

	private String propertyAddress; // 产证地址

	private Integer numberOfParticipants; // 参与人数

	private Integer numberOfPeople; // 房间容纳人数

	private Integer actNumberOfPeople; // 实际容纳人数

	private String transactItemCode; // 办理事项编号(签合同:contract,办贷款:doLoan,E+贷款:Eloan)

	private String specialRequirement; // 特殊需求

	private Long tradeCenterId; // 交易中心标识

	private String selDate; // 预约日期

	private String bespeakTime; // 预约时间段

	private String serviceSpecialist; // 服务顾问

	private String startDate; // 预约开始时间

	private String endDate; // 预约结束时间

	private String flag; // 如果normal,就是正常的预约;如果accept,那就是预约小一点的房间

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
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

	public String getServiceSpecialist() {
		return serviceSpecialist;
	}

	public void setServiceSpecialist(String serviceSpecialist) {
		this.serviceSpecialist = serviceSpecialist;
	}

	public Integer getActNumberOfPeople() {
		return actNumberOfPeople;
	}

	public void setActNumberOfPeople(Integer actNumberOfPeople) {
		this.actNumberOfPeople = actNumberOfPeople;
	}

	public Integer getNumberOfPeople() {
		return numberOfPeople;
	}

	public void setNumberOfPeople(Integer numberOfPeople) {
		this.numberOfPeople = numberOfPeople;
	}

	public Long getTradeCenterId() {
		return tradeCenterId;
	}

	public void setTradeCenterId(Long tradeCenterId) {
		this.tradeCenterId = tradeCenterId;
	}

	public String getSelDate() {
		return selDate;
	}

	public void setSelDate(String selDate) {
		this.selDate = selDate;
	}

	public String getBespeakTime() {
		return bespeakTime;
	}

	public void setBespeakTime(String bespeakTime) {
		this.bespeakTime = bespeakTime;
	}

	public String getResType() {
		return resType;
	}

	public void setResType(String resType) {
		this.resType = resType;
	}

	public String getResPersonId() {
		return resPersonId;
	}

	public void setResPersonId(String resPersonId) {
		this.resPersonId = resPersonId;
	}

	public String getScheduleId() {
		return scheduleId;
	}

	public void setScheduleId(String scheduleId) {
		this.scheduleId = scheduleId;
	}

	public String getResId() {
		return resId;
	}

	public void setResId(String resId) {
		this.resId = resId;
	}

	public String getCaseCode() {
		return caseCode;
	}

	public void setCaseCode(String caseCode) {
		this.caseCode = caseCode;
	}

	public String getPropertyAddress() {
		return propertyAddress;
	}

	public void setPropertyAddress(String propertyAddress) {
		this.propertyAddress = propertyAddress;
	}

	public Integer getNumberOfParticipants() {
		return numberOfParticipants;
	}

	public void setNumberOfParticipants(Integer numberOfParticipants) {
		this.numberOfParticipants = numberOfParticipants;
	}

	public String getTransactItemCode() {
		return transactItemCode;
	}

	public void setTransactItemCode(String transactItemCode) {
		this.transactItemCode = transactItemCode;
	}

	public String getSpecialRequirement() {
		return specialRequirement;
	}

	public void setSpecialRequirement(String specialRequirement) {
		this.specialRequirement = specialRequirement;
	}

}
