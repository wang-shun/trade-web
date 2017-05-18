package com.centaline.trans.cases.service;

import com.aist.common.web.validate.AjaxResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by caoyuan7 on 2017/5/10.
 */
public interface CaseHandlerService {

    void BelongAndTransferList(HttpServletRequest request);

    void handlerDetailList(HttpServletRequest request, String userId, String detailCode);

    AjaxResponse changeLeadingPro(String[] caseCode, String userId,String leadingProId,String detailCode);
}
