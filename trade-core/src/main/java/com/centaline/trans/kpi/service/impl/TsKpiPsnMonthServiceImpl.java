package com.centaline.trans.kpi.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.centaline.trans.kpi.entity.TsKpiPsnMonth;
import com.centaline.trans.kpi.repository.TsKpiPsnMonthMapper;
import com.centaline.trans.kpi.service.TsKpiPsnMonthService;

@Service
public class TsKpiPsnMonthServiceImpl implements TsKpiPsnMonthService {
	
	@Autowired
	private TsKpiPsnMonthMapper tsKpiPsnMonthMapper;
	
	@Override
	public List<TsKpiPsnMonth> getTsKpiPsnMonthListByPro(TsKpiPsnMonth record) {
		return tsKpiPsnMonthMapper.getTsKpiPsnMonthListByPro(record);
	}

	@Override
	public int insertTsKpiPsnMonthList(List<TsKpiPsnMonth> list) {
		return tsKpiPsnMonthMapper.insertTsKpiPsnMonthList(list);
	}

}
