package com.centaline.trans.bankRebate.repository;

import java.util.List;

import com.centaline.trans.bankRebate.entity.ToBankRebateInfo;
import com.centaline.trans.common.MyBatisRepository;
/**
 * 
 * @author wbwangxj
 *
 */
@MyBatisRepository
public interface ToBankRebateInfoMapper {
    int deleteByPrimaryKey(Long pkid);

    int insert(ToBankRebateInfo record);

    int insertSelective(ToBankRebateInfo record);

    ToBankRebateInfo selectByPrimaryKey(Long pkid);

    int updateByPrimaryKeySelective(ToBankRebateInfo record);

    int updateByPrimaryKey(ToBankRebateInfo record);
    
    int deleteRebateInfoByGuaranteeCompId(String guaCompId);

	List<ToBankRebateInfo> selectRebateInfoByGuaranteeCompId(String guaranteeCompId);
}