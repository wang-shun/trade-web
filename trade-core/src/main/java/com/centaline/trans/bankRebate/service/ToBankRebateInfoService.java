package com.centaline.trans.bankRebate.service;

import java.util.List;

import com.centaline.trans.bankRebate.entity.ToBankRebateInfo;
import com.centaline.trans.bankRebate.vo.ToBankRebateInfoVO;

/**
 * 
 * @author wbwangxj
 *
 */
public interface ToBankRebateInfoService {
	int deleteByPrimaryKey(Long pkid);

    int insert(ToBankRebateInfo record);

    int insertSelective(ToBankRebateInfo record);

    ToBankRebateInfo selectByPrimaryKey(Long pkid);

    int updateByPrimaryKeySelective(ToBankRebateInfo record);

    int updateByPrimaryKey(ToBankRebateInfo record);

	int deleteRebateInfoByGuaranteeCompId(String guaCompId);

	List<ToBankRebateInfo> selectRebateInfoByGuaranteeCompId(String guaranteeCompId);

	void saveToBankRebateInfoVO(ToBankRebateInfoVO toBankRebateInfoVO);
}
