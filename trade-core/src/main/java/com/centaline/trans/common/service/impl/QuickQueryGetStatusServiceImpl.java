package com.centaline.trans.common.service.impl;

import com.aist.common.quickQuery.service.CustomDictService;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;
import java.util.Map;


public class QuickQueryGetStatusServiceImpl implements CustomDictService {
	
	
	
	@Override
	@Cacheable(value="QuickQueryGetStatusServiceImpl",key="#root.targetClass + #root.methodName")
	public List<Map<String, Object>> findDicts(List<Map<String, Object>> keys) {
		for(Map<String, Object> key:keys){			
			String val = "";
			Object mortType = key.get("MORT_TYPE");
			Object isYucui = key.get("IS_DELEGATE_YUCUI");
			Object loanReq = key.get("LOAN_REQ");
			boolean flag = false;
		
			if(mortType!=null && isYucui !=null && loanReq !=null){				
				if("30016001".equals(mortType.toString()) || "30016002".equals(mortType.toString())){
					flag = true;
				}
				if(flag && "1".equals(isYucui.toString()) && "1".equals(loanReq.toString())){
					val="收单";
				}
				if(flag && "0".equals(isYucui.toString()) && "1".equals(loanReq.toString())){
					val="流失";
				}				
				key.put("val", val);
			}
			
		}		
		return keys;
	}
	
	@Override
	public Boolean getIsBatch() {
		return true;
	}
}
