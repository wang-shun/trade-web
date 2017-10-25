package com.centaline.trans.task.service.impl;

import com.centaline.trans.task.entity.ToApproveRecord;
import com.centaline.trans.task.repository.ToApproveRecordMapper;
import com.centaline.trans.task.service.ToApproveRecordService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ToApproveRecordServiceImpl implements ToApproveRecordService {
	
	@Autowired
	private ToApproveRecordMapper toApproveRecordMapper;

	@Override
	public ToApproveRecord queryToApproveRecord(ToApproveRecord toApproveRecord) {
		return toApproveRecordMapper.findApproveRecordByRecord(toApproveRecord);
	}
	
	@Override
	public ToApproveRecord queryToApproveRecordForSpvApply(ToApproveRecord toApproveRecord) {
		return toApproveRecordMapper.findApproveRecordByRecordForSpvApply(toApproveRecord);
	}
	
	

	@Override
	public void saveToApproveRecord(ToApproveRecord toApproveRecord) {
		if(StringUtils.isBlank(toApproveRecord.getCaseCode())) {
			return;
		}
		if(toApproveRecord.getPkid() != null) {
			toApproveRecordMapper.updateByPrimaryKeySelective(toApproveRecord);
		} else {
			toApproveRecordMapper.insertSelective(toApproveRecord);
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

	@Override
	public void deleteByCaseCodeAndType(String caseCode, String approveType) {
		toApproveRecordMapper.deleteByCaseCodeAndType(caseCode,approveType);
	}
	
	@Override
	public List<ToApproveRecord> queryToApproveRecords(ToApproveRecord toApproveRecord) {
		return toApproveRecordMapper.findApproveRecordByRecords(toApproveRecord);
	}

}
