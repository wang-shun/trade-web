package com.centaline.trans.attachment.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.centaline.trans.attachment.entity.TsAttachmentReadLog;
import com.centaline.trans.attachment.repository.TsAttachmentReadLogMapper;
import com.centaline.trans.attachment.service.TsAttachmentReadLogService;

@Service
public class TsAttachmentReadLogServiceImpl implements TsAttachmentReadLogService {
	
	@Autowired
	private TsAttachmentReadLogMapper tsAttachmentReadLogMapper;
	
	@Override
	public int addTsAttachmentReadLog(TsAttachmentReadLog tsAttachmentReadLog) {
		return tsAttachmentReadLogMapper.insertSelective(tsAttachmentReadLog);
	}

}
