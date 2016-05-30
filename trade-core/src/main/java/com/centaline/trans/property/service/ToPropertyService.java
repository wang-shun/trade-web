package com.centaline.trans.property.service;

import java.util.List;

import com.centaline.trans.common.entity.Pic;
import com.centaline.trans.common.entity.ToAttachment;
import com.centaline.trans.task.entity.ToPropertyResearch;

/**
 * 产调service
 * @author aisliaohl
 *
 */
public interface ToPropertyService {

	public int doChangePrDistrictId(String pkidArr,String prDistrictId);
	/**
	 * 根据caseCode修改已受理产调
	 * @param disposeCodeArr
	 * @return
	 */
	public int updateProcessingListStatus(String[] pkidArr,String userId);

    int insert(ToPropertyResearch record);

    int insertSelective(ToPropertyResearch record);
    
    List<ToPropertyResearch> queryUnClosePropertyResearchsByCaseCode(String caseCode);
    
	/**
	 * 处理无效标记数据
	 * @param property
	 * @return
	 */
	public int nullityTag(ToPropertyResearch property);

	/**
	 * 查询产调信息
	 * @param caseCode
	 * @return
	 */
	public ToPropertyResearch findToPropertyResearchsByCaseCode(String caseCode);

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


	int updateProcessWaitListStatus(String district);
	int updateProcessWaitListStatusByPkId(String pkid);

	List<ToPropertyResearch> getUnProcessListByDistrict(String district);

	void sendMessage(List<ToPropertyResearch> list);

	ToPropertyResearch findByPKID(Long pkid);
	int saveProcessingList(String pkid, String userId, String isScuess, String unSuccessReason, Boolean isSubmit);
}
