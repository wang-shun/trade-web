package com.centaline.trans.common.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;

import com.aist.common.quickQuery.service.CustomDictService;
import com.aist.uam.basedata.remote.UamBasedataService;
import com.centaline.trans.common.service.KeyValueService;
/**
 * 查询多个字典名称，名称直接以分号隔开
 * 
 * */
public class QuickQueryMultiDictCustomDictServiceImpl implements CustomDictService{
	@Autowired
	private UamBasedataService uamBasedataService;
	@Autowired
	private KeyValueService keyValueService;
	
	/*字典分类代码*/
	private String dictType;
	/*多个名称之间的分割符*/
	private final String SEPARATOR = "; ";
	@Override
	@Cacheable(value="QuickQueryMultiDictCustomDictServiceImpl",key="#root.target.getDictType()+'/'+#keys")
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
	
	
	@Override
	@Cacheable(value="QuickQueryMultiDictCustomDictServiceImpl",key="#root.target.getDictType()+'/'+#keys")
	public List<Map<String, Object>> findDicts(List<Map<String, Object>> keys) {
		
		List<Map<String, Object>> maps = new ArrayList<Map<String, Object>>();
		Map map = null;
		for(Map<String, Object> key:keys){
			Object codes = key.get("NOT_APPROVE_OLD");
			key.put("val", "");
			if(codes!=null){
				String[] array = codes.toString().split(",");
				for(int i=0;i<array.length;i++){
					map = new HashMap();
					map.put("CODE",array[i]);
					maps.add(map);
				}
				
				maps = keyValueService.queryToPropertyInfoByCaseCode(maps,dictType);
				StringBuilder sb = new StringBuilder();
				for(int i=0;i<maps.size();i++){
					if(maps.get(i).get("val")!=null){
						String value = (String) maps.get(i).get("val");
						if(i>0){sb.append(SEPARATOR);}
						sb.append(value);
					}
				}
				key.put("val", sb.toString());
			}
		}
		return keys;
	}

	public String getDictType() {
		return dictType;
	}

	public void setDictType(String dictType) {
		this.dictType = dictType;
	}
	
	@Override
	public Boolean getIsBatch() {
		return true;
	}

}
