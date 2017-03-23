/**
 * 
 */
package com.centaline.trans.common.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.aist.common.quickQuery.service.CustomDictService;
import com.aist.common.quickQuery.utils.QuickQueryJdbcTemplate;
import com.centaline.trans.transplan.service.TransplanServiceFacade;
import com.centaline.trans.transplan.vo.TsTransPlanHistoryVO;

/**
 * @author zhoujp
 * @date 2016年11月15日
 */
public class QuickQueryGetSolutionListServiceImpl implements
		CustomDictService {

	@Autowired
	protected QuickQueryJdbcTemplate jdbcTemplate;

	@Override
	public List<Map<String, Object>> findDicts(List<Map<String, Object>> keys) {
		
		String sql = "SELECT "
					+ "(SELECT NAME FROM sctrans.sys_dict WHERE type='SPV_DE_COND' AND code=V.de_cond_code) AS term, "
					+ "S.ACCOUNT AS target, "
					+ "SUM(V.DE_AMOUNT) AS amount "
					+ "FROM  sctrans.T_TO_SPV_DE_DETAIL V "
					+ "INNER JOIN sctrans.T_TO_SPV_DE T ON T.PKID = V.DE_ID "
					+ "INNER JOIN sctrans.T_TO_SPV_ACCOUNT S ON V.PAYEE_ACCOUNT_ID = S.PKID "
					+ "WHERE   V.IS_DELETED = 0 "
					+ "AND T.IS_DELETED = 0 "
					+ "AND S.IS_DELETED = 0 "
					+ "AND T.SPV_CODE = :bizCode "
					+ "GROUP BY T.spv_code ,V.de_cond_code ,S.ACCOUNT";
		
		for (Map<String, Object> key : keys) {
			List<Map<String,Object>> maps = new ArrayList<Map<String,Object>>();
			Object bizCode = key.get("bizCode");
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("bizCode", bizCode);
			if (null!=bizCode) {
				maps = jdbcTemplate.queryForList(sql, map);
			}
			key.put("val", maps);
		}
		return keys;
	}

	@Override
	public Boolean getIsBatch() {
		return true;
	}

}
