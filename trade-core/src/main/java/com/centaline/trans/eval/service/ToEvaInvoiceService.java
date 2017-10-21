package com.centaline.trans.eval.service;

import com.centaline.trans.eval.entity.ToEvaInvoice;
import com.centaline.trans.task.entity.ToApproveRecord;

public interface ToEvaInvoiceService {
	//  查出当前案件所指向的评估单
	ToEvaInvoice selectByCaseCodeWithEvaPointer(String caseCode);
  
    ToEvaInvoice selectByEvaCode(String evaCode);
    
	int deleteByPrimaryKey(Long pkid);

    int insert(ToEvaInvoice record);

    int insertSelective(ToEvaInvoice record);

    ToEvaInvoice selectByPrimaryKey(Long pkid);
    
    ToEvaInvoice selectByCaseCode(String caseCode);
    
    ToEvaInvoice selectByCaseCodeWithEvalCompany(String caseCode);

    int updateByPrimaryKeySelective(ToEvaInvoice record);

    int updateByPrimaryKey(ToEvaInvoice record);
//	权证经理发票审核是否通过
    int updateEvalInvoiceApproveRecord(ToApproveRecord toApproveRecord);

}
