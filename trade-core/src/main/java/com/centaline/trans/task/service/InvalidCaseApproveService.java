package com.centaline.trans.task.service;


import com.centaline.trans.task.entity.ToApproveRecord;
import com.centaline.trans.task.vo.LoanlostApproveVO;
import com.centaline.trans.task.vo.ProcessInstanceVO;

public interface InvalidCaseApproveService {
	ToApproveRecord saveToApproveRecord(ProcessInstanceVO processInstanceVO, LoanlostApproveVO loanlostApproveVO,
			String loanLost, String loanLost_response);

	Boolean invalidCaseApprove( ProcessInstanceVO processInstanceVO,
			LoanlostApproveVO loanlostApproveVO, String InvalidCaseApprove, String InvalidCaseApprove_response);
}
