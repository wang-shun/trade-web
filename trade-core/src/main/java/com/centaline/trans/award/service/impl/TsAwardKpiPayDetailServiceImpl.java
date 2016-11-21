package com.centaline.trans.award.service.impl;

import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.centaline.trans.award.repository.TsAwardKpiPayDetailMapper;
import com.centaline.trans.award.service.TsAwardKpiPayDetailService;

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

	@Override
	public Double getPersonBonusTotal(Date belongM) {
		return tsAwardKpiPayDetailMapper.getPersonBonusTotal(belongM);
	}

}
