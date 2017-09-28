package com.centaline.trans.task.service;

import com.centaline.trans.mortgage.entity.ToMortgage;
import com.centaline.trans.task.vo.MortgageToSaveVO;

public interface ToMortgageTosaveService {

	int saveMortgageTosave(MortgageToSaveVO mortgageTosaveVo);

	boolean submit(MortgageToSaveVO mortgageToSaveVO);

	MortgageToSaveVO getTosave(ToMortgage mort);

	MortgageToSaveVO selectByCaseCode(String code);

	int updateByPrimary(MortgageToSaveVO mortgageToSaveVO);
}
