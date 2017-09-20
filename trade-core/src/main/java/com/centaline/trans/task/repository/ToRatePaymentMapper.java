package com.centaline.trans.task.repository;

import com.centaline.trans.common.MyBatisRepository;
import com.centaline.trans.task.entity.ToRatePayment;

/**
 * @author wbzhouht
 * 缴税
 */
@MyBatisRepository
public interface ToRatePaymentMapper {
    int deleteByPrimaryKey(Long pkid);

    int insert(ToRatePayment record);

    int insertSelective(ToRatePayment record);

    ToRatePayment selectByPrimaryKey(Long pkid);

    int updateByPrimaryKeySelective(ToRatePayment record);

    int updateByPrimaryKey(ToRatePayment record);
    
    ToRatePayment findToRatePaymentByCaseCode(String caseCode);
}