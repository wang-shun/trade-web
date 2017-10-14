package com.centaline.trans.eval.service;

import java.util.List;

import com.centaline.trans.eval.entity.ToEvalSettle;
/***
 * 
 * @author wbwangxj
 *
 */
public interface ToEvalSettleService {
	int deleteByPrimaryKey(Long pkid);

    int insert(ToEvalSettle record);

    int insertSelective(ToEvalSettle record);

    ToEvalSettle selectByPrimaryKey(Long pkid);

    int updateByPrimaryKeySelective(ToEvalSettle toEvalSettle);

    int updateByPrimaryKey(ToEvalSettle record);
    
    int updateByCaseCode(ToEvalSettle record);
    
    int updateSettleFeeByCaseCode(ToEvalSettle record);
    
    int newSettleFeeByCaseCode(ToEvalSettle record);
    
    ToEvalSettle findToCaseByCaseCode(String caseCode);
    
    //待财务审批案件信息及其他相关信息
    List<ToEvalSettle> findCaseCodesByStauts();
    //待财务审批案件信息
    List<String> waitApproCaseCodes();
}
