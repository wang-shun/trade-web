package com.centaline.trans.common.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;

import com.aist.common.quickQuery.service.CustomDictService;
import com.aist.uam.basedata.remote.UamBasedataService;

public class QuickQuerySrvsDictCustomDictServiceImpl implements CustomDictService{

	@Autowired
	private UamBasedataService uamBasedataService;
	
	private String dictType;
	
	@Override
	@Cacheable(value="QuickQuerySrvsDictCustomDictServiceImpl",key="#root.target.getDictType()+'/'+#key")
	public String getValue(String key) {
		if(StringUtils.isEmpty(key))return null;
		String[] strs = key.split("/");
		StringBuffer reStrs = new StringBuffer();
		for(String s:strs){
			String value = uamBasedataService.getDictValue(dictType, s);
			reStrs.append(value);
			reStrs.append("/");
		} 
		reStrs.deleteCharAt(reStrs.length()-1); 
		return reStrs.toString();
	}

	public String getDictType() {
		return dictType;
	}

	public void setDictType(String dictType) {
		this.dictType = dictType;
	}

}
