package com.centaline.trans.cases.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.centaline.trans.cases.entity.ToCaseInfo;
import com.centaline.trans.cases.entity.ToCaseInfoCountVo;
import com.centaline.trans.cases.vo.CaseAssistantVO;
import com.centaline.trans.common.MyBatisRepository;

@MyBatisRepository
public interface ToCaseInfoMapper {
	int deleteByPrimaryKey(Long pkid);

	int insert(ToCaseInfo record);

	int insertSelective(ToCaseInfo record);

	ToCaseInfo selectByPrimaryKey(Long pkid);

	int updateByPrimaryKey(ToCaseInfo record);
	
	int updateByPrimaryKeySelective(ToCaseInfo record); 
	
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
	@Deprecated
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
	@Deprecated
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
	
	/**
	 * 查询组下所有交易助理
	 * @param orgId
	 * @return
	 */
	List<CaseAssistantVO> queryAssistantInfo(String orgId);
	
	/**
	 * 功能：根据成交报告编号 获取案件编号
	 * 
	 * @author yinchao
	 * @since 2017-8-16
	 */
	public String findcaseCodeByCcaiCode(String ccaiCode);
	/**
	 * 统计CCAICODE 是否已存在
	 * @param caseCode
	 * @return
	 * @author yinchao
	 * @since 2017-8-16
	 */
	int isExistCcaiCode(String ccaiCode);
	
	 /**
     * 获取案件责任主管
     * @param caseCode
     * @return
     * @author jinwl6
     */
    String getCaseManager(String caseCode);
	
}