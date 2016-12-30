package com.centaline.parportal.mobile.login.service;

import com.aist.uam.auth.remote.vo.SessionUser;
import com.centaline.parportal.mobile.login.vo.MobileUserVo;
import com.centaline.trans.common.vo.TokenVo;

public interface TokenService {
    /**
     * 过期Token
     * 
     * @param vo
     * @return 新的token
     */
    TokenVo expToken(TokenVo vo);

    /**
     * 根据tokenId获得token
     * 
     * @param tokenId
     * @return
     */
    TokenVo getToken(String tokenId);

    /**
     * 生成一个新的token 并返回该token
     * 
     * @param user
     * @return
     */
    TokenVo generateToken(MobileUserVo user);

    /**
     * token过期之后仍使用旧的token进行访问 此时记录token过期访问次数
     * 
     * @param vo
     * @return
     */
    int tokenExpAccessCount(String token);

    //////////////////////////////////////////////////////////////////

    public TokenVo generateToken(SessionUser sessionUser);

    public TokenVo generateToken(SessionUser sessionUser, String preToken);

    /**
     * 安全码校验
     * @param oldToken
     * @param securityCode
     * @param serverSecurityCode
     * @return
     */
    public TokenVo refreshToken(String oldToken, String securityCode,
                                String serverSecurityCode) throws Exception;

    //////////////////////////////////////////////////////////////////////////////////////////

}
