package com.centaline.trans.task.repository;

import com.centaline.trans.common.MyBatisRepository;
import com.centaline.trans.task.entity.MortgageSelect;
@MyBatisRepository
public interface MortgageSelectMapper {

	int save( MortgageSelect mortgageSelect);
	
	MortgageSelect selectByCaseCode(String caseCode);

	int update(MortgageSelect mortgageSelect);

	void deleteByCode(String caseCode);

}
