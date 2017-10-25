package com.centaline.trans.ransom.vo;

import java.util.Date;

/**
 * 赎楼完成环节VO
 * @author wbwumf
 *
 *2017年10月24日
 */
public class VRansomFinishTaskVo {

	private String caseCode;
	
	private String ransomCode;
	
	private String taskProperty;
	
	private String partCode;
	
	private Date applyTime;
	
	private String applyCode;
	
	private Date signTime;
	
	private String signCode;
	
	private Date payOneTime;
	
	private String payOneCode;
	
	private Date payTwoTime;
	
	private String payTwoCode;
	
	private Date cancelOneTime;
	
	private String cancelOneCode;

	private Date cancelTwoeTime;
	
	private String cancelTwoCode;
	
	private Date receiveOneTime;

	private String receiveOneCode;
	
	private Date receiveTwoTime;
	
	private String receiveTwoCode;
	
	private Date paymentTime;
	
	private String paymentCode;
	
	/**
	 * 执行人
	 */
	private String doUser;

	public String getCaseCode() {
		return caseCode;
	}

	public void setCaseCode(String caseCode) {
		this.caseCode = caseCode;
	}

	public String getRansomCode() {
		return ransomCode;
	}

	public void setRansomCode(String ransomCode) {
		this.ransomCode = ransomCode;
	}

	public String getTaskProperty() {
		return taskProperty;
	}

	public void setTaskProperty(String taskProperty) {
		this.taskProperty = taskProperty;
	}

	public String getPartCode() {
		return partCode;
	}

	public void setPartCode(String partCode) {
		this.partCode = partCode;
	}

	public Date getApplyTime() {
		return applyTime;
	}

	public void setApplyTime(Date applyTime) {
		this.applyTime = applyTime;
	}

	public String getApplyCode() {
		return applyCode;
	}

	public void setApplyCode(String applyCode) {
		this.applyCode = applyCode;
	}

	public Date getSignTime() {
		return signTime;
	}

	public void setSignTime(Date signTime) {
		this.signTime = signTime;
	}

	public String getSignCode() {
		return signCode;
	}

	public void setSignCode(String signCode) {
		this.signCode = signCode;
	}

	public Date getPayOneTime() {
		return payOneTime;
	}

	public void setPayOneTime(Date payOneTime) {
		this.payOneTime = payOneTime;
	}

	public String getPayOneCode() {
		return payOneCode;
	}

	public void setPayOneCode(String payOneCode) {
		this.payOneCode = payOneCode;
	}

	public Date getPayTwoTime() {
		return payTwoTime;
	}

	public void setPayTwoTime(Date payTwoTime) {
		this.payTwoTime = payTwoTime;
	}

	public String getPayTwoCode() {
		return payTwoCode;
	}

	public void setPayTwoCode(String payTwoCode) {
		this.payTwoCode = payTwoCode;
	}

	public Date getCancelOneTime() {
		return cancelOneTime;
	}

	public void setCancelOneTime(Date cancelOneTime) {
		this.cancelOneTime = cancelOneTime;
	}

	public String getCancelOneCode() {
		return cancelOneCode;
	}

	public void setCancelOneCode(String cancelOneCode) {
		this.cancelOneCode = cancelOneCode;
	}

	public Date getCancelTwoeTime() {
		return cancelTwoeTime;
	}

	public void setCancelTwoeTime(Date cancelTwoeTime) {
		this.cancelTwoeTime = cancelTwoeTime;
	}

	public String getCancelTwoCode() {
		return cancelTwoCode;
	}

	public void setCancelTwoCode(String cancelTwoCode) {
		this.cancelTwoCode = cancelTwoCode;
	}

	public Date getReceiveOneTime() {
		return receiveOneTime;
	}

	public void setReceiveOneTime(Date receiveOneTime) {
		this.receiveOneTime = receiveOneTime;
	}

	public String getReceiveOneCode() {
		return receiveOneCode;
	}

	public void setReceiveOneCode(String receiveOneCode) {
		this.receiveOneCode = receiveOneCode;
	}

	public Date getReceiveTwoTime() {
		return receiveTwoTime;
	}

	public void setReceiveTwoTime(Date receiveTwoTime) {
		this.receiveTwoTime = receiveTwoTime;
	}

	public String getReceiveTwoCode() {
		return receiveTwoCode;
	}

	public void setReceiveTwoCode(String receiveTwoCode) {
		this.receiveTwoCode = receiveTwoCode;
	}

	public Date getPaymentTime() {
		return paymentTime;
	}

	public void setPaymentTime(Date paymentTime) {
		this.paymentTime = paymentTime;
	}

	public String getPaymentCode() {
		return paymentCode;
	}

	public void setPaymentCode(String paymentCode) {
		this.paymentCode = paymentCode;
	}

	public String getDoUser() {
		return doUser;
	}

	public void setDoUser(String doUser) {
		this.doUser = doUser;
	}

	@Override
	public String toString() {
		return "VRansomFinishTaskVo [caseCode=" + caseCode + ", ransomCode=" + ransomCode + ", taskProperty="
				+ taskProperty + ", partCode=" + partCode + ", applyTime=" + applyTime + ", applyCode=" + applyCode
				+ ", signTime=" + signTime + ", signCode=" + signCode + ", payOneTime=" + payOneTime + ", payOneCode="
				+ payOneCode + ", payTwoTime=" + payTwoTime + ", payTwoCode=" + payTwoCode + ", cancelOneTime="
				+ cancelOneTime + ", cancelOneCode=" + cancelOneCode + ", cancelTwoeTime=" + cancelTwoeTime
				+ ", cancelTwoCode=" + cancelTwoCode + ", receiveOneTime=" + receiveOneTime + ", receiveOneCode="
				+ receiveOneCode + ", receiveTwoTime=" + receiveTwoTime + ", receiveTwoCode=" + receiveTwoCode
				+ ", paymentTime=" + paymentTime + ", paymentCode=" + paymentCode + ", doUser=" + doUser + "]";
	}

}
