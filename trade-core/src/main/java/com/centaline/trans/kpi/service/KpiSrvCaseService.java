package com.centaline.trans.kpi.service;

import java.util.List;

import com.centaline.trans.kpi.vo.KpiSrvCaseVo;

public interface KpiSrvCaseService {

	boolean importBatch(List<KpiSrvCaseVo> listVOs,Boolean currentMonth);
	void callKpiStastic(Boolean currentMonth);
}
