package com.centaline.parportal.mobile.login.service;

import com.aist.uam.auth.remote.vo.SessionUser;
import com.centaline.parportal.mobile.login.vo.MobileUserVo;

public interface MobileUserService {
    public MobileUserVo getUserByUserId(String userId);

    public MobileUserVo getUserByUsername(String userName);

    public SessionUser getUserInfoByUsername(String userName);

    /**
     * 根据登录用户获取SessionUser
     * @param userName
     * @param orgId
     * @param jobCode
     * @return
     */
    public SessionUser getUserByUsernameAndOrgJob(String userName, String orgId, String jobCode);

}
