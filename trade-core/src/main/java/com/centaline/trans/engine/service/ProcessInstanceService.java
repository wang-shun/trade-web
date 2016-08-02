package com.centaline.trans.engine.service;

import java.util.Map;

import com.centaline.trans.engine.vo.StartProcessInstanceVo;

public interface ProcessInstanceService {

	StartProcessInstanceVo startWorkFlowByDfId(String processDefinitionId, String businessKey);

	StartProcessInstanceVo startWorkFlowByDfKey(String processDefinitionKey, String businessKey);

	StartProcessInstanceVo startWorkFlowByMessage(String message, String businessKey);

	StartProcessInstanceVo startWorkFlowByDfId(String processDefinitionId, String businessKey,
			Map<String, Object> vars);

	StartProcessInstanceVo startWorkFlowByDfKey(String processDefinitionKey, String businessKey,
			Map<String, Object> vars);

	StartProcessInstanceVo startWorkFlowByMessage(String message, String businessKey, Map<String, Object> vars);

	void activateOrSuspendProcessInstance(String processInstanceId, boolean activate);

	void deleteProcess(String processInstanceId);

	StartProcessInstanceVo getHistoryInstances(String processInstanceId);

}
