package com.centaline.trans.wdcase.repository;

import com.centaline.trans.common.MyBatisRepository;
import com.centaline.trans.wdcase.entity.TdmPaidSubs;
@MyBatisRepository
public interface TdmPaidSubsMapper {
    int deleteByPrimaryKey(Long pkid);

    int insert(TdmPaidSubs record);

    int insertSelective(TdmPaidSubs record);

    TdmPaidSubs selectByPrimaryKey(Long pkid);

    int updateByPrimaryKeySelective(TdmPaidSubs record);

    int updateByPrimaryKey(TdmPaidSubs record);
    /**
     * 查询应收款项
     * @author hejf10
     * @date 2017年4月26日18:33:36
     * @param paymentCode
     * @return
     */
    TdmPaidSubs selectByPaymentCode(String paymentCode);
}