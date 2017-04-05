package com.centaline.trans.common.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.aist.common.quickQuery.service.CustomDictService;
import com.aist.common.quickQuery.utils.QuickQueryJdbcTemplate;

/**
 * 根据案件编号集合获取贷款银行和贷款支行信息服务接口
 * 
 * @author yinjf2
 *
 */
public class QuickQueryGetMainAndBranchBankByCaseCodeListServiceImpl implements
		CustomDictService {

	@Autowired
	protected QuickQueryJdbcTemplate jdbcTemplate;

	@Override
	public List<Map<String, Object>> findDicts(List<Map<String, Object>> keys) {
		// 新建一个存储案件编号集合
		List<String> caseCodeList = new ArrayList<String>();

		for (Map<String, Object> keyer : keys) {
			Object caseCodeKey = keyer.get("caseCode");

			if (caseCodeKey != null) {
				// 将案件编号放到集合中
				caseCodeList.add(caseCodeKey.toString());
			}
		}

		String sql = "SELECT DISTINCT m.CASE_CODE AS caseCode,"
				+ "(SELECT FIN_ORG_NAME_YC FROM sctrans.T_TS_FIN_ORG WHERE FIN_ORG_CODE = (select FA_FIN_ORG_CODE from sctrans.T_TS_FIN_ORG WHERE FIN_ORG_CODE = fo.FIN_ORG_CODE)) AS mainBankName,"
				+ "fo.FIN_ORG_NAME_YC AS branchBankName"
				+ " FROM sctrans.T_TO_MORTGAGE m"
				+ " LEFT JOIN sctrans.T_TS_FIN_ORG fo ON fo.FIN_ORG_CODE = m.FIN_ORG_CODE"
				+ " WHERE m.IS_ACTIVE = '1' AND m.CASE_CODE IN (:caseCodeList)";

		// 设置条件参数
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("caseCodeList", caseCodeList);

		// 根据案件编号集合查询贷款银行、贷款支行信息
		List<Map<String, Object>> resultMapList = jdbcTemplate.queryForList(
				sql, paramMap);

		if (resultMapList != null && resultMapList.size() > 0) {
			for (Map<String, Object> keyer : keys) {
				String caseCode = keyer.get("caseCode").toString();

				for (Map<String, Object> resultMap : resultMapList) {
					String rCaseCode = (String) resultMap.get("caseCode");

					// 将查询到的数据与每行的记录数据进行绑定
					if (caseCode.equals(rCaseCode)) {
						// 将贷款银行放入keyer中
						keyer.put("mainBankName", resultMap.get("mainBankName"));

						// 将贷款支行放入keyer中
						keyer.put("branchBankName",
								resultMap.get("branchBankName"));
					}
				}
			}
		}

		return keys;
	}

	@Override
	public Boolean getIsBatch() {
		return true;
	}

}
