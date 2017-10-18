package com.centaline.trans.eval.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.centaline.trans.cases.repository.ToCaseInfoMapper;
import com.centaline.trans.eval.entity.ToEvaRefund;
import com.centaline.trans.eval.repository.ToEvaRefundMapper;
import com.centaline.trans.eval.service.ToEvaRefundService;
@Service
public class ToEvaRefundServiceImpl implements ToEvaRefundService{

	@Autowired
	private ToEvaRefundMapper toEvaRefundMapper;
	
	@Autowired
	private ToCaseInfoMapper toCaseInfoMapper;
	
	
	@Override
	public int deleteByPrimaryKey(Long pkid) {
		// TODO Auto-generated method stub
		return toEvaRefundMapper.deleteByPrimaryKey(pkid);
	}

	@Override
	public int insert(ToEvaRefund record) {
		// TODO Auto-generated method stub
		return toEvaRefundMapper.insert(record);
	}

	@Override
	public String insertSelective(ToEvaRefund record) {
		if(StringUtils.isBlank(record.getCcaiCode())){
			return null;
		}
		String caseCode = toCaseInfoMapper.findcaseCodeByCcaiCode(record.getCcaiCode());
		if(StringUtils.isBlank(caseCode)){
			return null;
		}
		record.setCaseCode(caseCode); 
		int count = toEvaRefundMapper.insertSelective(record);
		if(count == 0){
			return null;
		}
		return caseCode;
	}

	@Override
	public ToEvaRefund selectByPrimaryKey(Long pkid) {
		// TODO Auto-generated method stub
		ToEvaRefund toEvaRefund = toEvaRefundMapper.selectByPrimaryKey(pkid);
		return toEvaRefund;
	}

	@Override
	public ToEvaRefund selectByCaseCode(String caseCode) {
		// TODO Auto-generated method stub
		ToEvaRefund toEvaRefund = toEvaRefundMapper.selectByCaseCode(caseCode);
		return toEvaRefund;
	}

	@Override
	public int updateByPrimaryKeySelective(ToEvaRefund record) {
		// TODO Auto-generated method stub
		return toEvaRefundMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(ToEvaRefund record) {
		// TODO Auto-generated method stub
		return toEvaRefundMapper.updateByPrimaryKey(record);
	}
	

}
