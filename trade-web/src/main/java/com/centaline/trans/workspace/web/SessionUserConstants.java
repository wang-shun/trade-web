/**
 * Shanghai Centalinehink.com Inc.
 * Copyright (c) 2015-2015 All Rights Reserved.
 */
package com.centaline.trans.workspace.web;

import com.aist.common.utils.SpringUtils;
import com.aist.uam.auth.remote.UamSessionService;
import com.aist.uam.auth.remote.vo.SessionUser;

/**
 * 
 * @author jjm
 * @version $Id: SesstionUserConstants.java, v 0.1 2015年7月2日 上午11:45:10 jjm Exp $
 */
public class SessionUserConstants {
    public static SessionUser getSesstionUser() {
        SessionUser sessionUser = SpringUtils.getBean(UamSessionService.class).getSessionUser();
        return sessionUser;
    }
}
