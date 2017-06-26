package com.centaline.trans.mortgage.repository;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.centaline.trans.common.MyBatisRepository;
import com.centaline.trans.mortgage.entity.ToMortLoaner;

@MyBatisRepository
public interface ToMortLoanerMapper {

	int deleteByPrimaryKey(Long pkid);

	int insert(ToMortLoaner record);

	int insertSelective(ToMortLoaner record);

	ToMortLoaner selectByPrimaryKey(Long pkid);

	int updateByPrimaryKeySelective(ToMortLoaner record);

	int updateByPrimaryKey(ToMortLoaner record);

	ToMortLoaner findToMortLoanerByCaseCodeAndLoanerStatus(
			Map<String, String> map);

	ToMortLoaner findToMortLoanerByCaseCode(String caseCode);

	/**
	 * 根据按揭信息id获取按揭接收记录信息
	 * 
	 * @param id
	 *            按揭信息id
	 * @return 按揭接收记录信息
	 */
	ToMortLoaner getToMortLoanerById(Long id);

	/**
	 * 根据按揭信息id更新按揭接收记录信息
	 * 
	 * @param toMortLoaner
	 *            按揭接收记录信息
	 * @return 返回1,更新成功;返回0，更新失败。
	 */
	public int updateToMortLoanerByMortId(ToMortLoaner toMortLoaner);

	List<ToMortLoaner> findToMortLoanerByCaseCodeAndIsMainBank(
			Map<String, String> map);

	List<ToMortLoaner> findToMortLoaner(ToMortLoaner toMortLoaner);
	
	/**
	 * 根据安全编号和主次银行获取有效的派单信息 (ACCEPTING  ，AUDITING ，COMPLETED) 
	 * @param caseCode
	 * @param isMainLoanBankProcess
	 * @return
	 */
	ToMortLoaner findActiveToMortLoaner(@Param("caseCode") String caseCode,@Param("isMainLoanBankProcess") String isMainLoanBankProcess);
	/**
	 * 根据安全编号和主次银行获取派单信息 (主要为了查询最后的状态) 
	 * @param caseCode
	 * @param isMainLoanBankProcess
	 * @return
	 */
	ToMortLoaner findLastToMortLoaner(@Param("caseCode") String caseCode,@Param("isMainLoanBankProcess") String isMainLoanBankProcess);
}