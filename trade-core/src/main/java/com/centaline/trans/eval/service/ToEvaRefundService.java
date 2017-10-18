package com.centaline.trans.eval.service;

import com.centaline.trans.eval.entity.ToEvaRefund;


/**
 * @author wbfanglj
 *
 */
public interface ToEvaRefundService {
	int deleteByPrimaryKey(Long pkid);

    int insert(ToEvaRefund record);

    String insertSelective(ToEvaRefund record);

    ToEvaRefund selectByPrimaryKey(Long pkid);
    
    ToEvaRefund selectByCaseCode(String caseCode);

    int updateByPrimaryKeySelective(ToEvaRefund record);

    int updateByPrimaryKey(ToEvaRefund record);
}
