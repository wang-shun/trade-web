package com.centaline.trans.common.service.impl;

import com.aist.common.quickQuery.service.CustomDictService;

public class QuickQuerySrvPartServiceImpl implements CustomDictService {

	@Override
	public String getValue(String[] key) {

		if (null == key || key.length != 2) {
			return "";
		}

		String in = key[0];
		String total = key[1];
		
		in = in.indexOf(".") > 0 ? in.substring(0, in.indexOf(".")) : in;
		total = total.indexOf(".") > 0 ? total.substring(0, total.indexOf(".")) : total;
		
		String val = in + "/" + total;
		return val;
	}

}
