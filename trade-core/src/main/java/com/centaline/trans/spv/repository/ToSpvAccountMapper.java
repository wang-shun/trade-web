package com.centaline.trans.spv.repository;

import com.centaline.trans.common.MyBatisRepository;
import com.centaline.trans.spv.entity.ToSpvAccount;

@MyBatisRepository
public interface ToSpvAccountMapper {
    int deleteByPrimaryKey(Long pkid);

    int insert(ToSpvAccount record);

    int insertSelective(ToSpvAccount record);

    ToSpvAccount selectByPrimaryKey(Long pkid);

    int updateByPrimaryKeySelective(ToSpvAccount record);

    int updateByPrimaryKey(ToSpvAccount record);
}