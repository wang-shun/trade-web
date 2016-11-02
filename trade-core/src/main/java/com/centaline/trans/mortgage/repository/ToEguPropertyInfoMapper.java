package com.centaline.trans.mortgage.repository;

import com.centaline.trans.common.MyBatisRepository;
import com.centaline.trans.mortgage.entity.ToEguPropertyInfo;

@MyBatisRepository
public interface ToEguPropertyInfoMapper {
	int deleteByPrimaryKey(Long pkid);

	int insertSelective(ToEguPropertyInfo record);

	ToEguPropertyInfo selectByPrimaryKey(Long pkid);

	int updateByPrimaryKeySelective(ToEguPropertyInfo record);

	ToEguPropertyInfo findByEvaCode(String evaCode);
}