package com.centaline.trans.cases.service;

import com.centaline.trans.cases.vo.EditCaseDetailVO;

public interface EditCaseDetailService{

	EditCaseDetailVO queryCaseDetai(String caseCode);
	
	void saveCaseDetai(EditCaseDetailVO editCaseDetailVO);
}
