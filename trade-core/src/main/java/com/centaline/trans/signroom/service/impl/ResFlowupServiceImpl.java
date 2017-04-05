package com.centaline.trans.signroom.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.centaline.trans.signroom.entity.ResFlowup;
import com.centaline.trans.signroom.repository.ResFlowupMapper;
import com.centaline.trans.signroom.service.ResFlowupService;
import com.centaline.trans.signroom.vo.ResFlowupVo;

@Service
public class ResFlowupServiceImpl implements ResFlowupService {

	@Autowired
	private ResFlowupMapper resFlowupMapper;

	@Override
	public int saveResFlowup(ResFlowup resFlowup) {
		return resFlowupMapper.insertSelective(resFlowup);
	}

	@Override
	public List<ResFlowupVo> getResFlowupListByResId(Long resId) {
		return resFlowupMapper.getResFlowupListByResId(resId);
	}

}
