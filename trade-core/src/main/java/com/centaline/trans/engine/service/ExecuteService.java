package com.centaline.trans.engine.service;

import java.util.Map;

import com.centaline.trans.engine.vo.ExecutionVo;

public interface ExecuteService {
	ExecutionVo signal(String executionId, String signalName);

	ExecutionVo signal(String executionId, String signalName, Map<String, Object> vars);

	ExecutionVo signalEventReceived(String executionId, String signalName);

	ExecutionVo signalEventReceived(String executionId, String signalName, Map<String, Object> vars);

	ExecutionVo messageEventReceived(String executionId, String messageName);

	ExecutionVo messageEventReceived(String executionId, String messageName, Map<String, Object> vars);
}
