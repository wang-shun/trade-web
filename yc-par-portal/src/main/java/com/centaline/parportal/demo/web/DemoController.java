/*
 * Copyright (c) 2016.
 */
package com.centaline.parportal.demo.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by linjiarong on 2016/10/12.
 */
@Controller
public class DemoController {
    //	@Autowired
    //	private UamSessionService uamsessionService;
    //    @Autowired
    //    private DemoService demoService;

    @RequestMapping("/test")
    public @ResponseBody String demoTest(String code) {
        return String.format("hello world");
        //        return demoService.sayHi(uamsessionService.getSessionUser().getRealName());
    }
}
