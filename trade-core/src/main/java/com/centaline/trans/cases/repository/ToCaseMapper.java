package com.centaline.trans.cases.repository;

import java.util.List;

import com.centaline.trans.cases.entity.ToCase;
import com.centaline.trans.cases.entity.ToCaseInfoCountVo;
import com.centaline.trans.cases.entity.ToOrgVo;
import com.centaline.trans.common.MyBatisRepository;

@MyBatisRepository
public interface ToCaseMapper {
    int deleteByPrimaryKey(Long pkid);

    int insert(ToCase record);

    int insertSelective(ToCase record);

    ToCase selectByPrimaryKey(Long pkid);

    int updateByPrimaryKeySelective(ToCase record);

    int updateByPrimaryKey(ToCase record);
    
    int updateByCaseCodeSelective(ToCase record);
    

    ToCase findToCaseByCaseCode(String caseCode);
    int findToLoanAgentByCaseCode(String caseCode);

	/**
	 * 查询统计
	 * @param toCase
	 * @return ToCaseInfoCountVo
	 */
	ToCaseInfoCountVo queryConutCase(ToCase toCase);

	/**
	 * 接单数统计查询
	 * @param toCase
	 * @return ToCaseInfoCountVo
	 */
	ToCaseInfoCountVo queryConutCaseByOrg(ToCase toCase);

	/**
	 * 查询接单数
	 * @param orgId
	 * @return ToCaseInfoCountVo
	 */
	//ToCaseInfoCountVo countToCaseInfoByOrgId(String orgId);

	ToCaseInfoCountVo countToCaseInfoByOrgId(ToCase toCase);

	/**
	 * 获取组织
	 * @return List<ToCase>
	 */
	List<ToCase> getOrgId();

	/**
	 * 接单数多条统计数据查询
	 * @param orgId
	 * @return
	 */
	List<ToCaseInfoCountVo> countToCaseInfoListByOrgId(ToCase toCase);

	/**
	 * 查询统计数据
	 * @param orgList
	 * @return
	 */
	List<ToCaseInfoCountVo> countToCaseInfoListByOrgList(List<String> orgList);

	/**
	 * 统计数据列表
	 * @param strArrayList
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	int countToCaseInfoByOrgList(List<String> strArrayList,
			String startDate, String endDate);

	/**
	 * 初始化统计数据
	 * @param orgId
	 * @return
	 */
	int initCountToCaseInfoByOrgId(ToCase toCase);

	/**
	 * 获取所有org
	 * @return
	 */
	List<ToOrgVo> getOrgIdAll(String dep);

	/**
	 * 获取红灯数
	 * @param orgIdList
	 * @param strNum
	 * @param endNum
	 * @return
	 */
	int getRedcountByOrgList(List<String> orgIdList, String strNum,
			String endNum);

	/**
	 * 查询统计数
	 * @param idList
	 * @return
	 */
	List<ToCaseInfoCountVo> countToCaseInfoListByIdList(List<String> idList);

	/**
	 * 查询统计
	 * @param idList
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	int countToCaseInfoByIdList(List<String> idList, String startDate,
			String endDate);

	/**
	 * 获取红灯数
	 * @param idList
	 * @param strNum
	 * @param endNum
	 * @return
	 */
	int getRedcountByIdList(List<String> idList, String strNum, String endNum);
	
	
}