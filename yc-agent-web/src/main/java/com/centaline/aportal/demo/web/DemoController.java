/*
 * Copyright (c) 2016.
 */
package com.centaline.aportal.demo.web;

import com.centaline.aportal.demo.service.DemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by linjiarong on 2016/10/12.
 */
@Controller
@RequestMapping("/demo")
public class DemoController {
    @Autowired
    private DemoService demoService;

    @RequestMapping("/")
    public @ResponseBody String demoTest(){
        return demoService.helloworld();
    }
}
