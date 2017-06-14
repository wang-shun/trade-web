package com.centaline.trans.common.service.impl;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.aist.common.quickQuery.service.CustomDictService;
import com.aist.common.quickQuery.utils.QuickQueryJdbcTemplate;
import com.centaline.trans.common.repository.utils.QuickQueryBatchWarpper;
import com.centaline.trans.common.repository.utils.QuickQueryBatchWarpper.BatchQuery;
import com.centaline.trans.utils.CollectionUtils;
import com.centaline.trans.utils.CollectionUtils.Converter;




public class QuickQueryGetCaseInfoReportServiceImpl implements CustomDictService{
	
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
			Map<String,Object> paramMap = new HashMap<String,Object>();
			StringBuilder[] joins = CollectionUtils.join(rs, new String[]{"','"}, new String[]{"CASE_CODE"});
			//分行,片区,区域,区董,分行Code,大区,业务员姓名,业务员电话
			String sql = "SELECT  CASE_CODE,CTM_CODE,GRP_NAME,GRP_CODE,AR_NAME,WZ_NAME,QJDS_NAME,BA_NAME,(SELECT o.ORG_NAME FROM sctrans.SYS_USER u LEFT JOIN sctrans.SYS_ORG o on u.ORG_ID = o.ID WHERE u.ID = AGENT_CODE) AGENT_ORG_NAME,AGENT_NAME,AGENT_PHONE FROM sctrans.T_TO_CASE_INFO WHERE CASE_CODE  IN ('"+joins[0].toString()+"')";
			List<Map<String, Object>> caseInfos = jdbcTemplate.queryForList(sql, paramMap);
			CollectionUtils.merge(caseInfos, rs, new String[]{"CASE_CODE"});
			sql = "SELECT CASE_CODE,PROPERTY_ADDR FROM sctrans.T_TO_PROPERTY_INFO WHERE CASE_CODE in ('"+joins[0].toString()+"')";
			List<Map<String, Object>> propertyInfos = jdbcTemplate.queryForList(sql, paramMap);
			//产证地址
			CollectionUtils.merge(propertyInfos, rs, new String[]{"CASE_CODE"});
			sql = "SELECT CASE_CODE,PKID CASE_ID,(SELECT o.ORG_NAME FROM sctrans.SYS_ORG o WHERE o.ID = ORG_ID) C_ORG_NAME FROM sctrans.T_TO_CASE WHERE CASE_CODE IN ('"+joins[0].toString()+"')";
			List<Map<String, Object>> cases = jdbcTemplate.queryForList(sql, paramMap);
			//案件ID,归属组
			CollectionUtils.merge(cases, rs, new String[]{"CASE_CODE"});
			//是否审批通过转换
			CollectionUtils.convert(rs, converters);
			return rs;
		}
	},1000);
	
	private Map<String,Converter<String,Object>> converters = new HashMap<String,Converter<String,Object>>();
	public QuickQueryGetCaseInfoReportServiceImpl() {
		converters.put("N_STATUS", new Converter<String,Object>(){
			public Object convert(Map<String,Object> to) {
				Object status = to.get("status");
				if ( null == status ) {
					return null;
				}
				status = "1".equals(status.toString())?"是":"否";
				return status;
			};
		});
	}

	
	@Override
	public Boolean getIsBatch() {
		return true;
	}
	
}
