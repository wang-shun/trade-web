package com.centaline.trans.task.service;

import javax.servlet.http.HttpServletRequest;

import com.centaline.trans.cases.vo.EditCaseDetailVO;
import com.centaline.trans.task.vo.LoanlostApproveVO;
import com.centaline.trans.task.vo.ProcessInstanceVO;

public interface CaseCloseService {
	
	public boolean submitCaseClose(HttpServletRequest request, ProcessInstanceVO processInstanceVO,
			LoanlostApproveVO loanlostApproveVO,EditCaseDetailVO editCaseDetailVO) throws Exception;
	
	public Boolean caselostApproveThird(ProcessInstanceVO processInstanceVO,
			LoanlostApproveVO loanlostApproveVO, String CaseCloseThirdCheck, String CaseCloseThirdCheck_response) throws Exception;
	
	public Boolean caselostApproveFirst(HttpServletRequest request, ProcessInstanceVO processInstanceVO,
			LoanlostApproveVO loanlostApproveVO, String CaseCloseFirstCheck, String CaseCloseFirstCheck_response) throws Exception;
	
	public Boolean caselostApproveSecond(HttpServletRequest request, ProcessInstanceVO processInstanceVO,
			LoanlostApproveVO loanlostApproveVO, String CaseCloseSecondCheck, String CaseCloseSecondCheck_response) throws Exception;

}
