package com.centaline.trans.mgr.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.centaline.trans.mgr.entity.ToSupDocu;
import com.centaline.trans.mgr.repository.ToSupDocuMapper;
import com.centaline.trans.mgr.service.ToSupDocuService;

@Service
public class ToSupDocuServiceImpl implements ToSupDocuService{

	@Autowired
	private ToSupDocuMapper toSupDocuMapper;
	
	@Override
	public void saveToSupDocu(ToSupDocu toSupDocu) {
		
		toSupDocuMapper.insert(toSupDocu);
	}

	@Override
	public void updateToSupDocu(ToSupDocu toSupDocu) {
		toSupDocuMapper.updateByPrimaryKeySelective(toSupDocu);
		
	}

	@Override
	public ToSupDocu findByCaseCode(String caseCode) {
		
		return toSupDocuMapper.findByCaseCode(caseCode);
	}

}
