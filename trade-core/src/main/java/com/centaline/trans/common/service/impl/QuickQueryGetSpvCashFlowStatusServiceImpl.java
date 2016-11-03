package com.centaline.trans.common.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;

import com.aist.common.quickQuery.service.CustomDictService;
import com.aist.common.quickQuery.utils.QuickQueryJdbcTemplate;



public class QuickQueryGetSpvCashFlowStatusServiceImpl implements CustomDictService{
	
	@Autowired
	protected QuickQueryJdbcTemplate jdbcTemplate;

	@Override
	public List<Map<String, Object>> findDicts(List<Map<String, Object>> keys) {
		for (Map<String, Object> keyer : keys) {
			String val = "";
			Object tmpspvFlowStatusKey = keyer.get("STATUS");
			
			if(tmpspvFlowStatusKey != null){

			
			switch (tmpspvFlowStatusKey.toString()) {
			case "00":
				val = "入账起草";
				break;
			case "01":
				val = "总监审批";
				break;
			case "02":
				val = "财务审核";
				break;
			case "03":
				val = "审核完成";
				break;
			case "10":
				val = "出账起草";
				break;
			case "11":
				val = "出账起草";
				break;
			case "12":
				val = "总监审批";
				break;
			case "13":
				val = "财务初审";
				break;
			case "14":
				val = "财务复审";
				break;
			case "15":
				val = "审核完成";
				break;														
			default:
				break;
			}
			}
			keyer.put("val", val);
		}

		return keys;
	}

	@Override
	public Boolean getIsBatch() {
		return true;
	}
}
