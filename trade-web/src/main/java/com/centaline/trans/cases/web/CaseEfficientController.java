package com.centaline.trans.cases.web;

import javax.servlet.ServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.aist.uam.auth.remote.UamSessionService;
import com.aist.uam.auth.remote.vo.SessionUser;

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

    @Autowired
    private UamSessionService uamSessionService;

    /**
     * 进入案件时效管理列表
     * 
     * @param model
     * @param request
     * @return 返回案件时效列表信息
     */
    @RequestMapping(value = "list")
    public String list(Model model, ServletRequest request)
    {
        SessionUser currentUser = uamSessionService.getSessionUser();
        String serviceDepId = currentUser.getServiceDepId();

        // 运营部开放所有权限
        if ("YCYYZY".equals(currentUser.getServiceJobCode()) || "YCYYZG".equals(currentUser.getServiceJobCode()))
        {
            serviceDepId = "ff8080814f459a78014f45a73d820006";
        }

        request.setAttribute("serviceDepId", serviceDepId);
        request.setAttribute("currentUser", currentUser);

        return "case/caseEfficientList";
    }

}
