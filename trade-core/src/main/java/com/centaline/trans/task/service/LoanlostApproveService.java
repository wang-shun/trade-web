package com.centaline.trans.task.service;

import java.util.List;
import java.util.Map;

import com.aist.common.web.validate.AjaxResponse;
import com.centaline.trans.task.entity.ToApproveRecord;
import com.centaline.trans.task.vo.LoanlostApproveVO;
import com.centaline.trans.task.vo.ProcessInstanceVO;

public interface LoanlostApproveService {

	public Boolean saveLoanlostApprove(ToApproveRecord toApproveRecord);
	
	public List<String> findApproveRecordByList(ToApproveRecord toApproveRecord);
	
	public Map<String, Object> queryCaseInfo(String caseCode, String partCode,String instCode);

	public ToApproveRecord findLastApproveRecord(ToApproveRecord toApproveRecord);

	AjaxResponse saveAndSubmitLoanlostApproveFirst(ProcessInstanceVO processInstanceVO, LoanlostApproveVO loanlostApproveVO,String loanLost, String loanLost_response);

	AjaxResponse saveAndSubmitLoanlostApproveSecond(ProcessInstanceVO processInstanceVO, LoanlostApproveVO loanlostApproveVO, String caseCloseSecondCheck, String caseCloseSecondCheck_response);

	AjaxResponse saveAndSubmitLoanlostApproveThird(ProcessInstanceVO processInstanceVO, LoanlostApproveVO loanlostApproveVO, String caseCloseThirdCheck, String caseCloseThirdCheck_response);
}
