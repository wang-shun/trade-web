package com.centaline.trans.loan.service.impl;

import java.math.BigDecimal;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.centaline.trans.loan.entity.ToCloseLoan;
import com.centaline.trans.loan.repository.ToCloseLoanMapper;
import com.centaline.trans.loan.service.ToCloseLoanService;

@Service
public class ToCloseLoanServiceImpl implements ToCloseLoanService{

	@Autowired
	private ToCloseLoanMapper toCloseLoanMapper;
	
	@Override
	public Boolean saveToCloseLoan(ToCloseLoan toCloseLoan) {
		if(!StringUtils.isBlank(toCloseLoan.getCaseCode())) {
			toCloseLoan.setUncloseMoney(toCloseLoan.getUncloseMoney().multiply(new BigDecimal(10000)));
			if(toCloseLoan.getPkid() == null) {
				if(toCloseLoanMapper.findToCloseLoanByCaseCode(toCloseLoan.getCaseCode()) == null) {
				toCloseLoanMapper.insertSelective(toCloseLoan);
				}
			} else {
				toCloseLoanMapper.updateByPrimaryKeySelective(toCloseLoan);
			}
			return true;
		}
		return false;
	}

	@Override
	public ToCloseLoan qureyToCloseLoan(String caseCode) {
		ToCloseLoan toCloseLoan = toCloseLoanMapper.findToCloseLoanByCaseCode(caseCode);
		if(toCloseLoan!=null){
			toCloseLoan.setUncloseMoney(toCloseLoan.getUncloseMoney()!=null?toCloseLoan.getUncloseMoney().divide(new BigDecimal(10000)):null);
		}
		return toCloseLoan;
	}

}
