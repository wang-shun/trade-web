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
public class QuickQueryFormatDateServiceImpl implements CustomDictService {

	private String format;
	
	@Override
	public List<Map<String, Object>> findDicts(List<Map<String, Object>> keys) {
		for (Map<String, Object> key : keys) {
			String val = "";
			String str=key.values().toString();
				val=str.substring(str.indexOf("[")+1,str.indexOf(" "));
				key.put("val", val);

		}

		return keys;
	}

	@Override
	public Boolean getIsBatch() {
		return true;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

}
