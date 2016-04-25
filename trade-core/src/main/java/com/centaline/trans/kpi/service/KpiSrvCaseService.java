package com.centaline.trans.kpi.service;

import java.util.List;

import com.centaline.trans.kpi.vo.KpiSrvCaseVo;

public interface KpiSrvCaseService {
	boolean importOne(KpiSrvCaseVo vo);

	boolean importBatch(List<KpiSrvCaseVo> listVOs,Boolean currentMonth);
}
