package com.centaline.trans.common.service.impl;

import org.apache.commons.lang3.StringUtils;

import com.aist.common.quickQuery.service.CustomDictService;

public class QuickQueryFinServiceImpl implements CustomDictService {

	@Override
	public String getValue(String[] key) {

		if (null == key || key.length != 2) {
			return "";
		}

		String finOrder = key[0];
		String finOrderRoll = key[1];
		if(StringUtils.isBlank(finOrder) && StringUtils.isBlank(finOrderRoll)){
			return "";
		}
		String val = finOrder + "+" + finOrderRoll;
		return val;
	}

}
