package com.centaline.trans.eval.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.centaline.trans.eval.entity.ToEvaSettleUpdateLog;
import com.centaline.trans.eval.repository.ToEvaSettleUpdateLogMapper;
import com.centaline.trans.eval.service.ToEvaSettleUpdateLogService;

@Service
public class ToEvaSettleUpdateLogServiceImpl implements ToEvaSettleUpdateLogService {
	
	@Autowired
	private ToEvaSettleUpdateLogMapper toEvaSettleUpdateLogMapper; 
	
	@Override
	public int deleteByPrimaryKey(Long pkid) {
		// TODO Auto-generated method stub
		return toEvaSettleUpdateLogMapper.deleteByPrimaryKey(pkid);
	}

	@Override
	public int insert(ToEvaSettleUpdateLog record) {
		// TODO Auto-generated method stub
		return toEvaSettleUpdateLogMapper.insert(record);
	}

	@Override
	public int insertSelective(ToEvaSettleUpdateLog record) {
		// TODO Auto-generated method stub
		return toEvaSettleUpdateLogMapper.insertSelective(record);
	}

	@Override
	public ToEvaSettleUpdateLog selectByPrimaryKey(Long pkid) {
		// TODO Auto-generated method stub
		return toEvaSettleUpdateLogMapper.selectByPrimaryKey(pkid);
	}

	@Override
	public int updateByPrimaryKeySelective(ToEvaSettleUpdateLog record) {
		// TODO Auto-generated method stub
		return toEvaSettleUpdateLogMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(ToEvaSettleUpdateLog record) {
		// TODO Auto-generated method stub
		return toEvaSettleUpdateLogMapper.updateByPrimaryKey(record);
	}

	@Override
	public int updateEvaLogByCaseCode(ToEvaSettleUpdateLog record) {
		// TODO Auto-generated method stub
		return toEvaSettleUpdateLogMapper.updateEvaLogByCaseCode(record);
	}

	@Override
	public List<ToEvaSettleUpdateLog> selectUpdateLogByCaseCode(String caseCode) {
		// TODO Auto-generated method stub
		return toEvaSettleUpdateLogMapper.selectUpdateLogByCaseCode(caseCode);
	}

	@Override
	public List<ToEvaSettleUpdateLog> selectUpdateLog() {
		// TODO Auto-generated method stub
		return toEvaSettleUpdateLogMapper.selectUpdateLog();
	}

	@Override
	public int deleteByCaseCode(String caseCode) {
		// TODO Auto-generated method stub
		return toEvaSettleUpdateLogMapper.deleteByCaseCode(caseCode);
	}

	@Override
	public List<ToEvaSettleUpdateLog> selectUpdateLogByCaseCodeAndDesc(String caseCode) {
		// TODO Auto-generated method stub
		return toEvaSettleUpdateLogMapper.selectUpdateLogByCaseCodeAndDesc(caseCode);
	}

}
