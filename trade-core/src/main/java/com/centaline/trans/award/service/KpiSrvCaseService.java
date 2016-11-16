package com.centaline.trans.award.service;

import java.util.List;

import com.centaline.trans.award.vo.KpiSrvCaseVo;

public interface KpiSrvCaseService {

	List<KpiSrvCaseVo> importBatch(List<KpiSrvCaseVo> listVOs,Boolean currentMonth);
	void callKpiStastic(Boolean currentMonth);
}
