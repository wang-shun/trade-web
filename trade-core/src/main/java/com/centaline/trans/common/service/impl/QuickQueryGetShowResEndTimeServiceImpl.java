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
public class QuickQueryGetShowResEndTimeServiceImpl implements
		CustomDictService {

	@Override
	public List<Map<String, Object>> findDicts(List<Map<String, Object>> keys) {
		for (Map<String, Object> key : keys) {
			String val = "";

			Object endDate = key.get("endDate");
			if (endDate != null) {
				String strEndDate = endDate.toString();

				val = strEndDate.substring(strEndDate.indexOf(" ") + 1,
						strEndDate.lastIndexOf(":"));

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
