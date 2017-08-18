package com.centaline.trans.cases.service;
/**
 * 提供CCAI与案件相关的服务
 * @author yinchao
 *
 */

import com.centaline.trans.cases.entity.CcaiImportCase;
import com.centaline.trans.cases.entity.CcaiServiceResult;

public interface CcaiService {
	/**
	 * 导入一个ccai案件
	 * @param acase ccai案件信息
	 * @return
	 */
	public CcaiServiceResult importCase(CcaiImportCase acase);
	/**
	 * 案件信息修改
	 * 仅修改
	 * @param acase
	 * @return
	 */
	public CcaiServiceResult updateCase(CcaiImportCase acase);
	/**
	 * 案件信息修改
	 * 同时将流程设置到权证经理审核环节
	 * @param acase
	 * @return
	 */
	public CcaiServiceResult updateCaseAndFlow(CcaiImportCase acase);
	
	//TODO 后续根据需要进行完善
	/**
	 * 判断指定CCAI成交案件编号是否存在
	 * @param ccaiCode
	 * @return
	 */
	public boolean isExistCcaiCode(String ccaiCode);
	
}
