package com.centaline.trans.common.service.impl;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import com.aist.common.quickQuery.service.CustomDictService;
import com.aist.common.quickQuery.utils.QuickQueryJdbcTemplate;

public class QuickQueryGetCurrentProcessServiceImpl implements CustomDictService {

	@Autowired
	protected QuickQueryJdbcTemplate jdbcTemplate;

	@Override
	public List<Map<String, Object>> findDicts(List<Map<String, Object>> keys) {
		for (Map<String, Object> keyer : keys) {
			String val = "";
			Object currentProcessKey = keyer.get("currentProcess");
			Object isSubmitKey = keyer.get("isSubmit");
			
			if(isSubmitKey != null){
				keyer.put("val", val);
			}
			else{
				if (currentProcessKey != null) {
					if("ManagerAduit".equals(currentProcessKey.toString())){
						val = "主管审批";
					}
					else if("DirectorAudit".equals(currentProcessKey.toString())){
						val = "总监审批";
					}
					else if("SuperManagerAudit".equals(currentProcessKey.toString())){
						val = "高级主管审批";
					}
				}
				else {
					val = "商贷办理";
				}
				
				keyer.put("val", val);
			}
			
			

			
		}

		return keys;
	}

	@Override
	public Boolean getIsBatch() {
		return true;
	}

}
