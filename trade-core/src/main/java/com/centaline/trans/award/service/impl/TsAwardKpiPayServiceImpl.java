package com.centaline.trans.award.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.centaline.trans.award.entity.TsAwardKpiPay;
import com.centaline.trans.award.repository.TsAwardKpiPayMapper;
import com.centaline.trans.award.service.TsAwardKpiPayService;

@Service
@Transactional(readOnly = true)
public class TsAwardKpiPayServiceImpl implements TsAwardKpiPayService {
	@Autowired
	TsAwardKpiPayMapper tsAwardKpiPayMapper;
	
	@Override
	public List<TsAwardKpiPay> getTsAwardKpiPayByProperty(TsAwardKpiPay record) {
		return tsAwardKpiPayMapper.getTsAwardKpiPayByProperty(record);
	}

	@Override
	public int updateTsAwardKpiPayStatus(TsAwardKpiPay record) {
		return tsAwardKpiPayMapper.updateTsAwardKpiPayStatus(record);
	}

}
