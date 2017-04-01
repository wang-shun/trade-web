package com.centaline.trans.engine.service;

import com.centaline.trans.engine.bean.RestVariable;

/**
 * 流程变更Service
 * 
 * @author jimmy
 *
 */
public interface VariableService {
	RestVariable getVar(String processInstanceId, String variableName);
}
