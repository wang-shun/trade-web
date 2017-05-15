package com.centaline.trans.cases.service;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by caoyuan7 on 2017/5/10.
 */
public interface CaseHandlerService {

    void BelongAndTransferList(HttpServletRequest request);

    void handlerDetailList(HttpServletRequest request, String userId, String detailCode);
}
