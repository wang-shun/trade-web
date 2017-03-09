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
import com.centaline.trans.utils.CollectionUtils;

/**
 * 
 * @author zhoujp
 * @date 2016年11月22日
 */
public class QuickQueryGetRealNameAndOrgNameServiceImpl implements
		CustomDictService {
	
	@Autowired
	private QuickQueryJdbcTemplate jdbcTemplate;
	
	public List<Map<String, Object>> findDicts(List<Map<String, Object>> keys) {
		if(keys==null || keys.isEmpty()){
			return null;
		}
		return batchWarpper.batchWarp(keys);
	}

	
	@Override
	public Boolean getIsBatch() {
		return true;
	}
	
	private QuickQueryBatchWarpper batchWarpper = new QuickQueryBatchWarpper(new BatchQuery<Map<String, Object>>(){
		public List<Map<String, Object>> query(List<Map<String, Object>> rs) {
			// 设置条件参数
			Map<String, Object> paramMap = new HashMap<String, Object>();
			StringBuilder[] joins = CollectionUtils.join(rs, new String[]{"','","','"}, new String[]{"PR_APPLIANT","PR_DISTRICT_ID"});
			String sql = "SELECT ID AS PR_APPLIANT,REAL_NAME FROM SCTRANS.SYS_USER WHERE ID in ('"+joins[0].toString()+"')";
			List<Map<String, Object>> users = jdbcTemplate.queryForList(sql, paramMap);
			CollectionUtils.merge(users, rs, new String[]{"PR_APPLIANT"});
			sql = "SELECT ID AS PR_DISTRICT_ID,ORG_NAME FROM SCTRANS.SYS_ORG WHERE ID in ('"+joins[1].toString()+"')";
			List<Map<String, Object>>  orgs = jdbcTemplate.queryForList(sql, paramMap);
			CollectionUtils.merge(orgs, rs, new String[]{"PR_DISTRICT_ID"});
				
			return rs;
		}

	}, 1000);
	
}
