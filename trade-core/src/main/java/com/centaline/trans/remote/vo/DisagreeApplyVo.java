package com.centaline.trans.remote.vo;

public class DisagreeApplyVo {

	//案件编号
	private String case_id;
	
	//评估编号
	private String code;
	
	//申请后续业务编码
	private String apply_code;
	
	//期望价格
	private String expected_price;
	
	//备注
	private String remark;

	public String getCase_id() {
		return case_id;
	}

	public void setCase_id(String case_id) {
		this.case_id = case_id;
	}

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

	public String getExpected_price() {
		return expected_price;
	}

	public void setExpected_price(String expected_price) {
		this.expected_price = expected_price;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}
