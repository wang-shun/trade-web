package com.centaline.trans.common.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.aist.common.quickQuery.service.CustomDictService;
import com.aist.common.quickQuery.utils.QuickQueryJdbcTemplate;
import com.aist.uam.basedata.remote.UamBasedataService;
import com.centaline.trans.common.repository.utils.QuickQueryBatchWarpper;
import com.centaline.trans.common.repository.utils.QuickQueryBatchWarpper.BatchQuery;

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

	/**
	 * 通过案件编号获取上家姓名、上家手机号、下家姓名、下家手机号服务接口
	 */
	@Autowired
	private QuickQueryGetSellerAndBuyerAndProcessorInfoServiceImpl quickQuerySellerAndBuyerAndProcessorInfo;

	@Autowired
	private UamBasedataService uamBasedataService;

	private String dictType_caseProperty;

	private String dictType_caseStatus;

	private static String sql = "(SELECT u.real_name as processorName FROM sctrans.T_TG_SERV_ITEM_AND_PROCESSOR p inner join sctrans.sys_user u on p.PROCESSOR_ID = u.id WHERE CASE_CODE = :caseCode)"
			+ " EXCEPT"
			+ "(SELECT U.REAL_NAME FROM   sctrans.SYS_USER U    inner join  sctrans.T_TO_CASE C on U.ID=C.LEADING_PROCESS_ID  WHERE C.CASE_CODE = :caseCode)";

	/**
	 * 新建批量查询-1批次1000条
	 */
	private QuickQueryBatchWarpper batchWarpper = new QuickQueryBatchWarpper(
			new BatchQuery<Map<String, Object>>() {

				@Override
				public List<Map<String, Object>> query(
						List<Map<String, Object>> keys) {
					Map<String, Object> params = null;

					for (Map<String, Object> keyer : keys) {
						String casePropertyShow = "";
						String status = "";
						String processorNames = "";
						Object caseCodeKey = keyer.get("CASE_CODE");

						// 根据编号查询 案件性质（在途、爆单等）
						try {
							Object casePropertyKey = keyer.get("CASE_PROPERTY");
							Object statusKey = keyer.get("STATUS_OLD");

							if (casePropertyKey != null) {
								if (!casePropertyKey.equals("A")) {
									casePropertyShow = uamBasedataService
											.getDictValue(
													dictType_caseProperty,
													casePropertyKey.toString());
								} else {
									casePropertyShow = "未分单";
								}

								keyer.put("CASE_PROPERTY_SHOW",
										casePropertyShow);
							}

							// 根据编号查询 案件状态（已分单、未分单等）
							if (statusKey != null) {
								if (!statusKey.equals("A")) {
									status = uamBasedataService.getDictValue(
											dictType_caseStatus,
											statusKey.toString());
								} else {
									status = "未分单";
								}

								keyer.put("STATUS", status);
							}

							// 根据案件编号获取合作顾问姓名
							if (caseCodeKey != null) {
								params = new HashMap<String, Object>();
								params.put("caseCode", caseCodeKey.toString());

								List<Map<String, Object>> processorNameMapList = jdbcTemplate
										.queryForList(sql, params);

								if (processorNameMapList != null
										&& processorNameMapList.size() > 0) {

									for (Map processorNameMap : processorNameMapList) {
										String processorName = processorNameMap
												.get("processorName")
												.toString();
										processorNames += processorName + "/";
									}

									keyer.put("PROCESSOR_ID", processorNames
											.substring(0, processorNames
													.lastIndexOf("/")));
								}

							}

						} catch (Exception e) {
						}

					}

					// 根据案件编号集合查询案件编号、产证地址、申请人姓名、申请人所在组、申请人所在服务部
					quickQuerySellerAndBuyerAndProcessorInfo.findDicts(keys);

					return keys;
				}

			}, 1000);

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
