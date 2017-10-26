package com.centaline.trans.common.enums;
/**
 * 赎楼环节顺序枚举
 * @author wbcaiyx
 *
 */
public enum RansomPartOrderEnum {
	
	APPLY(1,"APPLY"),
	
	SING(2,"SIGN"),
	/**
	 * 陪同还贷_一抵
	 */
	PAYLOAN_ONE(3,"PAYLOAN_ONE"),
	/**
	 * 陪同还贷_二抵
	 */
	PAYLOAN_TWO(3,"PAYLOAN_TWO"),
	/**
	 * 注销抵押_一抵
	 */
	CANCELDIYA_ONE(4,"CANCELDIYA_ONE"),
	/**
	 * 注销抵押_二抵
	 */
	CANCELDIYA_TWO(4,"CANCELDIYA_TWO"),
	/**
	 * 领取产证_一抵
	 */
	RECEIVE_ONE(5,"RECEIVE_ONE"),
	/**
	 * 领取产证_二抵
	 */
	RECEIVE_TWO(5,"RECEIVE_TWO"),
	/**
	 * 回款结清
	 */
	PAY_CLEAR(6,"PAYCLEAR");

	private Integer order;

	private String part;
	


	private RansomPartOrderEnum(Integer order, String part) {
		this.order = order;
		this.part = part;
	}



	public Integer getOrder() {
		return order;
	}



	public void setOrder(Integer order) {
		this.order = order;
	}



	public String getPart() {
		return part;
	}



	public void setPart(String part) {
		this.part = part;
	}

	
	public static Integer getOrder(String part) {
		for (RansomPartOrderEnum sde : RansomPartOrderEnum.values()) {
			if (part.equalsIgnoreCase(sde.getPart())) {
				return sde.getOrder();
			}
		}
		return null;
	}
	


}
