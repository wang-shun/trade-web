package com.centaline.trans.spv.service;

import javax.servlet.http.HttpServletRequest;

import com.centaline.trans.spv.vo.SpvCloseInfoVO;

public interface SpvCloseApplyService {

	void spvCloseApplyPage(HttpServletRequest request, String spvCode, String businessKey);

	void spvCloseApplyProcess(HttpServletRequest request, String instCode, String taskId, String businessKey);

	void spvCloseHostAuditProcess(HttpServletRequest request, String instCode, String taskId, String businessKey);

	void spvCloseDirectorAuditProcess(HttpServletRequest request, String instCode, String taskId, String businessKey);

	public void spvClosePageDeal(HttpServletRequest request, SpvCloseInfoVO spvCloseInfoVO, String instCode);

	public void spvCloseApplyDeal(HttpServletRequest request, SpvCloseInfoVO spvCloseInfoVO, String taskId, String instCode, String businessKey, Boolean activateOrSuspend);

	public void spvCloseHostAuditDeal(HttpServletRequest request, SpvCloseInfoVO spvCloseInfoVO, String instCode, String taskitem, String taskId, String businessKey, Boolean result);

	public void spvCloseDirectorAuditDeal(HttpServletRequest request, SpvCloseInfoVO spvCloseInfoVO, String instCode, String taskitem, String taskId, String businessKey, Boolean result);

	public String  findInOutWorkFlowProcessBySpvCode(String spvCode);

}
