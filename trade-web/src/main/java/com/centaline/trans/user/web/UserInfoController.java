package com.centaline.trans.user.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aist.uam.auth.remote.UamSessionService;
import com.aist.uam.auth.remote.vo.SessionUser;
import com.aist.uam.userorg.remote.UamUserOrgService;
import com.aist.uam.userorg.remote.vo.UserOrgJob;
import com.centaline.trans.utils.URLAvailability;

@Controller
@RequestMapping(value = "/user")
public class UserInfoController {

	@Autowired(required = true)
	private UamSessionService uamSessionService;/* 用户信息 */
	@Autowired(required = true)
	private UamUserOrgService uamUserOrgService;/* 用户组织信息 */

	@RequestMapping(value = "userInfo")
	public String qureyUserInfo(HttpServletRequest request) {
		/* 员工基本信息 */
		SessionUser sessionUser = uamSessionService.getSessionUser();
		request.setAttribute("sessionUser", sessionUser);
		String url = "http://img.sh.centanet.com/shanghai/staticfile/agent/agentphoto/"+sessionUser.getEmployeeCode()+".jpg";
		URLAvailability urlAvailability = new URLAvailability();
		if(urlAvailability.isConnect(url) != null) {
			request.setAttribute("imgUrl", url);
		} 
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
