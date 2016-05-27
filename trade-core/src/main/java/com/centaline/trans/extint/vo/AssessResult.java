package com.centaline.trans.extint.vo;

/**
 * 询价结果
 * @author Administrator
 *
 */
public class AssessResult {

	//评估编号
	private String code;
	
	//单价
	private Double unit_price;
	
	//总价
	private Double total_price;
	
	//评估状态编码
	private Integer ass_status;
	
	//评估公司名称
	private String ass_company;
	
	//申请后续业务编号
	private String apply_code;
	
	//询价结果编码
	private String confirm_code;
	
	//案件编号
	private String case_id;
	
	//询价发起人
	private String un;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Double getUnit_price() {
		return unit_price;
	}

	public void setUnit_price(Double unit_price) {
		this.unit_price = unit_price;
	}

	public Double getTotal_price() {
		return total_price;
	}

	public void setTotal_price(Double total_price) {
		this.total_price = total_price;
	}

	public Integer getAss_status() {
		return ass_status;
	}

	public void setAss_status(Integer ass_status) {
		this.ass_status = ass_status;
	}

	public String getAss_company() {
		return ass_company;
	}

	public void setAss_company(String ass_company) {
		this.ass_company = ass_company;
	}

	public String getApply_code() {
		return apply_code;
	}

	public void setApply_code(String apply_code) {
		this.apply_code = apply_code;
	}

	public String getConfirm_code() {
		return confirm_code;
	}

	public void setConfirm_code(String confirm_code) {
		this.confirm_code = confirm_code;
	}

	public String getCase_id() {
		return case_id;
	}

	public void setCase_id(String case_id) {
		this.case_id = case_id;
	}

	public String getUn() {
		return un;
	}

	public void setUn(String un) {
		this.un = un;
	}

}
