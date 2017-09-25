package com.centaline.trans.task.service;

import com.centaline.trans.task.vo.MortgageToSaveVO;

public interface ToMortgageTosaveService {

	int saveMortgageTosave(MortgageToSaveVO mortgageTosaveVo);

	boolean submit(MortgageToSaveVO mortgageToSaveVO);

	
}
