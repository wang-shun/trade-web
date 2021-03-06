package com.centaline.trans.common.entity;

public class TgGuestInfo {
    private Long pkid;
    //保存CCAI系统中客户唯一ID
    private String guestCode;

    private String caseCode;

    private String guestName;

    private String guestPhone;

    private String identifyType;

    private String identifyNumber;

    private String workUnit;

    private String transPosition;

    /********************全国交易金融系统 新增字段 begin******************************************/
    //CCAI 成交报告编号
    private String ccaiCode;
	/********************全国交易金融系统 新增字段 end******************************************/
    
	/*********************全国交易金融系统   弃用字段 begin***********************/
	private String ctmCode;
	/*********************全国交易金融系统   弃用字段 end***********************/

    public Long getPkid() {
        return pkid;
    }

    public void setPkid(Long pkid) {
        this.pkid = pkid;
    }

    public String getGuestCode() {
        return guestCode;
    }

    public void setGuestCode(String guestCode) {
        this.guestCode = guestCode;
    }

    public String getCaseCode() {
        return caseCode;
    }

    public void setCaseCode(String caseCode) {
        this.caseCode = caseCode == null ? null : caseCode.trim();
    }

    public String getGuestName() {
        return guestName;
    }

    public void setGuestName(String guestName) {
        this.guestName = guestName == null ? null : guestName.trim();
    }

    public String getGuestPhone() {
        return guestPhone;
    }

    public void setGuestPhone(String guestPhone) {
        this.guestPhone = guestPhone == null ? null : guestPhone.trim();
    }

    public String getIdentifyType() {
        return identifyType;
    }

    public void setIdentifyType(String identifyType) {
        this.identifyType = identifyType == null ? null : identifyType.trim();
    }

    public String getIdentifyNumber() {
        return identifyNumber;
    }

    public void setIdentifyNumber(String identifyNumber) {
        this.identifyNumber = identifyNumber == null ? null : identifyNumber.trim();
    }

    public String getWorkUnit() {
        return workUnit;
    }

    public void setWorkUnit(String workUnit) {
        this.workUnit = workUnit == null ? null : workUnit.trim();
    }

    public String getTransPosition() {
        return transPosition;
    }

    public void setTransPosition(String transPosition) {
        this.transPosition = transPosition == null ? null : transPosition.trim();
    }

    public String getCtmCode() {
        return ctmCode;
    }

    public void setCtmCode(String ctmCode) {
        this.ctmCode = ctmCode == null ? null : ctmCode.trim();
    }

	public String getCcaiCode() {
		return ccaiCode;
	}

	public void setCcaiCode(String ccaiCode) {
		this.ccaiCode = ccaiCode;
	}
}