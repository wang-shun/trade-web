package com.centaline.trans.eval.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.centaline.trans.eval.entity.ToEvalSettle;
import com.centaline.trans.eval.repository.ToEvalSettleMapper;
import com.centaline.trans.eval.service.ToEvalSettleService;

@Service
public class ToEvalSettleServiceImpl implements ToEvalSettleService {
	
	
	@Autowired
	private ToEvalSettleMapper toEvalSettleMapper; 
	
	@Override
	public int deleteByPrimaryKey(Long pkid) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int insert(ToEvalSettle record) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int insertSelective(ToEvalSettle record) {
		return toEvalSettleMapper.insertSelective(record);
	}

	@Override
	public ToEvalSettle selectByPrimaryKey(Long pkid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int updateByPrimaryKeySelective(ToEvalSettle toEvalSettle) {
		return toEvalSettleMapper.updateByPrimaryKeySelective(toEvalSettle);
	}

	@Override
	public int updateByPrimaryKey(ToEvalSettle record) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public int updateByCaseCode(ToEvalSettle record) {
		return toEvalSettleMapper.updateByCaseCode(record);
	}
	
	@Override
	public ToEvalSettle findToCaseByCaseCode(String caseCode) {
		return toEvalSettleMapper.findToCaseByCaseCode(caseCode);
	}

	@Override
	public int updateSettleFeeByCaseCode(ToEvalSettle record) {
		return toEvalSettleMapper.updateSettleFeeByCaseCode(record);
	}
	
	@Override
	public int newSettleFeeByCaseCode(ToEvalSettle record) {
		return toEvalSettleMapper.newSettleFeeByCaseCode(record);
	}

}