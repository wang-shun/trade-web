package com.centaline.trans.award.repository;

import com.centaline.trans.award.entity.BaseImportEntity;
import com.centaline.trans.common.MyBatisRepository;
@MyBatisRepository
public interface BaseImportMapper {
	 int insertSelective(BaseImportEntity record); 
	  int deleteByBizKey(BaseImportEntity record);
}
