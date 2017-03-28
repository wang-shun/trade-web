package com.centaline.trans.cases.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.centaline.trans.cases.entity.ToCaseInfo;
import com.centaline.trans.cases.entity.ToCaseInfoCountVo;
import com.centaline.trans.common.MyBatisRepository;

@MyBatisRepository
public interface ToCaseInfoMapper {
	int deleteByPrimaryKey(Long pkid);

	int insert(ToCaseInfo record);

	int insertSelective(ToCaseInfo record);

	ToCaseInfo selectByPrimaryKey(Long pkid);

	int updateByPrimaryKey(ToCaseInfo record);
	
	int updateCaseInfoByOrgId(Map<String, Object> param);
	
	int updateCaseInfoByAngetId(Map<String, Object> param);		

	ToCaseInfo findToCaseInfoByCaseCode(String caseCode);

	Integer queryCountCasesByUserId(String userId);

	Integer queryCountMonthCasesByUserId(String userId);

	Integer queryCountUnTransCasesByUserId(String userId);

	/**
	 * 统计 接单,签约,过户,结案
	 * 
	 * @param userId
	 * @return ToCaseInfoCountVo
	 */
	ToCaseInfoCountVo countToCaseInfoById(String userId);

	/**
	 * 功能：交易单编号查询
	 * 
	 * @author zhangxb16
	 */
	public String findcaseCodeByctmCode(String ctmCode);

	/**
	 * 获取所有的统计数据
	 * 
	 * @return
	 */
	ToCaseInfoCountVo getToCaseInfoCountAll();

	/**
	 * 接单数统计查询
	 * 
	 * @param orgId
	 * @return
	 */
	ToCaseInfoCountVo countToCaseInfoByOrgId(String orgId);

	int isExistCaseCode(String caseCode);

	List<HashMap<String, Object>> selectBusiarbyGroupid(String groupId);

	/**
	 * 根据ctmCode 修改targetCode
	 * 
	 * @param param
	 * @return
	 */
	Integer updateByTragertCode(Map<String, Object> param);

	/**
	 * 导入单个案件
	 * @param ctmCode
	 * @return
	 */
	Integer exportCTMCase(String ctmCode);
}