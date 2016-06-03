package com.centaline.trans.cases.service;

import java.util.List;

import com.centaline.trans.cases.entity.ToCase;
import com.centaline.trans.cases.entity.ToCaseInfoCountVo;
import com.centaline.trans.cases.entity.ToOrgVo;
import com.centaline.trans.cases.vo.CaseBaseVO;

public interface ToCaseService {
	
	ToCase selectByPrimaryKey(Long pkid);
    ToCase findToCaseByCaseCode(String caseCode);
    int updateByPrimaryKey(ToCase record);
    int updateByPrimaryKeySelective(ToCase record);
    /**
     * 查询统计
     * @param toCase
     * @return ToCaseInfoCountVo
     */
	public ToCaseInfoCountVo queryConutCase(ToCase toCase);
    int updateByCaseCodeSelective(ToCase record);
    /**
     * 接单数统计查询
     * @param toCase
     * @return ToCaseInfoCountVo
     */
	ToCaseInfoCountVo queryConutCaseByOrg(ToCase toCase);
	/**
	 * 获取组织
	 * @return List<ToCase>
	 */
	List<ToCase> getOrgId();
	/**
	 * 
	 * @return
	 */
	List<ToCaseInfoCountVo> getCaseCount();

    int insertSelective(ToCase record);
    /**
     * 获取所有org
     * @return
     */
	List<ToOrgVo> getOrgIdAllByDep(String dep);
	
	/**
	 * 获取红灯数
	 * @param orgIdList
	 * @param strNum
	 * @param endNum
	 * @return
	 */
	int getRedcountByOrgList(List<String> orgIdList,String strNum,String endNum);
	/**
	 * 获取红灯数
	 * @param idList
	 * @param strNum
	 * @param endNum
	 * @return
	 */
	int getRedcountByIdList(List<String> idList, String strNum, String endNum);
	
	
	int orgChange(String caseCode ,String orgId);
	
	CaseBaseVO getCaseBaseVO(Long caseId);
	
}
