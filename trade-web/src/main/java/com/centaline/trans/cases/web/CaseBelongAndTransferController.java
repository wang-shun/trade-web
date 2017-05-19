package com.centaline.trans.cases.web;

import com.aist.common.exception.BusinessException;
import com.aist.common.web.validate.AjaxResponse;
import com.centaline.trans.cases.service.CaseHandlerService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by caoyuan7 on 2017/5/10.
 */
@Controller
@RequestMapping(value = "/case/handler")
public class CaseBelongAndTransferController {

    private Logger logger = Logger.getLogger(CaseBelongAndTransferController.class);

    @Autowired
    CaseHandlerService caseHandlerService;

    /**
     * 服务归属或待办转移功能,得到处理人员列表
     * @author caoy
     * @param request
     * @return
     */
    @RequestMapping(value = "" , method = RequestMethod.GET)
    public String BelongAndTransferList(HttpServletRequest request){
        caseHandlerService.BelongAndTransferList(request);
        return "case/belongAndTransfer/belongAndTransfer";
    }

    /**
     * 服务归属或待办转移功能,从人员列表进入查看该人员的服务项
     * @author caoy
     * @param request
     * @param userId
     * @param detailCode
     * @return
     */
    @RequestMapping(value = "{pageType}/detail" , method = RequestMethod.GET)
    public String handlerDetailList(HttpServletRequest request,String userId,String detailCode,@PathVariable String pageType){
        String url = null;
        caseHandlerService.handlerDetailList(request, userId, detailCode);
        if("processor".equals(pageType)){
            url="case/belongAndTransfer/processorHandlerDetail";
        }
        if("eloan".equals(pageType)){
            url="case/belongAndTransfer/eloanHandlerDetail";
        }
        if("spv".equals(pageType)){
            url="case/belongAndTransfer/spvHandlerDetail";
        }
        return url;
    }

    /**
     * 对责任人进行更改
     * @author caoy
     * @param changCaseCode
     * @param leadingProId
     * @return
     */
    @RequestMapping(value = "changeLeadingPro" , method = RequestMethod.POST)
    @ResponseBody
    public AjaxResponse changeLeadingPro(String changCaseCode,String userId,String leadingProId,String detailCode){
        AjaxResponse ajaxResponse = new AjaxResponse();
        try {
            String[] caseCode = changCaseCode.split(",");
            ajaxResponse =caseHandlerService.changeLeadingPro(caseCode,userId,leadingProId,detailCode);//detailCode是识别提交修改的类别，有时间改成枚举
        }catch (BusinessException e){
            ajaxResponse.setSuccess(false);
            ajaxResponse.setMessage("案件" + e.getMessage() + "更新出错!");
            logger.error(e.getMessage());
            return ajaxResponse;
        }
        return ajaxResponse;
    }



}
