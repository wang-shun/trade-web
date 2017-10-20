package com.centaline.trans.common.service.impl;

import com.aist.common.exception.BusinessException;
import com.aist.uam.auth.remote.UamSessionService;
import com.aist.uam.auth.remote.vo.SessionUser;
import com.aist.uam.basedata.remote.UamBasedataService;
import com.centaline.trans.common.service.PropertyUtilsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class PropertyUtilsServiceImpl implements PropertyUtilsService {

	//参数配置中，流程定义的模块编码
	private static final String PARAM_MODEL = "FLOW.DEFINITION";

	@Autowired
	private UamBasedataService uamBasedataService;

	@Autowired
	private UamSessionService uamSessionService;



	@Override
	public String getProcessDfId(String process) {
		SessionUser user = uamSessionService.getSessionUser();
		if (user == null) throw new BusinessException("未获取到当前登录人服务组织信息!");
		return getProcessDfId(process, user.getServiceDepId());
	}

	@Override
	public String getProcessDfId(String process, String orgId) {
		String processId = uamBasedataService.getParam(PARAM_MODEL, process, orgId);
		// System.out.println(orgId+"------------get processId is -----------"+processId);
		return processId;
	}

	@Override
	public Map<String, Object> getProcessDefVals(String process) {
		//不进行流程默认数据的设置 后面根据需要添加
		return new HashMap<>();
	}

	public String getSpvCashflowInProcess() {
		return getProcessDfId("Spv_CashflowInProcess");
	}

	public String getSPVCashflowOutProcessDfKey() {
		return getProcessDfId("SPVCashflowOutProcess");
	}

	public String getSpvCloseApplyProcessDfKey() {
		return getProcessDfId("SpvCloseApplyProcess");
	}

	public String getSpvProcessDfKey() {
		return getProcessDfId("Spv_Process");
	}

	public String getProcessTmpBankAuditDfKey() {
		return getProcessDfId("tempBankAudit");
	}

	public String getProcessEloanDfKey() {
		return getProcessDfId("Eloan_Process");
	}

	public String getProcessLoanerDfKey() {
		return getProcessDfId("Loaner_Process");
	}

	public String getSatisProcessDfKey() {
		return getProcessDfId("SatisProcess");
	}
	
	public String getEvalServiceRestartProcesDfKey() {
		return getProcessDfId("evalServiceRestartProces");
	}
	public String getEvalServiceStopProcessDfKey() {
		return getProcessDfId("evalServiceStopProcess");
	}


}
