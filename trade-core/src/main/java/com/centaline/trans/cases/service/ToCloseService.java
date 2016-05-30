package com.centaline.trans.cases.service;

import java.util.List;

import com.centaline.trans.cases.entity.ToCase;
import com.centaline.trans.cases.entity.ToCaseInfoCountVo;

public interface ToCloseService {

	/**
	 * 统计结案
	 * @param userId
	 * @return
	 */
	ToCaseInfoCountVo countToCloseById(String userId);

	/**
	 * 查询统计结案
	 * @param toCase
	 * @return
	 */
	ToCaseInfoCountVo queryCountToCloseById(ToCase toCase);

	/**
	 * 结案数统计查询
	 * @param orgId
	 * @return
	 */
	ToCaseInfoCountVo countToCloseByOrgId(String orgId,String startDate,String endDate);

	/**
	 * 结案统计查询
	 * @param toCase
	 * @return
	 */
	ToCaseInfoCountVo queryCountToCloseByOrg(ToCase toCase);

	/**
	 * 结案多条统计查询
	 * @param toCase
	 * @return
	 */
	List<ToCaseInfoCountVo> countToCloseListByOrgId(String orgId);

	/**
	 * 查询统计数据
	 * @param orgList
	 * @return
	 */
	List<ToCaseInfoCountVo> countToCloseListByOrgList(List<String> orgList);
	
	/**
	 * 查询统计数
	 * @param orgList
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	int countToCloseByOrgList(List<String> orgList, String startDate,
			String endDate);

	/**
	 * 初始化统计数据
	 * @param orgId
	 * @return
	 */
	int initCountToCloseByOrgId(String orgId,String createTime);

	/**
	 * 查询统计
	 * @param idList
	 * @return
	 */
	List<ToCaseInfoCountVo> countToCloseListByIdList(List<String> idList);

	/**
	 * 查询统计
	 * @param idList
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	int countToCloseByIdList(List<String> idList, String startDate,
			String endDate);

}
