package com.centaline.trans.common.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.centaline.trans.common.service.PropertiesGetService;

@Service
public class PropertiesGetServiceImpl implements PropertiesGetService {

	@Value("${img.sh.centanet.url}")
    private String agentImgUrl;


	@Override
	public String getAgentImgUrl() {
		return agentImgUrl;
	}

}
