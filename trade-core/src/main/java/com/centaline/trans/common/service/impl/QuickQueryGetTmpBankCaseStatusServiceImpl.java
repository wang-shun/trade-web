package com.centaline.trans.common.service.impl;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import com.aist.common.quickQuery.service.CustomDictService;
import com.aist.common.quickQuery.utils.QuickQueryJdbcTemplate;

public class QuickQueryGetTmpBankCaseStatusServiceImpl implements CustomDictService {

	@Autowired
	protected QuickQueryJdbcTemplate jdbcTemplate;

	@Override
	public List<Map<String, Object>> findDicts(List<Map<String, Object>> keys) {
		for (Map<String, Object> keyer : keys) {
			String val = "";
			Object tmpBankStatusKey = keyer.get("tmpBankStatus");
			
			if(tmpBankStatusKey != null){
				if("0".equals(tmpBankStatusKey.toString())){
					val = "已驳回";
				}
				else if("1".equals(tmpBankStatusKey.toString())){
					val = "已通过";
				}
				else {
					val = "审批中";
				}
			}
			else{
				val = "审批中";
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
