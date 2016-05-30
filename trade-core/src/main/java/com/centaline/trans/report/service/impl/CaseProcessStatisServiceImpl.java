package com.centaline.trans.report.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.centaline.trans.report.entity.CaseProcessStatis;
import com.centaline.trans.report.repository.CaseProcessStatisMapper;
import com.centaline.trans.report.service.CaseProcessStatisService;

@Service
public class CaseProcessStatisServiceImpl implements CaseProcessStatisService {
	
	Logger logger = LoggerFactory.getLogger(CaseProcessStatisServiceImpl.class);
	
	@Autowired
	private CaseProcessStatisMapper caseProcessStatisMapper;

	@Override
	public int insert(CaseProcessStatis caseProcessStatis) {
		return this.caseProcessStatisMapper.insert(caseProcessStatis);
	}

	@Override
	public int update(CaseProcessStatis caseProcessStatis) {
		return this.caseProcessStatisMapper.update(caseProcessStatis);
	}

}
