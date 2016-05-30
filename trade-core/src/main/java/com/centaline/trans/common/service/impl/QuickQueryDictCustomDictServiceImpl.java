package com.centaline.trans.common.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;

import com.aist.common.quickQuery.service.CustomDictService;
import com.aist.uam.basedata.remote.UamBasedataService;

public class QuickQueryDictCustomDictServiceImpl implements CustomDictService{

	@Autowired
	private UamBasedataService uamBasedataService;
	
	private String dictType;
	
	@Override
	@Cacheable(value="QuickQueryCustomDictServiceImpl",key="#root.target.getDictType()+'/'+#key")
	public String getValue(String key) {
		String value = uamBasedataService.getDictValue(dictType, key);
		return value;
	}

	public String getDictType() {
		return dictType;
	}

	public void setDictType(String dictType) {
		this.dictType = dictType;
	}

}
