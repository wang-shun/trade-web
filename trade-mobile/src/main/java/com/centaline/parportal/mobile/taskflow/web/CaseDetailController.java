package com.centaline.parportal.mobile.taskflow.web;

import com.aist.uam.basedata.remote.UamBasedataService;
import com.alibaba.fastjson.JSONObject;
import com.centaline.trans.common.enums.TransDictEnum;
import com.centaline.trans.transplan.entity.ToTransPlan;
import com.centaline.trans.transplan.service.TransplanServiceFacade;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by caoyuan7 on 2017/4/28.
 */
@Controller
@RequestMapping(value = "/case")
public class CaseDetailController {

    @Autowired(required = true)
    TransplanServiceFacade transplanServiceFacade;
    @Autowired
    UamBasedataService uamBasedataService;
    /**
     * 交易计划变更 页面初始化
     * @author caoy
     * @param caseCode
     * @param partCode
     * @return
     */
    @RequestMapping(value = "getTransPlanByCaseCode")
    @ResponseBody
    public Object getTransPlanByCaseCode(String caseCode, String partCode) {
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
        jsonObject.put("plans",plans);
        return jsonObject;
    }


}
