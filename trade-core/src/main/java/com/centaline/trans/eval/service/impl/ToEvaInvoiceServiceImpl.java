package com.centaline.trans.eval.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.centaline.trans.eval.entity.ToEvaInvoice;
import com.centaline.trans.eval.repository.ToEvaInvoiceMapper;
import com.centaline.trans.eval.service.ToEvaInvoiceService;
import com.centaline.trans.task.entity.ToApproveRecord;
import com.centaline.trans.task.repository.ToApproveRecordMapper;
@Service
public class ToEvaInvoiceServiceImpl implements ToEvaInvoiceService {
	@Autowired
	private ToApproveRecordMapper toApproveRecordMapper; 
	@Autowired
	private ToEvaInvoiceMapper toEvaInvoiceMapper; 

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
		return toEvaInvoiceMapper.insertSelective(record);
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
//	权证经理发票审核是否通过
	public int updateEvalInvoiceApproveRecord(ToApproveRecord toApproveRecord) {
			return toApproveRecordMapper.insertSelective(toApproveRecord);
	}

	@Override
	public ToEvaInvoice selectByCaseCode(String caseCode) {
		ToEvaInvoice toEvaInvoice = toEvaInvoiceMapper.selectByCaseCode(caseCode);
		return toEvaInvoice;
	}

	@Override
	public ToEvaInvoice selectByCaseCodeWithEvalCompany(String caseCode) {
		return toEvaInvoiceMapper.selectByCaseCodeWithEvalCompany(caseCode);
	}

	@Override
	public ToEvaInvoice selectByEvaCode(String evaCode) {
		// TODO Auto-generated method stub
		return toEvaInvoiceMapper.selectByEvaCode(evaCode);
	}



}
