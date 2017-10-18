package com.centaline.trans.ransom.repository;

import java.util.List;

import com.centaline.trans.common.MyBatisRepository;
import com.centaline.trans.common.entity.TgGuestInfo;
import com.centaline.trans.mgr.entity.TsFinOrg;
import com.centaline.trans.ransom.entity.ToRansomApplyVo;
import com.centaline.trans.ransom.entity.ToRansomCancelVo;
import com.centaline.trans.ransom.entity.ToRansomCaseVo;
import com.centaline.trans.ransom.entity.ToRansomMortgageVo;
import com.centaline.trans.ransom.entity.ToRansomPaymentVo;
import com.centaline.trans.ransom.entity.ToRansomPermitVo;
import com.centaline.trans.ransom.entity.ToRansomPlanVo;
import com.centaline.trans.ransom.entity.ToRansomSignVo;
import com.centaline.trans.ransom.entity.ToRansomTailinsVo;
import com.centaline.trans.ransom.vo.ToRansomLinkVo;
import com.centaline.trans.ransom.vo.ToRansomVo;

@MyBatisRepository
public interface RansomListFormMapper {

	/**
	 * 添加赎楼列表信息数据
	 * @param trco
	 * @return
	 */
	int addRansomDetail(ToRansomCaseVo trco);
	
	/**
	 * 查询赎楼单列表信息数据by caseCode
	 * @param caseCode
	 * @return
	 */
	ToRansomCaseVo getRansomCase(String caseCode);
	
	/**
	 * 根据caseCode保存赎楼中止信息
	 * @param caseCode
	 * @return
	 */
	int updateRansomDiscountinue(String caseCode);
	
	/**
	 * 获取客户信息
	 * @param caseCode
	 * @return
	 */
	List<TgGuestInfo> getGuestInfo(String caseCode);

	/**
	 * 获取机构信息
	 * @param finOrgCode
	 * @return
	 */
	TsFinOrg getTsFinOrgIinfo(String finOrgCode);
	
	/**
	 * 获取时间计划信息
	 * @param ransomCode
	 * @return
	 */
	List<ToRansomPlanVo> getRansomPlanTime(String ransomCode);
	
	/**
	 * 时间计划信息更新
	 * @param ransomCode
	 * @return
	 */
	int updateRansomPlanTimeInfoByRansomCode(ToRansomPlanVo ransomPlanVo);
	
	/**
	 * 修改赎楼列表信息
	 * @param ransomCode
	 * @return
	 */
	boolean updateRansomCaseByRansomCode(ToRansomCaseVo caseVo);
	
	/**
	 * 修改尾款信息
	 * @param ransomCode
	 * @return
	 */
	boolean updateRansomTailinsByRansomCode(ToRansomTailinsVo tailinsVo);
	
	/**
	 * 获取计划时间信息
	 * @param caseCode
	 * @return
	 */
	List<ToRansomVo> getRansomInfoByRansomCode(String ransomCode);
	
	/**
	 * 计划时间信息插入
	 * @param planVo
	 * @return
	 */
	int insertRansomPlanTimeVo(ToRansomPlanVo planVo);
	/**
	 * 赎楼申请信息更新
	 * @param ransomCode
	 * @return
	 */
	int updateRansomApplyInfoByRansomCode(ToRansomApplyVo applyVo);
	
	/**
	 * 赎楼面签信息更新
	 * @param ransomCode
	 * @return
	 */
	int updateRansomInterviewInfoByRansomCode(ToRansomSignVo signVo);
	
	/**
	 * 赎楼陪同还贷信息更新
	 * @param ransomCode
	 * @return
	 */
	int updateRansomRepayInfoByRansomCode(ToRansomMortgageVo mortgageVo);
	
	/**
	 * 赎楼注销抵押信息更新
	 * @param ransomCode
	 * @return
	 */
	int updateRansomCancelInfoByRansomCode(ToRansomCancelVo cancelVo);
	
	/**
	 * 领取产证信息更新
	 * @param ransomCode
	 * @return
	 */
	int updateRansomRedeemInfoByRansomCode(ToRansomPermitVo permitVo);
	
	/**
	 * 赎楼回款结清信息更新
	 * @param ransomCode
	 * @return
	 */
	int updateRansomPaymentInfoByRansomCode(ToRansomPaymentVo paymentVo);
	
	/**
	 * 赎楼案件关联信息查询
	 * @param caseCode
	 * @return
	 */
	ToRansomLinkVo getRansomLinkInfoByCaseCode(String caseCode);
	
	/**
	 * 根据caseCode保存赎楼中止信息
	 * @param caseCode
	 * @return
	 */
	int updateRansomDiscountinue(ToRansomCaseVo ransomCaseVo);

	/**
	 * 当前单数
	 * @param userId
	 * @return
	 */
	Integer queryCountRansomsByUserId(String userId);

	/**
	 * 本月单数
	 * @param userId
	 * @return
	 */
	Integer queryCountMonthRansomsByUserId(String userId);
	
	

}
