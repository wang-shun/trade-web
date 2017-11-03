package com.centaline.trans.eval.service;

import com.centaline.trans.cases.vo.ToEvaRefundVO;
import com.centaline.trans.eval.entity.ToEvaRefund;


/**
 * @author wbfanglj
 *
 */
public interface ToEvaRefundService {
	int deleteByPrimaryKey(Long pkid);

    int insert(ToEvaRefund record);

    int insertSelective(ToEvaRefund record);

    ToEvaRefund selectByPrimaryKey(Long pkid);
    
    ToEvaRefund selectByCaseCode(String caseCode);

    int updateByPrimaryKeySelective(ToEvaRefund record);

    int updateByPrimaryKey(ToEvaRefund record);

	boolean submitManager(ToEvaRefundVO toEvaRefundvo, String approveResult);

	boolean submitAssistant(ToEvaRefundVO toEvaRefundvo);

	boolean submitChif(ToEvaRefundVO toEvaRefundvo, String approveResult);
}
