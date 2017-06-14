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
			throw new BusinessException("提交计件奖金数据请求参数有误！");
		}
		return tsAwardKpiPayMapper.updateTsAwardKpiPayStatusAndSyncManager(record);
	}

	
	@Override
	public TsAwardKpiPay getTsAwardKpiPayByStatus(TsAwardKpiPay record) {
		if(null == record){
			throw new BusinessException("查询计件奖金数据请求参数有误！");
		}	
		
		List<TsAwardKpiPay> list = tsAwardKpiPayMapper.getTsAwardKpiPayByProperty(record);
		TsAwardKpiPay tsAwardKpiPay = null;
		if(null != list && list.size() > 0){
			tsAwardKpiPay = list.get(0);
		}
		return tsAwardKpiPay;
	}

}
