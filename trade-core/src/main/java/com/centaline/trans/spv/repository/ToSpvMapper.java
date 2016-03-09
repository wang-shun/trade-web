package com.centaline.trans.spv.repository;

import java.util.List;

import com.centaline.trans.cases.entity.ToCase;
import com.centaline.trans.cases.entity.ToCaseInfoCountVo;
import com.centaline.trans.common.MyBatisRepository;
import com.centaline.trans.spv.entity.ToSpv;

@MyBatisRepository
public interface ToSpvMapper {
    int deleteByPrimaryKey(Long pkid);

    int insert(ToSpv record);

    int insertSelective(ToSpv record);

    ToSpv selectByPrimaryKey(Long pkid);

    int updateByPrimaryKeySelective(ToSpv record);

    int updateByPrimaryKeyWithBLOBs(ToSpv record);

    int updateByPrimaryKey(ToSpv record);

    ToSpv queryToSpvByCaseCode(String caseCode);
    
    ToSpv findToSpvBySpvCode(String spvCode);

    /**
     * 统计签约数
     * @param userId
     * @return
     */
	ToCaseInfoCountVo countToSignById(String userId);

	/**
	 * 查询统计签约数
	 * @param toCase
	 * @return
	 */
	ToCaseInfoCountVo queryCountToSignById(ToCase toCase);

	/**
	 * 签约数统计查询
	 * @param orgId
	 * @return
	 */
	ToCaseInfoCountVo countToSignByOrgId(ToCase toCase);

	/**
	 * 签约数统计查询
	 * @param toCase
	 * @return
	 */
	ToCaseInfoCountVo queryCountToSignByOrg(ToCase toCase);

	/**
	 * 签约数多条统计查询
	 * @param toCase
	 * @return
	 */
	List<ToCaseInfoCountVo> countToSignListByOrgId(ToCase toCase);

	/**
	 * 查询统计数据
	 * @param orgList
	 * @return
	 */
	List<ToCaseInfoCountVo> countToSignListByOrgList(List<String> orgList);

	/**
	 * 查询统计数
	 * @param strArrayList
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	int countToSignByOrgList(List<String> strArrayList, String startDate,
			String endDate);

	/**
	 * 初始化统计数据
	 * @param orgId
	 * @return
	 */
	int initCountToSignByOrgId(ToCase toCase);

	/**
	 * 查询统计数
	 * @param idList
	 * @return
	 */
	List<ToCaseInfoCountVo> countToSignListByIdList(List<String> idList);

	/**
	 * 查询统计数
	 * @param idList
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	int countToSignByIdList(List<String> idList, String startDate,
			String endDate);

}