package com.centaline.trans.common.service;

import com.centaline.trans.common.entity.LampRule;

public interface LampRuleService {
	
	int addLampRule(LampRule lampRule);
	
	int deleteLampRuleByProperty(LampRule lampRule);

}