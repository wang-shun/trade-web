package com.centaline.trans.common.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.aist.common.quickQuery.service.CustomDictService;
import com.aist.common.quickQuery.utils.QuickQueryJdbcTemplate;

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
		List<String> caseCodeList = new ArrayList<String>();

		for (Map<String, Object> keyer : keys) {
			Object caseCodeKey = keyer.get("CASE_CODE");

			// 先将案件编号统一放到集合里边
			caseCodeList.add(caseCodeKey.toString());
		}

		// 查询上家信息
		String sql = "select CASE_CODE as caseCode,GUEST_NAME as sellerName,GUEST_PHONE as sellerMobile FROM sctrans.T_TG_GUEST_INFO WHERE CASE_CODE in (:caseCodeList) AND TRANS_POSITION = :sellerTransPosition";
		// 设置条件参数
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("caseCodeList", caseCodeList);
		paramMap.put("sellerTransPosition", sellerTransPosition);

		// 根据案件编号集合查询上家姓名、上家手机号
		List<Map<String, Object>> sellerMapList = jdbcTemplate.queryForList(
				sql, paramMap);

		// 查询下家信息
		sql = "select CASE_CODE as caseCode,GUEST_NAME as buyerName,GUEST_PHONE as buyerMobile FROM sctrans.T_TG_GUEST_INFO WHERE CASE_CODE in (:caseCodeList) AND TRANS_POSITION = :buyerTransPosition";
		// 设置条件参数
		paramMap = new HashMap<String, Object>();
		paramMap.put("caseCodeList", caseCodeList);
		paramMap.put("buyerTransPosition", buyerTransPosition);

		// 根据案件编号集合查询下家姓名、下家手机号
		List<Map<String, Object>> buyerMapList = jdbcTemplate.queryForList(sql,
				paramMap);

		// 根据案件编号集合获取交易顾问id、姓名、手机号
		sql = "SELECT c.CASE_CODE as caseCode,u.ID as id,u.MOBILE as mobile,u.REAL_NAME as realName FROM sctrans.SYS_USER u inner join sctrans.T_TO_CASE c on u.ID = c.LEADING_PROCESS_ID AND c.CASE_CODE in (:caseCodeList)";
		// 设置条件参数
		paramMap = new HashMap<String, Object>();
		paramMap.put("caseCodeList", caseCodeList);

		List<Map<String, Object>> tradeConsultantMapList = jdbcTemplate
				.queryForList(sql, paramMap);

		// 如果上家和下家信息都查不到,直接返回
		if ((sellerMapList == null || sellerMapList.size() == 0)
				&& (buyerMapList == null || buyerMapList.size() == 0)
				&& (tradeConsultantMapList == null || tradeConsultantMapList
						.size() == 0)) {
			return keys;
		}

		// 如果上家或者下家有信息,将每一行的上家或下家信息通过案件编号绑定起来
		for (Map<String, Object> keyer : keys) {
			Object caseCodeKey = keyer.get("CASE_CODE");
			String caseCode = caseCodeKey.toString();

			if (sellerMapList != null && sellerMapList.size() > 0) {
				for (Map sellerMap : sellerMapList) {
					String caseCode_seller = (String) sellerMap.get("caseCode");

					if (caseCode.equals(caseCode_seller)) {
						keyer.put("SELLER", sellerMap.get("sellerName"));
						keyer.put("sellerMobile", sellerMap.get("sellerMobile"));
						break;
					}
				}
			}

			if (buyerMapList != null && buyerMapList.size() > 0) {
				for (Map buyerMap : buyerMapList) {
					String caseCode_buyer = (String) buyerMap.get("caseCode");

					if (caseCode.equals(caseCode_buyer)) {
						keyer.put("BUYER", buyerMap.get("buyerName"));
						keyer.put("buyerMobile", buyerMap.get("buyerMobile"));
						break;
					}
				}
			}

			if (tradeConsultantMapList != null
					&& tradeConsultantMapList.size() > 0) {
				for (Map tradeConsultantMap : tradeConsultantMapList) {
					String caseCode_tradeConsultant = (String) tradeConsultantMap
							.get("caseCode");

					if (caseCode.equals(caseCode_tradeConsultant)) {
						keyer.put("FONT_NAME",
								tradeConsultantMap.get("realName"));
						keyer.put("id", tradeConsultantMap.get("id"));
						keyer.put("tradeConsultantMobile",
								tradeConsultantMap.get("mobile"));
						break;
					}
				}
			}
		}

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
