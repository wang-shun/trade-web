package com.centaline.trans.task.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.centaline.trans.task.entity.ToApproveRecord;
import com.centaline.trans.task.repository.ToApproveRecordMapper;
import com.centaline.trans.task.service.ToApproveRecordService;

@Service
public class ToApproveRecordServiceImpl implements ToApproveRecordService {
	
	@Autowired
	private ToApproveRecordMapper toApproveRecordMapper;

	@Override
	public ToApproveRecord queryToApproveRecord(ToApproveRecord toApproveRecord) {
		return toApproveRecordMapper.findApproveRecordByRecord(toApproveRecord);
	}

	@Override
	public void saveToApproveRecord(ToApproveRecord toApproveRecord) {
		if(StringUtils.isBlank(toApproveRecord.getCaseCode())) {
			return;
		}
		ToApproveRecord t=new ToApproveRecord();
		t.setTaskId(toApproveRecord.getTaskId());
		if(toApproveRecord.getPkid() != null) {
			toApproveRecordMapper.updateByPrimaryKeySelective(toApproveRecord);
		} else {
			if(toApproveRecordMapper.findApproveRecordByRecord(t) == null) {
				toApproveRecordMapper.insertSelective(toApproveRecord);
			}
		}
	}

	@Override
	public List<String> findOperatorList(ToApproveRecord toApproveRecord) {
		return toApproveRecordMapper.findApproveRecordByList(toApproveRecord);
	}

	@Override
	public void insertToApproveRecord(ToApproveRecord toApproveRecord) {
		toApproveRecordMapper.insertSelective(toApproveRecord);
		
	}

}
