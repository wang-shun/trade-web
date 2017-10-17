package com.centaline.trans.eval.service;

import com.aist.common.web.validate.AjaxResponse;
import com.centaline.trans.cases.vo.ServiceRestartVo;
import com.centaline.trans.engine.vo.StartProcessInstanceVo;

/**
 * @Description:TODO
 * @author：jinwl6
 * @date:2017年10月16日
 * @version:
 */
public interface EvalServiceStopService {
	
	AjaxResponse<StartProcessInstanceVo> checkIsCanStop(ServiceRestartVo vo);
	
	AjaxResponse<StartProcessInstanceVo> applySubmit(ServiceRestartVo vo);
	
	AjaxResponse<StartProcessInstanceVo> approveSubmit(ServiceRestartVo vo);

}
