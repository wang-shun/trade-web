package com.centaline.trans.performance.repository;

import java.util.List;

import com.centaline.trans.common.MyBatisRepository;
import com.centaline.trans.performance.entity.ReceivablePerf;

@MyBatisRepository
public interface ReceivablePerfMapper {
	int deleteByPrimaryKey(Long pkid);

	int insert(ReceivablePerf record);

	int insertSelective(ReceivablePerf record);

	ReceivablePerf selectByPrimaryKey(Long pkid);

	int updateByPrimaryKeySelective(ReceivablePerf record);

	int updateByPrimaryKey(ReceivablePerf record);

}