package com.centaline.trans.cases.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.centaline.trans.cases.entity.VCaseTradeInfo;
import com.centaline.trans.cases.repository.VCaseTradeInfoMapper;
import com.centaline.trans.cases.service.VCaseTradeInfoService;

@Service
public class VCaseTradeInfoServiceImpl implements VCaseTradeInfoService {

    @Autowired
    private VCaseTradeInfoMapper vCaseTradeInfoMapper;
    
	@Override
	public VCaseTradeInfo queryCaseTradeInfoByCaseCode(String caseCode) {
		// TODO Auto-generated method stub
		return vCaseTradeInfoMapper.queryCaseTradeInfoByCaseCode(caseCode);
	}

}
