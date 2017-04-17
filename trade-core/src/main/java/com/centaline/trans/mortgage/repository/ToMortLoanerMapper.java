package com.centaline.trans.mortgage.repository;

import java.util.Map;

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
    
    ToMortLoaner findToMortLoanerByCaseCodeAndLoanerStatus(Map<String,String> map);
    
    /**
     * 根据按揭信息id获取按揭接收记录信息
     * @param mortgageId 按揭信息id
     * @return 按揭接收记录信息
     */
	ToMortLoaner getToMortLoanerByMortgageId(String mortgageId);
	
	/**
	 * 根据按揭信息id更新按揭接收记录信息
	 * @param toMortLoaner 按揭接收记录信息
	 * @return 返回1,更新成功;返回0，更新失败。
	 */
	public int updateToMortLoanerByMortId(ToMortLoaner toMortLoaner);
}