package com.centaline.trans.ransom.service;

import java.util.List;
import java.util.Map;

import com.centaline.trans.common.entity.TgGuestInfo;
import com.centaline.trans.ransom.entity.ToRansomCaseVo;
import com.centaline.trans.ransom.entity.ToRansomPlanVo;
import com.centaline.trans.ransom.entity.ToRansomTailinsVo;
import com.centaline.trans.ransom.vo.ToRansomVo;
import com.centaline.trans.ransom.vo.VRansomFinishTaskVo;

public interface RansomListFormService {
	/**
	 * 添加赎楼列表信息数据
	 * @param trco
	 * @return
	 */
	int addRansomDetail(ToRansomCaseVo trco);
	
	/**
	 * 查询赎楼单列表
	 * @param caseCode
	 * @return
	 */
	ToRansomCaseVo getRansomCase(String caseCode, String ransomCode);
	
	/**
	 * 根据caseCode保存赎楼中止信息
	 * @param caseCode
	 * @return
	 */
	int updateRansomDiscountinue(ToRansomCaseVo ransomCase);

	int updateRansomDiscountinue(String caseCode);
	
	/**
	 * 获取赎楼修改单信息
	 * @param caseCode
	 * @return
	 */
	List getUpdateRansomInfo(String caseCode);
	
	ToRansomCaseVo getRansomCase(String caseCode);
	/**
	 * 尾款信息查询
	 * @param caseCode
	 * @return
	 */
	List<ToRansomTailinsVo> getTailinsInfoByCaseCode(String caseCode);
	
	List<TgGuestInfo> getGuestInfo(String caseCode);
	
	
	/**
	 * 修改赎楼列表信息
	 * @param ransomCode
	 * @return
	 */
	boolean updateRansomCaseInfo(ToRansomCaseVo caseVo);
	
	/**
	 * 修改尾款信息
	 * @param ransomCode
	 * @return
	 */
	boolean updateRansomTailinsInfo(ToRansomTailinsVo tailinsVo);
	
	/**
	 * 获取赎楼计划单信息
	 * @param caseCode
	 * @return
	 */
	List<ToRansomVo> getRansomPlanInfo(String ransomCode);
	
	/**
	 * 赎楼时间计划信息
	 * @param ransomCode
	 * @return
	 */
	List<ToRansomPlanVo> getRansomPlanTimeInfo(String ransomCode);
	
	/**
	 * 赎楼时间计划信息
	 * @param ransomCode
	 * @return
	 */
	List<String> getRansomPlanCodeInfo(String ransomCode);
	
	/**
	 * 赎楼时间计划信息插入
	 * @param ransomCode
	 * @return
	 */
	int insertRansomPlanTimeInfo(ToRansomPlanVo planVo);
	
	/**
	 * 时间计划批量插入
	 * @param planVo
	 * @return
	 */
	int insertListRansomPlanTime(List<ToRansomPlanVo> list);
	
	/**
	 * 赎楼时间计划信息更新
	 * @param ransomCode
	 * @return
	 */
	int updateRansomPlanTimeInfo(ToRansomPlanVo ransomPlanVo);
	
	/**
	 * 赎楼时间计划信息更新
	 * @param ransomCode
	 * @return
	 */
	int updateListPlanTimeInfo(List<ToRansomPlanVo> planList);
	
	
	/**
	 * 赎楼申请信息更新
	 * @param ransomCode
	 * @return
	 */
	int updateRansomApplyInfo(ToRansomVo ransomVo);
	
	/**
	 * 赎楼面签信息更新
	 * @param ransomCode
	 * @return
	 */
	int updateRansomInterviewInfo(ToRansomVo ransomVo);
	
	/**
	 * 赎楼陪同还贷信息更新
	 * @param ransomCode
	 * @return
	 */
	int updateRansomRepayInfo(ToRansomVo ransomVo);
	
	/**
	 * 赎楼注销抵押信息更新
	 * @param ransomCode
	 * @return
	 */
	int updateRansomCancelInfo(ToRansomVo ransomVo);
	
	/**
	 * 领取产证信息更新
	 * @param ransomCode
	 * @return
	 */
	int updateRansomRedeemInfo(ToRansomVo ransomVo);
	
	/**
	 * 赎楼回款结清信息更新
	 * @param ransomCode
	 * @return
	 */
	int updateRansomPaymentInfo(ToRansomVo ransomVo);
	
	/**
	 * 当前单数
	 * @param userId
	 * @return
	 */
	int queryCountRansomsByUserId(String userId);
	
	/**
	 * 本月单数
	 * @param userId
	 * @return
	 */
	int queryCountMonthRansomsByUserId(String userId);
	
	/**
	 * 获取已完成任务信息
	 * @param ransomCode
	 * @return
	 */
	VRansomFinishTaskVo getRansomTaskInfoByRansomCode(String ransomCode);
}
