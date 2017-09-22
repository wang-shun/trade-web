package com.centaline.trans.eval.service;

import com.centaline.trans.eval.entity.ToEvalRebate;
/***
 * 
 * @author wbwangxj
 *
 */
public interface ToEvalRebateService {
	int deleteByPrimaryKey(Long pkid);

    int insert(ToEvalRebate record);

    int insertSelective(ToEvalRebate record);

    ToEvalRebate selectByPrimaryKey(Long pkid);

    int updateByPrimaryKeySelective(ToEvalRebate toEvalRebate);

    int updateByPrimaryKey(ToEvalRebate record);
    
	ToEvalRebate findToEvalRebateByCaseCode(String caseCode);
	
}
