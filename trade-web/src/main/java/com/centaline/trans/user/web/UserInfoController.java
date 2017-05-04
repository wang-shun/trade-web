package com.centaline.trans.user.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aist.uam.userorg.remote.UamUserOrgService;
import com.aist.uam.userorg.remote.vo.UserOrgJob;

@Controller
@RequestMapping(value = "/user")
public class UserInfoController {
	@Autowired(required = true)
	private UamUserOrgService uamUserOrgService;/* 用户组织信息 */

	@RequestMapping(value = "userInfo")
	public String qureyUserInfo(HttpServletRequest request) {
		return "user/userInfoShow";
	}
	
	@RequestMapping(value="getUserInfo")
	@ResponseBody
	public UserOrgJob qureyUserInfo(HttpServletRequest request,String userId) {
		List<UserOrgJob> userOrgList = uamUserOrgService.getUserOrgJobByUserId(userId);
		if(!CollectionUtils.isEmpty(userOrgList)) {
			return userOrgList.get(0);
		} else {
			return null;
		}
	}
}
