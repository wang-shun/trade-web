package com.centaline.trans.cases.service;

import java.util.List;

import com.aist.uam.auth.remote.vo.SessionUser;
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
	
	
	
	CaseBaseVO getCaseBaseVO(Long caseId);
	/**
	 * 案件分配
	 * @param caseCode
	 * @param userId
	 * @param sessionUser
	 */
	void caseAssign(String caseCode, String userId, SessionUser sessionUser);
	/**
	 * 案件分配消息提醒
	 * @param caseCode
	 * @param userId
	 * @param sessionUser
	 */
	void sendcaseAssignMsg(String caseCode, String userId, SessionUser sessionUser);
	
	void changeTaskAssignee(String caseCode,String taskId,String userId);
	
}
