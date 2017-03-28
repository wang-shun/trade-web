package com.centaline.trans.spv.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.centaline.trans.spv.entity.ToCashFlow;
import com.centaline.trans.spv.repository.ToCashFlowMapper;
import com.centaline.trans.spv.service.ToCashFlowService;


@Service
public class ToCashFlowServiceImpl implements ToCashFlowService{

	
	@Autowired
	private ToCashFlowMapper tocashFlowMapper;
	
	@Override
	public int insertCashFlow(ToCashFlow cashflow) {
		
		int insertcash=tocashFlowMapper.insert(cashflow);
		return insertcash;
	}

}
