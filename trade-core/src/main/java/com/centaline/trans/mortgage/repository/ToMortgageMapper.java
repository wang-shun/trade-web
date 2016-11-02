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
    
    int updateBySign(ToMortgage record);
    
    int updateCustNameByCasecode(String caseCode);
    
    ToMortgage findToMortgageByCaseCode(String caseCode);
    
    List<ToMortgage> findToMortgageByCaseCodeNoBlank(String caseCode);
    
    List<ToMortgage> findToMortgageByCaseCodeAndBankType(ToMortgage toMortgage);
    
    List<ToMortgage> findToMortgageByCondition(ToMortgage toMortgage);
    List<ToMortgage> findToMortgageByConditionWithCommLoan(ToMortgage toMortgage);
    
    int inActiveMortageByCaseCode(String caseCode);
    /**重新设定最终贷款银行（商贷）**/
    int restSetLastLoanBank(ToMortgage toMortgage); 
    
    public ToMortgage getMortgageByCaseCode(String caseCode);
    
    public int updateTmpBankStatus(String caseCode);
    
    ToMortgage  findToMortgageByCaseCodeAndCustcode(ToMortgage toMortgage);
}