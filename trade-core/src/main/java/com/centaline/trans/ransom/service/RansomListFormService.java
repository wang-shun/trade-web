package com.centaline.trans.ransom.service;

import java.util.List;

import com.centaline.trans.ransom.entity.ToRansomApplyVo;
import com.centaline.trans.ransom.entity.ToRansomCancelVo;
import com.centaline.trans.ransom.entity.ToRansomCaseVo;
import com.centaline.trans.ransom.entity.ToRansomMortgageVo;
import com.centaline.trans.ransom.entity.ToRansomPaymentVo;
import com.centaline.trans.ransom.entity.ToRansomPermitVo;
import com.centaline.trans.ransom.entity.ToRansomSignVo;
import com.centaline.trans.ransom.entity.ToRansomTailinsVo;
import com.centaline.trans.ransom.vo.ToRansomVo;

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
	ToRansomCaseVo getRansomCase(String caseCode);
	
	/**
	 * 根据caseCode保存赎楼中止信息
	 * @param caseCode
	 * @return
	 */
	int updateRansomDiscountinue(String caseCode);
	
	/**
	 * 获取赎楼修改单信息
	 * @param caseCode
	 * @return
	 */
	List getUpdateRansomInfo(String caseCode);
	
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
	ToRansomVo getRansomPlanInfo(String ransomCode);
	
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
}
