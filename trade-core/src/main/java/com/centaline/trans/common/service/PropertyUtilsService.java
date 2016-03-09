package com.centaline.trans.common.service;

import java.util.Map;

public interface PropertyUtilsService {
	String getProcessDfId(String process);

	Map<String, Object> getProcessDefVals(String process);
}
