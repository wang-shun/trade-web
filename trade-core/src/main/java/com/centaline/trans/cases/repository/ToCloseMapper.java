package com.centaline.trans.cases.repository;

import java.util.List;

import com.centaline.trans.cases.entity.ToCase;
import com.centaline.trans.cases.entity.ToCaseInfoCountVo;
import com.centaline.trans.cases.entity.ToClose;
import com.centaline.trans.common.MyBatisRepository;

@MyBatisRepository
public interface ToCloseMapper {
    int deleteByPrimaryKey(Long pkid);

    int insert(ToClose record);

    int insertSelective(ToClose record);

    ToClose selectByPrimaryKey(Long pkid);

    int updateByPrimaryKeySelective(ToClose record);

    int updateByPrimaryKeyWithBLOBs(ToClose record);

    int updateByPrimaryKey(ToClose record);
    
    ToClose findToCloseByCaseCode(String caseCode);

    /**
     * 统计结案
     * @param userId
     * @return
     */
	ToCaseInfoCountVo countToCloseById(String userId);

	/**
	 * 查询统计结案
	 */
	ToCaseInfoCountVo queryCountToCloseById(ToCase toCase);

	/**
	 * 结案数统计查询
	 * @param orgId
	 * @return
	 */
	ToCaseInfoCountVo countToCloseByOrgId(ToCase toCase);

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
	List<ToCaseInfoCountVo> countToCloseListByOrgId(ToCase toCase);

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
	int initCountToCloseByOrgId(ToCase toCase);

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