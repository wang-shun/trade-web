package com.centaline.trans.ransom.service;

import java.util.Date;
import java.util.List;

import com.centaline.trans.ransom.entity.ToRansomDetailVo;
import com.centaline.trans.ransom.entity.ToRansomPlanVo;
import com.centaline.trans.ransom.entity.ToRansomSubmitVo;

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
	public ToRansomDetailVo getRansomDetail(String caseCode);
	
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

}
