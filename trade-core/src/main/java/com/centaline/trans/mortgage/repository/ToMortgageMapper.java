package com.centaline.trans.mortgage.repository;

import java.util.List;

import com.centaline.trans.common.MyBatisRepository;
import com.centaline.trans.mortgage.entity.ToMortgage;

@MyBatisRepository
public interface ToMortgageMapper {
    int deleteByPrimaryKey(Long pkid);

    int insertSelective(ToMortgage record);

    ToMortgage selectByPrimaryKey(Long pkid);

    int update(ToMortgage record);
    
    ToMortgage findToMortgageByCaseCode(String caseCode);
    
    List<ToMortgage> findToMortgageByCaseCodeNoBlank(String caseCode);
    
    List<ToMortgage> findToMortgageByCaseCodeAndBankType(ToMortgage toMortgage);
    
    List<ToMortgage> findToMortgageByCondition(ToMortgage toMortgage);
    int inActiveMortageByCaseCode(String caseCode);
}