package com.centaline.trans.user.web;

import java.util.List;

import com.aist.common.utils.SpringUtils;
import com.aist.uam.auth.remote.UamSessionService;
import com.aist.uam.auth.remote.vo.SessionUser;
import com.aist.uam.permission.remote.UamPermissionService;
import com.aist.uam.permission.remote.vo.Menu;

public class MenuConstants {
	/**
	 * 获取菜单
	 * 
	 * @return
	 */
	public static Menu getMenu() {
		UamSessionService uamSessionService = SpringUtils.getBean(UamSessionService.class);
		SessionUser sessionUser = uamSessionService.getSessionUser();
		String jobId = sessionUser.getServiceJobId();
		String code = "TradeMenu";
		Menu menu = SpringUtils.getBean(UamPermissionService.class).getMenuByJobAndMenuCode(jobId, code); 
		return menu;
	}
	
	public static List<com.aist.uam.userorg.remote.vo.Org> getOrg(){
		String depType = "YUCUI";
		String parentId = "ff8080814f459a78014f45a73d820006";
		String id = "ff8080814f459a78014f45a73d820006";
		return SpringUtils.getBean(UamPermissionService.class)
				.getOrg(depType,parentId,id);
	}
}
