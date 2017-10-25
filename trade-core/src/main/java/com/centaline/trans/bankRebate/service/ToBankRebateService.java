package com.centaline.trans.bankRebate.service;

import com.centaline.trans.bankRebate.entity.ToBankRebate;
import com.centaline.trans.bankRebate.vo.ToBankRebateInfoVO;

/**
 * 
 * @author wbwangxj
 *
 */
public interface ToBankRebateService {


	/**
	 * 新增银行返利信息 带事务
	 * @param info 银行返利申请信息
	 * @return
	 */
	void insertBankRebate(ToBankRebateInfoVO info);

	/**
	 * 根据批次号删除银行返利申请
	 * @param guaCompIds
	 */
	void deleteByGuaranteeCompId(String[] guaCompIds);

	/**
	 * 根据银行返利批次号获取银行返利申请信息
	 * @param guaCompId 批次号
	 * @return
	 */
	ToBankRebateInfoVO selectRebateByGuaranteeCompId(String guaCompId);

	/**
	 * 删除指定银行返利案件信息
	 * @param pkid
	 */
	void deleteTobankRebateInfo(Long pkid);

	/**
	 * 修改银行返利信息 带事务
	 * @param info 银行返利申请信息
	 * @return
	 */
	void updateBankRebate(ToBankRebateInfoVO info);
}
