package com.centaline.trans.eval.repository;

import java.util.List;
import java.util.Map;

import com.centaline.trans.common.MyBatisRepository;
import com.centaline.trans.eval.entity.ToEvalSettle;

/***
 * 
 * @author wbwangxj
 *
 */
@MyBatisRepository
public interface ToEvalSettleMapper {
    int deleteByPrimaryKey(Long pkid);

    int insert(ToEvalSettle record);

    int insertSelective(ToEvalSettle record);

    ToEvalSettle selectByPrimaryKey(Long pkid);

    int updateByPrimaryKeySelective(ToEvalSettle toEvalSettle);

    int updateByPrimaryKey(ToEvalSettle record);
    
    int updateByCaseCode(ToEvalSettle record);
    
    int updateSettleTimeByCaseCode(ToEvalSettle record);
    
    ToEvalSettle findToCaseByCaseCode(String caseCode);

	int updateSettleFeeByCaseCode(ToEvalSettle record);
	
	int newSettleFeeByCaseCode(ToEvalSettle record);
	
	List<ToEvalSettle> findCaseCodesByStauts();
}