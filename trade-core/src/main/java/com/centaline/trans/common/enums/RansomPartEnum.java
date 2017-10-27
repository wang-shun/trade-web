package com.centaline.trans.common.enums;

import java.util.ArrayList;
import java.util.List;

import com.centaline.trans.ransom.entity.RansomPartOrderVo;

/**
 * 赎楼环节枚举
 * @author wbcaiyx
 *
 */
public enum RansomPartEnum {
	  
	APPLY(         	"APPLY",			"申请",			"sctrans.T_RA_APPLY",		"APPLY_TIME"     ,1),
	SIGN(          	"SIGN",				"面签",			"sctrans.T_RA_INTERVIEW",	"INTERVIEW_TIME" ,2),
	PAYLOAN_ONE(    "PAYLOAN_ONE",		"陪同还贷(一抵)",	"sctrans.T_RA_REPAY",		"REPAY_TIME"     ,3),
	PAYLOAN_TWO(    "PAYLOAN_TWO",		"陪同还贷(二抵)",	"sctrans.T_RA_REPAY",		"REPAY_TIME"     ,3),
	CANCELDIYA_ONE( "CANCELDIYA_ONE",	"注销抵押(一抵)",	"sctrans.T_RA_CANCEL",		"CANCEL_TIME"    ,4),
	CANCELDIYA_TWO( "CANCELDIYA_TWO",	"注销抵押(二抵)",	"sctrans.T_RA_CANCEL",		"CANCEL_TIME"    ,4),
	RECEIVE_ONE(    "RECEIVE_ONE",		"领取产证(一抵)",	"sctrans.T_RA_REDEEM",		"REDEEM_TIME"    ,5),
	RECEIVE_TWO(    "RECEIVE_TWO",		"领取产证(二抵)",	"sctrans.T_RA_REDEEM",		"REDEEM_TIME"    ,5),
	PAYCLEAR(      	"PAYCLEAR",			"回款结清",		"sctrans.T_RA_PAYMENT",		"PAYMENT_TIME"   ,6);

	private String code;

	private String name;
	
	private String table;
	
	private String cloumn;
	
	private Integer order;

	private RansomPartEnum(String code, String name,String table,String cloumn,Integer order) {
		this.code = code;
		this.name = name;
		this.table = table;
		this.cloumn = cloumn;
		this.order = order;
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
	
	public Integer getOrder() {
		return order;
	}

	public void setOrder(Integer order) {
		this.order = order;
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
	
	public static List<RansomPartOrderVo> getDiyaOne(){
		List<RansomPartOrderVo> list = new ArrayList<RansomPartOrderVo>();
		list.add(new RansomPartOrderVo(RansomPartEnum.APPLY.getCode(), RansomPartEnum.APPLY.getName(),RansomPartEnum.APPLY.getOrder()));
		list.add(new RansomPartOrderVo(RansomPartEnum.SIGN.getCode(), RansomPartEnum.SIGN.getName(),RansomPartEnum.SIGN.getOrder()));
		list.add(new RansomPartOrderVo(RansomPartEnum.PAYLOAN_ONE.getCode(), RansomPartEnum.PAYLOAN_ONE.getName(),RansomPartEnum.PAYLOAN_ONE.getOrder()));
		list.add(new RansomPartOrderVo(RansomPartEnum.CANCELDIYA_ONE.getCode(), RansomPartEnum.CANCELDIYA_ONE.getName(),RansomPartEnum.CANCELDIYA_ONE.getOrder()));
		list.add(new RansomPartOrderVo(RansomPartEnum.RECEIVE_ONE.getCode(), RansomPartEnum.RECEIVE_ONE.getName(),RansomPartEnum.RECEIVE_ONE.getOrder()));
		list.add(new RansomPartOrderVo(RansomPartEnum.PAYCLEAR.getCode(), RansomPartEnum.PAYCLEAR.getName(),RansomPartEnum.PAYCLEAR.getOrder()));
		return list;
	}
	
	public static List<RansomPartOrderVo> getDiyaTwo(){
		List<RansomPartOrderVo> list = new ArrayList<RansomPartOrderVo>();
		for(RansomPartEnum rpe:RansomPartEnum.values()){
			list.add(new RansomPartOrderVo(rpe.getCode(), rpe.getName(),rpe.getOrder()));
		}
		return list;
	}
	
	public static List<RansomPartOrderVo> getDiyaTwoTwo(){
		List<RansomPartOrderVo> list = new ArrayList<RansomPartOrderVo>();
		list.add(new RansomPartOrderVo(RansomPartEnum.PAYLOAN_TWO.getCode(), RansomPartEnum.PAYLOAN_TWO.getName(),RansomPartEnum.PAYLOAN_TWO.getOrder()));
		list.add(new RansomPartOrderVo(RansomPartEnum.CANCELDIYA_TWO.getCode(), RansomPartEnum.CANCELDIYA_TWO.getName(),RansomPartEnum.CANCELDIYA_TWO.getOrder()));
		list.add(new RansomPartOrderVo(RansomPartEnum.RECEIVE_TWO.getCode(), RansomPartEnum.RECEIVE_TWO.getName(),RansomPartEnum.RECEIVE_TWO.getOrder()));
		return list;
	}
}
