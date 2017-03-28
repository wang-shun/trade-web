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




public class QuickQueryGetPostInfoServiceImpl implements CustomDictService{
	
	@Autowired
	private QuickQueryJdbcTemplate jdbcTemplate;

	private static String POST_SQL = "SELECT  CASE_CODE,USE_CARD_PAY,CARD_PAY_AMOUNT FROM sctrans.T_TO_HOUSE_TRANSFER WHERE CASE_CODE  IN (:caseCode)";
    
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
				
				List<Map<String, Object>>  postInfos = new ArrayList<Map<String, Object>>();
				paramMap.put("caseCode", caseCodeList);
				postInfos = jdbcTemplate.queryForList(POST_SQL, paramMap);

				for (Map<String, Object> key : rs) {
					Object caseCode   = key.get("CASE_CODE");
					
					for(Map<String, Object> postInfo:postInfos){
						if(postInfo.get("CASE_CODE") !=null && caseCode != null){
							if(caseCode.equals(postInfo.get("CASE_CODE"))){
								
								if(postInfo.get("USE_CARD_PAY") != null){
									key.put("USE_CARD_PAY", "1".equals(postInfo.get("USE_CARD_PAY").toString()) ? "是":"否");	
								}
								if(postInfo.get("CARD_PAY_AMOUNT") != null){
									key.put("CARD_PAY_AMOUNT", postInfo.get("CARD_PAY_AMOUNT"));
								}						
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
