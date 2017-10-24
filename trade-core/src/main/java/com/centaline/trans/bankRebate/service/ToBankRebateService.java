package com.centaline.trans.bankRebate.service;

import com.centaline.trans.bankRebate.entity.ToBankRebate;

/**
 * 
 * @author wbwangxj
 *
 */
public interface ToBankRebateService {
	int deleteByPrimaryKey(Long pkid);

    int insert(ToBankRebate record);

    int insertSelective(ToBankRebate record);

    ToBankRebate selectByPrimaryKey(Long pkid);

    int updateByPrimaryKeySelective(ToBankRebate record);

    int updateByPrimaryKey(ToBankRebate record);

	int deleteByGuaranteeCompId(String guaCompId);

	int updateByGuaranteeCompId(ToBankRebate toBankRebate);
}
