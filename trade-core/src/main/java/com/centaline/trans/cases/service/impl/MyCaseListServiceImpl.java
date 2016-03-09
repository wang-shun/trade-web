package com.centaline.trans.cases.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.centaline.trans.cases.entity.ToCase;
import com.centaline.trans.cases.entity.ToCaseInfo;
import com.centaline.trans.cases.repository.ToCaseInfoMapper;
import com.centaline.trans.cases.repository.ToCaseMapper;
import com.centaline.trans.cases.service.MyCaseListService;
import com.centaline.trans.common.entity.TgGuestInfo;
import com.centaline.trans.common.entity.TgServItemAndProcessor;
import com.centaline.trans.common.entity.ToPropertyInfo;
import com.centaline.trans.common.repository.TgGuestInfoMapper;
import com.centaline.trans.common.repository.TgServItemAndProcessorMapper;
import com.centaline.trans.common.repository.ToPropertyInfoMapper;

@Service
public class MyCaseListServiceImpl implements MyCaseListService {

    @Autowired
    private TgGuestInfoMapper tgGuestInfoMapper;
    @Autowired
    private TgServItemAndProcessorMapper tgServItemAndProcessorMapper;
    @Autowired
    private ToCaseInfoMapper toCaseInfoMapper;
    @Autowired
    private ToCaseMapper toCaseMapper;
    @Autowired
    private ToPropertyInfoMapper toPropertyInfoMapper;
    
	@Override
	public ToCase findToCaseByCaseCode(String caseCode) {
		// TODO Auto-generated method stub
		return toCaseMapper.findToCaseByCaseCode(caseCode);
	}

	@Override
	public ToCaseInfo findToCaseInfoByCaseCode(String caseCode) {
		// TODO Auto-generated method stub
		return toCaseInfoMapper.findToCaseInfoByCaseCode(caseCode);
	}

	@Override
	public List<TgGuestInfo> findTgGuestInfoByCaseCode(String caseCode) {
		// TODO Auto-generated method stub
		return tgGuestInfoMapper.findTgGuestInfoByCaseCode(caseCode);
	}

	@Override
	public ToPropertyInfo findToPropertyInfoByCaseCode(String caseCode) {
		// TODO Auto-generated method stub
		return toPropertyInfoMapper.findToPropertyInfoByCaseCode(caseCode);
	}

	@Override
	public List<TgServItemAndProcessor> findTgServItemAndProcessorByUserId(
			String userId) {
		// TODO Auto-generated method stub
		return tgServItemAndProcessorMapper.findTgServItemAndProcessorByUserId(userId);
	}

	@Override
	public List<String> findCaseCodesByUserId(
			String userId) {
		// TODO Auto-generated method stub
		return tgServItemAndProcessorMapper.findCaseCodesByUserId(userId);
	}


}
