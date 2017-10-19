package com.centaline.trans.eval.service;

import javax.servlet.http.HttpServletRequest;

import com.aist.common.web.validate.AjaxResponse;

/**
 * @Description:TODO
 * @author：jinwl6
 * @date:2017年10月18日
 * @version:
 */
public interface EvalDetailService {
	
	/**
	 * 评估详情页面服务
	 * @param request
	 * @param caseCode
	 * @param evaCode
	 */
	void evalDetail(HttpServletRequest request,String caseCode,String evaCode);
	
	 /**
	  * 评估单总览->评估驳回
	  * @param request
	  * @param caseCode
	  * @param evaCode
	  * @return
	  */
	 AjaxResponse<?>  evalReject(HttpServletRequest request,String caseCode,String evaCode);

}
