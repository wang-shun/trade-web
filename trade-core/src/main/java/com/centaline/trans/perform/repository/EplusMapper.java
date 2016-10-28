package com.centaline.trans.perform.repository;

import java.util.Date;
import java.util.List;

import com.centaline.trans.common.MyBatisRepository;
import com.centaline.trans.perform.entity.EplusEntity;
@MyBatisRepository
public interface EplusMapper {
	  int insertSelective(EplusEntity record); 
	  int deleteByBizKey(EplusEntity record);
	  Integer getLastOrders(EplusEntity record);
	  List<EplusEntity> getByBelongMonth(Date belongMonth);
	  Integer getTransferCount(Date belongMonth,String orgId);
	  int updateSelective(EplusEntity record);
}
