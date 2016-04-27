package com.centaline.trans.kpi.repository;

import java.util.Date;
import java.util.List;

import com.centaline.trans.common.MyBatisRepository;
import com.centaline.trans.kpi.entity.TsKpiSrvCase;

@MyBatisRepository
public interface TsKpiSrvCaseMapper {
	int deleteByPrimaryKey(Long pkid);

	int insert(TsKpiSrvCase record);

	int insertSelective(TsKpiSrvCase record);

	TsKpiSrvCase selectByPrimaryKey(Long pkid);

	int updateByPrimaryKeySelective(TsKpiSrvCase record);

	int updateByPrimaryKey(TsKpiSrvCase record);
	
	int deleteByBelongMonth(Date belongMoht);
	
	int batchInsert(List<TsKpiSrvCase> list);
	
	void callKpiStastic(Date date);
}