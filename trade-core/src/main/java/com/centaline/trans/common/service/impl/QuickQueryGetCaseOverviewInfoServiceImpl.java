package com.centaline.trans.common.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.aist.common.quickQuery.service.CustomDictService;
import com.aist.common.quickQuery.utils.QuickQueryJdbcTemplate;
import com.aist.uam.basedata.remote.UamBasedataService;
import com.centaline.trans.common.repository.utils.QuickQueryBatchWarpper;
import com.centaline.trans.common.repository.utils.QuickQueryBatchWarpper.BatchQuery;
import com.centaline.trans.utils.CollectionUtils;
import com.centaline.trans.utils.CollectionUtils.Converter;

/**
 * 获取案件总览部分信息接口
 * 
 * @author yinjf2
 *
 */
public class QuickQueryGetCaseOverviewInfoServiceImpl implements
		CustomDictService {

	@Autowired
	protected QuickQueryJdbcTemplate jdbcTemplate;
	@Autowired
	private UamBasedataService uamBasedataService;

	private String dictType_caseProperty;

	private String dictType_caseStatus;
	/**
	 * 新建批量查询-1批次1000条
	 */
	private QuickQueryBatchWarpper batchWarpper = new QuickQueryBatchWarpper(
		new BatchQuery<Map<String, Object>>() {
			public List<Map<String, Object>> query(List<Map<String, Object>> keys) {
				StringBuilder[] joins = CollectionUtils.join(keys, new String[]{"','"}, new String[]{"CASE_CODE"});
				// 设置条件参数
				Map<String, Object> paramMap = new HashMap<String, Object>();
				// 查询上家信息
				String sql = "SELECT CASE_CODE,GUEST_NAME as SELLER,GUEST_PHONE as sellerMobile FROM sctrans.T_TG_GUEST_INFO WHERE CASE_CODE in ('"+joins[0].toString()+"') AND TRANS_POSITION = '30006001'";
				// 根据案件编号集合查询上家姓名、上家手机号
				List<Map<String, Object>> sellerMapList = jdbcTemplate.queryForList(sql, paramMap);
				CollectionUtils.merge(sellerMapList, keys, new String[]{"CASE_CODE"});
				
				// 查询下家信息
				sql = "SELECT CASE_CODE,GUEST_NAME as BUYER,GUEST_PHONE as buyerMobile FROM sctrans.T_TG_GUEST_INFO WHERE CASE_CODE in ('"+joins[0].toString()+"') AND TRANS_POSITION ='30006002'";
				// 根据案件编号集合查询下家姓名、下家手机号
				List<Map<String, Object>> buyerMapList = jdbcTemplate.queryForList(sql,paramMap);
				CollectionUtils.merge(buyerMapList, keys, new String[]{"CASE_CODE"});
				
				// 根据案件编号集合获取交易顾问id、姓名、手机号
				sql = "SELECT c.CASE_CODE,u.ID as id,u.MOBILE as mobile,u.REAL_NAME as FONT_NAME FROM sctrans.SYS_USER u inner join sctrans.T_TO_CASE c on u.ID = c.LEADING_PROCESS_ID AND c.CASE_CODE in ('"+joins[0].toString()+"')";
				List<Map<String, Object>> tradeConsultantMapList = jdbcTemplate.queryForList(sql, paramMap);
				CollectionUtils.merge(tradeConsultantMapList, keys, new String[]{"CASE_CODE"});
				
				//根据caseCode集合获取红灯锁记录
				sql= "SELECT COUNT(RED_LOCK) AS RED_COUNT, CASE_CODE FROM sctrans.T_TO_TRANS_PLAN WHERE RED_LOCK='1' AND CASE_CODE IN ('"+joins[0].toString()+"') GROUP BY CASE_CODE";
				List<Map<String, Object>> redLockMapList = jdbcTemplate.queryForList(sql, paramMap);
				CollectionUtils.merge(redLockMapList, keys, new String[]{"CASE_CODE"});
				
				// 根据案件编号获取合作顾问姓名
				// 根据编号查询 案件状态（已分单、未分单等）
				sql="SELECT STUFF((SELECT '/'+u.real_name FROM sctrans.sys_user u INNER JOIN sctrans.T_TG_SERV_ITEM_AND_PROCESSOR p ON  p.PROCESSOR_ID = u.id WHERE p.CASE_CODE = c.CASE_CODE AND u.id <> c.LEADING_PROCESS_ID  FOR XML PATH('')),1,1,'') as PROCESSOR_ID,c.CASE_CODE ";
				sql+="FROM sctrans.T_TO_CASE c WHERE c.CASE_CODE in('"+joins[0].toString()+"') ";
				List<Map<String, Object>> processorNameMapList = jdbcTemplate.queryForList(sql, paramMap);
				CollectionUtils.merge(processorNameMapList, keys, new String[]{"CASE_CODE"});
				CollectionUtils.convert(keys,converters);
				return keys;
			}

		}, 1000);

	private Map<String,Converter<String,Object>> converters = new HashMap<String,Converter<String,Object>>();
	public QuickQueryGetCaseOverviewInfoServiceImpl() {
		converters.put("CASE_PROPERTY_SHOW", new Converter<String,Object>(){
			public Object convert(Map<String,Object> to) {
				Object casePropertyKey = to.get("CASE_PROPERTY");
				if (casePropertyKey == null) {
					return null;
				}
				String casePropertyShow = "未分单";
				if (!"A".equals(casePropertyKey.toString())) {
					casePropertyShow = uamBasedataService.getDictValue(dictType_caseProperty,casePropertyKey.toString());
				}
				return casePropertyShow;
			};
		});
		converters.put("STATUS", new Converter<String,Object>(){
			public Object convert(Map<String,Object> to) {
				Object statusKey = to.get("STATUS_OLD");
				if (statusKey == null) {
					return null;
				}
				// 根据编号查询 案件状态（已分单、未分单等）
				String status = "未分单";
				if (!"A".equals(statusKey.toString())) {
					status = uamBasedataService.getDictValue(dictType_caseStatus,statusKey.toString());
				}
				return status;
			};
		});
	}
	
	
	@Override
	public List<Map<String, Object>> findDicts(List<Map<String, Object>> keys) {
		return batchWarpper.batchWarp(keys);
	}

	public String getDictType_caseProperty() {
		return dictType_caseProperty;
	}

	public void setDictType_caseProperty(String dictType_caseProperty) {
		this.dictType_caseProperty = dictType_caseProperty;
	}

	public String getDictType_caseStatus() {
		return dictType_caseStatus;
	}

	public void setDictType_caseStatus(String dictType_caseStatus) {
		this.dictType_caseStatus = dictType_caseStatus;
	}

	@Override
	public Boolean getIsBatch() {
		return true;
	}

}
