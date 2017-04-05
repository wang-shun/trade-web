package com.centaline.trans.common.service;

public interface GenerateBusinessCodeService {
	
	public static final String ITEM_CODE = "ITEM_CODE";
	
	/****
	 *  创建物品管理的物品编号
	 * 
	 *  @return String
	 */
	String createItemCode();
	
	
	String createCodeByMonthPattern(String code);

}
