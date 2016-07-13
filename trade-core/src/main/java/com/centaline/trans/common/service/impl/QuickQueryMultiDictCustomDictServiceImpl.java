package com.centaline.trans.common.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;

import com.aist.common.quickQuery.service.CustomDictService;
import com.aist.uam.basedata.remote.UamBasedataService;
/**
 * 查询多个字典名称，名称直接以分号隔开
 * 
 * */
public class QuickQueryMultiDictCustomDictServiceImpl implements CustomDictService{
	@Autowired
	private UamBasedataService uamBasedataService;
	/*字典分类代码*/
	private String dictType;
	/*多个名称之间的分割符*/
	private final String SEPARATOR = "; ";
	@Override
	@Cacheable(value="QuickQueryCustomMultiDictServiceImpl",key="#root.target.getDictType()+'/'+#keys")
	public String getValue(String keys) {
		if(StringUtils.isBlank(keys)){
			return "";
		}
		StringBuilder sb = new StringBuilder();
		String[] array = keys.split(",");
		for(int i=0;i<array.length;i++){
			String value = uamBasedataService.getDictValue(dictType,array[i]);
			if(i>0){sb.append(SEPARATOR);}
			sb.append(value);
		}
		return sb.toString();
	}

	public String getDictType() {
		return dictType;
	}

	public void setDictType(String dictType) {
		this.dictType = dictType;
	}

}
