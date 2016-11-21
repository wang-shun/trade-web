package com.centaline.trans.wechar.web;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aist.common.quickQuery.bo.JQGridParam;
import com.aist.common.quickQuery.service.QuickGridService;
import com.aist.common.utils.SpringUtils;
import com.aist.common.web.validate.AjaxResponse;
import com.aist.scheduler.execution.remote.Job;
import com.aist.uam.auth.remote.UamSessionService;
import com.aist.uam.auth.remote.vo.SessionUser;
import com.aist.uam.basedata.remote.UamBasedataService;
import com.aist.uam.permission.remote.UamPermissionService;
import com.aist.uam.permission.remote.vo.App;
import com.aist.uam.userorg.remote.UamUserOrgService;
import com.aist.uam.userorg.remote.vo.Org;
import com.aist.uam.userorg.remote.vo.User;
import com.centaline.trans.common.entity.ToAttachment;
import com.centaline.trans.common.enums.SalesDepTypeEnum;
import com.centaline.trans.common.enums.SalesJobEnum;
import com.centaline.trans.common.service.ToAttachmentService;
import com.centaline.trans.common.service.ToPropertyInfoService;
import com.centaline.trans.income.service.ProfitService;
import com.centaline.trans.property.service.ToPropertyResearchService;
import com.centaline.trans.task.entity.ToPropertyResearch;
import com.centaline.trans.task.entity.ToPropertyResearchVo;

@Controller
@RequestMapping("/weixin/property")
public class WechartPropertyController {
	@Autowired
	private ToPropertyResearchService propertyService;
	@Autowired
	private ToAttachmentService attachmentService;
	@Autowired
	private UamPermissionService uamPermissionService;
	@Autowired
	private ToPropertyInfoService propertyInfoService;
	@Autowired
	private ProfitService profitService;
	@Autowired
	private UamSessionService uamSesstionService;
	@Autowired
	private UamUserOrgService uamUserOrgService;
	@Autowired
	private QuickGridService quickGridService;
	@Autowired
	private UamBasedataService uamBasedataService;
	
	@Autowired(required = true)
	private UamSessionService uamSessionService;

	@RequestMapping("toApply")
	public String toApply(HttpServletRequest request, HttpServletResponse response, String code, String state)
			throws ServletException, IOException {

			SessionUser u= uamSesstionService.getSessionUser();
			request.setAttribute("username", u.getUsername());
			request.setAttribute("depId", u.getServiceDepId());
			
			// 查询战区和区蕫相关信息
			getOrgAndUserInfo(request, u.getServiceDepId());
			
			return "weixin/property/toApply";
	}

	private void getOrgAndUserInfo(HttpServletRequest request, String userServiceDepId) {
		// 查询SessionUser对应的区蕫信息
		Org org = uamUserOrgService.getParentOrgByDepHierarchy(userServiceDepId, SalesDepTypeEnum.BUSIWZ.getCode());
		if(org != null){
			request.setAttribute("orgId", org.getId());
			request.setAttribute("orgName", org.getOrgName());
			User user =  uamUserOrgService.getLeaderUserByOrgIdAndJobCode(org.getId(), SalesJobEnum.JQYDS.getCode());
			if(user != null){
				request.setAttribute("realname", user.getRealName());
			}
		}else{
			// 查询所有的战区信息
			List<Org> orgs = uamUserOrgService.getOrgByDepHierarchy("1D29BB468F504774ACE653B946A393EE", SalesDepTypeEnum.BUSIWZ.getCode());
			if(orgs != null && orgs.size() > 0){
				request.setAttribute("orgs", orgs);
			}
		}
	}

	@RequestMapping("toResult")
	public String toResult(HttpServletRequest request, HttpServletResponse response, String msg, String districtId) {
		if (StringUtils.isBlank(msg)) {
			msg = "产调信息提交成功！";
		}
		request.setAttribute("userList", removeDuplicate(propertyService.getZLList(districtId)));
		request.setAttribute("msg", msg);
		return "mobile/propresearch/wecharaddResult";
	}
	
