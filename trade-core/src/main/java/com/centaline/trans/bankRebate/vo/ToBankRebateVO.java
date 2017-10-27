package com.centaline.trans.bankRebate.vo;



import com.aist.common.utils.excel.annotation.ExcelField;

public class ToBankRebateVO {
	
	@ExcelField(title = "案件编号")
    private String caseCode;

    @ExcelField(title = "银行")
    private String bankName;

    @ExcelField(title = "返利金额")
    private String rebateMoney;
    
    @ExcelField(title = "权证返利金额")
    private String rebateWarrant;
    
    @ExcelField(title = "业务返利金额")
    private String rebateBusiness;

	public String getCaseCode() {
		return caseCode;
	}

	public void setCaseCode(String caseCode) {
		this.caseCode = caseCode;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getRebateMoney() {
		return rebateMoney;
	}

	public void setRebateMoney(String rebateMoney) {
		this.rebateMoney = rebateMoney;
	}

	public String getRebateWarrant() {
		return rebateWarrant;
	}

	public void setRebateWarrant(String rebateWarrant) {
		this.rebateWarrant = rebateWarrant;
	}

	public String getRebateBusiness() {
		return rebateBusiness;
	}

	public void setRebateBusiness(String rebateBusiness) {
		this.rebateBusiness = rebateBusiness;
	}
}
