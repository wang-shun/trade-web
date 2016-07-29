package com.centaline.trans.common.service;

import java.util.List;
import java.util.Map;

public interface KeyValueService {

	public List<Map<String,Object>> queryToPropertyInfoByCaseCode(List<Map<String, Object>> keys,String dictType); 
	public List<Map<String,Object>> queryGuestInfoCustomDict(List<Map<String, Object>> keys,String dictType);
	public List<Map<String, Object>> queryGuestInfoPhoneCustomDict(List<Map<String, Object>> keys,String dictType);
	public List<Map<String, Object>> queryProcessorNameCustomDict(List<Map<String, Object>> keys); 
	public List<Map<String, Object>> queryGetLastContentCustomDict(List<Map<String, Object>> keys,String partCode); 
	
}
