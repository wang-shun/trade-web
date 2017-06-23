package com.centaline.trans.cases.service;

import java.util.List;

import com.aist.uam.auth.remote.vo.SessionUser;
import com.centaline.trans.cases.entity.ToCase;
import com.centaline.trans.cases.entity.ToCaseInfoCountVo;
import com.centaline.trans.cases.entity.ToOrgVo;
import com.centaline.trans.common.entity.CaseMergerParameter;
import com.centaline.trans.common.entity.TgServItemAndProcessor;
import com.centaline.trans.common.entity.ToPropertyInfo;
import com.centaline.trans.cases.vo.CaseBaseVO;
import com.centaline.trans.cases.vo.VCaseDistributeUserVO;

public interface ToCaseService {
	
	ToCase selectByPrimaryKey(Long pkid);
	
    ToCase findToCaseByCaseCode(String caseCode);
    
    List<ToCase> findToCaseByStatus(String status);
    
    int findToLoanAgentByCaseCode(String caseCode);
    
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
	CaseBaseVO getCaseBaseVO(String caseCode);
	/**
	 * 合流案件
	 * @throws Exception
	 */
	void updateMergeCase(CaseMergerParameter caseMergerParameter) throws Exception;
	/**
	 * 申请合流
	 * @throws Exception
	 */
	void mergeCase(CaseMergerParameter caseMergerParameter) throws Exception;
	/**
	 * 申请合流
	 * @throws Exception
	 */
	void qfMergeCase(CaseMergerParameter caseMergerParameter) throws Exception;
	/**
	 * 返回ToCase
	 * @author hejf10
	 */
	VCaseDistributeUserVO getVCaseDistributeUserVO(String caseCode);
	/**
	 * 查询是否有可以合流的案件
	 * @author hejf10 2016-12-26 11:10:54
	 * @return
	 */
	String getMergeInfoList(List<ToPropertyInfo> toPropertyInfos);
	/**
	 * 根据案件所在组获取对应的主管
	 * 
	 * @param caseCode
	 *            案件编号
	 * @return 主管的名称
	 */
	public String getManagerByCaseOwner(String caseCode);
	/**
	 * 服务编码[srv_code]和案件编号[case_code]到服务表[T_TG_SERV_ITEM_AND_PROCESSOR]中去查询交易顾问id[processor_id]
	 * @author hejf10 2017-3-16 13:55:58
	 * @param srvCode
	 * @param caseCode  
	 * @date 2017-3-16 13:55:58
	 * @return
	 */
	public String selectServItem(String caseCode,String srvCode);
	/**
	 * 服务编码[srv_code]和案件编号[case_code]到服务表[T_TG_SERV_ITEM_AND_PROCESSOR]中去查询交易顾问id[processor_id]
	 * @author hejf10 2017-3-16 13:55:58
	 * @param srvCode
	 * @param caseCode  
	 * @date 2017-3-16 13:55:58
	 * @return
	 */
	public String selectAtt(String caseCode);
	/**
	 * 删除流水
	 * @author hejf10
	 * @date 2017年6月9日18:09:00
	 * @param pkid
	 * @throws Exception
	 */
	public void delLiushui(Long pkid) throws Exception;
}
