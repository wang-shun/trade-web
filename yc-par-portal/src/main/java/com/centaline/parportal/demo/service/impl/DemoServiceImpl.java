package com.centaline.parportal.demo.service.impl;

import org.springframework.stereotype.Service;

import com.centaline.parportal.demo.service.DemoService;

/**
 * Created by linjiarong on 2016/10/12.
 */
@Service
public class DemoServiceImpl implements DemoService {

    @Override
    public String helloworld() {
        return "hello world";
    }

    @Override
    public String sayHi(String realName) {
        return "Hi" + realName;
    }
}
