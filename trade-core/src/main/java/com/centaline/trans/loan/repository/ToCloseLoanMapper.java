package com.centaline.trans.loan.repository;

import com.centaline.trans.common.MyBatisRepository;
import com.centaline.trans.loan.entity.ToCloseLoan;

@MyBatisRepository
public interface ToCloseLoanMapper {
    int deleteByPrimaryKey(Long pkid);

    int insert(ToCloseLoan record);

    int insertSelective(ToCloseLoan record);

    ToCloseLoan selectByPrimaryKey(Long pkid);

    int updateByPrimaryKeySelective(ToCloseLoan record);

    int updateByPrimaryKeyWithBLOBs(ToCloseLoan record);

    int updateByPrimaryKey(ToCloseLoan record);
    
    ToCloseLoan findToCloseLoanByCaseCode(String caseCode);
}