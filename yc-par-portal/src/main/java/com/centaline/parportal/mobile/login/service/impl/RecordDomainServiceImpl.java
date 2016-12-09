package com.centaline.parportal.mobile.login.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.centaline.parportal.mobile.login.service.RecordDomainService;

@Service
public class RecordDomainServiceImpl implements RecordDomainService {
	
	@Value("${app.record.domain.name}")
	private String recordIp;
	
	@Value("${app.custom.record.domain.name}")
	private String customRecordIp;

	@Override
	public String getRecordDomain() {
		// TODO Auto-generated method stub
		return recordIp;
	}

	@Override
	public String getCustomRecordDomain() {
		// TODO Auto-generated method stub
		return customRecordIp;
	}
	
}
