package com.centaline.trans.engine.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.centaline.trans.engine.WorkFlowConstant;
import com.centaline.trans.engine.bean.RestVariable;
import com.centaline.trans.engine.core.WorkFlowEngine;
import com.centaline.trans.engine.service.VariableService;

@Service
public class VariableServiceImpl implements VariableService {
	@Autowired
	private WorkFlowEngine engine;

	@Override
	public RestVariable getVar(String processInstanceId, String variableName) {
		Map<String, String> vars = new HashMap<>();
		vars.put("processInstanceId", processInstanceId);
		vars.put("variableName", variableName);
		return (RestVariable) engine.RESTfulWorkFlow(WorkFlowConstant.GET_PROCESS_VARIABLES_KEY, RestVariable.class,
				vars, null);
	}
}
