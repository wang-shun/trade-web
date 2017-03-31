/*
 * Copyright (c) 2016.
 */
package com.centaline.aportal.demo.web;

import com.aist.common.quickQuery.bo.JQGridParam;
import com.aist.common.web.validate.AjaxResponse;
import com.aist.uam.auth.remote.UamSessionService;
import com.aist.uam.conf.ShiroConfig;
import com.aist.uam.permission.remote.UamPermissionService;
import com.centaline.aportal.demo.service.DemoService;
import com.centaline.trans.workspace.entity.CacheGridParam;

import java.util.Map;

import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.jsoup.helper.StringUtil;
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


    @RequestMapping("/test")
    public @ResponseBody AjaxResponse<Map> demoTest(String code,String curDate,String roomType,String useStatus,String depId){
    	JQGridParam gp = new CacheGridParam();
		if(!StringUtil.isBlank(curDate)){
			gp.put("curDate", curDate);
		}
		if(!StringUtil.isBlank(roomType)){
			gp.put("roomType", roomType);
		}
		if(!StringUtil.isBlank(useStatus)){
			gp.put("useStatus", useStatus);
		}
		if(!StringUtil.isBlank(depId)){
			gp.put("depId", depId);
		}
        return demoService.sayHi(code,gp);
    }
}
