package com.centaline.trans.transplan.web;

import java.util.List;

import javax.servlet.ServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.aist.uam.auth.remote.UamSessionService;
import com.aist.uam.auth.remote.vo.SessionUser;
import com.aist.uam.userorg.remote.UamUserOrgService;
import com.centaline.trans.common.enums.ToAttachmentEnum;
import com.centaline.trans.common.service.ToPropertyInfoService;
import com.centaline.trans.task.entity.ToTransPlanOrToPropertyInfo;
import com.centaline.trans.task.service.ToTransPlanService;

/**
 * 待办事项
 * @author aisliahail
 *
 */
@Controller
@RequestMapping(value="/transplan")
public class ToTransPlanContorller {

	@Autowired(required = true)
	UamSessionService uamSessionService;
	@Autowired(required = true)
	UamUserOrgService uamUserOrgService;
	@Autowired(required = true)
	ToTransPlanService toTransPlanService;
	@Autowired(required = true)
	ToPropertyInfoService toPropertyInfoService;
	
	
	/**
	 * 获取 待办事项
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value="getToTransPlan")
	public List<ToTransPlanOrToPropertyInfo> getToTransPlan(Model model, ServletRequest request){
		SessionUser user = uamSessionService.getSessionUser();
		//获取到待办事项
		List<ToTransPlanOrToPropertyInfo> toTransPlanList = toTransPlanService.getToTransPlanByUserId(user.getId());
		for (ToTransPlanOrToPropertyInfo toTransPlan : toTransPlanList) {
			toTransPlan.setPartCode(ToAttachmentEnum.getName(toTransPlan.getPartCode()));
		}
		return toTransPlanList;
	}

}




