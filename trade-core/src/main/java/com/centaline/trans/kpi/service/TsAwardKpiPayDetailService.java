package com.centaline.trans.kpi.service;

import java.util.Date;
import java.util.Map;
public interface TsAwardKpiPayDetailService {

	 void getPAwardKpiRate(Map map);
	 // 传入belongMonth createBy createTime
	 void getPAwardKpiRateStatic(Map map);
	Double getPersonBonusTotal(Date belongM);
}