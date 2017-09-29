package com.centaline.trans.ransom.service;

import java.util.Date;
import java.util.List;

import com.centaline.trans.ransom.entity.ToRansomDetailVo;
import com.centaline.trans.ransom.entity.ToRansomPlanVo;

/**
 * 赎楼service
 * @author wbcaiyx
 *
 */
public interface RansomService {
	
	/**
	 * 赎楼详情查询
	 * @param ransomCode
	 * @return
	 */
	public ToRansomDetailVo getRansomDetail(String ransomCode);
	
	/**
	 * 赎楼计划Vo
	 * @param ransomCode
	 * @return
	 */
	public List<ToRansomPlanVo> getPartPlanTime(String ransomCode);
	
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
