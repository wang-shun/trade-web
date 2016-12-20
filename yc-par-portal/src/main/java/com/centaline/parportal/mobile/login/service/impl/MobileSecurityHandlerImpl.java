package com.centaline.parportal.mobile.login.service.impl;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.AuthorizationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.centaline.parportal.mobile.login.service.MobileSecurityHandler;
import com.centaline.parportal.mobile.login.service.TokenService;
import com.centaline.trans.common.vo.TokenVo;

@Service
public class MobileSecurityHandlerImpl implements MobileSecurityHandler {
    @Autowired
    private TokenService tokenService;

    @Override
    public boolean doCheckAuthorization(HttpServletRequest request) {

        String token = request.getParameter("token");
        String random = request.getParameter("random"); //时间戳

        if (StringUtils.isBlank(token)) {
            return false;
        }
        TokenVo tokenVo = tokenService.getToken(token);
        if (tokenVo == null) {
            return false;
        }
        if (Integer.valueOf(1).equals(tokenVo.getIsExp())) {

            throw new AuthorizationException("授权已过期，请重新登录");
        }
        return true;
    }
}
