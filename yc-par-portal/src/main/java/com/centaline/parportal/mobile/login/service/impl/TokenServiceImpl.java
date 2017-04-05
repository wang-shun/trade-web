package com.centaline.parportal.mobile.login.service.impl;

import java.util.Date;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.aist.common.exception.BusinessException;
import com.aist.uam.auth.remote.UamCrossAppSessionService;
import com.aist.uam.auth.remote.vo.SessionUser;
import com.aist.uam.userorg.remote.UamUserOrgService;
import com.aist.uam.userorg.remote.vo.User;
import com.centaline.parportal.mobile.login.entity.TSmMobileToken;
import com.centaline.parportal.mobile.login.repository.TokenRepositoryCustom;
import com.centaline.parportal.mobile.login.service.MobileSecurityHandler;
import com.centaline.parportal.mobile.login.service.MobileUserService;
import com.centaline.parportal.mobile.login.service.TokenService;
import com.centaline.parportal.mobile.login.vo.MobileUserVo;
import com.centaline.trans.common.vo.MobileHolder;
import com.centaline.trans.common.vo.TokenVo;

@Transactional(readOnly = true)
@Service
public class TokenServiceImpl implements TokenService {
    @Resource
    private TokenRepositoryCustom     tokenRepositoryCustom;
    @Resource
    private MobileUserService         mobileUserService;

    @Autowired
    private UamUserOrgService         uamUserOrgService;

    @Autowired
    private UamCrossAppSessionService uamCrossAppSessionService;

    ////////////////////////////////////////////
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    @Override
    public TokenVo generateToken(SessionUser sessionUser) {
        TokenVo tokenVo = generateToken(sessionUser, null);
        return tokenVo;
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public TokenVo generateToken(SessionUser sessionUser, String preToken) {
        TokenVo token = new TokenVo();
        token.setToken(UUID.randomUUID().toString().replaceAll("-", ""));
        token.setIsExp(0);
        token.setExpDate(new Date(new Date().getTime() + (MobileSecurityHandler.expTime)));
        token.setUserId(sessionUser.getId());
        token.setPreToken(preToken);
        token.setSessionUser(sessionUser);
        token.setSessionId(sessionUser.getSsoSessionId());

        uamCrossAppSessionService.putObjectInto(sessionUser.getSsoSessionId(),
            SessionUser.SESSION_USER, sessionUser); //存储SesionUser
        tokenRepositoryCustom.insterToken(token);
        return token;
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    @Override
    public TokenVo expToken(TokenVo vo) {
        vo.setIsExp(1);
        tokenRepositoryCustom.updateToken(vo);
        TokenVo newToken = generateToken(MobileHolder.getMobileUser());
        newToken.setPreToken(vo.getToken());
        newToken.setExpDate(new Date(new Date().getTime() + (MobileSecurityHandler.expTime)));
        return newToken;
    }

    @Override
    public TokenVo getToken(String tokenId) {
        TSmMobileToken token = tokenRepositoryCustom.selectToken(tokenId);

        if (token == null) {
            return null;
        }
        TokenVo vo = new TokenVo();
        vo.setIsExp(token.getIsexp());
        vo.setExpAccess(token.getExpaccess());
        vo.setExpDate(token.getExpdate());
        vo.setToken(token.getToken());
        vo.setSessionId(token.getSessionId());
        SessionUser oldSessionUser = (SessionUser) uamCrossAppSessionService
            .getObjectFrom(token.getSessionId(), SessionUser.SESSION_USER);
        vo.setSessionUser(oldSessionUser);
        return vo;
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    @Override
    public TokenVo generateToken(MobileUserVo user) {
        return generateToken(user, null);
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public TokenVo generateToken(MobileUserVo user, String preToken) {
        TokenVo token = new TokenVo();
        token.setToken(UUID.randomUUID().toString().replaceAll("-", ""));
        token.setIsExp(0);
        token.setExpDate(new Date(new Date().getTime() + (MobileSecurityHandler.expTime)));
        token.setUserId(user.getEmpId());
        token.setPreToken(preToken);
        //token.setMobileUser(user);

        tokenRepositoryCustom.insterToken(token);
        return token;
    }

    private TokenVo ETV(TSmMobileToken token, String uojId) {
        if (token == null)
            return null;
        TokenVo vo = new TokenVo();
        vo.setIsExp(token.getIsexp());
        vo.setExpAccess(token.getExpaccess());
        vo.setExpDate(token.getExpdate());
        //vo.setMobileUser(mobileUserService.getUserByUserId(token.getUserid()));
        vo.setToken(token.getToken());
        return vo;
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    @Override
    public int tokenExpAccessCount(String token) {
        TokenVo vo = new TokenVo();
        TSmMobileToken tokenObj = tokenRepositoryCustom.selectToken(token);
        Integer expAccess = tokenObj.getExpaccess();
        expAccess = expAccess == null ? 0 : expAccess;
        vo.setExpAccess(expAccess + 1);
        return tokenRepositoryCustom.updateToken(vo);
    }

    @Override
    public TokenVo refreshToken(String oldToken, String securityCode,
                                String serverSecurityCode) throws Exception {
        TokenVo oldTokenVo = this.getToken(oldToken);
        TokenVo newTokenVo = null;
        if (StringUtils.equals(securityCode, serverSecurityCode)) {
            User user = uamUserOrgService.getUserById(oldTokenVo.getSessionUser().getId());
            if (user == null || !"1".equals(user.getAvailable())) {
                throw new BusinessException("离职员工");
            }
            newTokenVo = this.updateToken(oldTokenVo);
        } else {
            throw new BusinessException("刷新token失败");
        }
        return newTokenVo;
    }

    //更新token
    private TokenVo updateToken(TokenVo oldTokenVo) {
        tokenRepositoryCustom.updateOldToken(oldTokenVo.getToken());
        TokenVo newToken = generateToken(MobileHolder.getMobileUser());
        newToken.setPreToken(oldTokenVo.getToken());
        newToken.setExpDate(new Date(new Date().getTime() + (MobileSecurityHandler.expTime)));
        return newToken;
    }

}
