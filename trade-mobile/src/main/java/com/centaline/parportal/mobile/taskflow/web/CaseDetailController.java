package com.centaline.parportal.mobile.taskflow.web;

import com.aist.common.exception.BusinessException;
import com.aist.common.web.validate.AjaxResponse;
import com.aist.uam.auth.remote.UamSessionService;
import com.aist.uam.basedata.remote.UamBasedataService;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.centaline.trans.cases.service.TransplanService;
import com.centaline.trans.common.enums.TransDictEnum;
import com.centaline.trans.transplan.entity.ToTransPlan;
import com.centaline.trans.transplan.service.TransplanServiceFacade;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by caoyuan7 on 2017/4/28.
 */
@Controller
@RequestMapping(value = "/case")
public class CaseDetailController {

    private Logger logger = Logger.getLogger(CaseDetailController.class);

    @Autowired(required = true)
    TransplanServiceFacade transplanServiceFacade;
    @Autowired
    UamBasedataService uamBasedataService;
    @Autowired(required = true)
    UamSessionService uamSessionService;
    @Autowired(required = true)
    TransplanService transplanService;
    /**
     * 交易计划变更 页面初始化
     * @author caoy
     * @param caseCode
     * @param partCode
     * @return
     */
    @RequestMapping(value = "/getTransPlanByCaseCode")
    @ResponseBody
    public Object getTransPlanByCaseCode(String caseCode, String partCode,String partName) {
        JSONObject jsonObject = new JSONObject();
        List<ToTransPlan> plans = transplanServiceFacade.queryPlansByCaseCode(caseCode);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        if (plans != null && plans.size() > 0) {
            for (ToTransPlan plan : plans) {
                if (!StringUtils.isEmpty(plan.getPartCode())) {
                    String partNameString = uamBasedataService.getDictValue(TransDictEnum.TPARTCODE.getCode(),plan.getPartCode());
                    plan.setPartName(partNameString);
                }
                if (plan.getEstPartTime() != null)
                    plan.setEstPartTimeStr(format.format(plan.getEstPartTime()));
            }
        }
        jsonObject.put("currentPartCode",partCode);
        jsonObject.put("partName",partName);
        jsonObject.put("plans",plans);
        return jsonObject;
    }
    /**
     * 变更交易计划
     * @author caoy
     * @return AjaxResponse
     */
    @RequestMapping(value = "/savePlanItems" , method = RequestMethod.POST)
    @ResponseBody
    public Object savePlanItems(@RequestParam(required = true) String planItems) {
        AjaxResponse ajaxResponse = new AjaxResponse();
        JSONArray fileListArray= JSONArray.parseArray(planItems);
        try{
            ajaxResponse = transplanService.saveTransPlans(fileListArray);
        }catch (BusinessException e){
            ajaxResponse.setMessage(e.getMessage());
            ajaxResponse.setSuccess(false);
            logger.error(e.getMessage());
        }
        return ajaxResponse;
    }

}
