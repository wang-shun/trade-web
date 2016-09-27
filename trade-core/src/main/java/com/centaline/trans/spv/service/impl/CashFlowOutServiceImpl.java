package com.centaline.trans.spv.service.impl;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aist.common.web.validate.AjaxResponse;
import com.aist.uam.auth.remote.UamSessionService;
import com.aist.uam.permission.remote.UamPermissionService;
import com.aist.uam.userorg.remote.UamUserOrgService;
import com.centaline.trans.common.service.ToAccesoryListService;
import com.centaline.trans.spv.service.CashFlowOutService;
import com.centaline.trans.spv.service.ToSpvService;
import com.centaline.trans.spv.vo.SpvChargeInfoVO;

@Service
public class CashFlowOutServiceImpl implements CashFlowOutService {
	@Autowired
	private ToSpvService toSpvService;	
	@Autowired
	private ToAccesoryListService toAccesoryListService;	
	@Autowired
	private UamPermissionService uamPermissionService;	
	@Autowired
	private UamSessionService uamSessionService;
	
	@Autowired
	private UamUserOrgService uamUserOrgService;

	@Override
	public String cashFlowOutPage(HttpServletRequest request, String source, String instCode, String taskId,
			String handle, String businessKey) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AjaxResponse<?> cashFlowOutPageDeal(HttpServletRequest request, String instCode, String taskId,
			String handle, SpvChargeInfoVO spvChargeInfoVO, String businessKey) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String cashFlowOutApplyProcess(HttpServletRequest request, String source, String instCode, String taskId,
			String handle, String businessKey) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AjaxResponse<?> cashFlowOutApplyDeal(HttpServletRequest request, String instCode, String taskId,
			String handle, SpvChargeInfoVO spvChargeInfoVO, String businessKey) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String cashFlowOutDirectorAduitProcess(HttpServletRequest request, String source, String instCode,
			String taskId, String handle, String businessKey) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AjaxResponse<?> cashFlowOutDirectorAduitDeal(HttpServletRequest request, String instCode, String taskId,
			String handle, SpvChargeInfoVO spvChargeInfoVO, String businessKey, Boolean chargeOutAppr)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String cashFlowOutFinanceAduitProcess(HttpServletRequest request, String source, String instCode,
			String taskId, String handle, String businessKey) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AjaxResponse<?> cashFlowOutFinanceAduitDeal(HttpServletRequest request, String instCode, String taskId,
			String handle, SpvChargeInfoVO spvChargeInfoVO, String businessKey, Boolean chargeOutAppr)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String cashFlowOutFinanceSecondAduitProcess(HttpServletRequest request, String source, String instCode,
			String taskId, String handle, String businessKey) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AjaxResponse<?> cashFlowOutFinanceSecondAduitDeal(HttpServletRequest request, String instCode, String taskId,
			String handle, SpvChargeInfoVO spvChargeInfoVO, String businessKey, Boolean chargeOutAppr)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
