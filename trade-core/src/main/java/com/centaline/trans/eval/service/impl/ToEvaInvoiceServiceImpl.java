package com.centaline.trans.eval.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.centaline.trans.eval.entity.ToEvaInvoice;
import com.centaline.trans.eval.service.ToEvaInvoiceService;
import com.centaline.trans.task.entity.ToApproveRecord;
import com.centaline.trans.task.repository.ToApproveRecordMapper;
@Service
public class ToEvaInvoiceServiceImpl implements ToEvaInvoiceService {
	@Autowired
	private ToApproveRecordMapper toApproveRecordMapper; 
	
	@Override
	public int deleteByPrimaryKey(Long pkid) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int insert(ToEvaInvoice record) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int insertSelective(ToEvaInvoice record) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public ToEvaInvoice selectByPrimaryKey(Long pkid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int updateByPrimaryKeySelective(ToEvaInvoice record) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateByPrimaryKey(ToEvaInvoice record) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateEvalInvoiceApproveRecord(ToApproveRecord toApproveRecord) {
		ToApproveRecord findApproveRecordByRecord = toApproveRecordMapper.findApproveRecordByRecord(toApproveRecord);
		if(null!=findApproveRecordByRecord){
			findApproveRecordByRecord.setNotApprove(toApproveRecord.getNotApprove());
			findApproveRecordByRecord.setContent(toApproveRecord.getContent());
			return toApproveRecordMapper.updateByPrimaryKeyWithBLOBs(findApproveRecordByRecord);
		}else{
			return toApproveRecordMapper.insertSelective(toApproveRecord);
		}

	}

}
