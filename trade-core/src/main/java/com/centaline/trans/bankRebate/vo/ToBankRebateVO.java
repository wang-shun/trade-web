package com.centaline.trans.bankRebate.vo;



import com.aist.common.utils.excel.annotation.ExcelField;

public class ToBankRebateVO {
	
	@ExcelField(title = "成交编号")
    private String ccaiCode;

    @ExcelField(title = "银行")
    private String bankName;

    @ExcelField(title = "返利金额")
    private String rebateMoney;
    
    @ExcelField(title = "权证返利金额")
    private String rebateWarrant;
    
    @ExcelField(title = "业务返利金额")
    private String rebateBusiness;
   
    public String getCcaiCode() {
		return ccaiCode;
	}

	public void setCcaiCode(String ccaiCode) {
		this.ccaiCode = ccaiCode;
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
