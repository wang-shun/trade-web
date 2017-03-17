package com.centaline.trans.common.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aist.common.quickQuery.service.CustomDictService;
import com.aist.common.quickQuery.utils.QuickQueryJdbcTemplate;

@Service
public class QuickQueryEPlusLoanStatusServiceImpl implements CustomDictService {

	@Override
	public Boolean isCacheable() {
		return true;
	}

	@Autowired
	protected QuickQueryJdbcTemplate jdbcTemplate;


	@Override
	public List<Map<String, Object>> findDicts(List<Map<String, Object>> keys) {
		if(null == keys) {
			return null;
		}
		List<String> bizList = new ArrayList<String>();
		for (Map<String, Object> map : keys) {
			Object obj = map.get("eloanCode");
			if(null != obj) {
				bizList.add(String.valueOf(obj));
			}
		}
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("bizList", bizList);
		String sql = "	select c1.biz_code eloanCode,sd.NAME status from "
				+ "		(select row_number() over(partition by c.biz_code order by create_time desc) rn , c.BIZ_CODE,c.SRV_CODE" + 
				"			from sctrans.T_TO_CASE_COMMENT  c  where c.BIZ_CODE  in (:bizList)" + 
				"		) c1 left join sctrans.SYS_DICT sd on sd.CODE = c1.SRV_CODE  "
				+ " where c1.rn = 1";
		
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, paramMap);
		return list;
	}
	
	@Override
	public Boolean getIsBatch() {
		return true;
	}

}
