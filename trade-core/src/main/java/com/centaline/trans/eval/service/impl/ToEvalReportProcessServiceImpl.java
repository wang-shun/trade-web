package com.centaline.trans.eval.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.centaline.trans.eval.entity.ToEvalReportProcess;
import com.centaline.trans.eval.repository.ToEvalReportProcessMapper;
import com.centaline.trans.eval.service.ToEvalReportProcessService;

@Service
public class ToEvalReportProcessServiceImpl implements ToEvalReportProcessService {
	
	@Autowired
	private ToEvalReportProcessMapper toEvalReportProcessMapper;
	
	@Override
	public int deleteByPrimaryKey(Long pkid) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int insert(ToEvalReportProcess record) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int insertSelective(ToEvalReportProcess record) {
		return toEvalReportProcessMapper.insertSelective(record);
	}

	@Override
	public ToEvalReportProcess selectByPrimaryKey(Long pkid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int updateByPrimaryKeySelective(ToEvalReportProcess record) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateByPrimaryKey(ToEvalReportProcess record) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public ToEvalReportProcess findToEvalReportProcessByCaseCode(String caseCode) {
		return toEvalReportProcessMapper.findToEvalReportProcessByCaseCode(caseCode);
	}

}
