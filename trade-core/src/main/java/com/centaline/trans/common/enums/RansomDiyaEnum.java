package com.centaline.trans.common.enums;
/**
 * 赎楼抵押环节枚举
 * @author wbcaiyx
 *
 */
public enum RansomDiyaEnum {
	
	SING("1","SIGN"),
	/**
	 * 陪同还贷_一抵
	 */
	PAYLOAN_ONE("1","PAYLOAN_ONE"),
	/**
	 * 陪同还贷_二抵
	 */
	PAYLOAN_TWO("2","PAYLOAN_TWO"),
	/**
	 * 注销抵押_一抵
	 */
	CANCELDIYA_ONE("1","CANCELDIYA_ONE"),
	/**
	 * 注销抵押_二抵
	 */
	CANCELDIYA_TWO("2","CANCELDIYA_TWO"),
	/**
	 * 领取产证_一抵
	 */
	RECEIVE_ONE("1","RECEIVE_ONE"),
	/**
	 * 领取产证_二抵
	 */
	RECEIVE_TWO("2","RECEIVE_TWO"),
	/**
	 * 回款结清
	 */
	PAY_CLEAR("1","PAYCLEAR");

	private String code;

	private String part;
	


	private RansomDiyaEnum(String code, String part) {
		this.code = code;
		this.part = part;
	}

	/**
	 * 根据code获取Part
	 * 
	 * @param code
	 * @return
	 */
	public static String getPart(String code) {
		for (RansomDiyaEnum sde : RansomDiyaEnum.values()) {
			if (code.equalsIgnoreCase(sde.getCode())) {
				return sde.getPart();
			}
		}
		return null;
	}
	
	public String getPart() {
		return part;
	}

	public void setPart(String part) {
		this.part = part;
	}

	public String getCode() {
		return code;
	}

}
