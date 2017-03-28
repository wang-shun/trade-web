package com.centaline.trans.common.service.impl;

import java.util.List;
import java.util.Map;

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
	
	@Override
	@Cacheable(value="QuickQuerySrvsDictCustomDictServiceImpl",key="#root.target.getDictType()+'/'+#keys")
	public List<Map<String, Object>> findDicts(List<Map<String, Object>> keys) {
		for(Map<String, Object> keyer:keys){
			String val = "";
			try {
				Object key = keyer.values().iterator().next();
				if(key!=null){
					String[] strs = key.toString().split("/");
					StringBuffer reStrs = new StringBuffer();
					for(String s:strs){
						String value = uamBasedataService.getDictValue(dictType, s);
						reStrs.append(value);
						reStrs.append("/");
					} 
					reStrs.deleteCharAt(reStrs.length()-1); 
					val = reStrs.toString();
				}
				
			} catch (Exception e) {
			} 
			//对应的贷款类型为空或者null的情况  设置为"未选择"
			if(val==null || val.equals("null") || "".equals(val)){
				val = "未选择";
			}
			keyer.put("val", val);
		}
		
		return keys;
	}
	
	@Override
	public Boolean getIsBatch() {
		return true;
	}

	public String getDictType() {
		return dictType;
	}

	public void setDictType(String dictType) {
		this.dictType = dictType;
	}

}
