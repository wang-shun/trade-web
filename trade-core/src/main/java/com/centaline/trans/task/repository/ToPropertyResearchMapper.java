package com.centaline.trans.task.repository;

import java.util.List;

import com.centaline.trans.common.MyBatisRepository;
import com.centaline.trans.task.entity.ToPropertyResearch;
import com.centaline.trans.task.entity.ToPropertyResearchVo;

@MyBatisRepository
public interface ToPropertyResearchMapper {
	int deleteByPrimaryKey(Long pkid);

	int insert(ToPropertyResearch record);

	int insertSelective(ToPropertyResearch record);

	ToPropertyResearch selectByPrimaryKey(Long pkid);

	int updateByPrimaryKeySelective(ToPropertyResearch record);

	int updateByPrimaryKey(ToPropertyResearch record);
	/**
	 * 更新产调为完成
	 * @param pkid
	 * @param userId
	 * @return
	 */
	int updatePropertyToComplete(String pkid,String userId);
	/**
	 * 处理未处理产调
	 * 
	 * @param caseCode
	 * @return
	 */
	int updateProcessWaitListStatus(String pkid);

	/**
	 * 处理已受理产调
	 * 
	 * @param caseCode
	 * @return
	 */
	int updateProcessingListStatus(String pkid);

	/**
	 * 查询产调信息
	 * 
	 * @param pkid
	 * @return
	 */
	ToPropertyResearch findToPropertyResearchById(Long pkid);

	/**
	 * 查询物业地址
	 * 
	 * @param pkid
	 * @return
	 */
	ToPropertyResearchVo findToPropertyResearchAddressById(Long pkid);

	/**
	 * 插入受理时间
	 * 
	 * @param string
	 */
	void updateProcessWaitListPrAccpetTime(Long pkid);

	/**
	 * 插入完成时间
	 * 
	 * @param parseLong
	 */
	void updateProcessingListPrCompleteTime(long parseLong);

	/**
	 * 未完成产调
	 * 
	 * @param caseCode
	 */
	List<ToPropertyResearch> queryUnClosePropertyResearchsByCaseCode(
			String caseCode);

	// ToPropertyResearch findToSignByCaseCode(String caseCode);

	/**
	 * 处理无效标记
	 * 
	 * @param property
	 * @return
	 */
	int nullityTag(ToPropertyResearch property);

	/**
	 * 查询产调信息
	 * 
	 * @param toPropertyResearch
	 * @return
	 */
	ToPropertyResearch findToPropertyResearchsByCaseCode(
			ToPropertyResearch toPropertyResearch);

	/**
	 * 查询产调信息 (只根据caseCode不根据其它任何条件)
	 * 
	 * @param caseCode
	 * @return
	 */
	ToPropertyResearch getToPropertyResearchsByPrCode(String caseCode);

	ToPropertyResearch selectByForSamePrCode(String address,String PrCat);

	/**
	 * 查询基本产调信息 (交易顾问)
	 * 
	 * @param caseCode
	 */
	ToPropertyResearch getBasePRConsult(String caseCode);

	/**
	 * 查询基本产调信息 (经纪人)
	 * 
	 * @param caseCode
	 */
	List<ToPropertyResearch> getBasePRAgent();
	
	List<ToPropertyResearch> getUnProcessListByDistrict(String district);
	int processWaitListByDistrict(String district);
	String getPrCostMgrByOrgId(String orgId);
	String getOrgIdByUserId(String userId);
	
	List<ToPropertyResearch> queryAllProperty(String prStatus);

}