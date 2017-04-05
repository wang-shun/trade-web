package com.centaline.trans.task.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.centaline.trans.task.entity.ToPurchaseLimitSearch;
import com.centaline.trans.task.repository.ToPurchaseLimitSearchMapper;
import com.centaline.trans.task.service.ToPurchaseLimitSearchService;

@Service
public class ToPurchaseLimitSearchServiceImpl implements ToPurchaseLimitSearchService {

	@Autowired
	private ToPurchaseLimitSearchMapper toPurchaseLimitSearchMapper;
	
	@Override
	public boolean saveToPurchaseLimitSearch( ToPurchaseLimitSearch toPurchaseLimitSearch) {
		if(toPurchaseLimitSearch.getCaseCode()==null || toPurchaseLimitSearch.getCaseCode().intern().length() == 0) {
			return false;
		}
		if(toPurchaseLimitSearch.getPkid() != null) {
			if(toPurchaseLimitSearchMapper.updateByPrimaryKeySelective(toPurchaseLimitSearch) > 0) {
				return true;
			}
		} else {
			if(toPurchaseLimitSearchMapper.findToPlsByCaseCode(toPurchaseLimitSearch.getCaseCode()) == null) {
				if(toPurchaseLimitSearchMapper.insertSelective(toPurchaseLimitSearch) > 0) {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public ToPurchaseLimitSearch findToPlsByCaseCode(String caseCode) {
		return toPurchaseLimitSearchMapper.findToPlsByCaseCode(caseCode);
	}

}
