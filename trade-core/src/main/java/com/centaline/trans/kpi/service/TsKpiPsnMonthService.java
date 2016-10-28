package com.centaline.trans.kpi.service;

import java.util.Date;
import java.util.List;
import com.centaline.trans.kpi.entity.TsKpiPsnMonth;
import com.centaline.trans.kpi.vo.KpiMonthVO;

public interface TsKpiPsnMonthService {
	
	List<TsKpiPsnMonth> getTsKpiPsnMonthListByPro(TsKpiPsnMonth record);
	
	int insertTsKpiPsnMonthList(List<TsKpiPsnMonth> list);
	
	int importExcelTsKpiPsnMonthList(Date belongM, String createBy, List<KpiMonthVO> list);
	
	void getPMonthKpiStastic(Date belongMonth);
	
	int deleteTsKpiPsnMonthByProperty(TsKpiPsnMonth record);
}
