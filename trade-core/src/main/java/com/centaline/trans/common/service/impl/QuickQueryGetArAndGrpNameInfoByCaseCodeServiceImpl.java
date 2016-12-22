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
import com.centaline.trans.common.repository.utils.QuickQueryBatchWarpper;
import com.centaline.trans.common.repository.utils.QuickQueryBatchWarpper.BatchQuery;

/**
 * 查询店组、片区名称
 * @author zhoujp
 * @date 2016年11月24日
 */
public class QuickQueryGetArAndGrpNameInfoByCaseCodeServiceImpl implements
		CustomDictService {
	private static String ARGRPNAME_SQL = "SELECT CASE_CODE,AR_NAME,GRP_NAME FROM SCTRANS.T_TO_CASE_INFO WHERE CASE_CODE in (:caseCode)";
	
	@Autowired
	private QuickQueryJdbcTemplate jdbcTemplate;
	
	public List<Map<String, Object>> findDicts(List<Map<String, Object>> keys) {
		if(keys==null || keys.isEmpty()){
			return null;
		}
		return batchWarpper.batchWarp(keys);
	}

	private QuickQueryBatchWarpper batchWarpper = new QuickQueryBatchWarpper(new BatchQuery<Map<String, Object>>(){
		public List<Map<String, Object>> query(List<Map<String, Object>> rs) {
			List<String> caseCode = new ArrayList<String>();
			if(rs!=null && !rs.isEmpty()){
				for (Map<String, Object> key : rs) {
					Object case_code   = key.get("CASE_CODE");
					if(case_code!=null){
						caseCode.add(case_code.toString());
					}
				}
				Map<String,Object> paramMap = new HashMap<String,Object>();
				List<Map<String, Object>>  argrps = new ArrayList<Map<String, Object>>();
				paramMap.put("caseCode", caseCode);
				argrps = jdbcTemplate.queryForList(ARGRPNAME_SQL, paramMap);
				
				for (Map<String, Object> key : rs) {
					Object case_code   = key.get("CASE_CODE");
					for(Map<String, Object> argrp:argrps){
						if(case_code.equals(argrp.get("CASE_CODE"))){
							key.put("AR_NAME", argrp.get("AR_NAME"));
							key.put("GRP_NAME", argrp.get("GRP_NAME"));
							continue;
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
