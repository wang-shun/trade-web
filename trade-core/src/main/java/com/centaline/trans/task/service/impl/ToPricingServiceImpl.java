package com.centaline.trans.task.service.impl;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.centaline.trans.task.entity.ToPricing;
import com.centaline.trans.task.repository.ToPricingMapper;
import com.centaline.trans.task.service.ToPricingService;

@Service
public class ToPricingServiceImpl implements ToPricingService {

	@Autowired
	private ToPricingMapper toPricingMapper;

	@Override
	public boolean saveToPricing(ToPricing toPricing) {
		if (toPricing == null || toPricing.getCaseCode() == null
				|| toPricing.getCaseCode().intern().isEmpty()) {
			return false;
		}
		toPricing.setTaxPricing(toPricing.getTaxPricing() != null ? toPricing
				.getTaxPricing().multiply(new BigDecimal(10000)) : null);
		if (toPricing.getPkid() != null) {
			if (toPricingMapper.updateByPrimaryKeySelective(toPricing) > 0) {
				return true;
			}
		} else {
			if (toPricingMapper
					.findToPricingByCaseCode(toPricing.getCaseCode()) == null) {
				if (toPricingMapper.insertSelective(toPricing) > 0) {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public ToPricing qureyPricing(String caseCode) {
		ToPricing toPricing = toPricingMapper.findToPricingByCaseCode(caseCode);
		if (toPricing != null) {
			toPricing
					.setTaxPricing(toPricing.getTaxPricing() != null ? toPricing
							.getTaxPricing().divide(new BigDecimal(10000))
							: null);
		}
		return toPricing;
	}

}
