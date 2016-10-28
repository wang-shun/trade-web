package com.centaline.trans.spv.vo;

public class SpvDeRecVo {

	private String caseCode;
	
	private Long condId;
	
	private String address;
	
	private String spvCode;
	
	private Double deAmount;
	
	private String deCondition;
	
	private String remark;

	public String getCaseCode() {
		return caseCode;
	}

	public void setCaseCode(String caseCode) {
		this.caseCode = caseCode;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getSpvCode() {
		return spvCode;
	}

	public void setSpvCode(String spvCode) {
		this.spvCode = spvCode;
	}

	public Double getDeAmount() {
		return deAmount;
	}

	public void setDeAmount(Double deAmount) {
		this.deAmount = deAmount;
	}

	public String getDeCondition() {
		return deCondition;
	}

	public void setDeCondition(String deCondition) {
		this.deCondition = deCondition;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Long getCondId() {
		return condId;
	}

	public void setCondId(Long condId) {
		this.condId = condId;
	}

}
