package com.centaline.trans.cases.repository;

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
    
    ToCaseInfo findToCaseInfoByCaseCode(String caseCode);
    Integer queryCountCasesByUserId(String userId);
    Integer queryCountMonthCasesByUserId(String userId);
    Integer queryCountUnTransCasesByUserId(String userId);

    /**
     * 统计 接单,签约,过户,结案
     * @param userId
     * @return ToCaseInfoCountVo
     */
    ToCaseInfoCountVo countToCaseInfoById(String userId);
    
    
    /**
	 * 功能：交易单编号查询
	 * @author zhangxb16
	 */
	public String findcaseCodeByctmCode(String ctmCode);

	/**
	 * 获取所有的统计数据
	 * @return
	 */
	ToCaseInfoCountVo getToCaseInfoCountAll();

	/**
	 * 接单数统计查询
	 * @param orgId
	 * @return
	 */
	ToCaseInfoCountVo countToCaseInfoByOrgId(String orgId);
	
	
	int isExistCaseCode(String caseCode);
	
}