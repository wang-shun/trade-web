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
    @Autowired(required = true)
    private ToCaseService toCaseService;
    @Autowired
    private WorkFlowManager workFlowManager;
    @Autowired
    private TgGuestInfoService tgGuestInfoService;

    @RequestMapping(value = "process")
    public JSONObject toProcess(String taskitem, String processInstanceId) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("taskId", taskitem);
        jsonObject.put("processInstanceId", processInstanceId);
        return jsonObject;
    }

    @RequestMapping(value = "submitToGetPropertyBook",method = RequestMethod.POST)
    @ResponseBody
    public AjaxResponse submitToGetPropertyBook(ToGetPropertyBook toGetPropertyBook, String taskId,String processInstanceId) {
        AjaxResponse<?> response = new AjaxResponse<>();
        try {
            Boolean saveFlag =  toGetPropertyBookService.saveToGetPropertyBook(toGetPropertyBook);
            if(saveFlag){
                List<RestVariable> variables = new ArrayList<RestVariable>();
                ToCase toCase = toCaseService.findToCaseByCaseCode(toGetPropertyBook.getCaseCode());
                workFlowManager.submitTask(variables, taskId, processInstanceId,toCase.getLeadingProcessId(),toGetPropertyBook.getCaseCode());
                toCase.setStatus("30001005");	/* 修改案件状态 */
                toCaseService.updateByCaseCodeSelective(toCase);
                int result = tgGuestInfoService.sendMsgHistory(toGetPropertyBook.getCaseCode(),toGetPropertyBook.getPartCode());
                if (result >0) {
                    response.setMessage("领证保存成功");
                }else {
                    response.setMessage("短信发送失败, 请您线下手工再次发送！");
                }
                response.setSuccess(true);
            } else {
                response.setSuccess(false);
                response.setMessage("保存领证出错");
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.error(e.getMessage());
        }
        return response;
    }
}
