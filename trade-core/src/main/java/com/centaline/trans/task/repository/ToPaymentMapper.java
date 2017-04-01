package com.centaline.trans.task.repository;

import java.util.List;

import com.centaline.trans.common.MyBatisRepository;
import com.centaline.trans.task.entity.ToPayment;

@MyBatisRepository
public interface ToPaymentMapper {
    int deleteByPrimaryKey(Long pkid);

    int insert(ToPayment record);

    int insertSelective(ToPayment record);

    ToPayment selectByPrimaryKey(Long pkid);

    int updateByPrimaryKeySelective(ToPayment record);

    int updateByPrimaryKey(ToPayment record);
    
    List<ToPayment> findToPaymentByCaseCode(String caseCode);
    
    ToPayment findToPaymentByPayment(ToPayment record);
}