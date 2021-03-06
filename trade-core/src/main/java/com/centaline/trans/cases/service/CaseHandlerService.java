package com.centaline.trans.cases.service;

import com.aist.common.web.validate.AjaxResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by caoyuan7 on 2017/5/10.
 */
public interface CaseHandlerService {

    void belongAndTransferList(HttpServletRequest request);

    void handlerDetailList(HttpServletRequest request, String userId, String detailCode);

    AjaxResponse changeLeadingPro(String[] changItem, String userId,String leadingProId,String detailCode);

    void processorChangeList(HttpServletRequest request);
}
