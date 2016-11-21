package com.centaline.trans.spv.service;

import javax.servlet.http.HttpServletRequest;

import com.centaline.trans.spv.vo.SpvCloseInfoVO;

public interface SpvCloseApplyService {

	void spvCloseApplyPage(HttpServletRequest request, String spvCode, String businessKey);

	void spvCloseApplyProcess(HttpServletRequest request, String businessKey);

	void spvCloseManagerAuditProcess(HttpServletRequest request, String businessKey);

	void spvCloseDirectorAuditProcess(HttpServletRequest request, String businessKey);

	public void spvCloseApplyPageDeal(HttpServletRequest request, String spvCode, SpvCloseInfoVO spvCloseInfoVO, String businessKey);

	public void spvCloseApplyDeal(HttpServletRequest request, SpvCloseInfoVO spvCloseInfoVO, String businessKey);

	public void spvCloseManagerAuditDeal(HttpServletRequest request, SpvCloseInfoVO spvCloseInfoVO, String businessKey);

	public void spvCloseDirectorAuditDeal(HttpServletRequest request, SpvCloseInfoVO spvCloseInfoVO, String businessKey);

}
