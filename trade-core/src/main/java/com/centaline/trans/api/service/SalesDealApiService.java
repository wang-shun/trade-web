package com.centaline.trans.api.service;

import com.aist.common.exception.BusinessException;

/**
 * 三级市场中原成交接口
 * @author linjiarong
 *
 */
public interface SalesDealApiService {
	
	/**
	 * 通知三级市场中原成交
	 * @param ctmCode
	 * @throws BusinessException
	 */
	public void noticeSalesDeal(String ctmCode) throws BusinessException;
	
	/**
	 * 调用销售接口,判断是否可以进行过户操作
	 * @param ctmCode
	 * @return boolean
	 *    true   可以进行过户
	 *    false  不可进行过户
	 * @throws BusinessException 调用接口异常时,对外抛出异常
	 * 
	 */
	public SalesApiResponse checkCanHouseTransfer(String ctmCode) throws BusinessException;

}
