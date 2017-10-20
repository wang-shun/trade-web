package com.centaline.trans.cases.service;

import com.aist.common.web.validate.AjaxResponse;
import com.centaline.trans.cases.vo.ServiceRestartVo;
import com.centaline.trans.engine.vo.StartProcessInstanceVo;

/**
 * 案件爆单Service接口
 * @author wbcaiyx
 *
 */
public interface CaseStopService {
	
	AjaxResponse<StartProcessInstanceVo> checkIsCanStop(String caseCode);
	
	AjaxResponse<String> applySubmit(ServiceRestartVo vo);
	
	AjaxResponse<String> approveSubmit(ServiceRestartVo vo);

}
