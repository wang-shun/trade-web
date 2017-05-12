package com.centaline.trans.cases.service.impl;

import com.aist.uam.auth.remote.UamSessionService;
import com.aist.uam.auth.remote.vo.SessionUser;
import com.centaline.trans.cases.service.CaseHandlerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by caoyuan7 on 2017/5/10.
 */
@Service
public class CaseHandlerServiceImpl implements CaseHandlerService {

    @Autowired(required = true)
    UamSessionService uamSessionService;

    @Override
    public void BelongAndTransferList(HttpServletRequest request) {
        SessionUser user = uamSessionService.getSessionUser();
        if("Manager".equals(user.getServiceJobCode())){
            request.setAttribute("orgId", user.getServiceDepId());
            request.setAttribute("selectJobCode", "consultant");
        }
    }

    @Override
    public void handlerDetailList(HttpServletRequest request, String userId,String detailCode) {
        SessionUser user = uamSessionService.getSessionUser();
        if("Manager".equals(user.getServiceJobCode())){
            request.setAttribute("userId",userId);
            request.setAttribute("detailCode", detailCode);
        }

    }
}
