package com.centaline.trans.signroom.vo;

public class CanceReservionVo {
	
	private Long resId; //预约id
	private String resPersonId; // 预约人id
	private int isCanceConfirm;//是否与经纪人确认
	private String comment; // 取消原因跟进信息 
	private String signingCenter;//签约中心
	private String actStartTime;//预约起始时间
	private String actEndTime;//预约结束时间
	private String resDateTime;//预约日期
	
	public Long getResId() {
		return resId;
	}
	public void setResId(Long resId) {
		this.resId = resId;
	}
	public String getResPersonId() {
		return resPersonId;
	}
	public void setResPersonId(String resPersonId) {
		this.resPersonId = resPersonId;
	}
	public int getIsCanceConfirm() {
		return isCanceConfirm;
	}
	public void setIsCanceConfirm(int isCanceConfirm) {
		this.isCanceConfirm = isCanceConfirm;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getActStartTime() {
		return actStartTime;
	}
	public void setActStartTime(String actStartTime) {
		this.actStartTime = actStartTime;
	}
	public String getActEndTime() {
		return actEndTime;
	}
	public void setActEndTime(String actEndTime) {
		this.actEndTime = actEndTime;
	}
	public String getResDateTime() {
		return resDateTime;
	}
	public void setResDateTime(String resDateTime) {
		this.resDateTime = resDateTime;
	}
	public String getSigningCenter() {
		return signingCenter;
	}
	public void setSigningCenter(String signingCenter) {
		this.signingCenter = signingCenter;
	}
	
	

}
