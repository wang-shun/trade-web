package com.centaline.trans.remote.vo;

public class InquiryreportVo {

	//评估编号
	private String code;
	
	//询价结果确认序号
	private String confirm_seq;
	
	//主贷人姓名
	private String proposer;
	
	//房产证材料是否上传
	private Integer hc_upload;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getConfirm_seq() {
		return confirm_seq;
	}

	public void setConfirm_seq(String confirm_seq) {
		this.confirm_seq = confirm_seq;
	}

	public String getProposer() {
		return proposer;
	}

	public void setProposer(String proposer) {
		this.proposer = proposer;
	}

	public Integer getHc_upload() {
		return hc_upload;
	}

	public void setHc_upload(Integer hc_upload) {
		this.hc_upload = hc_upload;
	}
	
}
