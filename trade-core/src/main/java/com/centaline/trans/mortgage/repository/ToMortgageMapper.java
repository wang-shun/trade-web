package com.centaline.trans.mortgage.repository;

import java.util.List;
import java.util.Map;

import com.centaline.trans.common.MyBatisRepository;
import com.centaline.trans.mortgage.entity.ToMortgage;

@MyBatisRepository
public interface ToMortgageMapper {
    int deleteByPrimaryKey(Long pkid);

    int insertSelective(ToMortgage record);

    ToMortgage selectByPrimaryKey(Long pkid);

    int update(ToMortgage record);
    
    int update1(ToMortgage record);
    
    int updateByPrimaryKey(ToMortgage record);

    int updateByTest(ToMortgage record);
    
    int updateByPkId(ToMortgage record);

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

    List<ToMortgage> findToMortgageByCaseCodeAndCustcode(ToMortgage toMortgage);

    /**
     * 根据biz_code更新按揭垫款在银行内部的审批状态
     * 
     * @param toMortgage
     * @return
     */
    public int updateStateInBankByBizCode(ToMortgage toMortgage);

    /**
     * 根据biz_code查询按揭贷款
     * 
     * @param bizCode
     * @return
     */
    public ToMortgage getMortgageByBizCode(String bizCode);
    
    /**
     * 根据caseCode 和 LoanerId查询贷款信息
     * 
     * @param caseCode
     * @param LoanerId
     * @return
     */
    ToMortgage findToMortgageByCaseCodeAndLoanerId(Map<String,Object> map);
    
    /**
     * 根据caseCode 和 派单信息查询贷款信息
     * 
     * @param caseCode
     * @param LoanerId
     * @return
     */

	ToMortgage findToMortgageByCaseCodeAndDisTime(Map<String,Object> map);

	void updateIsPatch(ToMortgage toMortgage);
	
	/**
	 * 取贷款信息，主副
	 * @param caseCode
	 * @return
	 */
	List<ToMortgage> findToMortgageByCaseCodes(String caseCode);
	
	/**
	 * 查询贷款挽回信息
	 * @param caseCode
	 * @return
	 */
	String findToMortgageSave(String caseCode);

	/**
	 * 删除所有的贷款信息
	 * @param caseCode
	 */
	void deleteMortageByCaseCode(String caseCode);
}