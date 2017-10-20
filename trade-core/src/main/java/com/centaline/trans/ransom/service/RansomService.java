package com.centaline.trans.ransom.service;

import java.util.Date;
import java.util.List;

import com.centaline.trans.ransom.entity.ToRansomApplyVo;
import com.centaline.trans.ransom.entity.ToRansomCancelVo;
import com.centaline.trans.ransom.entity.ToRansomCaseVo;
import com.centaline.trans.ransom.entity.ToRansomDetailVo;
import com.centaline.trans.ransom.entity.ToRansomMortgageVo;
import com.centaline.trans.ransom.entity.ToRansomPaymentVo;
import com.centaline.trans.ransom.entity.ToRansomPermitVo;
import com.centaline.trans.ransom.entity.ToRansomPlanVo;
import com.centaline.trans.ransom.entity.ToRansomSignVo;
import com.centaline.trans.ransom.entity.ToRansomSubmitVo;
import com.centaline.trans.ransom.entity.ToRansomTailinsVo;
import com.centaline.trans.ransom.vo.ToRansomLinkVo;

/**
 * 赎楼service
 * @author wbcaiyx
 *
 */
public interface RansomService {
	
	/**
	 * 赎楼详情查询
	 * @param caseCode
	 * @return
	 */
	public List<ToRansomDetailVo> getRansomDetail(String caseCode);
	
	public ToRansomDetailVo getRansomDetail(String caseCode, String ransomCode);
	
	/**
	 * 赎楼计划Vo
	 * @param ransomCode
	 * @return
	 */
	public List<ToRansomPlanVo> getPartPlanTime(String ransomCode);
	
	/**
	 * 申请数据更新
	 * @param submitVo
	 * @return
	 */
	public int updateRansomApply(ToRansomSubmitVo submitVo);
	
	/**
	 * 查询是否存在二抵
	 * @param ransomCode
	 * @return
	 */
	public int queryErdiByRansomCode(String ransomCode);
	
	/**
	 * 面签数据更新
	 * @param submitVo
	 * @return
	 */
	public int updateRansomSign(ToRansomSubmitVo submitVo,int count);
	
	/**
	 * 陪同还贷数据更新
	 * @param submitVo
	 * @return
	 */
	public int updateRansomMortgage(ToRansomSubmitVo submitVo);
	
	/**
	 * 注销抵押实际时间更新
	 * @param ransomCode
	 * @param tailPkid
	 * @param cancelDiyaTime
	 * @return
	 */
	public int updateRansomCancel(String ransomCode,Integer diyaType,Date cancelTime);
	
	/**
	 * 领取产证实际时间更新
	 * @param ransomCode
	 * @param tailPkid
	 * @param permitTime
	 * @return
	 */
	public int updateRansomPermit(String ransomCode, Integer diyaType, Date permitTime);
	
	/**
	 * 回款结清实际时间更新
	 * @param ransomCode
	 * @param paymentTime
	 * @return
	 */
	public int updateRansomPayment(String ransomCode, Date paymentTime);
	
	/**
	 * 赎楼案件信息查询
	 * @param caseCode
	 * @return
	 */
	public ToRansomCaseVo getRansomCaseInfo(String caseCode);
	
	/**
	 * 赎楼案件信息查询
	 * @param caseCode
	 * @return
	 */
	public ToRansomCaseVo getRansomInfoByRansomCode(String caseCode);
	
	/**
	 * 尾款信息查询
	 * @param caseCode
	 * @return
	 */
	public List<ToRansomTailinsVo> getTailinsInfoByCaseCode(String caseCode);
	
	/**
	 * 查询赎楼面签信息
	 * @param ransomCode
	 * @return
	 */
	public ToRansomSignVo getInterviewInfo(String ransomCode);
	
	/**
	 * 申请信息查询
	 * @param ransomCode
	 * @return
	 */
	public ToRansomApplyVo getApplyInfo(String ransomCode);
	
	/**
	 * 陪同还贷信息查询
	 * @param ransomCode
	 * @return
	 */
	ToRansomMortgageVo getMortgageInfo(String ransomCode);

	/**
	 * 注销抵押信息查询
	 * @param ransomCode
	 * @return
	 */
	ToRansomCancelVo getCancelInfo(String ransomCode);
	
	/**
	 * 查询领取产证信息
	 * @param ransomCode
	 * @return
	 */
	ToRansomPermitVo getPermitInfo(String ransomCode);
	
	/**
	 * 查询回款结清信息
	 * @param ransomCode
	 * @return
	 */
	ToRansomPaymentVo getPaymentInfo(String ransomCode);
	
	/**
	 * 关联案件信息查询
	 * @param caseCode
	 * @return
	 */
	ToRansomLinkVo getRansomLinkInfo(String caseCode);
	
	/**
	 * 根据ransomCode删除赎楼申请表对应数据
	 * @param ransomCode
	 * @return
	 */
	boolean deleteRansomApplyByRansomCode(String ransomCode);
}
