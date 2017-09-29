package com.centaline.trans.common.enums;
/**
 * 赎楼环节枚举
 * @author wbcaiyx
 *
 */
public enum RansomPartEnum {
	  
	APPLY(         	"APPLY",			"申请",			"sctrans.T_RA_APPLY",		"APPLY_TIME"),
	SIGN(          	"SIGN",				"面签",			"sctrans.T_RA_INTERVIEW",	"INTERVIEW_TIME"),
	PAYLOAN_ONE(    "PAYLOAN_ONE",		"陪同还贷(一抵)",	"sctrans.T_RA_REPAY",		"REPAY_TIME"),
	PAYLOAN_TWO(    "PAYLOAN_TWO",		"陪同还贷(二抵)",	"sctrans.T_RA_REPAY",		"REPAY_TIME"),
	CANCELDIYA_ONE( "CANCELDIYA_ONE",	"注销抵押(一抵)",	"sctrans.T_RA_CANCEL",		"CANCEL_TIME"),
	CANCELDIYA_TWO( "CANCELDIYA_TWO",	"注销抵押(二抵)",	"sctrans.T_RA_CANCEL",		"CANCEL_TIME"),
	RECEIVE_ONE(    "RECEIVE_ONE",		"领取产证(一抵)",	"sctrans.T_RA_REDEEM",		"REDEEM_TIME"),
	RECEIVE_TWO(    "RECEIVE_TWO",		"领取产证(二抵)",	"sctrans.T_RA_REDEEM",		"REDEEM_TIME"),
	PAYCLEAR(      	"PAYCLEAR",			"回款结清",		"sctrans.T_RA_PAYMENT",		"PAYMENT_TIME");

	private String code;

	private String name;
	
	private String table;
	
	private String cloumn;

	private RansomPartEnum(String code, String name,String table,String cloumn) {
		this.code = code;
		this.name = name;
		this.table = table;
		this.cloumn = cloumn;
	}

	/**
	 * 根据code获取Name
	 * 
	 * @param code
	 * @return
	 */
	public static String getName(String code) {
		for (RansomPartEnum sde : RansomPartEnum.values()) {
			if (code.equalsIgnoreCase(sde.getCode())) {
				return sde.getName();
			}
		}
		return null;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}
	
	public String getTable() {
		return table;
	}
	
	public static String getTable(String code){
		for(RansomPartEnum rpe : RansomPartEnum.values()){
			if(rpe.getCode().equals(code)){
				return rpe.getTable();
			}
		}
		return null;
	}
	
	public String getCloumn(){
		return cloumn;
	}
	
	public static String getCloumn(String code){
		for(RansomPartEnum rpe : RansomPartEnum.values()){
			if(rpe.getCode().equals(code)){
				return rpe.getCloumn();
			}
		}
		return null;
	}
}
