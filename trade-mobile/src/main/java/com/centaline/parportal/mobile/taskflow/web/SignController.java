package com.centaline.parportal.mobile.taskflow.web;

import com.aist.uam.basedata.remote.UamBasedataService;
import com.aist.uam.basedata.remote.vo.Dict;
import com.aist.uam.permission.remote.UamPermissionService;
import com.aist.uam.permission.remote.vo.App;
import com.alibaba.fastjson.JSONObject;
import com.centaline.trans.attachment.service.ToAccesoryListService;
import com.centaline.trans.cases.entity.Result2;
import com.centaline.trans.common.entity.TgGuestInfo;
import com.centaline.trans.common.enums.AppTypeEnum;
import com.centaline.trans.common.service.TgGuestInfoService;
import com.centaline.trans.task.service.SignService;
import com.centaline.trans.task.vo.TransSignVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
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
    private TgGuestInfoService tgGuestInfoService;
    @Autowired
    private ToAccesoryListService toAccesoryListService;
    @Autowired
    private UamPermissionService uamPermissionService;
    @Autowired
    private UamBasedataService uamBasedataService;/* 字典 */

    @RequestMapping("process")
    @ResponseBody
    public Object querySignTask(HttpServletRequest request,String source) {
        JSONObject jsonObject = new JSONObject();
        String caseCode = request.getParameter("caseCode");
        String taskitem = request.getParameter("taskitem");
        jsonObject.put("source", source);
        jsonObject.put("caseCode", caseCode);
        toAccesoryListService.getAccesoryList(request, taskitem);
        jsonObject.put("transSign", signService.qureyGuestInfo(caseCode));
        jsonObject.put("accesoryList", request.getAttribute("accesoryList"));
        App app = uamPermissionService.getAppByAppName(AppTypeEnum.APP_FILESVR.getCode());
        jsonObject.put("imgweb", app.genAbsoluteUrl());
        jsonObject.put("guest", queryGuestInfo(caseCode, null));

        Dict dict = uamBasedataService.findDictByTypeAndLevel("yu_shanghai_district", "2");
        jsonObject.put("distCode",dict);


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
