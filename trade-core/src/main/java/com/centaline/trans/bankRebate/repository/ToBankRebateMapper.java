package com.centaline.trans.bankRebate.repository;

import com.centaline.trans.bankRebate.entity.ToBankRebate;
import com.centaline.trans.common.MyBatisRepository;
/**
 * 
 * @author wbwangxj
 *
 */
@MyBatisRepository
public interface ToBankRebateMapper {
    int deleteByPrimaryKey(Long pkid);

    int insert(ToBankRebate record);

    int insertSelective(ToBankRebate record);

    ToBankRebate selectByPrimaryKey(Long pkid);

    int updateByPrimaryKeySelective(ToBankRebate record);

    int updateByPrimaryKey(ToBankRebate record);
    
    int deleteByGuaranteeCompId(String guaCompId);
    
    int updateByGuaranteeCompId(ToBankRebate toBankRebate);

    /**
     * 根据批次号 获取返利基本信息
     * @param guaranteeCompId
     * @return
     */
    ToBankRebate selectRebateByGuaranteeCompId(String guaranteeCompId);
}