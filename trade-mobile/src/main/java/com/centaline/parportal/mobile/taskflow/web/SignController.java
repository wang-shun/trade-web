package com.centaline.parportal.mobile.taskflow.web;

import com.aist.common.web.validate.AjaxResponse;
import com.aist.uam.basedata.remote.UamBasedataService;
import com.aist.uam.basedata.remote.vo.Dict;
import com.aist.uam.permission.remote.UamPermissionService;
import com.aist.uam.permission.remote.vo.App;
import com.alibaba.fastjson.JSONObject;
import com.centaline.trans.attachment.entity.ToAttachment;
import com.centaline.trans.attachment.service.ToAccesoryListService;
import com.centaline.trans.attachment.service.ToAttachmentService;
import com.centaline.trans.cases.entity.Result2;
import com.centaline.trans.common.entity.TgGuestInfo;
import com.centaline.trans.common.enums.AppTypeEnum;
import com.centaline.trans.common.service.TgGuestInfoService;
import com.centaline.trans.task.service.SignService;
import com.centaline.trans.task.vo.TransSignVO;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.List;

/**
 * Created by caoyuan7 on 2017/6/6.
 */
@Controller
@RequestMapping(value = "/task/sign")
public class SignController {

    private Logger logger = Logger.getLogger(SignController.class);

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
    @Autowired
    private ToAttachmentService toAttachmentService;

    @RequestMapping("process")
    @ResponseBody
    public Object querySignTask(HttpServletRequest request,String source, String processInstanceId) {
        JSONObject jsonObject = new JSONObject();
        String caseCode = request.getParameter("caseCode");
        String taskitem = request.getParameter("taskitem");
        String taskId = request.getParameter("taskId");
        jsonObject.put("taskId", taskId);
        jsonObject.put("processInstanceId", processInstanceId);
        ToAttachment toAttachment = new ToAttachment();
        toAttachment.setCaseCode(caseCode);
        toAttachment.setPartCode("TransSign");
        toAttachmentService.updateAvaliableAttachmentByProperty(toAttachment);
        List<ToAttachment> attachments = toAttachmentService.quereyAttachments(toAttachment);
        if (CollectionUtils.isNotEmpty(attachments)) {
            for (ToAttachment attachment : attachments) {
                if (!StringUtils.isEmpty(attachment.getPreFileCode())) {
                    attachment.setPreFileName(toAccesoryListService.findAccesoryNameByCode(attachment.getPreFileCode()));
                }
            }
        }
        TransSignVO transSignVO = signService.qureyGuestInfo(caseCode);
        if(transSignVO!=null){
            if(transSignVO.getConPrice()!=null){
                transSignVO.setConPrice(transSignVO.getConPrice().divide(new BigDecimal(10000)));
            }
            if(transSignVO.getRealPrice()!=null){
                transSignVO.setRealPrice(transSignVO.getRealPrice().divide(new BigDecimal(10000)));
            }
        }
        jsonObject.put("attachments", attachments);
        jsonObject.put("caseCode", caseCode);
        toAccesoryListService.getAccesoryList(request, taskitem);
        jsonObject.put("transSign",transSignVO);
        jsonObject.put("accesoryList", request.getAttribute("accesoryList"));
        App app = uamPermissionService.getAppByAppName(AppTypeEnum.APP_FILESVR.getCode());
        jsonObject.put("imgweb", app.genAbsoluteUrl());
        jsonObject.put("guest", queryGuestInfo(caseCode, null));
        jsonObject.put("partCode", taskitem);

        Dict dict = uamBasedataService.findDictByTypeAndLevel("yu_shanghai_district", "2");
        jsonObject.put("distCode",dict);


        return jsonObject;
    }

    @RequestMapping(value = "submitSign")
    @ResponseBody
    public Object submitSign(TransSignVO transSignVO) {
        AjaxResponse ajaxResponse = new AjaxResponse();
        try {
            signService.submitSign(transSignVO);
            ajaxResponse.setSuccess(true);
        }catch (Exception e){
            ajaxResponse.setSuccess(false);
            logger.error(e.getMessage());
            e.printStackTrace();
        }
        return ajaxResponse;
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
