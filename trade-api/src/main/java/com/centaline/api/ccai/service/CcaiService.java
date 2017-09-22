package com.centaline.api.ccai.service;
import com.centaline.api.ccai.vo.CaseImport;
import com.centaline.api.common.vo.CcaiServiceResult;

public interface CcaiService {
	/**
	 * 导入一个ccai案件
	 * @param acase ccai案件信息
	 * @return
	 */
	CcaiServiceResult importCase(CaseImport acase);
	/**
	 * 案件信息修改
	 * 仅修改
	 * @param acase
	 * @return
	 */
	CcaiServiceResult updateCase(CaseImport acase);
	/**
	 * 案件信息修改
	 * 同时将流程设置到权证经理审核环节
	 * @param acase
	 * @return
	 */
	CcaiServiceResult updateCaseAndFlow(CaseImport acase);
	
	//TODO 后续根据需要进行完善
	/**
	 * 判断指定CCAI成交案件编号是否存在
	 * @param ccaiCode
	 * @return
	 */
	boolean isExistCcaiCode(String ccaiCode);
}
