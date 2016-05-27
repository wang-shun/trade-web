package com.centaline.trans.log.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.centaline.trans.log.entity.TsAttachmentReadLog;
import com.centaline.trans.log.repository.TsAttachmentReadLogMapper;
import com.centaline.trans.log.service.TsAttachmentReadLogService;

@Service
public class TsAttachmentReadLogServiceImpl implements TsAttachmentReadLogService {
	
	@Autowired
	private TsAttachmentReadLogMapper tsAttachmentReadLogMapper;
	
	@Override
	public int addTsAttachmentReadLog(TsAttachmentReadLog tsAttachmentReadLog) {
		return tsAttachmentReadLogMapper.insertSelective(tsAttachmentReadLog);
	}

}
