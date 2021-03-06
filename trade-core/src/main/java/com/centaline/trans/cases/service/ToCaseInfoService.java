package com.centaline.trans.cases.service;

import com.centaline.trans.cases.entity.ToCaseInfo;
import com.centaline.trans.cases.entity.ToCaseInfoCountVo;
import com.centaline.trans.cases.vo.CaseAssistantVO;
import com.centaline.trans.cases.vo.CaseDetailShowVO;
import com.centaline.trans.mortgage.entity.ToMortgage;

import java.util.List;
import java.util.Map;

public interface ToCaseInfoService {

    ToCaseInfo findToCaseInfoByCaseCode(String caseCode);
    

    int updateByPrimaryKey(ToCaseInfo record);
    
    int updateCaseInfoByOrgId(Map<String, Object> param);
    
    int updateCaseInfoByAngetId(Map<String, Object> param);
    
	public int queryCountCasesByUserId(String userId);
	public int queryCountMonthCasesByUserId(String userId);
	public int queryCountUnTransCasesByUserId(String userId);
	/**
	 * 统计  接单
	 * @return ToCaseInfo
	 */
	public ToCaseInfoCountVo countToCaseInfoById(String userId);
	
	/**
	 * 功能：交易单编号查询
	 * @author zhangxb16
	 */
	public String findcaseCodeByctmCode(String ctmCode);
	/**
	 *获取所有统计数据
	 */
	ToCaseInfoCountVo getToCaseInfoCountAll();
	/**
	 * 查询所有统计(接单数)
	 * @return
	 */
	ToCaseInfoCountVo countToCaseInfoAll();
	/**
	 * 接单数统计数据查询
	 * @param orgId
	 * @return
	 */
	ToCaseInfoCountVo countToCaseInfoByOrgId(String orgId,String startDate,String endDate);
	/**
	 * 接单数多条统计数据查询
	 * @param orgId
	 * @return
	 */
	List<ToCaseInfoCountVo> countToCaseInfoListByOrgId(String orgId);
	/**
	 * 查询统计数
	 * @param orgList
	 * @return
	 */
	List<ToCaseInfoCountVo> countToCaseInfoListByOrgList(List<String> orgList);
	
	int countToCaseInfoByOrgList(List<String> strList,
			String startDate, String endDate);
	/**
	 * 初始化统计数
	 * @param orgId
	 * @return
	 */
	int initCountToCaseInfoByOrgId(String orgId,String createTime);
	/**
	 * 查询统计数
	 * @param idList
	 * @return
	 */
	List<ToCaseInfoCountVo> countToCaseInfoListByIdList(List<String> idList);
	/**
	 * 查询统计数
	 * @param idList
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	int countToCaseInfoByIdList(List<String> idList, String startDate,
			String endDate);
	
	
	int isExistCaseCode(String caseCode);
	CaseDetailShowVO getCaseDetailShowVO(String caseCode, ToMortgage toMortgage);
	
	/**
	 * 根据ctmCode 修改targetCode
	 * @param param
	 * @return
	 */
	Integer updateByTargetCode(Map<String, Object> param);
	
	/**
	 * 导入单个新案件
	 * @param ctmCode
	 * @return
	 */
	Integer exportCTMCase(String ctmCode);
	
    int insertSelective(ToCaseInfo record);
    
    /**
     * 获取组下所有交易助理
     * @param orgId
     * @return
     */
    List<CaseAssistantVO> getAssistantInfo(String orgId);
    
    /**
     * 获取案件责任主管
     * @param caseCode
     * @return
     * @author jinwl6
     */
    String getCaseManager(String caseCode);

	/**
	 * 功能：根据CCAI成交报告获取caseCode
	 * @author yinchao
	 */
	public String findcaseCodeByccaiCode(String ccaiCode);

	/**
	 * 功能：根据CCAI成交报告获取caseCode
	 * @author yinchao
	 */
	public String findccaiCodeBycaseCode(String caseCode);
    
}
