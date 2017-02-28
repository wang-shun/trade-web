package com.centaline.trans.common.service.impl;

import java.math.BigDecimal;
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

		int totalSlCount = 0; // 总的受理案件量
		int totalQyCount = 0; // 总的签约量
		int totalSdCount = 0; // 总的商贷办理量
		int totalZbCount = 0; // 总的客户自办量
		int totalGjjCount = 0; // 总的公积金量
		int totalGhCount = 0; // 总的过户量
		double totalPgfRate = 0; // 总的评估费转化率
		double totalPgfAmount = 0; // 总的评估费金额
		int totalECardCount = 0; // 总的E+卡证量
		double totalECardAmount = 0; // 总的E+卡证金额
		int totalEProCount = 0; // 总的E+贷款量
		double totalEProAmount = 0; // 总的E+贷款金额

		String strTotalPgfRate = "0"; // 格式化后的总的评估费转化率
		String strTotalPgfAmount = "0"; // 格式化后的总的评估费金额
		String strTotalEProAmount = "0"; // 格式化后的总的E+贷款金额
		for (Map<String, Object> keyer : keys) {
			int slCount = (Integer) keyer.get("slCount");
			int qyCount = (Integer) keyer.get("qyCount");
			int sdCount1 = (Integer) keyer.get("sdCount");
			int zbCount = (Integer) keyer.get("zbCount");
			int gjjCount = (Integer) keyer.get("gjjCount");
			int ghCount = (Integer) keyer.get("ghCount");

			double pgfAmount = Double.parseDouble(String
					.valueOf((BigDecimal) keyer.get("pgfAmount")));

			int eCardCount = (Integer) keyer.get("eCardCount");

			double eCardAmount = Double.parseDouble(String
					.valueOf((BigDecimal) keyer.get("eCardAmount")));

			int eProCount = (Integer) keyer.get("eProCount");

			double eProAmount = Double.parseDouble(String
					.valueOf((BigDecimal) keyer.get("eProAmount")));

			double pgfCount = Double.parseDouble(String.valueOf((Integer) keyer
					.get("pgfCount")));
			double sdCount = Double.parseDouble(String.valueOf((Integer) keyer
					.get("sdCount")));

			totalSlCount += slCount;
			totalQyCount += qyCount;
			totalSdCount += sdCount1;
			totalZbCount += zbCount;
			totalGjjCount += gjjCount;
			totalGhCount += ghCount;
			totalECardCount += eCardCount;
			totalEProCount += eProCount;

			DecimalFormat df = new DecimalFormat("#.00");

			String strPgfRate = "0";
			if (sdCount != 0) {
				double pgfRate = pgfCount / sdCount * 100;
				totalPgfRate += pgfRate;

				if (pgfRate != 0) {
					strPgfRate = df.format(pgfRate);
					strTotalPgfRate = df.format(totalPgfRate);
				}
			}

			totalPgfAmount += pgfAmount;
			totalECardAmount += eCardAmount;
			totalEProAmount += eProAmount;

			if (totalPgfAmount != 0) {
				strTotalPgfAmount = df.format(totalPgfAmount);
			}

			if (totalEProAmount != 0) {
				strTotalEProAmount = df.format(totalEProAmount);
			}

			keyer.put("totalSlCount", totalSlCount);
			keyer.put("totalQyCount", totalQyCount);
			keyer.put("totalSdCount", totalSdCount);
			keyer.put("totalZbCount", totalZbCount);
			keyer.put("totalGjjCount", totalGjjCount);
			keyer.put("totalGhCount", totalGhCount);
			keyer.put("totalPgfRate", strTotalPgfRate);
			keyer.put("totalPgfAmount", strTotalPgfAmount);
			keyer.put("totalECardCount", totalECardCount);
			keyer.put("totalECardAmount", totalECardAmount);
			keyer.put("totalEProCount", totalEProCount);
			keyer.put("totalEProAmount", strTotalEProAmount);
			keyer.put("pgfRate", strPgfRate);
		}

		return keys;
	}

	@Override
	public Boolean getIsBatch() {
		return true;
	}

}
