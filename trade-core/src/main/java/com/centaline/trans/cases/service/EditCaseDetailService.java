package com.centaline.trans.cases.service;

import com.centaline.trans.cases.vo.EditCaseDetailVO;
import com.centaline.trans.task.vo.ProcessInstanceVO;

public interface EditCaseDetailService{

	EditCaseDetailVO queryCaseDetai(String caseCode);
	
	void saveCaseDetai(EditCaseDetailVO editCaseDetailVO);

	void saveCaseCloseDetai(EditCaseDetailVO editCaseDetailVO, ProcessInstanceVO processInstanceVO);
}
