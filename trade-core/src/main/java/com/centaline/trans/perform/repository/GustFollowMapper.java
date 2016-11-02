package com.centaline.trans.perform.repository;

import com.centaline.trans.common.MyBatisRepository;
import com.centaline.trans.perform.entity.EplusEntity;
import com.centaline.trans.perform.entity.GustFollowEntity;
@MyBatisRepository
public interface GustFollowMapper {
	  int insertSelective(GustFollowEntity record);
	  int deleteByBizKey(GustFollowEntity record);
}
