/**
 * 
 */
package com.centaline.trans.bizwarn.service;

import com.centaline.trans.bizwarn.entity.BizWarnInfo;

/**
 * 商贷预警业务接口
 * 
 * @author yinjf2
 * @date 2016年8月5日
 */
public interface BizWarnInfoService {

	/**
	 * 获取本组所有的状态为生效的商贷预警案件数
	 * 
	 * @param userLoginName
	 *            用户登录名
	 * 
	 * @return 返回商贷预警案件个数
	 */
	public int getAllBizwarnCountByTeam(String userLoginName);

	/**
	 * 获取本区所有的状态为生效的商贷预警案件数
	 * 
	 * @param currentOrgId
	 *            当前用户的机构id
	 * 
	 * @return 返回商贷预警案件个数
	 */
	public int getAllBizwarnCountByDistinct(String currentOrgId);

	/**
	 * 通过案件编号获取商贷预警信息
	 * 
	 * @param caseCode
	 *            案件编号
	 * @return 商贷预警对象信息
	 */
	public BizWarnInfo getBizWarnInfoByCaseCode(String caseCode);

	/**
	 * 保存商贷预警信息
	 * 
	 * @param bizWarnInfo
	 *            商贷预警信息对象
	 * @return 如果返回1，则说明保存成功，返回0，则说明保存失败。
	 */
	public int insertSelective(BizWarnInfo bizWarnInfo);

	/**
	 * 更新商贷预警信息
	 * 
	 * @param bizWarnInfo
	 *            商贷预警信息对象
	 * @return 如果返回1，则说明更新成功，返回0，则说明更新失败。
	 */
	public int updateByCaseCode(BizWarnInfo bizWarnInfo);

	/**
	 * 根据案件编号删除指定的商贷预警信息
	 * 
	 * @param caseCode
	 *            案件编号
	 * @return 如果返回1，则说明删除成功，返回0，则说明删除失败。
	 */
	public int deleteByCaseCode(String caseCode);

	/**
	 * 解除
	 * 
	 * @param bizWarnInfo
	 *            商贷预警对象信息
	 * @return 如果返回1，则说明解除成功;如果返回0，则说明解除失败。
	 */
	public int updateStatusByCaseCode(BizWarnInfo bizWarnInfo);
}
