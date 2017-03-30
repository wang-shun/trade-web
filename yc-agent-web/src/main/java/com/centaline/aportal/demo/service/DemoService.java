package com.centaline.aportal.demo.service;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.aist.common.quickQuery.bo.JQGridParam;
import com.aist.common.web.validate.AjaxResponse;

/**
 * Created by linjiarong on 2016/10/12.
 */
public interface DemoService {
    public String helloworld();
  
    AjaxResponse<Map> sayHi(String caseCode, JQGridParam gp);
}
