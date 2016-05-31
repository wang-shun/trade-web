package com.centaline.trans.kpi.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.centaline.trans.kpi.repository.TsAwardKpiPayDetailMapper;
import com.centaline.trans.kpi.service.TsAwardKpiPayDetailService;

@Service
public class TsAwardKpiPayDetailServiceImpl implements TsAwardKpiPayDetailService {
	@Autowired
	TsAwardKpiPayDetailMapper tsAwardKpiPayDetailMapper;
	
	@Override
	public void getPAwardKpiRate(Map map) {
		tsAwardKpiPayDetailMapper.getPAwardKpiRate(map);
	}

	@Override
	public void getPAwardKpiRateStatic(Map map) {
		tsAwardKpiPayDetailMapper.getPAwardKpiRateStatic(map);
	}

}
