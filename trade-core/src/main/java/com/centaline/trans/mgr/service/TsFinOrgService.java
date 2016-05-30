package com.centaline.trans.mgr.service;

import java.util.List;

import com.centaline.trans.mgr.entity.TsFinOrg;

public interface TsFinOrgService {
	/**
	 * 根据finOrgname模糊查询
	 * 
	 * @param orgCode
	 * @return
	 */
	List<TsFinOrg> queryFinOrgNameLike(String finOrgName);

	/**
	 * 保存金融机构
	 * 
	 * @param tsFinOrg
	 */
	void saveTsFinOrg(TsFinOrg tsFinOrg);

	/**
	 * 查询egu或非egu的分行列表
	 * 
	 * @param flag
	 * @return
	 */
	List<TsFinOrg> findParentBankList(String flag,String tag);

	/**
	 * 根据分行查询egu或非egu的支行列表
	 * 
	 * @param flag
	 * @param faFinOrgCode
	 * @return
	 */
	List<TsFinOrg> findBankListByParentCode(String flag, String faFinOrgCode,String tag);

	/**
	 * 根据金融机构编码查询金融机构
	 * 
	 * @param orgCode
	 * @return
	 */
	TsFinOrg findBankByFinOrg(String orgCode);

	/**
	 * 根据id查询金融机构
	 * 
	 * @param pkid
	 * @return
	 */
	TsFinOrg findById(Long pkid);

	/**
	 * 查询评估公司列表
	 * 
	 * @return
	 */
	List<TsFinOrg> findEvaCompany();

	/**
	 * 查询资金监管机构列表
	 * 
	 * @return
	 */
	List<TsFinOrg> findSpvCompany();

	/**
	 * 删除金融机构
	 * 
	 * @param pkid
	 */
	void deleteTsFinOrg(Long pkid);

	/**
	 * 查询支行信息
	 * 
	 * @return
	 */
	List<TsFinOrg> findBranchBank();

	/**
	 * 查询所有金融机构
	 * 
	 * @return
	 */
	List<TsFinOrg> findAllFinOrg();

	/**
	 * 查询公积金贷款供应商
	 * 
	 * @return
	 */
	List<TsFinOrg> findPrfLoanBank();

	/**
	 * 查询金融贷款机构
	 * 
	 * @return
	 */
	List<TsFinOrg> findFinCompany();

}