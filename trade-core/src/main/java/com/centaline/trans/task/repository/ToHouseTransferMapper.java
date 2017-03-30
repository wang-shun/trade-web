package com.centaline.trans.task.repository;

import java.util.List;

import com.centaline.trans.cases.entity.ToCase;
import com.centaline.trans.cases.entity.ToCaseInfoCountVo;
import com.centaline.trans.common.MyBatisRepository;
import com.centaline.trans.task.entity.ToHouseTransfer;

@MyBatisRepository
public interface ToHouseTransferMapper {
    int deleteByPrimaryKey(Long pkid);

    int insert(ToHouseTransfer record);

    int insertSelective(ToHouseTransfer record);

    ToHouseTransfer selectByPrimaryKey(Long pkid);

    int updateByPrimaryKeySelective(ToHouseTransfer record);

    int updateByPrimaryKeyWithBLOBs(ToHouseTransfer record);

    int updateByPrimaryKey(ToHouseTransfer record);
    
    ToHouseTransfer findToGuoHuByCaseCode(String caseCode);

    /**
     * 统计过户
     * @param userId
     * @return
     */
	ToCaseInfoCountVo countToHouseTransferById(String userId);

	/**
	 * 查询统计过户
	 * @param toCase
	 * @return
	 */
	ToCaseInfoCountVo queryCountToHouseTransferById(ToCase toCase);

	/**
	 * 过户数统计查询
	 * @param orgId
	 * @return
	 */
	ToCaseInfoCountVo countToHouseTransferByOrgId(ToCase toCase);

	/**
	 * 过户统计查询
	 * @param toCase
	 * @return
	 */
	ToCaseInfoCountVo queryCountToHouseTransferByOrg(ToCase toCase);
	/**
	 * 过户多条统计查询
	 * @param toCase
	 * @return
	 */
	List<ToCaseInfoCountVo> countToHouseTransferListByOrgId(ToCase toCase);

	/**
	 * 统计数据查询
	 * @param orgList
	 * @return
	 */
	List<ToCaseInfoCountVo> countToHouseTransferListByOrgList(
			List<String> orgList);

	/**
	 * 查询统计数
	 * @param strList
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	int countToHouseTransferByOrgList(List<String> strList, String startDate,
			String endDate);

	/**
	 * 初始化统计数据
	 * @param toCase
	 * @return
	 */
	int initCountToHouseTransferByOrgId(ToCase toCase);

	/**
	 * 查询统计
	 */
	List<ToCaseInfoCountVo> countToHouseTransferListByIdList(List<String> idList);

	/**
	 * 查询统计
	 * @param idList
	 * @param startDate
	 * @param endDate
	 */
	int countToHouseTransferByIdList(List<String> idList, String startDate,
			String endDate);
}