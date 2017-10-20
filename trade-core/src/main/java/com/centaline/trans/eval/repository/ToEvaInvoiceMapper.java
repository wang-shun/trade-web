package com.centaline.trans.eval.repository;

import com.centaline.trans.common.MyBatisRepository;
import com.centaline.trans.eval.entity.ToEvaInvoice;
@MyBatisRepository
public interface ToEvaInvoiceMapper {
    int deleteByPrimaryKey(Long pkid);

    int insert(ToEvaInvoice record);

    int insertSelective(ToEvaInvoice record);

    ToEvaInvoice selectByPrimaryKey(Long pkid);
    
    ToEvaInvoice selectByCaseCode(String caseCode);
    
    ToEvaInvoice selectByEvaCode(String evaCode);
    
    ToEvaInvoice selectByCaseCodeWithEvalCompany(String caseCode);

    int updateByPrimaryKeySelective(ToEvaInvoice record);

    int updateByPrimaryKey(ToEvaInvoice record);
}