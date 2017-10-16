package com.centaline.trans.task.service;

import com.centaline.trans.task.vo.ToAppRecordInfoVO;

public interface SelfLoanApprService {

	boolean saveAndSubmit(ToAppRecordInfoVO vo);

}
