package com.centaline.trans.task.repository;

import com.centaline.trans.common.MyBatisRepository;
import com.centaline.trans.mortgage.entity.ToMortgage;
import com.centaline.trans.task.vo.MortgageToSaveVO;

@MyBatisRepository
public interface ToMortgageTosaveMapper {

	int saveMortgageTosave(MortgageToSaveVO mortgageTosaveVo);

	MortgageToSaveVO getTosave(ToMortgage mort);

	MortgageToSaveVO selectByCaseCode(String code);

	int updateByPrimary(MortgageToSaveVO mortgageToSaveVO);
}
