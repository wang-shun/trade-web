package com.centaline.trans.remote.vo;

public class ReportVo {

	//询价编号
	private String code;
	
	//询价结果确认序号
	private String confirm_seq;
	
	//主贷人姓名
	private String proposer;
	
	//主贷人电话
	private String proposer_phone;
	
	//房产证是否上传
	private Integer hc_upload;
	
	//有效证件材料是否上传
	private Integer ic_upload;

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

	public String getProposer_phone() {
		return proposer_phone;
	}

	public void setProposer_phone(String proposer_phone) {
		this.proposer_phone = proposer_phone;
	}

	public Integer getHc_upload() {
		return hc_upload;
	}

	public void setHc_upload(Integer hc_upload) {
		this.hc_upload = hc_upload;
	}

	public Integer getIc_upload() {
		return ic_upload;
	}

	public void setIc_upload(Integer ic_upload) {
		this.ic_upload = ic_upload;
	}
	
}
