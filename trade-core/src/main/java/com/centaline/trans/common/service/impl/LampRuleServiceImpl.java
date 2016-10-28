package com.centaline.trans.common.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.centaline.trans.common.entity.LampRule;
import com.centaline.trans.common.repository.LampRuleMapper;
import com.centaline.trans.common.service.LampRuleService;

@Service
public class LampRuleServiceImpl implements LampRuleService {
	@Autowired
	private LampRuleMapper lampRuleMapper;
	
	@Override
	public int addLampRule(LampRule lampRule) {
		return lampRuleMapper.insertSelective(lampRule);
	}

	@Override
	public int deleteLampRuleByProperty(LampRule lampRule) {
		return lampRuleMapper.deleteLampRuleByProperty(lampRule);
	}

}
