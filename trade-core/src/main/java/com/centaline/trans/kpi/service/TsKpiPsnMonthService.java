package com.centaline.trans.kpi.service;

import java.util.List;

import com.centaline.trans.kpi.entity.TsKpiPsnMonth;

public interface TsKpiPsnMonthService {
	
	List<TsKpiPsnMonth> getTsKpiPsnMonthListByPro(TsKpiPsnMonth record);
	
	int insertTsKpiPsnMonthList(List<TsKpiPsnMonth> list);
}
