package com.centaline.trans.eval.repository;

import java.util.List;

import com.centaline.trans.common.MyBatisRepository;
import com.centaline.trans.eval.entity.ToEvaInvoice;
@MyBatisRepository
public interface ToEvaInvoiceMapper {
    int deleteByPrimaryKey(Long pkid);

    int insert(ToEvaInvoice record);

    int insertSelective(ToEvaInvoice record);

    ToEvaInvoice selectByPrimaryKey(Long pkid);
    
    ToEvaInvoice selectByCaseCode(String caseCode);
//    查出当前案件所指向的评估单
    List<ToEvaInvoice> selectByCaseCodeWithEvaPointer(String caseCode);
    
    ToEvaInvoice selectByEvaCode(String evaCode);
    
    ToEvaInvoice selectByCaseCodeWithEvalCompany(String caseCode);

    int updateByPrimaryKeySelective(ToEvaInvoice record);

    int updateByPrimaryKey(ToEvaInvoice record);
}