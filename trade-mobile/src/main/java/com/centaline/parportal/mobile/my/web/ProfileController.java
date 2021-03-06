/**
 * AISThink.com Inc.
 * Copyright (c) 2015-2016 All Rights Reserved.
 */
package com.centaline.parportal.mobile.my.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.aist.common.exception.BusinessException;
import com.aist.common.utils.PasswordHelper;
import com.aist.uam.auth.remote.UamSessionService;
import com.aist.uam.userorg.remote.UamUserOrgService;
import com.aist.uam.userorg.remote.vo.User;
import com.alibaba.fastjson.JSONObject;
import com.centaline.trans.common.vo.MobileHolder;

/**
 * 
 * @author sstonehu
 * @version $Id: ProfileController.java, v 0.1 2016年12月20日 上午7:32:02 sstonehu Exp $
 */
@RestController
@RequestMapping({ "my" })
public class ProfileController {

    @Autowired
    private UamUserOrgService uamUserOrgService;
    
    @Autowired
    private UamSessionService sessionService;

    @RequestMapping(value = "/changePasswd", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject changePasswd(@RequestBody User userVo) {
        User user = uamUserOrgService.getUserById(sessionService.getSessionUser().getId());
        String oldPass = new PasswordHelper().encryptPassword(user.getSalt(),
            userVo.getOldPassword(), user.getUsername());
        if (!oldPass.equals(user.getPassword()))
            throw new BusinessException("旧密码输入错误，请检查您的密码");

        userVo.setId(user.getId());
        userVo.setUsername(user.getUsername());
        uamUserOrgService.changePassword(userVo);
        JSONObject json = new JSONObject();
        json.put("msg", "密码修改成功!");
        return json;
    }
}
