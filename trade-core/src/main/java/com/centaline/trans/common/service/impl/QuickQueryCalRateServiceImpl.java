package com.centaline.trans.common.service.impl;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;

import com.aist.common.quickQuery.service.CustomDictService;

/**
 * 签贷款数据统计模块---计算占比、商贷金额占比、公积金金额占比
 * 
 * @author yinjf2
 *
 */
public class QuickQueryCalRateServiceImpl implements CustomDictService {

	@Override
	public List<Map<String, Object>> findDicts(List<Map<String, Object>> keys) {
		// 格式化占比四舍五入
		DecimalFormat df = new DecimalFormat("0");

		double tatalCount = 0; // 纯商+组合+纯公积金案件总和
		double totalSdAmount = 0; // 总商贷金额
		double totalGjjAmount = 0; // 总公积金金额
		double totalHtAmount = 0; // 总合同金额
		double totalCaseCountRate = 0; // 总案件量占比
		double totalSdAmountRate = 0; // 总商贷金额占比
		double totalGjjAmountRate = 0; // 总公积金金额占比

		String strTotalSdAmount = "0"; // 格式化之后总商贷金额
		String strTotalGjjAmount = "0"; // 格式化之后总公积金金额
		String strTotalHtAmount = "0"; // 格式化之后总合同金额
		String strTotalCaseCountRate = "0"; // 格式化之后案件量占比
		String strTotalSdAmountRate = "0"; // 格式化之后的商贷金额占比
		String strTotalGjjAmountRate = "0"; // 格式化之后的公积金金额占比

		// 循环计算纯商+组合+纯公积金案件总和
		for (Map<String, Object> keyer : keys) {
			double caseCount = Double.parseDouble(String
					.valueOf((Integer) keyer.get("caseCount")));
			tatalCount += caseCount;
		}

		for (Map<String, Object> keyer : keys) {
			// 案件数
			double caseCount = Double.parseDouble(String
					.valueOf((Integer) keyer.get("caseCount")));

			// 商贷金额
			double sdAmount = Double.parseDouble(String
					.valueOf((BigDecimal) keyer.get("sdAmount")));

			// 公积金金额
			double gjjAmount = Double.parseDouble(String
					.valueOf((BigDecimal) keyer.get("gjjAmount")));

			// 合同金额
			double htAmount = Double.parseDouble(String
					.valueOf((BigDecimal) keyer.get("htAmount")));

			double caseCountRate = 0; // 案件数量占比
			double sdAmountRate = 0; // 商贷金额占比
			double gjjAmountRate = 0;// 公积金金额占比
			String strCaseCountRate = "0"; // 格式化之后的案件数量占比
			String strSdAmountRate = "0"; // 格式化之后的商贷金额占比
			String strGjjAmountRate = "0"; // 格式化之后的公积金金额占比

			if (tatalCount != 0) {
				caseCountRate = caseCount / tatalCount * 100;
				totalCaseCountRate += caseCountRate;
			}

			if (htAmount != 0) {
				sdAmountRate = sdAmount / htAmount * 100;
				gjjAmountRate = gjjAmount / htAmount * 100;
			}

			if (caseCountRate != 0) {
				strCaseCountRate = df.format(caseCountRate);
			}

			if (sdAmountRate != 0) {
				strSdAmountRate = df.format(sdAmountRate);
			}

			if (gjjAmountRate != 0) {
				strGjjAmountRate = df.format(gjjAmountRate);
			}

			// 计算总计
			totalSdAmount += sdAmount;
			totalGjjAmount += gjjAmount;
			totalHtAmount += htAmount;

			if (totalSdAmount != 0) {
				strTotalSdAmount = df.format(totalSdAmount);
			}

			if (totalGjjAmount != 0) {
				strTotalGjjAmount = df.format(totalGjjAmount);
			}

			if (totalHtAmount != 0) {
				strTotalHtAmount = df.format(totalHtAmount);
			}

			if (totalCaseCountRate != 0) {
				strTotalCaseCountRate = df.format(totalCaseCountRate);
			}

			if (totalHtAmount != 0) {
				totalSdAmountRate = totalSdAmount / totalHtAmount * 100;
				totalGjjAmountRate = totalGjjAmount / totalHtAmount * 100;

				if (totalSdAmountRate != 0) {
					strTotalSdAmountRate = df.format(totalSdAmountRate);
				}

				if (totalGjjAmountRate != 0) {
					strTotalGjjAmountRate = df.format(totalGjjAmountRate);
				}
			}

			keyer.put("caseCountRate", strCaseCountRate);
			keyer.put("sdAmountRate", strSdAmountRate);
			keyer.put("gjjAmountRate", strGjjAmountRate);
			keyer.put("tatalCaseCount", tatalCount);
			keyer.put("totalSdAmount", strTotalSdAmount);
			keyer.put("totalGjjAmount", strTotalGjjAmount);
			keyer.put("totalHtAmount", strTotalHtAmount);
			keyer.put("totalCaseCountRate", strTotalCaseCountRate);
			keyer.put("totalSdAmountRate", strTotalSdAmountRate);
			keyer.put("totalGjjAmountRate", strTotalGjjAmountRate);

		}

		return keys;
	}

	@Override
	public Boolean getIsBatch() {
		return true;
	}

}
