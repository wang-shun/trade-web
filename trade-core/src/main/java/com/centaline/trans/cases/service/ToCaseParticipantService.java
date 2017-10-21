package com.centaline.trans.cases.service;

import java.util.List;

import com.aist.uam.userorg.remote.vo.User;
import com.centaline.trans.cases.entity.ToCaseParticipant;

/**
 * @Description:案件参与人相关服务
 * @author：jinwl6
 * @date:2017年9月15日
 * @version:
 */
public interface ToCaseParticipantService {
	
	/**
	 * 根据 条件查询案件参与人信息
	 * @param caseCode
	 * @return
	 * @author jinwl6
	 */
	List<ToCaseParticipant> findToCaseParticipantByCondition(ToCaseParticipant toCaseParticipant);
	
	/**
	 * 更新案件参与人
	 * @param caseCode
	 * @param user
	 * @return
	 * @author wbcaiyx
	 */
	int updateCaseParticipant(String caseCode, User user ,User manager);

	/**
	 * 获取案件主办人信息
	 * 案件有贷款则为贷款权证
	 * 案件无贷款则为过户权证
	 * @param caseCode 案件编号
	 * @return
	 * @author：yinchao
	 */
	User findCaseOwner(String caseCode);

	/**
	 * 获取案件贷款权证信息
	 * 如果没有贷款则返回null
	 * @param caseCode 案件编号
	 * @return
	 * @author：yinchao
	 */
	User findCaseLoan(String caseCode);

	/**
	 * 获取案件内勤信息
	 * @param caseCode 案件编号
	 * @return
	 * @author：yinchao
	 */
	User findCaseAssistant(String caseCode);

	/**
	 * 获取案件过户权证信息
	 * @param caseCode 案件编号
	 * @return
	 * @author：yinchao
	 */
	User findCaseWarrant(String caseCode);

	/**
	 * 获取案件的权证经理
	 * 有贷款则为贷款的权证经理
	 * 否则为过户的权证经理
	 * @param caseCode 案件编号
	 * @return
	 * @author：yinchao
	 */
	User findCaseLeader(String caseCode);

}
