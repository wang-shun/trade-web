package com.centaline.trans.cases.service;

import com.aist.uam.auth.remote.vo.SessionUser;
import com.centaline.trans.cases.entity.TsCaseEfficient;

/**
 * 案件时效管理service接口
 * 
 * @author yinjf2
 *
 */
public interface TsCaseEfficientService {

	/**
	 * 保存案件时效管理信息
	 * 
	 * @param tsCaseEfficient
	 *            案件时效对象
	 * 
	 * @return 如果结果大于0,说明保存成功;等于0,说明保存失败。
	 */
	public int save(TsCaseEfficient tsCaseEfficient);

	/**
	 * 根据案件编号查询是否存在同样案件的时效信息
	 * 
	 * @param caseCode
	 *            案件编号
	 * @return 返回true,存在记录,返回false,不存在记录。
	 */
	public boolean isExistByCaseCode(String caseCode);

	/**
	 * 延期
	 * 
	 * @param currentUser
	 *            当前用户
	 * @param caseCode
	 *            案件编号
	 * @param partCode
	 *            环节编码
	 * @param delayDays
	 *            延期天数
	 * @param comment
	 *            延期原因
	 * @return true,延期成功;false,延期失败。
	 */
	public boolean delay(SessionUser currentUser, String caseCode, String partCode, String delayDays, String comment);
}
