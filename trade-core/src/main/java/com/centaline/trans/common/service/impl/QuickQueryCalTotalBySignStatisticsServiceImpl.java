package com.centaline.trans.common.service.impl;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;

import com.aist.common.quickQuery.service.CustomDictService;

/**
 * 签贷款数据统计模块---签约数据(总表总计)
 * 
 * @author yinjf2
 *
 */
public class QuickQueryCalTotalBySignStatisticsServiceImpl implements
		CustomDictService {

	@Override
	public List<Map<String, Object>> findDicts(List<Map<String, Object>> keys) {

		double totalCaseCount = 0; // 总的案件单数
		double totalHtAmount = 0; // 总的合同价
		double totalCaseCountRate = 0; // 总的占比

		String strTotalHtAmount = "0"; // 格式化后的总的案件单数
		String strTotalCaseCountRate = "0";

		DecimalFormat df = new DecimalFormat("0");
		DecimalFormat df1 = new DecimalFormat("0.00");

		// 计算总的案件单数
		for (Map<String, Object> keyer : keys) {
			double caseCount = Double.parseDouble(String
					.valueOf((Integer) keyer.get("caseCount")));
			totalCaseCount += caseCount;
		}

		for (Map<String, Object> keyer : keys) {
			double caseCount = Double.parseDouble(String
					.valueOf((Integer) keyer.get("caseCount")));

			double htAmount = Double.parseDouble(String
					.valueOf((BigDecimal) keyer.get("htAmount")));

			double caseCountRate = 0;
			String strCaseCountRate = "0"; // 格式化后的占比
			if (totalCaseCount != 0) {
				caseCountRate = caseCount / totalCaseCount * 100;

				if (caseCountRate != 0) {
					strCaseCountRate = df1.format(caseCountRate);
					totalCaseCountRate += caseCountRate;
				}
			}

			totalHtAmount += htAmount;

			if (totalHtAmount != 0) {
				strTotalHtAmount = df.format(totalHtAmount);
			}

			if (totalCaseCountRate != 0) {
				strTotalCaseCountRate = df.format(totalCaseCountRate);
			}

			keyer.put("caseCountRate", strCaseCountRate);
			keyer.put("totalCaseCount", totalCaseCount);
			keyer.put("totalHtAmount", strTotalHtAmount);
			keyer.put("totalCaseCountRate", strTotalCaseCountRate);
		}

		return keys;
	}

	@Override
	public Boolean getIsBatch() {
		return true;
	}

}
