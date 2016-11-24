package com.centaline.trans.common.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.aist.common.quickQuery.service.CustomDictService;
import com.aist.common.quickQuery.utils.QuickQueryJdbcTemplate;
import com.centaline.trans.common.repository.utils.QuickQueryBatchWarpper;
import com.centaline.trans.common.repository.utils.QuickQueryBatchWarpper.BatchQuery;

public class QuickQueryGetTempBankInfoServiceImpl implements CustomDictService {

	@Autowired
	protected QuickQueryJdbcTemplate jdbcTemplate;

	/**
	 * 根据案件编号集合获取案件编号、产证地址、申请人姓名、申请人所在组、申请人所在服务部信息服务接口
	 */
	@Autowired
	private QuickQueryGetUserAndOrgInfoByCaseCodeListServiceImpl quickQueryGetUserAndOrgInfoService;

	/**
	 * 根据案件编号集合获取贷款银行和贷款支行信息服务接口
	 */
	@Autowired
	private QuickQueryGetMainAndBranchBankByCaseCodeListServiceImpl quickQueryGetMainAndBranchBankService;

	@Override
	public List<Map<String, Object>> findDicts(List<Map<String, Object>> keys) {

		return batchWarpper.batchWarp(keys);
	}

	/**
	 * 批量查询临时银行信息(一批次1000条)
	 */
	private QuickQueryBatchWarpper batchWarpper = new QuickQueryBatchWarpper(
			new BatchQuery<Map<String, Object>>() {

				@Override
				public List<Map<String, Object>> query(
						List<Map<String, Object>> keys) {
					for (Map<String, Object> keyer : keys) {
						String currentProcess = "";
						String tmpBankStatus = "";
						Object currentProcessKey = keyer.get("currentProcess");
						Object isSubmitKey = keyer.get("isSubmit");
						Object tmpBankStatusKey = keyer.get("tmpBankStatus");

						// 获取当前流程信息
						if (isSubmitKey != null) {
							keyer.put("currentProcess1", currentProcess);
						} else {
							if (currentProcessKey != null) {
								if ("ManagerAduit".equals(currentProcessKey
										.toString())) {
									currentProcess = "主管审批";
								} else if ("DirectorAudit".equals(currentProcessKey
										.toString())) {
									currentProcess = "总监审批";
								} else if ("SuperManagerAudit".equals(currentProcessKey
										.toString())) {
									currentProcess = "高级主管审批";
								}
							} else {
								currentProcess = "商贷办理";
							}

							keyer.put("currentProcess1", currentProcess);
						}

						// 获取当前状态信息
						if (tmpBankStatusKey != null) {
							if ("0".equals(tmpBankStatusKey.toString())) {
								tmpBankStatus = "已驳回";
							} else if ("1".equals(tmpBankStatusKey.toString())) {
								tmpBankStatus = "已通过";
							} else {
								tmpBankStatus = "审批中";
							}
						} else {
							tmpBankStatus = "审批中";
						}

						keyer.put("tmpBankStatus1", tmpBankStatus);
					}

					// 根据案件编号集合查询案件编号、产证地址、申请人姓名、申请人所在组、申请人所在服务部
					quickQueryGetUserAndOrgInfoService.findDicts(keys);

					// 根据案件编号集合获取贷款银行和贷款支行信息
					quickQueryGetMainAndBranchBankService.findDicts(keys);

					return keys;
				}

			}, 1000);

	@Override
	public Boolean getIsBatch() {
		return true;
	}

}
