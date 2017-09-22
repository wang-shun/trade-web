package com.centaline.trans.eval.repository;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.centaline.trans.common.MyBatisRepository;
import com.centaline.trans.eval.entity.ToEval;
/**
 * 
 * @author wblujian
 *
 */
@MyBatisRepository
public interface ToEvalMapper {
	int deleteByPrimaryKey(Long pkid);

	int insert(ToEval record);

	int insertSelective(ToEval record);

	ToEval selectByPrimaryKey(Long pkid);
	/**
	 *  根据贷款表ID查询评估费
	 * @param mortgageId
	 * @return
	 */
	ToEval selectMortgageId(@Param("mortgageId") Long mortgageId);

	int updateByPrimaryKeySelective(ToEval record);

	int updateByPrimaryKey(ToEval record);
	
	
	/**
	 * 获取Egu物业信息
	 * lujian
	 */
	List<Map<String,String>> queryEguProInfo(String caseCode);
	

}