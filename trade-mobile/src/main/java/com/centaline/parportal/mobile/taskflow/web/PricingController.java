package com.centaline.parportal.mobile.taskflow.web;

import com.aist.common.web.validate.AjaxResponse;
import com.alibaba.fastjson.JSONObject;
import com.centaline.trans.cases.entity.ToCase;
import com.centaline.trans.cases.service.ToCaseService;
import com.centaline.trans.common.service.TgGuestInfoService;
import com.centaline.trans.engine.bean.RestVariable;
import com.centaline.trans.engine.service.WorkFlowManager;
import com.centaline.trans.task.entity.ToPricing;
import com.centaline.trans.task.service.ToPricingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by caoyuan7 on 2017/4/14.
 */
@Controller
@RequestMapping(value = "/task/pricing")
public class PricingController {

    private Logger logger = Logger.getLogger(PricingController.class);

    @Autowired
    private ToPricingService toPricingService;
    @Autowired
    private TgGuestInfoService tgGuestInfoService;
    @Autowired(required = true)
    private ToCaseService toCaseService;
    @Autowired
    private WorkFlowManager workFlowManager;

    @RequestMapping(value = "process")
    @ResponseBody
    public Object toProcess(HttpServletRequest request, String processInstanceId) {
        String taskId = request.getParameter("taskId");
        String caseCode = request.getParameter("caseCode");
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("partCode", "Pricing");
        jsonObject.put("caseCode", caseCode);
        jsonObject.put("taskId", taskId);
        jsonObject.put("processInstanceId", processInstanceId);
        jsonObject.put("pricing", toPricingService.qureyPricing(caseCode));
        return jsonObject;
    }

    @RequestMapping(value = "savePricing")
    @ResponseBody
    public Object savePricing(ToPricing toPricing) {
        AjaxResponse response = new AjaxResponse<>();
        try{
            toPricingService.saveToPricing(toPricing);
            response.setSuccess(true);
            response.setMessage("操作成功！");
        }catch(Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            response.setSuccess(false);
            response.setMessage("操作失败！");
        }
        return response;
    }

    @RequestMapping(value = "submitPricing")
    @ResponseBody
    public Object submitPricing(ToPricing toPricing, String taskId, String processInstanceId) {
        AjaxResponse response = new AjaxResponse();
        try {
            toPricingService.saveToPricing(toPricing);
            List<RestVariable> variables = new ArrayList<RestVariable>();
            ToCase toCase = toCaseService.findToCaseByCaseCode(toPricing.getCaseCode());
            workFlowManager.submitTask(variables, taskId, processInstanceId, toCase.getLeadingProcessId(), toPricing.getCaseCode());
            response.setSuccess(true);
            int result = tgGuestInfoService.sendMsgHistory(toPricing.getCaseCode(), toPricing.getPartCode());
            if (result >0) {
                response.setMessage("核价保存成功");
            }else {
                response.setMessage("短信发送失败, 请您线下手工再次发送！");
            }
        }catch (Exception e){
            response.setMessage(e.getMessage());
            response.setSuccess(false);
            e.printStackTrace();
            logger.error(e.getMessage());
        }
        return response;
    }

}
