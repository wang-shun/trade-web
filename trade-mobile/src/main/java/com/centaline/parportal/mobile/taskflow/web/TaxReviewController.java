package com.centaline.parportal.mobile.taskflow.web;

import com.aist.common.web.validate.AjaxResponse;
import com.alibaba.fastjson.JSONObject;
import com.centaline.trans.cases.entity.ToCase;
import com.centaline.trans.cases.service.ToCaseService;
import com.centaline.trans.common.service.TgGuestInfoService;
import com.centaline.trans.engine.bean.RestVariable;
import com.centaline.trans.engine.service.WorkFlowManager;
import com.centaline.trans.task.entity.ToTax;
import com.centaline.trans.task.service.ToTaxService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by caoyuan7 on 2017/4/14.
 */
@Controller
@RequestMapping(value="/task/taxReview")
public class TaxReviewController {

    private Logger logger = Logger.getLogger(TaxReviewController.class);

    @Autowired
    private ToTaxService toTaxService;
    @Autowired(required = true)
    private ToCaseService toCaseService;
    @Autowired
    private WorkFlowManager workFlowManager;
    @Autowired
    private TgGuestInfoService tgGuestInfoService;

    @RequestMapping(value = "process")
    @ResponseBody
    public Object toProcess(HttpServletRequest request, String processInstanceId) {
        String taskId = request.getParameter("taskId");
        String caseCode = request.getParameter("caseCode");
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("partCode", "TaxReview");
        jsonObject.put("caseCode", caseCode);
        jsonObject.put("taskId", taskId);
        jsonObject.put("processInstanceId", processInstanceId);
        jsonObject.put("taxReview", toTaxService.findToTaxByCaseCode(caseCode));

        return jsonObject;
    }

    @RequestMapping(value = "submitTaxReview", method = RequestMethod.POST)
    @ResponseBody
    public Object submitTaxReview(ToTax toTax,String taskId, String processInstanceId, String partCode) {
        AjaxResponse<?> response = new AjaxResponse<>();
        try {
            Boolean saveFlag = toTaxService.saveToTax(toTax);
            if(saveFlag){
                List<RestVariable> variables = new ArrayList<RestVariable>();
                ToCase toCase = toCaseService.findToCaseByCaseCode(toTax.getCaseCode());
                workFlowManager.submitTask(variables, taskId, processInstanceId,toCase.getLeadingProcessId(), toTax.getCaseCode());
                int result=tgGuestInfoService.sendMsgHistory(toTax.getCaseCode(), partCode);
                if (result >0) {
                    response.setMessage("审税保存成功");
                }else {
                    response.setMessage("短信发送失败, 请您线下手工再次发送！");
                }
                response.setSuccess(true);
            } else {
                response.setSuccess(false);
                response.setMessage("保存审税出错");
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.error(e.getMessage());
        }
        return response;
    }
}
