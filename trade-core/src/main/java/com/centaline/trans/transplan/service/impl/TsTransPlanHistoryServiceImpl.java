package com.centaline.trans.transplan.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.centaline.trans.cases.repository.ToCaseMapper;
import com.centaline.trans.transplan.entity.TsTransPlanHistory;
import com.centaline.trans.transplan.repository.TsTransPlanHistoryMapper;
import com.centaline.trans.transplan.service.TsTransPlanHistoryService;
import com.centaline.trans.transplan.vo.TransPlanVO;
@Service
public class TsTransPlanHistoryServiceImpl implements TsTransPlanHistoryService {
	@Autowired
	private TsTransPlanHistoryMapper tsTransPlanHistoryMapper;
	@Override
	public int deleteByPrimaryKey(Long pkid) {
		return tsTransPlanHistoryMapper.deleteByPrimaryKey(pkid);
	}

	@Override
	public int insert(TsTransPlanHistory record) {
		return tsTransPlanHistoryMapper.insert(record);
	}

	@Override
	public int insertSelective(TsTransPlanHistory record) {
		return tsTransPlanHistoryMapper.insertSelective(record);
	}

	@Override
	public TsTransPlanHistory selectByPrimaryKey(Long pkid) {
		return tsTransPlanHistoryMapper.selectByPrimaryKey(pkid);
	}

	@Override
	public int updateByPrimaryKeySelective(TsTransPlanHistory record) {
		return tsTransPlanHistoryMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(TsTransPlanHistory record) {
		return tsTransPlanHistoryMapper.updateByPrimaryKey(record);
	}

	@Override
	public List<TransPlanVO> getTransPlanVOList(TransPlanVO transPlanVO) {
		return tsTransPlanHistoryMapper.getTransPlanVOList(transPlanVO);
	}

}
