package com.centaline.trans.common.service;

import java.util.List;
import java.util.Map;

import com.aist.uam.userorg.remote.vo.User;

public interface KeyValueService {

	public List<Map<String,Object>> queryToPropertyInfoByCaseCode(List<Map<String, Object>> keys,String dictType); 
	public List<Map<String,Object>> queryGuestInfoCustomDict(List<Map<String, Object>> keys,String dictType);
	public List<Map<String, Object>> queryGuestInfoPhoneCustomDict(List<Map<String, Object>> keys,String dictType);
	public List<Map<String, Object>> queryProcessorNameCustomDict(List<Map<String, Object>> keys); 	
	public User queryManagerByUserIdAndJobCode(String UserId,String JobCode); 	
}
