package com.centaline.trans.eval.repository;

import com.centaline.trans.common.MyBatisRepository;
import com.centaline.trans.eval.entity.ToEvaInvoice;
@MyBatisRepository
public interface ToEvaInvoiceMapper {
    int deleteByPrimaryKey(Long pkid);

    int insert(ToEvaInvoice record);

    int insertSelective(ToEvaInvoice record);

    ToEvaInvoice selectByPrimaryKey(Long pkid);

    int updateByPrimaryKeySelective(ToEvaInvoice record);

    int updateByPrimaryKey(ToEvaInvoice record);
}