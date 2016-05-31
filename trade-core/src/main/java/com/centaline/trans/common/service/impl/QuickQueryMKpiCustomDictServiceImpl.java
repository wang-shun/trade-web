package com.centaline.trans.common.service.impl;

import com.aist.common.quickQuery.service.CustomDictService;

public class QuickQueryMKpiCustomDictServiceImpl implements CustomDictService{

	@Override
	public String getValue(String key) {
		if("0".equals(key)) {
			return "否";
		} else if("1".equals(key)){
			return "是";
		} else {
			return null;
		}
	}
}
