package com.centaline.trans.remote.vo;

public class PricingConfirmVo {

	//询价编号
	private String code;
	
	//申请后续业务编号
	private String apply_code;
	
	//信贷员姓名
	private String lo_name;
	
	//信贷员电话
	private String lo_phone;
	
	//是否主选银行
	private String isMainLoanBank;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getApply_code() {
		return apply_code;
	}

	public void setApply_code(String apply_code) {
		this.apply_code = apply_code;
	}

	public String getLo_name() {
		return lo_name;
	}

	public void setLo_name(String lo_name) {
		this.lo_name = lo_name;
	}

	public String getLo_phone() {
		return lo_phone;
	}

	public void setLo_phone(String lo_phone) {
		this.lo_phone = lo_phone;
	}

	public String getIsMainLoanBank() {
		return isMainLoanBank;
	}

	public void setIsMainLoanBank(String isMainLoanBank) {
		this.isMainLoanBank = isMainLoanBank;
	}
	
}
