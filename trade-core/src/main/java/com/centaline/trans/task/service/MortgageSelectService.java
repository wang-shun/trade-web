package com.centaline.trans.task.service;

import com.centaline.trans.task.vo.MortgageSelecteVo;

public interface MortgageSelectService {
	public boolean submit(MortgageSelecteVo vo);
	
	public boolean submit2(MortgageSelecteVo vo);

	void loanRequirementChange(MortgageSelecteVo vo);
}
