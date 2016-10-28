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
public class QuickQueryGetShowResStatusServiceImpl implements CustomDictService {

	@Override
	public List<Map<String, Object>> findDicts(List<Map<String, Object>> keys) {
		for (Map<String, Object> key : keys) {
			String val = "";

			Object status = key.get("resStatus");
			if (status != null) {
				if ("0".equals(status.toString())) {
					val = "预约中";
				} else if ("1".equals(status.toString())) {
					val = "使用中";
				} else if ("2".equals(status.toString())) {
					val = "使用完";
				} else if ("3".equals(status.toString())) {
					val = "已过期";
				} else if ("4".equals(status.toString())) {
					val = "已取消";
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
