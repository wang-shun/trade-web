package com.centaline.trans.common.service.impl;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.ws.commons.schema.utils.UtilObjects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import com.aist.common.quickQuery.service.CustomDictService;
import com.aist.common.quickQuery.utils.QuickQueryJdbcTemplate;
import com.aist.common.utils.SpringUtils;

/**
 * 签贷款数据统计模块---分行、支行统计案件单数、商贷金额统计
 * 
 * @author yinjf2
 *
 */
public class QuickQueryCalTotalByBankServiceImpl implements CustomDictService {
	
	@Autowired
	protected QuickQueryJdbcTemplate jdbcTemplate;

	@Override
	public List<Map<String, Object>> findDicts(List<Map<String, Object>> keys) {

		String sql = "SELECT SUM(caseCount) AS sumCaseCount,SUM(sdAmount) AS sumSdAmount FROM ( select  FIN_ORG_NAME_YC as groupName,"
				+ " (SELECT COUNT(*) FROM sctrans.T_RPT_HISTORY_CASE_BASE_INFO c WHERE c.MORT_FA_FIN_ORG_CODE = FIN_ORG_CODE AND c.IS_DELEGATE_YUCUI = 1 AND (c.MORT_TYPE = '30016001' OR c.MORT_TYPE = '30016002') AND convert(varchar(7),c.MORT_SIGN_DATE,120) = :searchDateTime AND c.BELONG_MONTH = :searchBelongMonth ) AS caseCount, "
				+ " (SELECT round(isnull(SUM(c.MORT_COM_AMOUNT) / 10000,0),0) FROM sctrans.T_RPT_HISTORY_CASE_BASE_INFO c WHERE c.MORT_FA_FIN_ORG_CODE = FIN_ORG_CODE AND c.IS_DELEGATE_YUCUI = 1 AND (c.MORT_TYPE = '30016001' OR c.MORT_TYPE = '30016002') AND convert(varchar(7),c.MORT_SIGN_DATE,120) = :searchDateTime AND c.BELONG_MONTH = :searchBelongMonth ) AS sdAmount "
				+ " from sctrans.T_TS_FIN_ORG "
				+ " where fin_org_code in  ( select distinct FA_FIN_ORG_CODE  from sctrans.T_TS_FIN_ORG "
				+ " where FIN_ORG_CODE in (select FIN_ORG_CODE from sctrans.T_TS_SUP ts where ts.SUP_CAT='0')) ) t ";
		Map<String, Object> map = null;
		if(!CollectionUtils.isEmpty(keys)){
			Object searchDateTime = keys.get(0).get("searchDateTime");
			Object searchBelongMonth = keys.get(0).get("searchBelongMonth");
			if(!ObjectUtils.isEmpty(searchDateTime) && !ObjectUtils.isEmpty(searchBelongMonth)){
				map = new HashMap<String,Object>();
				Map<String,String> paramMap = new HashMap<String,String>();
				paramMap.put("searchDateTime", searchDateTime.toString());
				paramMap.put("searchBelongMonth", searchBelongMonth.toString());
				map = jdbcTemplate.queryForMap(sql, paramMap);
			}
		}
		
		
		int totalCount = 0; // 总的案件单数
		double totalSdAmount = 0; // 总的商贷金额
		String strTotalSdAmount = "0";
		DecimalFormat df = new DecimalFormat("0");

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
			
			if(!CollectionUtils.isEmpty(map)){
				keyer.put("sumCaseCount", map.get("sumCaseCount"));
				keyer.put("sumSdAmount", map.get("sumSdAmount"));
			}
			
		}

		return keys;
	}

	@Override
	public Boolean getIsBatch() {
		return true;
	}

}
