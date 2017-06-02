package com.centaline.parportal.mobile.taskflow.web;

import com.aist.common.exception.BusinessException;
import com.aist.common.web.validate.AjaxResponse;
import com.alibaba.fastjson.JSONObject;
import com.centaline.trans.cases.entity.ToCase;
import com.centaline.trans.cases.service.ToCaseService;
import com.centaline.trans.cases.vo.CaseBaseVO;
import com.centaline.trans.common.service.TgGuestInfoService;
import com.centaline.trans.eloan.entity.ToCloseLoan;
import com.centaline.trans.eloan.service.ToCloseLoanService;
import com.centaline.trans.engine.bean.RestVariable;
import com.centaline.trans.engine.service.WorkFlowManager;
import com.centaline.trans.utils.UiImproveUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by caoyuan7 on 2017/4/25.
 */
@Controller
@RequestMapping(value = "/task/closeLoan")
public class CloseLoanController {
    private Logger logger = Logger.getLogger(CloseLoanController.class);
    @Autowired
    private ToCloseLoanService toCloseLoanService;

    @Autowired
    private TgGuestInfoService tgGuestInfoService;

    @RequestMapping(value = "process")
    @ResponseBody
    public Object toProcess(HttpServletRequest request, String processInstanceId,String taskitem) {
        String taskId = request.getParameter("taskId");
        String caseCode = request.getParameter("caseCode");
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("partCode", taskitem);
        jsonObject.put("caseCode", caseCode);
        jsonObject.put("taskId", taskId);
        jsonObject.put("processInstanceId", processInstanceId);
        jsonObject.put("loanClose", toCloseLoanService.qureyToCloseLoan(caseCode));
        return jsonObject;
    }

    @RequestMapping(value = "submitCloseLoan")
    @ResponseBody
    public Object submitCloseLoan(ToCloseLoan toCloseLoan, String taskId, String processInstanceId, String taskitem) {
        AjaxResponse response = new AjaxResponse();
        try {
            response = toCloseLoanService.saveAndSubmitCloseLoan(toCloseLoan, taskId, processInstanceId);
            int result = tgGuestInfoService.sendMsgHistory(toCloseLoan.getCaseCode(), taskitem);
            if (result > 0) {
                response.setMessage("提交上家贷款还清成功");
            } else {
                response.setMessage("短信发送失败, 请您线下手工再次发送！");
            }
        }catch (Exception e){
            response.setSuccess(true);
            response.setMessage("提交上家贷款还清失败");
            e.printStackTrace();
            logger.error(e.getMessage());
        }
        return response;
    }
}
