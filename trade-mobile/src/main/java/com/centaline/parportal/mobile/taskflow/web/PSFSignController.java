package com.centaline.parportal.mobile.taskflow.web;

import com.aist.common.web.validate.AjaxResponse;
import com.alibaba.fastjson.JSONObject;
import com.centaline.trans.attachment.service.ToAccesoryListService;
import com.centaline.trans.cases.entity.ToCase;
import com.centaline.trans.cases.service.ToCaseService;
import com.centaline.trans.cases.vo.CaseBaseVO;
import com.centaline.trans.common.service.TgGuestInfoService;
import com.centaline.trans.engine.bean.RestVariable;
import com.centaline.trans.engine.service.WorkFlowManager;
import com.centaline.trans.task.service.PSFSignService;
import com.centaline.trans.task.vo.PSFSignVO;
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
 * Created by caoyuan7 on 2017/4/24.
 */
@Controller
@RequestMapping(value="/task/PSFSign")
public class PSFSignController {

    private Logger logger = Logger.getLogger(PSFSignController.class);

    @Autowired
    private PSFSignService psfSignService;
    @Autowired
    private TgGuestInfoService tgGuestInfoService;

    @Autowired(required = true)
    private ToCaseService toCaseService;
    @Autowired
    private WorkFlowManager workFlowManager;
    @Autowired
    private ToAccesoryListService toAccesoryListService;

    @RequestMapping("process")
    @ResponseBody
    public Object toProcess(HttpServletRequest request, String processInstanceId) {
        String taskId = request.getParameter("taskId");
        String caseCode = request.getParameter("caseCode");

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("partCode", "PSFSign");
        jsonObject.put("caseCode", caseCode);
        jsonObject.put("taskId", taskId);
        jsonObject.put("processInstanceId", processInstanceId);
        jsonObject.put("PSFSign",  psfSignService.queryPSFSignNoBlank(caseCode));

        return jsonObject;
    }

    @RequestMapping(value="submitPSFSign")
    @ResponseBody
    public Object submitPSFSign(PSFSignVO psfSignVO) {
        AjaxResponse response = new AjaxResponse();
        try {
            response = psfSignService.saveAndSubmitPSFSign(psfSignVO);
        }catch (Exception e){
            e.printStackTrace();
            logger.error(e.getMessage());
        }
        return response;
    }
}
