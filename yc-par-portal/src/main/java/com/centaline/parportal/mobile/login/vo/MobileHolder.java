package com.centaline.parportal.mobile.login.vo;

import com.aist.uam.auth.remote.vo.SessionUser;

public class MobileHolder {
    /**
     * 存放当前登录用户信息
     */
    private static final ThreadLocal<SessionUser> mobileUserHolder = new ThreadLocal<SessionUser>();
    //private static final ThreadLocal<MobileUserVo> mobileUserHolder = new ThreadLocal<MobileUserVo>();
    /**
     * 存放当前使用token信息
     */
    private static final ThreadLocal<TokenVo>     tokenHolder      = new ThreadLocal<TokenVo>();

    /**
     * 
     * @return当前登录用户
     */
    public static SessionUser getMobileUser() {
        return mobileUserHolder.get();
    }

    public static void setMobileUserHolder(SessionUser sessionUser) {
        mobileUserHolder.set(sessionUser);
    }

    public static TokenVo getToken() {
        return tokenHolder.get();
    }

    public static void setToken(TokenVo TokenVo) {
        tokenHolder.set(TokenVo);
    }

    public static void clearToken() {
        tokenHolder.remove();
    }

    public static void clearMobileUserHolder() {
        mobileUserHolder.remove();
    }

}
