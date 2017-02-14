package com.centaline.trans.common.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.aist.common.quickQuery.service.CustomDictService;
import com.aist.common.quickQuery.utils.QuickQueryJdbcTemplate;
import com.centaline.trans.utils.CollectionUtils;

/**
 * 通过案件编号获取上家姓名、上家手机号、下家姓名、下家手机号服务接口
 * 
 * @author yinjf2
 *
 */
public class QuickQueryGetSellerAndBuyerAndProcessorInfoServiceImpl implements
		CustomDictService {

	@Autowired
	protected QuickQueryJdbcTemplate jdbcTemplate;

	private String sellerTransPosition;

	private String buyerTransPosition;

	@Override
	public List<Map<String, Object>> findDicts(List<Map<String, Object>> keys) {
		StringBuilder[] joins = CollectionUtils.join(keys, new String[]{"','"}, new String[]{"CASE_CODE"});
		// 设置条件参数
		Map<String, Object> paramMap = new HashMap<String, Object>();
		// 查询上家信息
		String sql = "SELECT CASE_CODE,GUEST_NAME as SELLER,GUEST_PHONE as sellerMobile FROM sctrans.T_TG_GUEST_INFO WHERE CASE_CODE in (:caseCode) AND TRANS_POSITION = :sellerTransPosition";
		String finalSql = sql.replace(":caseCode","'"+joins[0].toString()+"'").replace(":sellerTransPosition", "'"+sellerTransPosition+"'");
		// 根据案件编号集合查询上家姓名、上家手机号
		List<Map<String, Object>> sellerMapList = jdbcTemplate.queryForList(finalSql, paramMap);
		CollectionUtils.merge(sellerMapList, keys, new String[]{"CASE_CODE"});
		
		// 查询下家信息
		sql = "SELECT CASE_CODE,GUEST_NAME as BUYER,GUEST_PHONE as buyerMobile FROM sctrans.T_TG_GUEST_INFO WHERE CASE_CODE in (:caseCode) AND TRANS_POSITION = :buyerTransPosition";
		finalSql = sql.replace(":caseCode","'"+joins[0].toString()+"'").replace(":buyerTransPosition", "'"+buyerTransPosition+"'");
		// 根据案件编号集合查询下家姓名、下家手机号
		List<Map<String, Object>> buyerMapList = jdbcTemplate.queryForList(finalSql,paramMap);
		CollectionUtils.merge(buyerMapList, keys, new String[]{"CASE_CODE"});
		
		// 根据案件编号集合获取交易顾问id、姓名、手机号
		sql = "SELECT c.CASE_CODE,u.ID as id,u.MOBILE as mobile,u.REAL_NAME as FONT_NAME FROM sctrans.SYS_USER u inner join sctrans.T_TO_CASE c on u.ID = c.LEADING_PROCESS_ID AND c.CASE_CODE in (:caseCode)";
		finalSql = sql.replace(":caseCode","'"+joins[0].toString()+"'");
		List<Map<String, Object>> tradeConsultantMapList = jdbcTemplate.queryForList(finalSql, paramMap);
		CollectionUtils.merge(tradeConsultantMapList, keys, new String[]{"CASE_CODE"});
		
		//根据caseCode集合获取红灯锁记录
		sql= "SELECT COUNT(RED_LOCK) AS RED_COUNT, CASE_CODE FROM sctrans.T_TO_TRANS_PLAN WHERE RED_LOCK='1' AND CASE_CODE IN (:caseCode) GROUP BY CASE_CODE";
		finalSql = sql.replace(":caseCode","'"+joins[0].toString()+"'");
		List<Map<String, Object>> redLockMapList = jdbcTemplate.queryForList(finalSql, paramMap);
		CollectionUtils.merge(redLockMapList, keys, new String[]{"CASE_CODE"});
		
		return keys;
	}

	public String getSellerTransPosition() {
		return sellerTransPosition;
	}

	public void setSellerTransPosition(String sellerTransPosition) {
		this.sellerTransPosition = sellerTransPosition;
	}

	public String getBuyerTransPosition() {
		return buyerTransPosition;
	}

	public void setBuyerTransPosition(String buyerTransPosition) {
		this.buyerTransPosition = buyerTransPosition;
	}

	@Override
	public Boolean getIsBatch() {
		return true;
	}

}
