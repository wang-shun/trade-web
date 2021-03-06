package com.centaline.trans.mgr.service;

import com.centaline.trans.mgr.vo.TsSupVo;

public interface TsSupService {
	
	/**
	 * 保存供应商信息
	 * @param tsSup
	 */
	void saveTsSup(TsSupVo tsSup);
	
	/**
	 * 查询供应商信息
	 * @param id
	 * @return
	 */
	TsSupVo findTsSupById(Long id);
	
	/**
	 * 删除供应商信息
	 * @param id
	 */
	void deleteTsSup(Long id);
	/**
	 * 根据供应商编码获取供应商名称
	 * @param finOrgCode
	 * @return
	 */
	String getFinOrgByFinCode(String finOrgCode);
}