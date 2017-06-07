package com.centaline.parportal.mobile.taskflow.web;

import com.aist.message.core.remote.UamMessageService;
import com.aist.uam.permission.remote.UamPermissionService;
import com.aist.uam.permission.remote.vo.App;
import com.alibaba.fastjson.JSONObject;
import com.centaline.trans.attachment.service.ToAccesoryListService;
import com.centaline.trans.cases.entity.Result2;
import com.centaline.trans.cases.service.ToCaseService;
import com.centaline.trans.cases.vo.CaseBaseVO;
import com.centaline.trans.common.entity.TgGuestInfo;
import com.centaline.trans.common.enums.AppTypeEnum;
import com.centaline.trans.common.service.TgGuestInfoService;
import com.centaline.trans.mortgage.service.ToMortgageService;
import com.centaline.trans.task.service.SignService;
import com.centaline.trans.task.service.ToHouseTransferService;
import com.centaline.trans.task.vo.TransSignVO;
import com.centaline.trans.utils.UiImproveUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by caoyuan7 on 2017/6/6.
 */
@Controller
@RequestMapping(value = "/task/sign")
public class SignController {

    @Autowired
    private SignService signService;

    @Autowired
    private ToMortgageService toMortgageService;

    @Autowired(required = true)
    private ToCaseService toCaseService;

    @Autowired
    private TgGuestInfoService tgGuestInfoService;

    @Qualifier("uamMessageServiceClient")
    @Autowired
    private UamMessageService uamMessageService;
    @Autowired
    private ToAccesoryListService toAccesoryListService;

    @Autowired
    private ToHouseTransferService toHouseTransferService;
    @Autowired
    private UamPermissionService uamPermissionService;

    @RequestMapping("process")
    @ResponseBody
    public Object querySignTask(HttpServletRequest request,String source) {
        JSONObject jsonObject = new JSONObject();
        String caseCode = request.getParameter("caseCode");
        String taskitem = request.getParameter("taskitem");
        jsonObject.put("source", source);
        toAccesoryListService.getAccesoryList(request, taskitem);
        jsonObject.put("transSign", signService.qureyGuestInfo(caseCode));
        jsonObject.put("accesoryList", request.getAttribute("accesoryList"));
        App app = uamPermissionService.getAppByAppName(AppTypeEnum.APP_FILESVR.getCode());
        jsonObject.put("imgweb", app.genAbsoluteUrl());
        jsonObject.put("guest", queryGuestInfo(caseCode, null));

        return jsonObject;
    }

    @RequestMapping(value = "submitSign")
    @ResponseBody
    public Result2 submitSign(TransSignVO transSignVO) {
        return signService.submitSign(transSignVO);
    }

    @RequestMapping(value = "queryGuestInfo")
    @ResponseBody
    public List<TgGuestInfo> queryGuestInfo(String caseCode,String transPosition) {
        TgGuestInfo tgGuestInfo = new TgGuestInfo();
        tgGuestInfo.setCaseCode(caseCode);
        tgGuestInfo.setTransPosition(transPosition);
        List<TgGuestInfo> list = tgGuestInfoService.findTgGuestInfosByCaseCode(tgGuestInfo);
        return list;
    }
}
