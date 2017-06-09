package com.centaline.trans.award.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aist.common.exception.BusinessException;
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
	
	
	@Override
	public int updateTsAwardKpiPayStatusAndSyncManager(TsAwardKpiPay record) {
		
		if(null == record){
			throw new BusinessException("提交基金奖金数据请求参数有误！");
		}
		return tsAwardKpiPayMapper.updateTsAwardKpiPayStatusAndSyncManager(record);
	}

}
