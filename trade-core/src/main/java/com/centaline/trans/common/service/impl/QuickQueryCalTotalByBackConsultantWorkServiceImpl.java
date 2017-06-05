package com.centaline.trans.common.service.impl;

import java.util.List;
import java.util.Map;

import com.aist.common.quickQuery.service.CustomDictService;

/**
 * 签贷款数据统计模块---后台交易顾问工作量总计
 * 
 * @author yinjf2
 *
 */
public class QuickQueryCalTotalByBackConsultantWorkServiceImpl implements
		CustomDictService {

	@Override
	public List<Map<String, Object>> findDicts(List<Map<String, Object>> keys) {

		int totalCxgCount = 0; // 总的查限购案件单数
		int totalHjCount = 0; // 总的核价案件单数
		int totalSsCount = 0; // 总的审税案件单数
		int totalGjjCount = 0; // 总的公积金办理案件单数
		int totalGhCount = 0; // 总的过户案件单数
		int totalLzCount = 0; // 总的领证案件单数

		for (Map<String, Object> keyer : keys) {
			int cxgCount = (Integer) keyer.get("cxgCount");
			int hjCount = (Integer) keyer.get("hjCount");
			int ssCount = (Integer) keyer.get("ssCount");
			int gjjCount = (Integer) keyer.get("gjjCount");
			int ghCount = (Integer) keyer.get("ghCount");
			int lzCount = (Integer) keyer.get("lzCount");

			totalCxgCount += cxgCount;
			totalHjCount += hjCount;
			totalSsCount += ssCount;
			totalGjjCount += gjjCount;
			totalGhCount += ghCount;
			totalLzCount += lzCount;

			keyer.put("totalCxgCount", totalCxgCount);
			keyer.put("totalHjCount", totalHjCount);
			keyer.put("totalSsCount", totalSsCount);
			keyer.put("totalGjjCount", totalGjjCount);
			keyer.put("totalGhCount", totalGhCount);
			keyer.put("totalLzCount", totalLzCount);
		}

		return keys;
	}

	@Override
	public Boolean getIsBatch() {
		return true;
	}

}
