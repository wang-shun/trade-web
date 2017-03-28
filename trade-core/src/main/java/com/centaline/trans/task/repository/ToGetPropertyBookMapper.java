package com.centaline.trans.task.repository;

import com.centaline.trans.common.MyBatisRepository;
import com.centaline.trans.task.entity.ToGetPropertyBook;

@MyBatisRepository
public interface ToGetPropertyBookMapper {
    int deleteByPrimaryKey(Long pkid);

    int insert(ToGetPropertyBook record);

    int insertSelective(ToGetPropertyBook record);

    ToGetPropertyBook selectByPrimaryKey(Long pkid);

    int updateByPrimaryKeySelective(ToGetPropertyBook record);

    int updateByPrimaryKeyWithBLOBs(ToGetPropertyBook record);

    int updateByPrimaryKey(ToGetPropertyBook record);
    
    ToGetPropertyBook findGetPropertyBookByCaseCode(String caseCode);
}