package com.centaline.trans.common.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.aist.common.quickQuery.service.CustomDictService;
import com.aist.common.quickQuery.utils.QuickQueryJdbcTemplate;

/**
 * 根据案件编号集合获取案件编号、产证地址、申请人姓名、申请人所在组、申请人所在服务部信息服务接口
 * 
 * @author yinjf2
 *
 */
public class QuickQueryGetUserAndOrgInfoByCaseCodeListServiceImpl implements
		CustomDictService {

	@Autowired
	protected QuickQueryJdbcTemplate jdbcTemplate;

	@Override
	public List<Map<String, Object>> findDicts(List<Map<String, Object>> keys) {
		List<String> caseCodeList = new ArrayList<String>();

		for (Map<String, Object> keyer : keys) {
			Object caseCodeKey = keyer.get("caseCode");

			// 先将案件编号统一放到集合里边
			caseCodeList.add(caseCodeKey.toString());
		}

		String sql = "SELECT c.CASE_CODE AS caseCode,c.PKID,p.PROPERTY_ADDR AS propertyAddress,u.REAL_NAME AS realName,o.ORG_NAME AS orgName,"
				+ "(SELECT ORG_NAME FROM sctrans.SYS_ORG WHERE ID = o.PARENT_ID) AS parentOrgName"
				+ " FROM sctrans.T_TO_CASE c"
				+ " LEFT JOIN sctrans.T_TO_PROPERTY_INFO p ON p.CASE_CODE = c.CASE_CODE"
				+ " LEFT JOIN sctrans.SYS_USER u ON u.ID = c.LEADING_PROCESS_ID"
				+ " LEFT JOIN sctrans.SYS_ORG o ON o.ID = c.ORG_ID"
				+ " WHERE c.CASE_CODE IN (:caseCodeList)";

		// 设置条件参数
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("caseCodeList", caseCodeList);

		// 根据案件编号集合查询案件编号、产证地址、申请人姓名、申请人所在组、申请人所在服务部
		List<Map<String, Object>> resultMapList = jdbcTemplate.queryForList(
				sql, paramMap);

		if (resultMapList != null && resultMapList.size() > 0) {
			for (Map<String, Object> keyer : keys) {
				String caseCode = keyer.get("caseCode").toString();

				for (Map<String, Object> resultMap : resultMapList) {
					String rCaseCode = (String) resultMap.get("caseCode");

					// 将查询到的数据与每行的记录数据进行绑定
					if (caseCode.equals(rCaseCode)) {
						// 将案件标识放入keyer中
						keyer.put("PKID", resultMap.get("PKID"));

						// 将产证地址放入keyer中
						keyer.put("propertyAddress",
								resultMap.get("propertyAddress"));

						// 将申请人姓名放入keyer中
						keyer.put("realName", resultMap.get("realName"));

						// 将申请人所在组放入keyer中
						keyer.put("orgName", resultMap.get("orgName"));

						// 将申请人所在服务部放入keyer中
						keyer.put("parentOrgName",
								resultMap.get("parentOrgName"));
					}
				}
			}
		}

		return keys;
	}

	@Override
	public Boolean getIsBatch() {
		return true;
	}

}
