package com.centaline.trans.mortgage.service;

import com.centaline.trans.mortgage.entity.MortStep;

public interface MortStepService {

	void saveMortStep(MortStep mortStep);
	
	MortStep findMortStep(MortStep mortStep);

	Integer[] getMortStep(String caseCode);
}
