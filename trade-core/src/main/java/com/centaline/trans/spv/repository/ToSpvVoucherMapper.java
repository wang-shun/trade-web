package com.centaline.trans.spv.repository;

import java.util.List;

import com.centaline.trans.common.MyBatisRepository;
import com.centaline.trans.spv.entity.ToSpvVoucher;
@MyBatisRepository
public interface ToSpvVoucherMapper {
    int deleteByPrimaryKey(Long pkid);

    int insert(ToSpvVoucher record);

    int insertSelective(ToSpvVoucher record);

    ToSpvVoucher selectByPrimaryKey(Long pkid);

    int updateByPrimaryKeySelective(ToSpvVoucher record);

    int updateByPrimaryKey(ToSpvVoucher record);

	List<ToSpvVoucher> selectByCashFlowId(String cashFlowId);
}