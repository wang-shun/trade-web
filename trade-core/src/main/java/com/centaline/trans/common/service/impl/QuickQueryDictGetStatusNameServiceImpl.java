package com.centaline.trans.common.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;

import com.aist.common.quickQuery.service.CustomDictService;
import com.aist.uam.basedata.remote.UamBasedataService;

public class QuickQueryDictGetStatusNameServiceImpl implements CustomDictService{

	@Autowired
	private UamBasedataService uamBasedataService;
	
	private String dictType;
	
	@Override
	@Cacheable(value="QuickQueryDictGetStatusNameServiceImpl",key="#root.target.getDictType()+'/'+#key")
	public String getValue(String key) {
		String value = uamBasedataService.getDictValue(dictType, key);
		return value;
	}
	
	@Override
	@Cacheable(value="QuickQueryDictGetStatusNameServiceImpl",key="#root.target.getDictType()+'/'+#keys")
	public List<Map<String, Object>> findDicts(List<Map<String, Object>> keys) {
		for(Map<String, Object> keyer:keys){
			String val = "";
			try {
				Object isResponsed = keyer.get("IS_RESPONSED");
				Object statusOld = keyer.get("STATUS_OLD");
				Object key = null;
				if("1".equals(isResponsed)){
					key = statusOld;
				}else{
					if("30001007".equals(statusOld)){key = statusOld;}else{
					key = "A";}
				}
				if(key!=null){
					if(!key.equals("A")){
						val = uamBasedataService.getDictValue(dictType, key.toString());
					}else{
						val="未分单";
					}
					
				}
			} catch (Exception e) {
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
