package com.centaline.trans.ransom.service;

import com.centaline.trans.ransom.entity.ToRansomCaseVo;
import com.centaline.trans.task.entity.ToApproveRecord;
import com.centaline.trans.task.vo.LoanlostApproveVO;
import com.centaline.trans.task.vo.ProcessInstanceVO;

public interface RansomDiscontinueService {
	
	boolean submitDiscontinueApply(ToRansomCaseVo ransomCase, ProcessInstanceVO processInstanceVO);
	
	ToApproveRecord saveToApproveRecord(ProcessInstanceVO processInstanceVO, LoanlostApproveVO loanlostApproveVO,
			String loanLost, String loanLost_response);
	boolean submitDiscontinueAppro(ProcessInstanceVO processInstanceVO, String examContent, String caseCode, String ransomCode);
	
	boolean startDiscontinueTask(String caseCode, String ransomCase);
}
