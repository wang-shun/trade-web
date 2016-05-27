package com.centaline.trans.task.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.centaline.trans.cases.repository.ToCaseMapper;
import com.centaline.trans.task.entity.TsTransPlanHistory;
import com.centaline.trans.task.repository.TsTransPlanHistoryMapper;
import com.centaline.trans.task.service.TsTransPlanHistoryService;
import com.centaline.trans.task.vo.TransPlanVO;
@Service
public class TsTransPlanHistoryServiceImpl implements TsTransPlanHistoryService {
	@Autowired
	private TsTransPlanHistoryMapper tsTransPlanHistoryMapper;
	@Override
	public int deleteByPrimaryKey(Long pkid) {
		// TODO Auto-generated method stub
		return tsTransPlanHistoryMapper.deleteByPrimaryKey(pkid);
	}

	@Override
	public int insert(TsTransPlanHistory record) {
		// TODO Auto-generated method stub
		return tsTransPlanHistoryMapper.insert(record);
	}

	@Override
	public int insertSelective(TsTransPlanHistory record) {
		// TODO Auto-generated method stub
		return tsTransPlanHistoryMapper.insertSelective(record);
	}

	@Override
	public TsTransPlanHistory selectByPrimaryKey(Long pkid) {
		// TODO Auto-generated method stub
		return tsTransPlanHistoryMapper.selectByPrimaryKey(pkid);
	}

	@Override
	public int updateByPrimaryKeySelective(TsTransPlanHistory record) {
		// TODO Auto-generated method stub
		return tsTransPlanHistoryMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(TsTransPlanHistory record) {
		// TODO Auto-generated method stub
		return tsTransPlanHistoryMapper.updateByPrimaryKey(record);
	}

	@Override
	public List<TransPlanVO> getTransPlanVOList(TransPlanVO transPlanVO) {
		// TODO Auto-generated method stub
		return tsTransPlanHistoryMapper.getTransPlanVOList(transPlanVO);
	}

}
