package com.centaline.trans.spv.repository;

import com.centaline.trans.spv.entity.toSpvVoucher;

public interface toSpvVoucherMapper {
    int deleteByPrimaryKey(Long pkid);

    int insert(toSpvVoucher record);

    int insertSelective(toSpvVoucher record);

    toSpvVoucher selectByPrimaryKey(Long pkid);

    int updateByPrimaryKeySelective(toSpvVoucher record);

    int updateByPrimaryKey(toSpvVoucher record);
}