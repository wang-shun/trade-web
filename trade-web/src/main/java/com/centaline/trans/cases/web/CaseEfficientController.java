package com.centaline.trans.cases.web;

import javax.servlet.ServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 案件时效管理控制层
 * 
 * @author yinjf2
 *
 */
@Controller
@RequestMapping(value = "caseEfficient")
public class CaseEfficientController
{

    @RequestMapping(value = "list")
    public String myCaseList(Model model, ServletRequest request)
    {

        return "case/caseEfficientList";
    }
}
