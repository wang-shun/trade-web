package com.centaline.trans.task.repository;

import com.centaline.trans.common.MyBatisRepository;
import com.centaline.trans.task.entity.ToSign;

@MyBatisRepository
public interface ToSignMapper {
    int deleteByPrimaryKey(Long pkid);

    int insert(ToSign record);

    int insertSelective(ToSign record);

    ToSign selectByPrimaryKey(Long pkid);

    int updateByPrimaryKeySelective(ToSign record);

    int updateByPrimaryKeyWithBLOBs(ToSign record);

    int updateByPrimaryKey(ToSign record);

    ToSign findToSignByCaseCode(String caseCode);
}