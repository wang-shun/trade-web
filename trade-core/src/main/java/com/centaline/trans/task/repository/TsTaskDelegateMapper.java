package com.centaline.trans.task.repository;

import java.util.List;

import com.centaline.trans.common.MyBatisRepository;
import com.centaline.trans.task.entity.TsTaskDelegate;

@MyBatisRepository
public interface TsTaskDelegateMapper {
	List<TsTaskDelegate> findTaskDelegatesByOwner(String owner);

	List<TsTaskDelegate> findOpenTaskDelegatesByOwner(String owner);

	int updateByPrimaryKey(TsTaskDelegate record);

	int insert(TsTaskDelegate record);

	int batchClose(TsTaskDelegate record);
}
