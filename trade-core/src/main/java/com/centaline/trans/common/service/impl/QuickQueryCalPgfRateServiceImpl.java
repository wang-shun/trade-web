package com.centaline.trans.common.service.impl;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;

import com.aist.common.quickQuery.service.CustomDictService;

/**
 * 按贵宾中心、组别、个人统计模块---计算评估转化率
 * 
 * @author yinjf2
 *
 */
public class QuickQueryCalPgfRateServiceImpl implements CustomDictService {

	@Override
	public List<Map<String, Object>> findDicts(List<Map<String, Object>> keys) {
		for (Map<String, Object> keyer : keys) {
			double pgfCount = Double.parseDouble(String.valueOf((Integer) keyer
					.get("pgfCount")));
			double sdCount = Double.parseDouble(String.valueOf((Integer) keyer
					.get("sdCount")));

			String strPgfRate = "0";
			if (sdCount != 0) {
				double pgfRate = pgfCount / sdCount * 100;

				if (pgfRate != 0) {
					DecimalFormat df = new DecimalFormat("#.00");
					strPgfRate = df.format(pgfRate);
				}
			}

			keyer.put("val", strPgfRate);
		}

		return keys;
	}

	@Override
	public Boolean getIsBatch() {
		return true;
	}

}
