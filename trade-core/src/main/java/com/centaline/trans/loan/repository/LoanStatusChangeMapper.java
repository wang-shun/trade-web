package com.centaline.trans.loan.repository;

import java.util.List;

import com.centaline.trans.common.MyBatisRepository;
import com.centaline.trans.loan.entity.LoanStatusChange;

@MyBatisRepository
public interface LoanStatusChangeMapper {
	int insertSelective(LoanStatusChange loanStatusChange);

	int updateByPrimaryKeySelective(LoanStatusChange loanStatusChange);

	List<LoanStatusChange> listByLoanId(Long loanId);

	int deleteUnConfirm(Long loanId);
}
