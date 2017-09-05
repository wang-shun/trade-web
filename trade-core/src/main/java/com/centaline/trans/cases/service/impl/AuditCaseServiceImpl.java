package com.centaline.trans.cases.service.impl;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.centaline.trans.cases.entity.ToCaseInfo;
import com.centaline.trans.cases.repository.ToCaseInfoMapper;

import com.centaline.trans.cases.service.AuditCaseService;
import com.centaline.trans.common.entity.ToCcaiAttachment;
import com.centaline.trans.common.repository.ToCcaiAttachmentMapper;

@Service
public class AuditCaseServiceImpl implements AuditCaseService {
	@Autowired
	private ToCcaiAttachmentMapper toCcaiAttachmentMapper;
	
	@Autowired
	private ToCaseInfoMapper toCaseInfoMapper;

	@Override
	public String getPayType(String caseCode) {
		// TODO Auto-generated method stub
		ToCaseInfo caseInfo = toCaseInfoMapper.findToCaseInfoByCaseCode(caseCode);
		return caseInfo.getPayType();
	}

	@Override
	public List<ToCcaiAttachment> getCcaiAttachment(String caseCode) {
		return toCcaiAttachmentMapper.findAttachmentsByCaseCode(caseCode);
	}
	
}
