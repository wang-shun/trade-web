package com.centaline.trans.mgr.repository;

import java.util.List;

import com.centaline.trans.common.MyBatisRepository;
import com.centaline.trans.mgr.entity.UnlocateGRP;
@MyBatisRepository
public interface UnlocateGRPMapper {
	Integer count();

	List<UnlocateGRP> groupByCount();
}
