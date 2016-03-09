package com.centaline.trans.mortgage.service.impl;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.centaline.trans.mortgage.entity.MortStep;
import com.centaline.trans.mortgage.entity.ToEguPricing;
import com.centaline.trans.mortgage.repository.MortStepMapper;
import com.centaline.trans.mortgage.repository.ToEguPricingMapper;
import com.centaline.trans.mortgage.service.MortStepService;

@Service
public class MortStepServiceImpl implements MortStepService {

	@Autowired
	private MortStepMapper mortStepMapper;

	@Autowired
	private ToEguPricingMapper toEguPricingMapper;

	@Override
	public void saveMortStep(MortStep mortStep) {
		MortStep step = mortStepMapper.findMortStep(mortStep);
		if (step == null) {
			mortStepMapper.insertSelective(mortStep);
		} else {
			if (mortStep.getStep() > step.getStep()) {
				step.setStep(mortStep.getStep());
				mortStepMapper.updateByPrimaryKeySelective(step);
			}
		}
	}

	@Deprecated
	@Override
	public MortStep findMortStep(MortStep mortStep) {
		List<ToEguPricing> toEguPricingList = toEguPricingMapper.findIsFinalEguPricing(mortStep.getCaseCode());
		if (CollectionUtils.isNotEmpty(toEguPricingList)) {
			if (StringUtils.isNotEmpty(toEguPricingList.get(0).getIsMainLoanBank())) {
				mortStep.setIsMainLoanBank(toEguPricingList.get(0).getIsMainLoanBank());
			} else {
				mortStep.setIsMainLoanBank("1");
			}
		}
		return mortStepMapper.findMortStep(mortStep);
	}
	@Override
	public Integer[] getMortStep(String caseCode){
		Integer[] result=new Integer[]{0,0};
		List<MortStep>mortSteps= mortStepMapper.listByCaseCode(caseCode);
		if(mortSteps!=null&&!mortSteps.isEmpty()){
			for (MortStep mortStep : mortSteps) {
				if("1".equals(mortStep.getIsMainLoanBank())){
					result[1]=mortStep.getStep();
				}else{
					result[0]=mortStep.getStep();
				}
			}
		}
		return result;
	}

}
