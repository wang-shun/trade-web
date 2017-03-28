package com.centaline.trans.task.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.centaline.trans.task.entity.ToTax;
import com.centaline.trans.task.repository.ToTaxMapper;
import com.centaline.trans.task.service.ToTaxService;

@Service
public class ToTaxServiceImpl implements ToTaxService{

	
	@Autowired
	private ToTaxMapper toTaxMapper;
	
	@Override
	public boolean saveToTax(ToTax toTax) {
		if(StringUtils.isBlank(toTax.getCaseCode())) {
			return false;
		}
		if(toTax.getPkid() != null) {
			if(toTaxMapper.updateByPrimaryKeySelective(toTax) > 0) {
				return true;
			}
		} else {
			if(toTaxMapper.findToTaxByCaseCode(toTax.getCaseCode()) == null) {
				if(toTaxMapper.insertSelective(toTax) > 0) {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public ToTax findToTaxByCaseCode(String caseCode) {
		return toTaxMapper.findToTaxByCaseCode(caseCode);
	}

}
