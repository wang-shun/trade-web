package com.centaline.trans.common.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.centaline.trans.common.repository.KeyValueMapper;
import com.centaline.trans.common.service.KeyValueService;

@Service
public class KeyValueServiceImpl implements KeyValueService {

	@Autowired
	private KeyValueMapper keyValueMapper;

	@Override
	public List<Map<String, Object>> queryToPropertyInfoByCaseCode(List<Map<String, Object>> keys, String dictType) {

		List<Map<String, Object>> maps = keyValueMapper.queryToPropertyInfoByCaseCode(keys, dictType);
		transfer(keys,maps);
		return keys;
	}

	@Override
	public List<Map<String, Object>> queryGuestInfoCustomDict(List<Map<String, Object>> keys, String dictType) {
		List<Map<String, Object>> maps = keyValueMapper.queryGuestInfoCustomDict(keys, dictType);
		transfer(keys,maps);
		return keys;
	}
	
	@Override
	public List<Map<String, Object>> queryGuestInfoPhoneCustomDict(List<Map<String, Object>> keys, String dictType) {
		List<Map<String, Object>> maps = keyValueMapper.queryGuestInfoPhoneCustomDict(keys, dictType);
		transfer(keys,maps);
		return keys;
	}
	

	@Override
	public List<Map<String, Object>> queryProcessorNameCustomDict(List<Map<String, Object>> keys) {
		List<Map<String, Object>> maps = keyValueMapper.queryProcessorNameCustomDict(keys);
		transfer(keys,maps);
		return keys;
	}
	
	public void transfer(List<Map<String, Object>> keys,List<Map<String, Object>> maps){
		if (keys != null && keys.size() > 0) {
			for (int i = 0; i < keys.size(); i++) {
				//keys.get(i).put("val", "");
				if (maps != null && maps.size() > 0) {
					for (int j = 0; j < maps.size(); j++) {
						if ( keys.get(i).values().iterator().next()!=null  && (keys.get(i).values().iterator().next()).equals(maps.get(j).get("CODE"))) {
							keys.get(i).put("val", maps.get(j).get("NAME"));
							break;
						}
					}
				}

			}
		}
	}


}
