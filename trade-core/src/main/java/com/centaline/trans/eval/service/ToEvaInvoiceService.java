package com.centaline.trans.eval.service;

import com.centaline.trans.eval.entity.ToEvaInvoice;
import com.centaline.trans.task.entity.ToApproveRecord;

public interface ToEvaInvoiceService {
	int deleteByPrimaryKey(Long pkid);

    int insert(ToEvaInvoice record);

    int insertSelective(ToEvaInvoice record);

    ToEvaInvoice selectByPrimaryKey(Long pkid);

    int updateByPrimaryKeySelective(ToEvaInvoice record);

    int updateByPrimaryKey(ToEvaInvoice record);
    
    int updateEvalInvoiceApproveRecord(ToApproveRecord toApproveRecord);

}
