package com.centaline.parportal.mobile.taskflow.web;

import com.aist.common.web.validate.AjaxResponse;
import com.alibaba.fastjson.JSONObject;
import com.centaline.trans.cases.entity.ToCase;
import com.centaline.trans.cases.service.ToCaseService;
import com.centaline.trans.common.service.TgGuestInfoService;
import com.centaline.trans.engine.bean.RestVariable;
import com.centaline.trans.engine.service.WorkFlowManager;
import com.centaline.trans.task.entity.ToPurchaseLimitSearch;
import com.centaline.trans.task.service.ToPurchaseLimitSearchService;
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
@RequestMapping(value = "/task/purchaseLimit")
public class ToPurchaseLimitSearchController {

    private Logger logger = Logger.getLogger(ToPurchaseLimitSearchController.class);

    @Autowired
    private ToPurchaseLimitSearchService toPurchaseLimitSearchService;

    @Autowired(required = true)
    private ToCaseService toCaseService;
    @Autowired
    private WorkFlowManager workFlowManager;

    @Autowired
    private TgGuestInfoService tgGuestInfoService;

    @RequestMapping(value = "process")
    @ResponseBody
    public JSONObject toProcess(HttpServletRequest request, String processInstanceId) {
        String taskId = request.getParameter("taskId");
        String caseCode = request.getParameter("caseCode");
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("caseCode", caseCode);
        jsonObject.put("taskId", taskId);
        jsonObject.put("processInstanceId", processInstanceId);
        return jsonObject;
    }

    @RequestMapping(value = "submitPls",method = RequestMethod.POST)
    @ResponseBody
    public AjaxResponse submitPls(ToPurchaseLimitSearch toPurchaseLimitSearch, String taskId,String processInstanceId) {
        AjaxResponse<?> response = new AjaxResponse<>();
        try {
            Boolean saveFlag = toPurchaseLimitSearchService.saveToPurchaseLimitSearch(toPurchaseLimitSearch);

            if(saveFlag){
                List<RestVariable> variables = new ArrayList<RestVariable>();
                ToCase toCase = toCaseService.findToCaseByCaseCode(toPurchaseLimitSearch.getCaseCode());
                workFlowManager.submitTask(variables, taskId, processInstanceId,toCase.getLeadingProcessId(),toPurchaseLimitSearch.getCaseCode());
                int result = tgGuestInfoService.sendMsgHistory(toPurchaseLimitSearch.getCaseCode(),toPurchaseLimitSearch.getPartId());
                if (result >0) {
                    response.setMessage("查限购保存成功");
                }else {
                    response.setMessage("短信发送失败, 请您线下手工再次发送！");
                }
                response.setSuccess(true);
            } else {
                response.setSuccess(false);
                response.setMessage("保存查限购出错");
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.error(e.getMessage());
        }
        return response;
    }

}
