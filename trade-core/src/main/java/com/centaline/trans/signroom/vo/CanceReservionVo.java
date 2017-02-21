package com.centaline.trans.signroom.vo;

public class CanceReservionVo {
	
	private Long resId; //预约id
	private String resPersonId; // 预约人id
	private int isCanceConfirm;//是否与经纪人确认
	private String comment; // 取消原因跟进信息 
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
	
	

}