	private List<User> removeDuplicate(List<User> list) { 
		for ( int i = 0 ; i < list.size() - 1 ; i ++ ) { 
			for ( int j = list.size() - 1 ; j > i; j -- ) { 
				if (list.get(j).getId().equals(list.get(i).getId())) { 
					list.remove(j); 
				} 
			} 
		}
		return list;
	} 

	@RequestMapping("show")
	public String show(String prCode, HttpServletRequest request) {
		App regApp = uamPermissionService.getAppByAppName("shcl-image-web");
		String imgHost = regApp.genAbsoluteUrl();
		List<ToAttachment> at = attachmentService.findToAttachmentByCaseCode(prCode);
		ToPropertyResearch propertyResearch = propertyService.getToPropertyResearchsByPrCode(prCode);

		if(propertyResearch.getIsSuccess() != null && propertyResearch.getIsSuccess().equals(1)){
			request.setAttribute("attachments", at);
		}
		
		// 查询执行人
		if(propertyResearch.getPrExecutor() != null){
			User user = uamUserOrgService.getUserById(propertyResearch.getPrExecutor());
			request.setAttribute("prAppliantName", user.getRealName());
		}
		// 查询区蕫
		request.setAttribute("prCostOrgMgr", propertyResearch.getPrCostOrgMgr());
		
		request.setAttribute("districtName", uamBasedataService.getDictValue("yu_shanghai_district", propertyResearch.getDistCode()));
		request.setAttribute("propertyResearch", propertyResearch);
		request.setAttribute("imgHost", imgHost);
		request.setAttribute("imgHost", imgHost);
		return "mobile/propresearch/attenList";
	}

	@RequestMapping("doApply")
	@ResponseBody
	public AjaxResponse doApply(ToPropertyResearchVo vo, HttpServletRequest request) {
		if (StringUtils.isBlank(vo.getUsername())) {
			AjaxResponse result = new AjaxResponse<>(false);
			result.setMessage("未授权用户");
			return result;
		}
		//记录产调申请人的组织
		ToPropertyResearchVo pro = propertyInfoService.getPropertyDepInfoByuserDepId(vo.getPrApplyDepId());
		//记录产调申请人的组织
		if(pro != null){
			vo.setPrApplyDepId(pro.getPrApplyDepId());
			vo.setPrApplyDepName(pro.getPrApplyDepName());
		}
		propertyService.recordProperty(vo);
		request.setAttribute("result", "scuess");
		AjaxResponse result = new AjaxResponse<>(true);
		result.setContent(vo.getDistrictId());
		return result;
	}

	@RequestMapping("hasMapping")
	@ResponseBody
	public AjaxResponse hasMapping(String district) {
		AjaxResponse result = new AjaxResponse<>(propertyService.hasMapping(district));
		return result;
	}
	
	/**
	 * 获取战区对应的区蕫信息
	 * @param orgId
	 * @return
	 */
	@RequestMapping("getOrgName")
	@ResponseBody
	public AjaxResponse getOrgName(String orgId) {
		User user = uamUserOrgService.getLeaderUserByOrgIdAndJobCode(orgId, SalesJobEnum.JQYDS.getCode());
		AjaxResponse result = null;
		if(user != null){
			result = new AjaxResponse<>(true, user.getRealName());
		}else{
			result = new AjaxResponse<>(false);
		}
		return result;
	}
	
	
	@RequestMapping("myProperty")
	public String myProperty(HttpServletRequest request, HttpServletResponse response, Model model, String code, String state) throws IOException {
		SessionUser user = uamSessionService.getSessionUser();
		model.addAttribute("prAppliantId", user.getId());
		return "weixin/property/myProperty";
	}
	
	@RequestMapping("findMyPropertyList")
	@ResponseBody
	public Page<Map<String, Object>> findMyPropertyList (JQGridParam gp ,String search_prAppliantId,String search_propertyAddr){
		gp.put("prAppliantId", search_prAppliantId);
		gp.put("propertyAddr", search_propertyAddr);
		return quickGridService.findPageForSqlServer(gp);
	} 
	
	@RequestMapping("ProcessingTimeJob")
	public void TimeJob(HttpServletRequest request, HttpServletResponse response){
		Job job = SpringUtils.getBean("ProcessingTimeJob");
    	job.execute(null);
	}
		
		
}
