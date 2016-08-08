/**
 * 
 */
package com.centaline.trans.common.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.aist.common.quickQuery.service.CustomDictService;

/**
 * @author yinjf2
 * @date 2016年8月5日
 */
@Service
public class QuickQueryGetStatusAndWarnTypeServiceImpl implements CustomDictService {

	
	@Override
	public List<Map<String, Object>> findDicts(List<Map<String, Object>> keys) {
		for(Map<String, Object> key:keys){
			String val = "";
			Object status = key.get("status");
			if(status!=null){
				val = "0".equals(status.toString())?"生效":"解除";
			}
			key.put("val", val);
			
			Object warnType = key.get("warnType");
			if(warnType!=null){
				if("LOANLOSS".equals(warnType.toString())){
					val = "贷款流失";
				}
			}
			key.put("val", val);
		}
		
		return keys;
	}

	@Override
	public Boolean getIsBatch() {
		return true;
	}

}
