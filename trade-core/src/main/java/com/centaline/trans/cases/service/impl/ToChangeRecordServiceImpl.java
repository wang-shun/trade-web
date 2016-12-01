package com.centaline.trans.cases.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.centaline.trans.cases.entity.ToChangeRecord;
import com.centaline.trans.cases.repository.ToChangeRecordMapper;
import com.centaline.trans.cases.service.ToChangeRecordService;

@Service
public class ToChangeRecordServiceImpl implements ToChangeRecordService {
	
	@Autowired
	private ToChangeRecordMapper toChangeRecordMapper;

	@Override
	public int saveToChangeRecord(ToChangeRecord toChangeRecord) {
		return toChangeRecordMapper.insertSelective(toChangeRecord);
	}

}
