package com.centaline.trans.task.repository;

import com.centaline.trans.common.MyBatisRepository;
import com.centaline.trans.task.vo.MortgageToSaveVO;

@MyBatisRepository
public interface ToMortgageTosaveMapper {

	int saveMortgageTosave(MortgageToSaveVO mortgageTosaveVo);

}
