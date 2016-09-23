package com.centaline.trans.spv.repository;

import com.centaline.trans.spv.entity.ToSpvVoucher;

public interface ToSpvVoucherMapper {
    int deleteByPrimaryKey(Long pkid);

    int insert(ToSpvVoucher record);

    int insertSelective(ToSpvVoucher record);

    ToSpvVoucher selectByPrimaryKey(Long pkid);

    int updateByPrimaryKeySelective(ToSpvVoucher record);

    int updateByPrimaryKey(ToSpvVoucher record);
}