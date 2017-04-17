package com.centaline.parportal.mobile.taskflow.web;

import com.aist.common.web.validate.AjaxResponse;
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
    @Autowired(required = true)
    private ToCaseService toCaseService;
    @Autowired
    private WorkFlowManager workFlowManager;
    @Autowired
    private TgGuestInfoService tgGuestInfoService;

    @RequestMapping(value = "submitPricing")
    @ResponseBody
    public AjaxResponse submitPricing(ToPricing toPricing, String taskId, String processInstanceId) {
        AjaxResponse<?> response = new AjaxResponse<>();
        try {
            Boolean saveFlag = toPricingService.saveToPricing(toPricing);
            if(saveFlag){
                List<RestVariable> variables = new ArrayList<RestVariable>();
                ToCase toCase = toCaseService.findToCaseByCaseCode(toPricing.getCaseCode());
                workFlowManager.submitTask(variables, taskId, processInstanceId,toCase.getLeadingProcessId(), toPricing.getCaseCode());
                int result = tgGuestInfoService.sendMsgHistory(toPricing.getCaseCode(), toPricing.getPartCode());
                if (result >0) {
                    response.setMessage("核价保存成功");
                }else {
                    response.setMessage("短信发送失败, 请您线下手工再次发送！");
                }
                response.setSuccess(true);
            } else {
                response.setSuccess(false);
                response.setMessage("保存核价出错");
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.error(e.getMessage());
        }
        return response;
    }

}
