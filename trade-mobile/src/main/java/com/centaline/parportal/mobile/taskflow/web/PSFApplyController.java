package com.centaline.parportal.mobile.taskflow.web;

import com.alibaba.fastjson.JSONObject;
import com.centaline.trans.mortgage.service.ToMortgageService;
import com.centaline.trans.transplan.entity.ToTransPlan;
import com.centaline.trans.transplan.service.TransplanServiceFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;


/**
 * Created by caoyuan7 on 2017/4/20.
 */
@Controller
@RequestMapping("task/PSFApply")
public class PSFApplyController {
    @Autowired
    private TransplanServiceFacade transplanServiceFacade;
    @Autowired
    private ToMortgageService toMortgageService;


    @RequestMapping("process")
    @ResponseBody
    public Object toLoanLostApproveManagerProcess(HttpServletRequest request,String caseCode, String source, String taskitem, String processInstanceId) {
        String taskId = request.getParameter("taskId");
        JSONObject jsonObject = new JSONObject();
        ToTransPlan toTransPlan = new ToTransPlan();
        toTransPlan.setPartCode(taskitem);
        toTransPlan.setCaseCode(caseCode);

        jsonObject.put("taskId", taskId);
        jsonObject.put("processInstanceId", processInstanceId);
        jsonObject.put("source", source);
        jsonObject.put("partCode", "PSFApply");

        jsonObject.put("toTransPlan", transplanServiceFacade.findTransPlan(toTransPlan));
        jsonObject.put("apply", toMortgageService.findToMortgageByMortTypeAndCaseCode(caseCode, "30016003"));// --

        return jsonObject;

    }
}
