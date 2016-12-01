/*
 * Copyright (c) 2016.
 */
package com.centaline.aportal.demo.web;

import com.aist.uam.auth.remote.UamSessionService;
import com.aist.uam.conf.ShiroConfig;
import com.aist.uam.permission.remote.UamPermissionService;
import com.centaline.aportal.demo.service.DemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by linjiarong on 2016/10/12.
 */
@Controller
public class DemoController {
	@Autowired
	private UamSessionService uamsessionService;
    @Autowired
    private DemoService demoService;
    @Autowired
    private UamPermissionService uamPermissionServiceClient;

    @RequestMapping("/test")
    public @ResponseBody String demoTest(String code){
    	uamPermissionServiceClient.findMenuByCode(code);
    	System.out.println(uamsessionService.getSessionUser());
        return demoService.helloworld();
    }
}
