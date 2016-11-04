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
public class QuickQueryGetShowTimeServiceImpl implements CustomDictService {

	@Override
	public List<Map<String, Object>> findDicts(List<Map<String, Object>> keys) {
		for (Map<String, Object> key : keys) {
			String val = "";

			Object checkInTime = key.get("checkInTime");
			if (checkInTime != null) {
				String strCheckInTime = checkInTime.toString();

				val = strCheckInTime.substring(strCheckInTime.indexOf(" ") + 1,
						strCheckInTime.lastIndexOf(":"));
			}

			Object checkOutTime = key.get("checkOutTime");
			if (checkOutTime != null) {
				String strCheckOutTime = checkOutTime.toString();

				val = strCheckOutTime.substring(
						strCheckOutTime.indexOf(" ") + 1,
						strCheckOutTime.lastIndexOf(":"));
			}

			Object startTime = key.get("startTime");
			if (startTime != null) {
				String strStartTime = startTime.toString();

				val = strStartTime.substring(strStartTime.indexOf(" ") + 1,
						strStartTime.lastIndexOf(":"));
			}

			Object endTime = key.get("endTime");
			if (endTime != null) {
				String strEndTime = endTime.toString();

				val = strEndTime.substring(strEndTime.indexOf(" ") + 1,
						strEndTime.lastIndexOf(":"));
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
