package com.centaline.parportal.mobile.taskflow.web;

import com.aist.common.web.validate.AjaxResponse;
import com.alibaba.fastjson.JSONObject;
import com.centaline.trans.cases.entity.ToCase;
import com.centaline.trans.cases.service.ToCaseService;
import com.centaline.trans.common.service.TgGuestInfoService;
import com.centaline.trans.engine.bean.RestVariable;
import com.centaline.trans.engine.service.WorkFlowManager;
import com.centaline.trans.task.entity.ToGetPropertyBook;
import com.centaline.trans.task.service.ToGetPropertyBookService;
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
@RequestMapping(value = "/task/houseBookGet")
public class ToGetPropertyBookController {

    private Logger logger = Logger.getLogger(ToHouseTransferController.class);

    @Autowired
    private ToGetPropertyBookService toGetPropertyBookService;
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
        jsonObject.put("propertyBook", toGetPropertyBookService.queryToGetPropertyBook(caseCode));
        jsonObject.put("partCode", "HouseBookGet");
        jsonObject.put("caseCode", caseCode);
        jsonObject.put("taskId", taskId);
        jsonObject.put("processInstanceId", processInstanceId);
        return jsonObject;
    }

    @RequestMapping(value = "saveToGetPropertyBook")
    @ResponseBody
    public Object saveToGetPropertyBook(ToGetPropertyBook toGetPropertyBook) {
        AjaxResponse response = new AjaxResponse<>();
        try{
            boolean isSuccess = toGetPropertyBookService.saveToGetPropertyBook(toGetPropertyBook);
            response.setSuccess(isSuccess);
        }catch(Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            response.setSuccess(false);
            response.setMessage("操作失败！");
        }
        return response;
    }

    @RequestMapping(value = "submitToGetPropertyBook",method = RequestMethod.POST)
    @ResponseBody
    public Object submitToGetPropertyBook(ToGetPropertyBook toGetPropertyBook, String taskId,String processInstanceId) {
        AjaxResponse<?> response = new AjaxResponse<>();
        try {
            toGetPropertyBookService.saveToGetPropertyBook(toGetPropertyBook);
            List<RestVariable> variables = new ArrayList<RestVariable>();
            ToCase toCase = toCaseService.findToCaseByCaseCode(toGetPropertyBook.getCaseCode());
            workFlowManager.submitTask(variables, taskId, processInstanceId,toCase.getLeadingProcessId(),toGetPropertyBook.getCaseCode());
            toCase.setStatus("30001005");	/* 修改案件状态 */
            toCaseService.updateByCaseCodeSelective(toCase);
            response.setSuccess(true);

            int result = tgGuestInfoService.sendMsgHistory(toGetPropertyBook.getCaseCode(),toGetPropertyBook.getPartCode());
            if (result >0) {
                response.setMessage("领证保存成功");
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
