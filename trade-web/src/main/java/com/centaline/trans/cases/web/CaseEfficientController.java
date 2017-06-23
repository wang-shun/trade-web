package com.centaline.trans.cases.web;

import javax.servlet.ServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aist.uam.auth.remote.UamSessionService;
import com.aist.uam.auth.remote.vo.SessionUser;
import com.centaline.trans.cases.service.TsCaseEfficientService;
import com.centaline.trans.comment.service.ToCaseCommentService;

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

    @Autowired
    private ToCaseCommentService toCaseCommentService;

    @Autowired
    private TsCaseEfficientService tsCaseEfficientService;

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

    @RequestMapping(value = "delay")
    @ResponseBody
    public boolean delay(String caseCode, String partCode, String delayDays, String comment)
    {
        SessionUser currentUser = uamSessionService.getSessionUser();

        return tsCaseEfficientService.delay(currentUser, caseCode, partCode, delayDays, comment);
    }

}
