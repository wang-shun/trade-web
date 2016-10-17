package com.centaline.aportal.demo.service.impl;

import com.centaline.aportal.demo.service.DemoService;
import org.springframework.stereotype.Service;

/**
 * Created by linjiarong on 2016/10/12.
 */
@Service
public class DemoServiceImpl implements DemoService{
    @Override
    public String helloworld() {
        return "hello world";
    }
}
