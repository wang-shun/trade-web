package com.centaline.trans.task.repository;

import com.centaline.trans.common.MyBatisRepository;
import com.centaline.trans.task.entity.ToTax;

@MyBatisRepository
public interface ToTaxMapper {
    int deleteByPrimaryKey(Long pkid);

    int insert(ToTax record);

    int insertSelective(ToTax record);

    ToTax selectByPrimaryKey(Long pkid);

    int updateByPrimaryKeySelective(ToTax record);

    int updateByPrimaryKeyWithBLOBs(ToTax record);

    int updateByPrimaryKey(ToTax record);
    
    ToTax findToTaxByCaseCode(String caseCode);
}