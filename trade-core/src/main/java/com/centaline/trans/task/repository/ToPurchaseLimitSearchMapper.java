package com.centaline.trans.task.repository;

import com.centaline.trans.common.MyBatisRepository;
import com.centaline.trans.task.entity.ToPurchaseLimitSearch;

@MyBatisRepository
public interface ToPurchaseLimitSearchMapper {
    int deleteByPrimaryKey(Long pkid);

    int insert(ToPurchaseLimitSearch record);

    int insertSelective(ToPurchaseLimitSearch record);

    ToPurchaseLimitSearch selectByPrimaryKey(Long pkid);

    int updateByPrimaryKeySelective(ToPurchaseLimitSearch record);

    int updateByPrimaryKeyWithBLOBs(ToPurchaseLimitSearch record);

    int updateByPrimaryKey(ToPurchaseLimitSearch record);

    ToPurchaseLimitSearch findToPlsByCaseCode(String caseCode);
}