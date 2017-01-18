package com.centaline.trans.common.service.impl;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;


import com.aist.common.quickQuery.service.CustomDictService;
import com.aist.common.quickQuery.utils.QuickQueryJdbcTemplate;
import com.centaline.trans.common.repository.utils.QuickQueryBatchWarpper;
import com.centaline.trans.common.repository.utils.QuickQueryBatchWarpper.BatchQuery;




public class QuickQueryGetCaseInfoReportServiceImpl implements CustomDictService{
	
	@Autowired
	private QuickQueryJdbcTemplate jdbcTemplate;

	private static String CASE_SQL = "SELECT  CASE_CODE,GRP_NAME,GRP_CODE,AR_NAME,WZ_NAME,QJDS_NAME,BA_NAME,AGENT_NAME,AGENT_PHONE FROM sctrans.T_TO_CASE_INFO WHERE CASE_CODE  IN (:caseCode)";
    
	public List<Map<String, Object>> findDicts(List<Map<String, Object>> keys) {
		if(keys==null || keys.isEmpty()){
			return null;
		}
		return batchWarpper.batchWarp(keys);
	}
  
	private QuickQueryBatchWarpper batchWarpper = new QuickQueryBatchWarpper(new BatchQuery<Map<String, Object>>(){
		public List<Map<String, Object>> query(List<Map<String, Object>> rs) {
			
			List<String> caseCodeList = new ArrayList<String>();
			if(rs!=null && !rs.isEmpty()){
				for (Map<String, Object> key : rs) {
					Object caseCode   = key.get("CASE_CODE");					
					if(caseCode!=null){
						caseCodeList.add(caseCode.toString());
					}

				}
				Map<String,Object> paramMap = new HashMap<String,Object>();
				
				List<Map<String, Object>>  caseInfos = new ArrayList<Map<String, Object>>();
				paramMap.put("caseCode", caseCodeList);
				caseInfos = jdbcTemplate.queryForList(CASE_SQL, paramMap);

				for (Map<String, Object> key : rs) {
					Object caseCode   = key.get("CASE_CODE");
					
					for(Map<String, Object> caseInfo:caseInfos){
						if(caseInfo.get("CASE_CODE") !=null && caseCode != null){
							if(caseCode.equals(caseInfo.get("CASE_CODE"))){
								key.put("GRP_NAME", caseInfo.get("GRP_NAME"));								
								key.put("AR_NAME", caseInfo.get("AR_NAME"));
								key.put("WZ_NAME", caseInfo.get("WZ_NAME"));
								key.put("QJDS_NAME", caseInfo.get("QJDS_NAME"));
								
								key.put("GRP_CODE", caseInfo.get("GRP_CODE"));
								key.put("BA_NAME", caseInfo.get("BA_NAME"));
								key.put("AGENT_NAME", caseInfo.get("AGENT_NAME"));
								key.put("AGENT_PHONE", caseInfo.get("AGENT_PHONE"));													
								continue;
							}
						}
					}					
				}
			}
			
			return rs;
		}
	},1000);

	
	@Override
	public Boolean getIsBatch() {
		return true;
	}
	
}
