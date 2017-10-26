package com.centaline.trans.eval.service;

import java.util.List;

import com.centaline.trans.eval.entity.ToEvalSettle;
import com.centaline.trans.mgr.entity.TsFinOrg;
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
    
    int updateSettleTimeByCaseCode(ToEvalSettle record);
    
    int updateSettleFeeByCaseCode(ToEvalSettle record);
    
    int newSettleFeeByCaseCode(ToEvalSettle record);
    
    ToEvalSettle findToCaseByCaseCode(String caseCode);
    
    //根据评估公司编号查询评估公司
    TsFinOrg findTsFinOrgByfinOrgCode(String finOrgCode);
    
    //待财务审批案件信息及其他相关信息
    List<ToEvalSettle> findCaseCodesByStauts();
    //待财务审批案件信息
    List<String> waitApproCaseCodes();
    
    //传入需要结算案件 feeChangeReason：1--发票税点；2--退报告；3--爆单
    int insertWaitAccount(String caseCode,String evaCode,String feeChangeReason);
    
    
}
