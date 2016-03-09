package com.centaline.trans.perform.repository;

import com.centaline.trans.common.MyBatisRepository;
import com.centaline.trans.perform.entity.EplusEntity;
@MyBatisRepository
public interface EplusMapper {
	  int insertSelective(EplusEntity record); 
	  int deleteByBizKey(EplusEntity record);
	  Integer getLastOrders(EplusEntity record);
}
