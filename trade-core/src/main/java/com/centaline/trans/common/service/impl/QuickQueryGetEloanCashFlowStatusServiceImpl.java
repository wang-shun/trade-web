package com.centaline.trans.common.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.aist.common.quickQuery.service.CustomDictService;
import com.aist.common.quickQuery.utils.QuickQueryJdbcTemplate;



public class QuickQueryGetEloanCashFlowStatusServiceImpl implements CustomDictService{
	
	@Autowired
	protected QuickQueryJdbcTemplate jdbcTemplate;

	@Override
	public List<Map<String, Object>> findDicts(List<Map<String, Object>> keys) {
		for (Map<String, Object> keyer : keys) {
			String val = "";
			Object tmpspvFlowStatusKey = keyer.get("CONFIRM_STATUS");
			
			if(tmpspvFlowStatusKey != null){

			
			switch (tmpspvFlowStatusKey.toString()) {
			case "0":
				val = "未完成";
				break;
			case "1":
				val = "已完成";
				break;
			case "2":
				val = "已拒绝";
				break;
			case "3":
				val = "已完成";
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
