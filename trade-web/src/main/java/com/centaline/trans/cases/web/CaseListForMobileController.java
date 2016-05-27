package com.centaline.trans.cases.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aist.common.quickQuery.bo.JQGridParam;
import com.aist.common.quickQuery.service.QuickGridService;
import com.aist.common.quickQuery.web.QuickQueryController;
import com.aist.common.quickQuery.web.vo.DatagridVO;
import com.aist.uam.userorg.remote.UamUserOrgService;
import com.aist.uam.userorg.remote.vo.User;
import com.centaline.trans.cases.entity.ToCase;
import com.centaline.trans.cases.service.ToCaseService;
import com.centaline.trans.cases.vo.CaseDetailProcessorVO;
import com.centaline.trans.common.entity.TgServItemAndProcessor;
import com.centaline.trans.common.entity.ToPropertyInfo;
import com.centaline.trans.common.service.TgServItemAndProcessorService;
import com.centaline.trans.common.service.ToPropertyInfoService;
import com.centaline.trans.utils.wechat.GetExistAccessToken;
import com.centaline.trans.utils.wechat.OAuth2Util;
import com.centaline.trans.utils.wechat.ParamesAPI;

@Controller
@RequestMapping("/mobile/case/box")
public class CaseListForMobileController {
	@Autowired
	private UamUserOrgService uamUserOrgService;
	@Autowired
	private QuickQueryController quickQuery;
	@Autowired
	private ToCaseService toCaseService;
	@Autowired
	private TgServItemAndProcessorService tgServItemAndProcessorService;
	@Autowired
	private ToPropertyInfoService toPropertyInfoService;

	@RequestMapping(value = "findPage")
	@ResponseBody
	public DatagridVO findPage(JQGridParam gridParam, HttpServletRequest request) {
		Object user = request.getSession().getAttribute("agentUser");
		if (user == null) {
			return null;
		}
		return quickQuery.findPage(gridParam, request);

	}

	@RequestMapping("progressQueryList")
	public String toMyCaseList(HttpServletRequest request, HttpServletResponse response, String code, String state)
			throws IOException {
		

		Object user = request.getSession().getAttribute("agentUser");
		if (user != null) {
			request.setAttribute("userId", ((User) user).getId());
			return "mobile/case/myCaseList";
		}
		if (code == null) {
			String url = OAuth2Util.GetCode(ParamesAPI.REDIRECT_URI_MYCASE_LIST);
			response.sendRedirect(url);
			return null;
		}
		if (!"authdeny".equals(code)) {
			String access_token = GetExistAccessToken.getInstance().getExistAccessToken();
			// agentid 跳转链接时所在的企业应用ID
			// 管理员须拥有agent的使用权限；agentid必须和跳转链接时所在的企业应用ID相同
			String username = OAuth2Util.GetUserID(access_token, code, ParamesAPI.NEW_AGENCE);
			if (StringUtils.isBlank(username)) {
				request.setAttribute("msg", "用户不存在！");
				return "mobile/propresearch/wecharaddResult";
			}
			User u = uamUserOrgService.getUserByUsername(username);
			if (u == null) {
				request.setAttribute("msg", "用户不存在！");
				return "mobile/propresearch/wecharaddResult";
			}
			// 设置要传递的参数
			request.setAttribute("userId", u.getId());
			request.getSession().setAttribute("agentUser", u);
			return "mobile/case/myCaseList";
		} else {
			request.setAttribute("msg", "用户取消授权！");
			return "mobile/propresearch/wecharaddResult";
		}
	}

	@RequestMapping("progressDetailList")
	public String caseDetils(HttpServletRequest request, HttpServletResponse response, String code, String state,
			Long caseId) throws IOException {
		Object user = request.getSession().getAttribute("agentUser");
		if (user == null) {
			return toMyCaseList(request, response, code, state);
		}
		ToCase toCase = toCaseService.selectByPrimaryKey(caseId);
		if(toCase!=null){
			// 合作顾问
			List<CaseDetailProcessorVO> proList = new ArrayList<CaseDetailProcessorVO>();
			TgServItemAndProcessor inProcessor = new TgServItemAndProcessor();
			inProcessor.setCaseCode(toCase.getCaseCode());
			inProcessor.setProcessorId(toCase.getLeadingProcessId());
			List<String> tgproList = tgServItemAndProcessorService.findProcessorsByCaseCode(inProcessor);
			ToPropertyInfo toPropertyInfo = toPropertyInfoService.findToPropertyInfoByCaseCode(toCase.getCaseCode());
			for (String sp : tgproList) {
				if (StringUtils.isEmpty(sp))
					continue;
				CaseDetailProcessorVO proVo = new CaseDetailProcessorVO();
				User processor = uamUserOrgService.getUserById(sp);
				proVo.setProcessorId(processor.getId());
				proVo.setProcessorName(processor.getRealName());
				proVo.setProcessorMobile(processor.getMobile());
				proList.add(proVo);
			}
			User leading=uamUserOrgService.getUserById(toCase.getLeadingProcessId());
			request.setAttribute("leading", leading);
			request.setAttribute("proList", proList);
			request.setAttribute("toCase", toCase);
			request.setAttribute("toPropertyInfo", toPropertyInfo);
		}
		return "mobile/case/detail";
	}
}
