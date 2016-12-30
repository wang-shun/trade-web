package com.centaline.parportal.mobile.login.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.centaline.parportal.mobile.login.service.PropertiesGetService;

@Service
public class ParPortalPropertiesGetServiceImpl implements PropertiesGetService {
    @Value("${authenticate.usernamepassword.ADLogin}")
    private Boolean useAdLogin;

    @Value("${img.sh.centanet.url}")
    private String  agentImgUrl;

    @Override
    public Boolean isUseADLogin() {
        return useAdLogin;
    }

    @Override
    public String getAgentImgUrl() {
        return agentImgUrl;
    }

}
