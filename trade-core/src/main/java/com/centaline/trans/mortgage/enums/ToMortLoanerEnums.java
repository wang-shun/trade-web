package com.centaline.trans.mortgage.enums;


/*
 * 信贷员接单状态
 * 注释的作用：在代码中即可看见具体的值
 * */
public enum ToMortLoanerEnums {
	/**
	 * 待接单
	 */
	LOANER_STATUS0("0","待接单"), 
	/**
	 * 已接单审核不通过
	 */
	LOANER_STATUS1("1","已接单审核不通过"),
	/**
	 * 已接单审核通过
	 */
	LOANER_STATUS2("2","已接单审核通过"),
	/**
	 * 取消派单
	 */
	LOANER_STATUS3("3","取消派单"), 
	/**
	 * 银行审批通过
	 */
	LOANER_STATUS4("4","银行审批通过"), 
	/**
	 * 银行驳回
	 */
	LOANER_STATUS5("5","银行驳回");
	
	private String code;
	private String value;
	
	ToMortLoanerEnums(String code,String value){
		this.code = code;
		this.value = value;
	}
	public String getCode(){
		return this.code;
	}
	public String getValue(){
		return this.value;
	}
}
