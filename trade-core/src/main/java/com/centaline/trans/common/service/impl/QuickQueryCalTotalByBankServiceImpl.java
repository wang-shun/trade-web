package com.centaline.trans.common.service.impl;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;

import com.aist.common.quickQuery.service.CustomDictService;

/**
 * 签贷款数据统计模块---分行、支行统计案件单数、商贷金额统计
 * 
 * @author yinjf2
 *
 */
public class QuickQueryCalTotalByBankServiceImpl implements CustomDictService {

	@Override
	public List<Map<String, Object>> findDicts(List<Map<String, Object>> keys) {

		int totalCount = 0; // 总的案件单数
		double totalSdAmount = 0; // 总的商贷金额
		String strTotalSdAmount = "0";
		DecimalFormat df = new DecimalFormat("#.00");

		for (Map<String, Object> keyer : keys) {
			int caseCount = (Integer) keyer.get("caseCount");

			double sdAmount = Double.parseDouble(String
					.valueOf((BigDecimal) keyer.get("sdAmount")));

			totalCount += caseCount;
			totalSdAmount += sdAmount;

			if (totalSdAmount != 0) {
				// 格式化占比四舍五入
				strTotalSdAmount = df.format(totalSdAmount);
			}

			keyer.put("totalCaseCount", totalCount);
			keyer.put("totalSdAmount", strTotalSdAmount);
		}

		return keys;
	}

	@Override
	public Boolean getIsBatch() {
		return true;
	}

}
