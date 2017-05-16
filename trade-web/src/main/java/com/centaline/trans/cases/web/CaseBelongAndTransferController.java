package com.centaline.trans.cases.web;

import com.centaline.trans.cases.service.CaseHandlerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by caoyuan7 on 2017/5/10.
 */
@Controller
@RequestMapping(value = "/case/handler")
public class CaseBelongAndTransferController {

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
        return "case/belongAndTransfer";
    }

    @RequestMapping(value = "detail" , method = RequestMethod.GET)
    public String handlerDetailList(HttpServletRequest request,String userId,String detailCode){

        caseHandlerService.handlerDetailList(request,userId,detailCode);
        return "case/handlerDetail";
    }
}
