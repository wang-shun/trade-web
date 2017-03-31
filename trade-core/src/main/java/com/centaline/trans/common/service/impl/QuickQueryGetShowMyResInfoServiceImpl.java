package com.centaline.trans.common.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.aist.common.quickQuery.service.CustomDictService;

/**
 * 获取我的预约信息
 * 
 * @author yinjf2
 * @date 2016年8月5日
 */
@Service
public class QuickQueryGetShowMyResInfoServiceImpl implements CustomDictService {

	@Override
	public List<Map<String, Object>> findDicts(List<Map<String, Object>> keys) {
		for (Map<String, Object> key : keys) {
			String actStartDate = "";
			String actEndDate = "";
			String actStatus = "";

			Object status = key.get("resStatus");
			Object startDate = key.get("startDate");
			Object endDate = key.get("endDate");

			// 获取预约状态
			if (status != null) {
				if ("0".equals(status.toString())) {
					actStatus = "预约中";
				} else if ("1".equals(status.toString())) {
					actStatus = "使用中";
				} else if ("2".equals(status.toString())) {
					actStatus = "使用完";
				} else if ("3".equals(status.toString())) {
					actStatus = "已过期";
				} else if ("4".equals(status.toString())) {
					actStatus = "已取消";
				}
			}

			// 获取预约开始时间
			if (startDate != null) {
				String strStartDate = startDate.toString();

				actStartDate = strStartDate.substring(
						strStartDate.indexOf(" ") + 1,
						strStartDate.lastIndexOf(":"));

			}

			// 获取预约结束时间
			if (endDate != null) {
				String strEndDate = endDate.toString();

				actEndDate = strEndDate.substring(strEndDate.indexOf(" ") + 1,
						strEndDate.lastIndexOf(":"));

			}

			key.put("actStartDate", actStartDate);
			key.put("actStatus", actStatus);
			key.put("actEndDate", actEndDate);
		}

		return keys;
	}

	@Override
	public Boolean getIsBatch() {
		return true;
	}

}
