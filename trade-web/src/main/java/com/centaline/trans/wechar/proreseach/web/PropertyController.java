package com.centaline.trans.wechar.proreseach.web;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aist.common.web.validate.AjaxResponse;
import com.aist.uam.auth.remote.UamSessionService;
import com.aist.uam.auth.remote.vo.SessionUser;
import com.aist.uam.permission.remote.UamPermissionService;
import com.aist.uam.permission.remote.vo.App;
import com.aist.uam.userorg.remote.vo.User;
import com.centaline.trans.common.entity.ToAttachment;
import com.centaline.trans.common.entity.ToPropertyInfo;
import com.centaline.trans.common.service.ToAttachmentService;
import com.centaline.trans.common.service.ToPropertyInfoService;
import com.centaline.trans.income.service.ProfitService;
import com.centaline.trans.property.service.ToPropertyResearchService;
import com.centaline.trans.task.entity.ToPropertyResearch;
import com.centaline.trans.task.entity.ToPropertyResearchVo;
import com.centaline.trans.utils.wechat.GetExistAccessToken;
import com.centaline.trans.utils.wechat.OAuth2Util;
import com.centaline.trans.utils.wechat.ParamesAPI;

@Controller
@RequestMapping("/mobile/property/box")
public class PropertyController {
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

	@RequestMapping("toApply")
	public String toApply(HttpServletRequest request, HttpServletResponse response, String code, String state)
			throws ServletException, IOException {
		//System.out.println("req_uri:" + request.getRequestURI());
		//System.out.println("ref" + request.getHeader("Referer"));
		
		if("dev".equals(propertyService.getEnvironment())){
			SessionUser u= uamSesstionService.getSessionUser();
			request.setAttribute("username", u.getUsername());
			return "mobile/propresearch/wecharadd";
		}
		
		if (code == null) {
			String url = OAuth2Util.GetCode();
			response.sendRedirect(url);
			return null;
		}
		if (!"authdeny".equals(code)) {
			String access_token = GetExistAccessToken.getInstance().getExistAccessToken();
			// agentid 跳转链接时所在的企业应用ID
			// 管理员须拥有agent的使用权限；agentid必须和跳转链接时所在的企业应用ID相同
			String UserID = OAuth2Util.GetUserID(access_token, code, ParamesAPI.NEW_AGENCE);
			// 设置要传递的参数
			request.setAttribute("username", UserID);
			return "mobile/propresearch/wecharadd";
		} else {
			request.setAttribute("msg", "用户取消授权！");
			return "mobile/propresearch/wecharaddResult";
		}

	}

	@RequestMapping("toResult")
	public String toResult(HttpServletRequest request, HttpServletResponse response, String msg, String districtId) {
		if (StringUtils.isBlank(msg)) {
			msg = "产调信息提交成功！";
		}
		request.setAttribute("userList", propertyService.getZLList(districtId));
		request.setAttribute("msg", msg);
		return "mobile/propresearch/wecharaddResult";
	}

	@RequestMapping("show")
	public String show(String prCode, HttpServletRequest request) {
		App regApp = uamPermissionService.getAppByAppName("shcl-image-web");
		String imgHost = regApp.genAbsoluteUrl();
		List<ToAttachment> at = attachmentService.findToAttachmentByCaseCode(prCode);
		ToPropertyResearch propertyResearch = propertyService.getToPropertyResearchsByPrCode(prCode);
		String caseCode =null;
		if(propertyResearch!=null){
			if(propertyResearch.getCaseCode()!=null){
				caseCode=propertyResearch.getCaseCode();
			}else{
				caseCode=prCode;
			}
		}
		ToPropertyInfo propertyInfo = propertyInfoService.findToPropertyInfoByCaseCode(caseCode);
		request.setAttribute("propertyResearch", propertyResearch);
		request.setAttribute("propertyInfo", propertyInfo);
		request.setAttribute("attachments", at);
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
}
