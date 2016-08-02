package com.centaline.trans.engine.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.centaline.trans.engine.WorkFlowConstant;
import com.centaline.trans.engine.bean.ExecuteAction;
import com.centaline.trans.engine.core.WorkFlowEngine;
import com.centaline.trans.engine.service.ExecuteService;
import com.centaline.trans.engine.utils.WorkFlowUtils;
import com.centaline.trans.engine.vo.ExecutionVo;

/**
 * 流程Execute处理类
 * 
 * @author jimmy
 *
 */
@Service
public class ExecuteServiceImpl implements ExecuteService {
	@Autowired
	private WorkFlowEngine engine;

	@Override
	public ExecutionVo signal(String executionId, String signalName) {
		return signal(executionId, signalName, null);
	}

	@Override
	public ExecutionVo signal(String executionId, String signalName, Map<String, Object> vars) {
		return executeAction(executionId, "signal", signalName, null, vars);
	}

	@Override
	public ExecutionVo signalEventReceived(String executionId, String signalName) {
		return signalEventReceived(executionId, signalName, null);
	}

	@Override
	public ExecutionVo signalEventReceived(String executionId, String signalName, Map<String, Object> vars) {

		return executeAction(executionId, "signalEventReceived", signalName, null, vars);
	}

	@Override
	public ExecutionVo messageEventReceived(String executionId, String messageName) {
		return messageEventReceived(executionId, messageName, null);
	}

	@Override
	public ExecutionVo messageEventReceived(String executionId, String messageName, Map<String, Object> vars) {
		return executeAction(executionId, "messageEventReceived", null, messageName, vars);
	}

	private ExecutionVo executeAction(String executionId, String action, String signalName, String messageName,
			Map<String, Object> vars) {
		ExecuteAction executeAction = new ExecuteAction();
		executeAction.setAction(action);
		executeAction.setExecutionId(executionId);
		executeAction.setMessageName(messageName);
		executeAction.setSignalName(signalName);
		executeAction.setVariables(WorkFlowUtils.mapToVarList(vars));
		return (ExecutionVo) engine.RESTfulWorkFlow(WorkFlowConstant.PUT_EXECUTE_KEY, ExecutionVo.class, executeAction);
	}

}
