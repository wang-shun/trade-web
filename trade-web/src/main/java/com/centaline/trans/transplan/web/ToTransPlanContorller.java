package com.centaline.trans.transplan.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.aist.common.quickQuery.bo.JQGridParam;
import com.aist.common.quickQuery.service.QuickGridService;
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
	//快速查询接口
	@Resource(name = "quickGridService")
	private QuickGridService quickGridService;
	
	
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
	
	//快速查询获取到待办事项
	@RequestMapping(value="qqToGetTransPlan")
	public List<ToTransPlanOrToPropertyInfo> qqToGetTransPlan(Model model, ServletRequest request){
		SessionUser user = uamSessionService.getSessionUser();		
		List<ToTransPlanOrToPropertyInfo> toTransPlanList = new ArrayList<ToTransPlanOrToPropertyInfo>();
		//获取到待办事项
		JQGridParam gp = new JQGridParam();
		gp.setPagination(false);
		gp.put("leadingProcessId", user.getId());
		
		gp.setQueryId("qqToGetTransPlanListQuery");
		Page<Map<String, Object>> GetTransPlanListResult = quickGridService.findPageForSqlServer(gp);	
		System.out.println("GetTransPlanListResult.getContent()================"+GetTransPlanListResult.getContent());
		if(GetTransPlanListResult!=null && GetTransPlanListResult.getContent()!=null && GetTransPlanListResult.getContent().size()>0){	
			System.out.println("GetTransPlanListResult.getContent()================"+GetTransPlanListResult.getContent());
			for(int i=0;i<GetTransPlanListResult.getContent().size();i++){
				toTransPlanList.add((ToTransPlanOrToPropertyInfo) GetTransPlanListResult.getContent());
			}		
		}
		for (ToTransPlanOrToPropertyInfo toTransPlan : toTransPlanList) {
			toTransPlan.setPartCode(ToAttachmentEnum.getName(toTransPlan.getPartCode()));
		}
		return toTransPlanList;
	}

}




