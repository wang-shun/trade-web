package com.centaline.parportal.mobile.taskflow.web;

import com.aist.common.exception.BusinessException;
import com.aist.common.web.validate.AjaxResponse;
import com.alibaba.fastjson.JSONObject;
import com.centaline.trans.attachment.service.ToAccesoryListService;
import com.centaline.trans.cases.service.ToCaseService;
import com.centaline.trans.common.service.TgGuestInfoService;
import com.centaline.trans.engine.bean.RestVariable;
import com.centaline.trans.mortgage.entity.ToMortgage;
import com.centaline.trans.mortgage.service.ToMortgageService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by caoyuan7 on 2017/4/25.
 */
@Controller
@RequestMapping(value="/task/PSFApprove")
public class PSFApproveController {

    private Logger logger = Logger.getLogger(PSFApproveController.class);

    @Autowired
    private ToMortgageService toMortgageService;

    @Autowired(required = true)
    private ToCaseService toCaseService;
    @Autowired
    private ToAccesoryListService toAccesoryListService;
    @Autowired
    private TgGuestInfoService tgGuestInfoService;

    @RequestMapping("process")
    public Object toPSFApproveProcess(HttpServletRequest request,String processInstanceId) {

        String taskId = request.getParameter("taskId");
        String caseCode = request.getParameter("caseCode");
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("partCode", "PSFApprove");
        jsonObject.put("caseCode", caseCode);
        jsonObject.put("taskId", taskId);
        jsonObject.put("processInstanceId", processInstanceId);
        jsonObject.put("PSFApprove",toMortgageService.findToMortgageByMortTypeAndCaseCode(caseCode, "30016003"));
        return jsonObject;
    }

    @RequestMapping(value="submitMortgage")
    @ResponseBody
    public Object submitMortgage(ToMortgage toMortgage, String taskId, String processInstanceId, String partCode) {
        AjaxResponse<?> response = new AjaxResponse<>();
        try {
            List<RestVariable> variables = new ArrayList<RestVariable>();
            toMortgageService.submitMortgage(toMortgage, variables, taskId, processInstanceId);
            int result=tgGuestInfoService.sendMsgHistory(toMortgage.getCaseCode(), partCode);
            if (result >0) {
                response.setMessage("公积金申请审批成功");
            }else {
                response.setMessage("短信发送失败, 请您线下手工再次发送！");
            }
            response.setSuccess(true);
        }catch (Exception e){
            response.setSuccess(false);
            response.setMessage("公积金申请审批失败");
            e.printStackTrace();
            logger.error(e.getMessage());
        }
        return response;
    }
}
