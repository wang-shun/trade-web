/**
 * AISThink.com Inc.
 * Copyright (c) 2015-2015 All Rights Reserved.
 */
package com.centaline.trans.workspace.web;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aist.common.web.validate.AjaxResponse;
import com.aist.uam.auth.remote.UamSessionService;
import com.aist.uam.auth.remote.vo.SessionUser;
import com.aist.uam.basedata.remote.UamBasedataService;
import com.aist.uam.userorg.remote.UamUserOrgService;
import com.aist.uam.userorg.remote.vo.Org;
import com.aist.uam.userorg.remote.vo.User;
import com.alibaba.fastjson.JSONArray;
import com.centaline.trans.bizwarn.service.BizWarnInfoService;
import com.centaline.trans.cases.entity.ToCase;
import com.centaline.trans.cases.entity.ToCaseInfoCountVo;
import com.centaline.trans.cases.entity.ToOrgVo;
import com.centaline.trans.cases.service.ToCaseInfoService;
import com.centaline.trans.cases.service.ToCaseService;
import com.centaline.trans.cases.service.ToCloseService;
import com.centaline.trans.common.enums.DepTypeEnum;
import com.centaline.trans.common.enums.LightColorEnum;
import com.centaline.trans.common.enums.TransJobs;
import com.centaline.trans.spv.service.ToSpvService;
import com.centaline.trans.task.service.ToHouseTransferService;
import com.centaline.trans.task.service.TsTransPlanHistoryService;
import com.centaline.trans.task.vo.TransPlanVO;
import com.centaline.trans.team.entity.TsTeamProperty;
import com.centaline.trans.team.service.TsTeamPropertyService;
import com.centaline.trans.utils.CheckMobileUtils;
import com.centaline.trans.utils.DateUtil;
import com.centaline.trans.workspace.entity.LoanStaDetails;
import com.centaline.trans.workspace.entity.WorkLoad;
import com.centaline.trans.workspace.entity.WorkSpace;
import com.centaline.trans.workspace.service.WorkSpaceService;

/**
 * 
 * @author sstonehu
 * @version $Id: WorkSpaceController.java, v 0.1 2015年7月24日 上午9:43:30 sstonehu`
 *          Exp $
 */
@Controller
@SuppressWarnings("all")
@RequestMapping(value = "/workspace")
public class WorkSpaceController {
	@Autowired
	private WorkSpaceService workSpaceService;
	@Autowired(required = true)
	UamSessionService uamSessionService;

	@Autowired
	private UamBasedataService uamBasedataService;

	@Autowired(required = true)
	UamUserOrgService uamUserOrgService;

	@Autowired(required = true)
	private ToCaseInfoService toCaseInfoService;

	@Autowired(required = true)
	private ToCaseService toCaseService;

	@Autowired(required = true)
	private ToSpvService toSpvService;

	@Autowired(required = true)
	private ToHouseTransferService toHouseTransferService;

	@Autowired(required = true)
	private ToCloseService toCloseService;

	@Autowired(required = true)
	private TsTransPlanHistoryService tsTransPlanHistoryService;
	@Autowired
	private TsTeamPropertyService teamPropertyService;
	
	@Autowired
	private BizWarnInfoService bizWarnInfoService;

	/**
	 * 检查访问方式是否为移动端
	 * 
	 * @Title: check
	 * @Date : 2014-7-7 下午03:55:19
	 * @param request
	 * @throws IOException
	 */
	public boolean checkMobile(HttpServletRequest request) throws IOException {
		boolean isFromMobile = false;

		String userAgent = request.getHeader("USER-AGENT").toLowerCase();
		if (null == userAgent) {
			userAgent = "";
		}
		isFromMobile = CheckMobileUtils.check(userAgent);

		return isFromMobile;
	}

/*	@RequestMapping(value = "dashboard")
	public String showWorkSpace(Model model, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		//本月目标达成报表，数据查询开始时间
		String startDate = DateUtil.getFormatDate(DateUtil.plusMonth(new Date(),-5),"yyyy-MM-01");
		model.addAttribute("startDate",startDate);
		
		boolean isMobile = checkMobile(request);
		if (isMobile) {
			return mainPage(model, request);
		}
		SessionUser user = uamSessionService.getSessionUser();
		List<User> uList = new ArrayList<>();
		String userId = user.getId();
		String orgName = "";
		// 判断登录用户
		String userOrgId = user.getServiceDepId();

		List<User> userList = null;
		List<User> userList2 = null;
		ToCaseInfoCountVo toCaseInfoCount = null;
		if (TransJobs.TZJL.getCode().equals(user.getServiceJobCode())) {// 如果是总经理
			List<Org> orgList = uamUserOrgService.getOrgByDepHierarchy(
					userOrgId, DepTypeEnum.TYCQY.getCode());

			if (CollectionUtils.isNotEmpty(orgList)) {
				// 各个区域
				for (Org toOrgVo : orgList) {
					User u = new User();
					u.setId(toOrgVo.getId());
					u.setRealName(toOrgVo.getOrgName());
					uList.add(u);
				}
			}
			//model.addAttribute("isConsult", "GeneralManager");
		} else if (TransJobs.TZJ.getCode().equals(user.getServiceJobCode())) { // 如果是总监
			// 各个组
			List<Org> orgIdList = uamUserOrgService.getOrgByDepHierarchy(
					userOrgId, DepTypeEnum.TYCTEAM.getCode());
			for (Org toOrgVo : orgIdList) {
				User u = new User();
				u.setId(toOrgVo.getId());
				u.setRealName(toOrgVo.getOrgName());
				uList.add(u);
			}
			//model.addAttribute("isConsult", "director");
		} else if (TransJobs.TSJYZG.getCode().equals(user.getServiceJobCode())|| TransJobs.TJYZG.getCode().equals(user.getServiceJobCode())) {// 如果是交易主管
			userList = uamUserOrgService.getUserByOrgIdAndJobCode(
					user.getServiceDepId(), TransJobs.TJYGW.getCode());
			for (User users : userList) {
				User u = new User();
				u.setId(users.getId());
				u.setRealName(users.getRealName());
				uList.add(u);
			}
			//model.addAttribute("isConsult", "Manager");
		} else{
			//model.addAttribute("isConsult", "1");
		}
		
		if (CollectionUtils.isNotEmpty(uList)) {
			model.addAttribute("uList", uList);
		}

		boolean isJygw = false;
		if (TransJobs.TJYGW.getCode().equals(user.getServiceJobCode())) {
			TransPlanVO transPlanVO = new TransPlanVO();
			transPlanVO.setUserId(user.getId());
			transPlanVO.setUserName(user.getUsername());
			List<TransPlanVO> transPlanVOList = tsTransPlanHistoryService
					.getTransPlanVOList(transPlanVO);
			for (TransPlanVO transPlanVOOld : transPlanVOList) {
				if (!StringUtils.isBlank(transPlanVOOld.getPartCode())) {
					String partCodeStr = uamBasedataService.getDictValue(
							"part_code", transPlanVOOld.getPartCode());
					transPlanVOOld.setPartCodeStr(partCodeStr);
				}
			}

			if (transPlanVOList != null && transPlanVOList.size() > 0
					&& !isCache(request, response)) {
				isJygw = true;
			}
			model.addAttribute("transPlanVOList", transPlanVOList);
		}
		model.addAttribute("isJygw", isJygw);
		Date now = new Date();
		WorkSpace wk= buildWorkSpaceBean(null, null);
		wk.setColor(0);
		int redLight = workSpaceService.countLight(wk);
		wk.setColor(1);
		int yeLight = workSpaceService.countLight(wk);
		
		int bizwarnCaseCount = bizWarnInfoService.getAllBizwarnCount();   //获取所有的状态为生效的商贷预警数
		
		model.addAttribute("bizwarnCaseCount", bizwarnCaseCount);
		model.addAttribute("redLight", redLight);
		model.addAttribute("yeLight", yeLight);
		model.addAttribute("userId", user.getId());
		TsTeamProperty tp = teamPropertyService.findTeamPropertyByTeamCode(user
				.getServiceDepCode());
		boolean isBackTeam = false;
		if (tp != null) {
			isBackTeam = "yu_back".equals(tp.getTeamProperty());
		}

		if (SecurityUtils.getSubject().isPermitted(
				"TRADE.WORKSPACE.WORKLOAD.CONSULTANT.FRONT")
				&& !isBackTeam) {
			Map sta = doSta(null, (now.getMonth() + 1) + "");
			model.addAttribute("sta", sta);
		}

		model.addAttribute("isBackTeam", isBackTeam);
		if (SecurityUtils.getSubject().isPermitted("TRADE.WORKSPACE.RANK")) {
			model.addAttribute("rank", doGetRank(user));
		}

		WorkSpace work = new WorkSpace();
		work.setOrgId(user.getServiceDepId());
		work.setUserId(user.getUsername());
		if (SecurityUtils.getSubject().isPermitted(
				"TRADE.WORKSPACE.WORKLOAD.MANAGE.END")
				&& isBackTeam) {
			model.addAttribute("managerWorkLoad",
					workloadManagerBackoffice(work));
		}
		if (SecurityUtils.getSubject().isPermitted(
				"TRADE.WORKSPACE.WORKLOAD.CONSULTANT.END")
				&& isBackTeam) {
			model.addAttribute("workLoadConsultant",
					workSpaceService.workloadConsultantBackoffice(work));
		}
		return "workspace/dashboard";
	}*/

	@RequestMapping(value = "dashboard")
	public String showWorkSpace2(Model model, HttpServletRequest request, HttpServletResponse response) throws IOException {
		/*判断是否为移动端登录*/
		boolean isMobile = checkMobile(request);
		if (isMobile) {
			return mainPage(model, request);
		}
		
		//本月目标达成报表，数据查询开始时间
		String startDate = DateUtil.getFormatDate(DateUtil.plusMonth(new Date(),-5),"yyyy-MM-01");
		model.addAttribute("startDate",startDate);
		
		//红 黄 商贷流失预警案件数灯
		WorkSpace wk= buildWorkSpaceBean(null, null);
		wk.setColor(0);
		int redLight = workSpaceService.countLight(wk);
		wk.setColor(1);
		int yeLight = workSpaceService.countLight(wk);
		int bizwarnCaseCount = bizWarnInfoService.getAllBizwarnCount();   //获取所有的状态为生效的商贷预警数
		model.addAttribute("bizwarnCaseCount", bizwarnCaseCount);
		model.addAttribute("redLight", redLight);
		model.addAttribute("yeLight", yeLight);
		
		SessionUser user = uamSessionService.getSessionUser();
		model.addAttribute("userId", user.getId());
		
		//判断组织是否为后台的
		TsTeamProperty tp = teamPropertyService.findTeamPropertyByTeamCode(user.getServiceDepCode());
		boolean isBackTeam = false;
		if (tp != null) {
			isBackTeam = "yu_back".equals(tp.getTeamProperty());
		}		
		
		List<User> uList = new ArrayList<User>();
		String userId = user.getId();
		String jobCode = user.getServiceJobCode();
		String userOrgId = user.getServiceDepId();
		Date now = new Date();
		if (TransJobs.TZJL.getCode().equals(jobCode)) {// 总经理
			/*各个贵宾服务部*/
			List<Org> orgList = uamUserOrgService.getOrgByDepHierarchy(userOrgId, DepTypeEnum.TYCQY.getCode());
			if (CollectionUtils.isNotEmpty(orgList)) {
				for (Org toOrgVo : orgList) {
					User u = new User();
					u.setId(toOrgVo.getId());
					u.setRealName(toOrgVo.getOrgName());
					uList.add(u);
				}
			}
			if (CollectionUtils.isNotEmpty(uList)) {
				model.addAttribute("uList", uList);
			}
			
			/*交易顾问工作数据显示,贷款详情,E+贷款*/
/*			Map sta = doSta(null, (now.getMonth() + 1) + "");
			model.addAttribute("sta", sta);*/
			
			/*龙虎榜*/
			model.addAttribute("rank", doGetRank(user));
			
			return "workbench/dashboard_generalManager";
		} else if (TransJobs.TZJ.getCode().equals(jobCode)) { // 总监
			/*各个组织*/
			List<Org> orgIdList = uamUserOrgService.getOrgByDepHierarchy(userOrgId, DepTypeEnum.TYCTEAM.getCode());
			for (Org toOrgVo : orgIdList) {
				User u = new User();
				u.setId(toOrgVo.getId());
				u.setRealName(toOrgVo.getOrgName());
				uList.add(u);
			}
			if (CollectionUtils.isNotEmpty(uList)) {
				model.addAttribute("uList", uList);
			}
			
			/*交易顾问工作数据显示,贷款详情,E+贷款*/
/*			Map sta = doSta(null, (now.getMonth() + 1) + "");
			model.addAttribute("sta", sta);*/
			
			/*龙虎榜*/
			model.addAttribute("rank", doGetRank(user));
			
			return "workbench/dashboard_director";
		} else if (TransJobs.TSJYZG.getCode().equals(jobCode) || TransJobs.TJYZG.getCode().equals(jobCode)) {// 交易主管
			/*龙虎榜*/
			model.addAttribute("rank", doGetRank(user));
			
			/*高级交易主管添加待办事项*/
			if (SecurityUtils.getSubject().isPermitted("TRADE.WORKSPACE.CALENDAR")) {
				model.addAttribute("rank", doGetRank(user));
			}
			
			if (isBackTeam) { //后台(高级)交易主管
				/*工作数据显示*/
				WorkSpace work = new WorkSpace();
				work.setOrgId(user.getServiceDepId());
				work.setUserId(user.getUsername());
				model.addAttribute("managerWorkLoad",workloadManagerBackoffice(work));
				
				return "workbench/dashboard_manager_back";
			} else { //前台(高级)交易主管
				/*各个交易顾问*/
				List<User> userList = uamUserOrgService.getUserByOrgIdAndJobCode(userOrgId, TransJobs.TJYGW.getCode());
				for (User users : userList) {
					User u = new User();
					u.setId(users.getId());
					u.setRealName(users.getRealName());
					uList.add(u);
				}
				if (CollectionUtils.isNotEmpty(uList)) {
					model.addAttribute("uList", uList);
				}
				
				/*交易顾问工作数据显示,贷款详情,E+贷款*/
/*				Map sta = doSta(null, (now.getMonth() + 1) + "");
				model.addAttribute("sta", sta);*/
				
				return "workbench/dashboard_manager_fornt";
			}

		} else if (TransJobs.TJYGW.getCode().equals(jobCode)) { //交易顾问
			/*任务小卫士*/
			boolean isJygw = false;
			TransPlanVO transPlanVO = new TransPlanVO();
			transPlanVO.setUserId(user.getId());
			transPlanVO.setUserName(user.getUsername());
			List<TransPlanVO> transPlanVOList = tsTransPlanHistoryService.getTransPlanVOList(transPlanVO);
			for (TransPlanVO transPlanVOOld : transPlanVOList) {
				if (!StringUtils.isBlank(transPlanVOOld.getPartCode())) {
					String partCodeStr = uamBasedataService.getDictValue("part_code", transPlanVOOld.getPartCode());
					transPlanVOOld.setPartCodeStr(partCodeStr);
				}
			}
			if (transPlanVOList != null && transPlanVOList.size() > 0&& !isCache(request, response)) {
				isJygw = true;
			}
			model.addAttribute("transPlanVOList", transPlanVOList);
			model.addAttribute("isJygw", isJygw);
			
			/*龙虎榜*/
			model.addAttribute("rank", doGetRank(user));
			
			/*待办事项*/
			model.addAttribute("rank", doGetRank(user));
			
			if (isBackTeam) { //后台交易顾问
				/*工作数据显示*/
				WorkSpace work = new WorkSpace();
				work.setOrgId(user.getServiceDepId());
				work.setUserId(user.getUsername());
				model.addAttribute("workLoadConsultant", workSpaceService.workloadConsultantBackoffice(work));
				
				return "workbench/dashboard_consultant_back";
			} else { //前台交易顾问
				/*交易顾问工作数据显示,贷款详情,E+贷款*/
/*				Map sta = doSta(null, (now.getMonth() + 1) + "");
				model.addAttribute("sta", sta);*/
				
				return "workbench/dashboard_consultant_fornt";
			}
		} else {
			
			return "workbench/dashboard_salesman";
		}
	}
	
	
	public Map workloadManagerBackoffice(WorkSpace work) {
		List<WorkLoad> list = workSpaceService.workloadManagerBackoffice(work);
		Map<String, List> result = new HashMap<>();
		Map<String, WorkLoad> temp1 = new HashMap<>();// 人员分组
		Map<String, WorkLoad> temp2 = new HashMap<>();// 任务分组
		if (list != null && !list.isEmpty()) {
			for (WorkLoad workLoad : list) {
				WorkLoad wl = temp1.get(workLoad.getUserId());
				WorkLoad wl1 = temp2.get(workLoad.getName());
				if (wl == null) {
					wl = new WorkLoad();
				}
				if (wl1 == null) {
					wl1 = new WorkLoad();
				}
				if (workLoad.gettCount() != null
						&& workLoad.gettCount().intValue() != 0) {
					String tCountStr = workLoad.getName() + ":"
							+ workLoad.gettCount();
					if (wl.gettCountStr() == null) {
						wl.settCountStr(tCountStr);
					} else {
						wl.settCountStr(wl.gettCountStr() + "&nbsp;&nbsp;"
								+ tCountStr);
					}
					if (wl1.gettCount() == null) {
						wl1.settCount(0);
					}
					wl1.settCount(wl1.gettCount() + workLoad.gettCount());
				}
				if (workLoad.getyCount() != null
						&& workLoad.getyCount().intValue() != 0) {
					String yCountStr = workLoad.getName() + ":"
							+ workLoad.getyCount();
					if (wl.getyCountStr() == null) {
						wl.setyCountStr(yCountStr);
					} else {
						wl.setyCountStr(wl.getyCountStr() + "&nbsp;&nbsp;"
								+ yCountStr);
					}
					if (wl1.getyCount() == null) {
						wl1.setyCount(0);
					}
					wl1.setyCount(wl1.getyCount() + workLoad.getyCount());
				}
				wl.setUserName(workLoad.getUserName());
				wl1.setName(workLoad.getName());
				temp1.put(workLoad.getUserId(), wl);
				temp2.put(workLoad.getName(), wl1);
			}
		}
		List<WorkLoad> resultList1 = mapToList(temp1);
		List<WorkLoad> resultList2 = mapToList(temp2);
		result.put("listByUser", resultList1);
		result.put("listByTask", resultList2);
		return result;
	}

	private List<WorkLoad> mapToList(Map<String, WorkLoad> map) {
		List<WorkLoad> listValue = new ArrayList<>();
		Iterator<String> it = map.keySet().iterator();
		while (it.hasNext()) {
			String key = it.next().toString();
			listValue.add(map.get(key));
		}
		return listValue;
	}

	public Map workloadConsultantBackoffice(WorkSpace work) {
		return null;
	}

	/****
	 * 增加cookie
	 * 
	 * @param request
	 * @param response
	 * @return
	 * 
	 * @author xin.hu
	 * @date 2015/12/1
	 */
	private boolean isCache(HttpServletRequest request,
			HttpServletResponse response) {
		boolean isCache = false;
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				String name = cookie.getName();
				if ("isCacheRemain".equals(name)) {
					isCache = true;
					break;
				}
			}
		}
		return isCache;
	}

	@RequestMapping(value = "cacheRemain")
	public AjaxResponse cacheRemain(Model model, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		boolean isCache = isCache(request, response);
		if (!isCache) {
			SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String nowtime = s.format(new java.util.Date());
			Cookie c2 = new Cookie("isCacheRemain", nowtime);
			// 20个小时之后失效
			c2.setMaxAge(72000);
			response.addCookie(c2);
			return AjaxResponse.success("你已经成功设置为不再提醒");
		} else {
			return AjaxResponse.success("你已经设置为不再提醒");
		}
	}

	@RequestMapping(value = "mobile/dashboard")
	public String showWorkSpaceMoblie(Model model, HttpServletRequest request)
			throws IOException {
		SessionUser user = uamSessionService.getSessionUser();
		List<User> uList = new ArrayList<>();
		String userId = user.getId();
		int jds = 0;
		int qys = 0;
		int ghs = 0;
		int jas = 0;

		// 判断登录用户
		String userOrgId = user.getServiceOrgId();
		List<User> userList = null;
		List<User> userList2 = null;
		ToCaseInfoCountVo toCaseInfoCount = null;
		if (TransJobs.TZJL.getCode().equals(user.getServiceJobCode())) {// 如果是总经理
			List<Org> orgList = uamUserOrgService.getOrgByParentId(userOrgId);
			if (CollectionUtils.isNotEmpty(orgList)) {
				for (Org org : orgList) {
					userList = uamUserOrgService.getUserByOrgIdAndJobCode(
							org.getId(), TransJobs.TZJ.getCode());
					for (User users : userList) {
						User u = new User();
						u.setId(users.getId());
						u.setRealName(users.getRealName());
						uList.add(u);
					}
				}
				// 获取全部的统计数据;
				userId = null;
				toCaseInfoCount = getToCaseInfoCountByOrgId(userId);
				jds += toCaseInfoCount.getCountJDS();
				qys += toCaseInfoCount.getCountQYS();
				ghs += toCaseInfoCount.getCountGHS();
				jas += toCaseInfoCount.getCountJAS();

				toCaseInfoCount.setCountJDS(jds);
				toCaseInfoCount.setCountQYS(qys);
				toCaseInfoCount.setCountGHS(ghs);
				toCaseInfoCount.setCountJAS(jas);
			}
		} else if (TransJobs.TZJ.getCode().equals(user.getServiceJobCode())) { // 如果是总监
			List<Org> orgList = uamUserOrgService.getOrgByParentId(userOrgId);
			if (CollectionUtils.isNotEmpty(orgList)) {
				for (Org org : orgList) {
					userList = uamUserOrgService.getUserByOrgIdAndJobCode(
							org.getId(), TransJobs.TJYZG.getCode());
					for (User users : userList) {
						User u = new User();
						u.setId(users.getId());
						u.setRealName(users.getRealName());
						uList.add(u);

					}
					// 获取全部的统计数据
					toCaseInfoCount = getToCaseInfoCountByOrgId(org.getId()
							.toString());
					jds += toCaseInfoCount.getCountJDS();
					qys += toCaseInfoCount.getCountQYS();
					ghs += toCaseInfoCount.getCountGHS();
					jas += toCaseInfoCount.getCountJAS();
				}
			}

			// 交易顾问+交易主管数据
			toCaseInfoCount.setCountJDS(jds);
			toCaseInfoCount.setCountQYS(qys);
			toCaseInfoCount.setCountGHS(ghs);
			toCaseInfoCount.setCountJAS(jas);

		} else if (TransJobs.TSJYZG.getCode().equals(user.getServiceJobCode())||TransJobs.TJYZG.getCode().equals(user.getServiceJobCode())) {// 如果是交易主管
			Org team = this.uamUserOrgService
					.getOrgById(user.getServiceDepId());

			userList = uamUserOrgService.getUserByOrgIdAndJobCode(team.getId(),
					TransJobs.TJYGW.getCode());
			for (User users : userList) {
				User u = new User();
				u.setId(users.getId());
				u.setRealName(users.getRealName());
				uList.add(u);

			}
			// 获取全部的统计数据
			toCaseInfoCount = getToCaseInfoCountByOrgId(team.getId());
			jds += toCaseInfoCount.getCountJDS();
			qys += toCaseInfoCount.getCountQYS();
			ghs += toCaseInfoCount.getCountGHS();
			jas += toCaseInfoCount.getCountJAS();

			toCaseInfoCount.setCountJDS(jds);
			toCaseInfoCount.setCountQYS(qys);
			toCaseInfoCount.setCountGHS(ghs);
			toCaseInfoCount.setCountJAS(jas);

		} else {
			toCaseInfoCount = getToCaseInfoCount(userId);
		}
		if (CollectionUtils.isNotEmpty(uList)) {
			model.addAttribute("uList", uList);
		}
		model.addAttribute("toCaseInfoCountVo", toCaseInfoCount);
		model.addAttribute("userId", user.getId());
		return "mobile/workspace/dashboard";
	}
	
	@RequestMapping(value = "mobile/mainPage")
	public String mainPage(Model model, HttpServletRequest request)
			throws IOException {
		WorkSpace wk= buildWorkSpaceBean(null, null);
		wk.setColor(Integer.parseInt(LightColorEnum.RED.getCode()));
		int redLight = workSpaceService.countLight(wk);
		wk.setColor(Integer.parseInt(LightColorEnum.YELLOW.getCode()));
		int yeLight = workSpaceService.countLight(wk);
		model.addAttribute("redLightCount", redLight);
		model.addAttribute("yeLightCount", yeLight);
		
		return "mobile/workspace/mainPage";
	}

	/**
	 * 龙虎榜排名统计
	 * 
	 * @param user
	 * @return
	 */
	private Map doGetRank(SessionUser user) {
		Map map = new HashMap<>();
		WorkSpace work = new WorkSpace();
		work.setUserId(user.getId());
		String jobCode = user.getServiceJobCode();
		List<String> args = new ArrayList<String>();
		if (TransJobs.TZJL.getCode().equals(jobCode)) { //总经理
			work.setRankType(TransJobs.TZJ.getCode());
			work.setOrgId(null);
			List<Org> orgList = uamUserOrgService.getOrgByDepHierarchy(user.getServiceDepId(), DepTypeEnum.TYCQY.getCode());
			if (CollectionUtils.isNotEmpty(orgList)) {
				for (Org toOrgVo : orgList) {
					User u = new User();
					args.add(toOrgVo.getId());
				}
			}
			work.setOrgs(args);
			
			work.setRankCat("loan_amount");
			map.put("loanAmountRankList", workSpaceService.topRankList(work));
			work.setRankCat("sign_amount");
			map.put("signAmountRankList", workSpaceService.topRankList(work));
			work.setRankCat("actual_amount");
			map.put("actualAmountRankList", workSpaceService.topRankList(work));
			
			map.put("loanAmountRank", null);
			map.put("signAmountRank", null);
			map.put("actualAmountRank", null);
			
		} else if(TransJobs.TZJ.getCode().equals(jobCode)) { //总监
			work.setRankType(jobCode);
			work.setOrgId(null);
			/*List<Org> orgList = uamUserOrgService.getOrgByDepHierarchy(
					uamUserOrgService.getParentOrgByDepHierarchy(user.getServiceDepId(), DepTypeEnum.TYCZB.getCode()).getId(), DepTypeEnum.TYCQY.getCode());
			if (CollectionUtils.isNotEmpty(orgList)) {
				for (Org toOrgVo : orgList) {
					args.add(toOrgVo.getId());
				}
			}*/
			args.add(user.getServiceDepId());
			work.setOrgs(args);			
		
			work.setRankCat("loan_amount");
			map.put("loanAmountRankList", workSpaceService.topRankList(work));
			work.setRankCat("sign_amount");
			map.put("signAmountRankList", workSpaceService.topRankList(work));
			work.setRankCat("actual_amount");
			map.put("actualAmountRankList", workSpaceService.topRankList(work));
			
			work.setRankCat("loan_amount");
			map.put("loanAmountRank", workSpaceService.getRank(work));
			work.setRankCat("sign_amount");
			map.put("signAmountRank", workSpaceService.getRank(work));
			work.setRankCat("actual_amount");
			map.put("actualAmountRank", workSpaceService.getRank(work));
			
		} else if (TransJobs.TSJYZG.getCode().equals(jobCode) || TransJobs.TJYZG.getCode().equals(jobCode)) { //(高级)交易主管
			work.setRankType(TransJobs.TJYZG.getCode());
			work.setOrgId(null);
			/* 主管只看当前组织
			List<Org> orgList = uamUserOrgService.getOrgByDepHierarchy(
					uamUserOrgService.getParentOrgByDepHierarchy(user.getServiceDepId(), DepTypeEnum.TYCQY.getCode()).getId(), DepTypeEnum.TYCTEAM.getCode());
			if (CollectionUtils.isNotEmpty(orgList)) {
				for (Org toOrgVo : orgList) {
					args.add(toOrgVo.getId());
				}
			}*/
			args.add(user.getServiceDepId());
			work.setOrgs(args);			
		
			work.setRankCat("loan_amount");
			map.put("loanAmountRankList", workSpaceService.topRankList(work));
			work.setRankCat("sign_amount");
			map.put("signAmountRankList", workSpaceService.topRankList(work));
			work.setRankCat("actual_amount");
			map.put("actualAmountRankList", workSpaceService.topRankList(work));
			
			work.setRankCat("loan_amount");
			map.put("loanAmountRank", workSpaceService.getRank(work));
			work.setRankCat("sign_amount");
			map.put("signAmountRank", workSpaceService.getRank(work));
			work.setRankCat("actual_amount");
			map.put("actualAmountRank", workSpaceService.getRank(work));
		} else if (TransJobs.TJYGW.getCode().equals(jobCode)) { //交易顾问
			work.setRankType(jobCode);
			work.setOrgs(null);
			work.setOrgId(user.getServiceDepId());
			
			work.setRankCat("loan_amount");
			map.put("loanAmountRankList", workSpaceService.topRankList(work));
			work.setRankCat("sign_amount");
			map.put("signAmountRankList", workSpaceService.topRankList(work));
			work.setRankCat("actual_amount");
			map.put("actualAmountRankList", workSpaceService.topRankList(work));
			
			work.setRankCat("loan_amount");
			map.put("loanAmountRank", workSpaceService.getRank(work));
			work.setRankCat("sign_amount");
			map.put("signAmountRank", workSpaceService.getRank(work));
			work.setRankCat("actual_amount");
			map.put("actualAmountRank", workSpaceService.getRank(work));
		}

		return map;
	}

	/**
	 * 进入报表(组别)
	 * 
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "report/team")
	public String showTeam(Model model, ServletRequest request) {
		// 获取到组织
		SessionUser user = uamSessionService.getSessionUser();
		Map<String, String> teamUserMap = new HashMap<>();

		Org team = this.uamUserOrgService.getOrgById(user.getServiceDepId());
		List<User> userList = uamUserOrgService.getUserByOrgIdAndJobCode(
				team.getId(), TransJobs.TJYGW.getCode());
		for (User teamUser : userList) {
			teamUserMap.put(teamUser.getId(), teamUser.getRealName());
		}
		model.addAttribute("teamUserMap", teamUserMap);

		return "workspace/report/team";
	}

	/**
	 * 进入报表(区域)
	 * 
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "report/district")
	public String showDistrict(Model model, ServletRequest request) {
		// 获取到组织
		String orgName = "";
		String orgId = "";
		Map<String, String> toCaseOrgNameList = new HashMap<>();
		// List<ToCase> orgIdList = toCaseService.getOrgId();
		List<ToOrgVo> orgIdList = toCaseService
				.getOrgIdAllByDep(DepTypeEnum.TYCTEAM.getCode());
		for (ToOrgVo orgVo : orgIdList) {
			Org org = uamUserOrgService.getOrgById(orgVo.getId());
			// Org orgParentList =
			// uamUserOrgService.getOrgById(org.getParentId());
			// List<ToOrgVo> orgParentList =
			// toCaseService.getOrgIdAllByDep(DepTypeEnum.TYCTEAM.getCode());
			toCaseOrgNameList.put(org.getId(), org.getOrgName());
		}
		// List<Org> toCaseOrgList = MenuConstants.getOrg();
		model.addAttribute("toCaseOrgNameList", toCaseOrgNameList);

		return "workspace/report/district";
	}

	/**
	 * 进入报表(总部)
	 * 
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "report/headquarter")
	public String showHeadquarter(Model model, ServletRequest request) {
		// 获取到组织
		String orgName = "";
		String orgId = "";
		Map<String, String> toCaseOrgNameList = new HashMap<>();
		List<ToCase> orgIdList = toCaseService.getOrgId();
		for (ToCase toCase : orgIdList) {
			if (null != toCase) {
				Org org = uamUserOrgService.getOrgById(toCase.getOrgId());
				Org orgParent = uamUserOrgService.getOrgById(org.getParentId());
				if (!orgName.equals(orgParent.getOrgName())) {
					orgName = orgParent.getOrgName();
					orgId = orgParent.getId();
					toCaseOrgNameList.put(orgId, orgName);
				}
			}
		}
		// List<Org> toCaseOrgList = MenuConstants.getOrg();
		model.addAttribute("toCaseOrgNameList", toCaseOrgNameList);

		return "workspace/report/headquarter";
	}

	// 跟进userid查询统计数据
	private ToCaseInfoCountVo getToCaseInfoCount(String userId) {// 查询统计 接单
		// 接单数
		ToCaseInfoCountVo toCaseInfoCount = toCaseInfoService
				.countToCaseInfoById(userId);
		// ,签约
		ToCaseInfoCountVo toSignCount = toSpvService.countToSignById(userId);
		// ,过户
		ToCaseInfoCountVo toHouseTransferCount = toHouseTransferService
				.countToHouseTransferById(userId);
		// ,结案
		ToCaseInfoCountVo toCloseCount = toCloseService
				.countToCloseById(userId);

		toCaseInfoCount.setCountJDS(toCaseInfoCount.getCountJDS()
				+ toCloseCount.getCountJAS());
		toCaseInfoCount.setCountQYS(toSignCount.getCountQYS());
		toCaseInfoCount.setCountGHS(toHouseTransferCount.getCountGHS());
		toCaseInfoCount.setCountJAS(toCloseCount.getCountJAS());

		return toCaseInfoCount;
	}

	// 跟进orgId查询统计数据
	private ToCaseInfoCountVo getToCaseInfoCountByOrgId(String orgId) {
		String startDate = null;
		String endDate = null;
		// 接单数
		ToCaseInfoCountVo toCaseInfoCount = toCaseInfoService
				.countToCaseInfoByOrgId(orgId, startDate, endDate);
		// ,签约
		ToCaseInfoCountVo toSignCount = toSpvService.countToSignByOrgId(orgId,
				startDate, endDate);
		// ,过户
		ToCaseInfoCountVo toHouseTransferCount = toHouseTransferService
				.countToHouseTransferByOrgId(orgId, startDate, endDate);
		// ,结案
		ToCaseInfoCountVo toCloseCount = toCloseService.countToCloseByOrgId(
				orgId, startDate, endDate);

		toCaseInfoCount.setCountJDS(toCaseInfoCount.getCountJDS()
				+ toCloseCount.getCountJAS());
		toCaseInfoCount.setCountQYS(toSignCount.getCountQYS());
		toCaseInfoCount.setCountGHS(toHouseTransferCount.getCountGHS());
		toCaseInfoCount.setCountJAS(toCloseCount.getCountJAS());

		return toCaseInfoCount;
	}

	private ToCaseInfoCountVo getToCaseInfoCountVoByOrgId(String orgId) {
		String createTime = null;
		int countJDS = toCaseInfoService.initCountToCaseInfoByOrgId(orgId,
				createTime);
		int countQYS = toSpvService.initCountToSignByOrgId(orgId, createTime);
		int countGHS = toHouseTransferService.initCountToHouseTransferByOrgId(
				orgId, createTime);
		int countJAS = toCloseService
				.initCountToCloseByOrgId(orgId, createTime);
		ToCaseInfoCountVo toCaseInfoCountVo = new ToCaseInfoCountVo();
		toCaseInfoCountVo.setCountJDS(countJDS + countJAS);
		toCaseInfoCountVo.setCountQYS(countQYS);
		toCaseInfoCountVo.setCountGHS(countGHS);
		toCaseInfoCountVo.setCountJAS(countJAS);

		return toCaseInfoCountVo;
	}

	@RequestMapping(value = "workSpaceSta")
	@ResponseBody
	public Map workSpaceSta(String serachId, String mo) {
		return doSta(serachId, mo);
	}

	private Map doSta(String serachId, String mo) {
		WorkSpace work = buildWorkSpaceBean(serachId, mo);
		Map m = workSpaceService.staLoanAgent(work);
		if (m == null) {
			m = new HashMap<>();
		}
		Double loanAmount = workSpaceService.staLoanAgentLoanAmount(work);
		Double signAmount = workSpaceService.staLoanAgentSignAmount(work);
		Double convRate = workSpaceService.staLoanAgentTransferRate(work);

		NumberFormat formatter = new DecimalFormat("###,##0.00万");
		NumberFormat formatter2 = new DecimalFormat("###,##0.00");
		
		if (loanAmount == null) {
			m.put("loanAmount", "0.00万");
		} else {
			m.put("loanAmount", formatter.format(loanAmount/10000));
		}
		
		if (signAmount == null) {
			m.put("signAmount", "0.00万");
		} else {
			m.put("signAmount", formatter.format(signAmount/10000));
		}
		
		if (m.get("actualAmount") == null) {
			m.put("actualAmount", "0.00万");
		} else {
			m.put("actualAmount", formatter.format(((BigDecimal)m.get("actualAmount")).divide(new BigDecimal(10000))));
		}
		
		if (convRate == null) {
			m.put("convRate", "0.00%");
		} else {
			m.put("convRate", formatter2.format(convRate*100) + "%");
		}
		
		Map m1 = workSpaceService.staEvaFee(work);
		if (m1 != null) {
			m.putAll(m1);
		}
		if (m.get("evalFee") == null) {
			m.put("evalFee", "0.00");
		} else {
			m.put("evalFee", formatter2.format(m.get("evalFee")));
		}
		if (m.get("efConvRate") == null) {
			m.put("efConvRate", "0.00%");
		} else {
			m.put("efConvRate", formatter2.format(m.get("efConvRate")) + "%");
		}
		
		m.put("receiveCount", workSpaceService.staReceiveCount(work));
		m.put("signCount", workSpaceService.staSignCount(work));
		//m.put("transferCount", workSpaceService.staTransferCount(work));
		m.put("loanApplyCount", workSpaceService.staLoanApplyCount(work));
		m.put("closeCount", workSpaceService.staCloseCount(work));

		m.put("staLoanApply",
				JSONArray.toJSON(workSpaceService.staLoanApply(work)));
		m.put("staLoanSign", JSONArray.toJSON(setStaItemStr(workSpaceService
				.staLoanSign(work))));
		m.put("staLoanRelease",
				JSONArray.toJSON(workSpaceService.staLoanRelease(work)));
		return m;
	}

	private List<LoanStaDetails> setStaItemStr(List<LoanStaDetails> list) {
		if (list != null) {
			for (LoanStaDetails loanStaDetails : list) {
				loanStaDetails.setStaItemStr(uamBasedataService
						.findDictByTypeAndCode("yu_serv_cat_code_tree",
								loanStaDetails.getStaItem()).getName());
			}
		}
		return list;
	}

	private WorkSpace buildWorkSpaceBean(String serachId, String mo) {
		WorkSpace w = new WorkSpace();
		SessionUser user = uamSessionService.getSessionUser();
		List<String> orgs = null;
		String org = null;
		String userId = null;
		if (TransJobs.TZJL.getCode().equals(user.getServiceJobCode())) {// 如果是总经理
			if (!StringUtils.isBlank(serachId)) {
				List<Org> orgList = uamUserOrgService
						.getOrgByParentId(serachId);
				orgs = orgListToListStr(orgList);
				orgs.add(user
						.getServiceDepId());
			}
		} else if (TransJobs.TZJ.getCode().equals(user.getServiceJobCode())) { // 如果是总监
			if (!StringUtils.isBlank(serachId)) {
				org = serachId;
			} else {
				List<Org> orgList = uamUserOrgService.getOrgByParentId(user
						.getServiceDepId());
				orgs = orgListToListStr(orgList);
				orgs.add(user
						.getServiceDepId());
			}
		} else if (TransJobs.TSJYZG.getCode().equals(user.getServiceJobCode())||TransJobs.TJYZG.getCode().equals(user.getServiceJobCode())) {// 如果是交易主管
			if (!StringUtils.isBlank(serachId)) {
				userId = serachId;
			} else {
				org = user.getServiceDepId();
			}
		} else {
			userId = user.getId();
		}
		w.setMo(mo);
		w.setOrgId(org);
		w.setOrgs(orgs);
		w.setUserId(userId);
		return w;
	}

	private List<String> orgListToListStr(List<Org> orgs) {
		if (orgs == null || orgs.isEmpty())
			return null;
		List<String> orgStrs = new ArrayList<>();
		for (Org org : orgs) {
			orgStrs.add(org.getId());
		}
		return orgStrs;
	}
	@RequestMapping(value = "ryLightList")
	public String RyLightList(Model model,String color) {
		SessionUser user = uamSessionService.getSessionUser();
		if (TransJobs.TZJL.getCode().equals(user.getServiceJobCode())) {// 如果是总经理
			
		}else if (TransJobs.TZJ.getCode().equals(user.getServiceJobCode())) { // 如果是总监
			model.addAttribute("parentOrgId", user
						.getServiceDepId());
		} else if (TransJobs.TSJYZG.getCode().equals(user.getServiceJobCode())||TransJobs.TJYZG.getCode().equals(user.getServiceJobCode())) {// 如果是交易主管
			model.addAttribute("orgId", user
					.getServiceDepId());
		} else {
			model.addAttribute("userId", user.getId());
		}
		if(TransJobs.TJYGW.getCode().endsWith(user.getServiceJobCode())){
			model.addAttribute("isJygw", true);
		}else{
			model.addAttribute("isJygw", false);
		}
		model.addAttribute("color", color);
		return "workspace/ryLightList";
	}
	@RequestMapping(value = "/mobile/ryLightList")
	public String MobileRyLightList(Model model,String color) {
		SessionUser user = uamSessionService.getSessionUser();
		if (TransJobs.TZJL.getCode().equals(user.getServiceJobCode())) {// 如果是总经理
			
		}else if (TransJobs.TZJ.getCode().equals(user.getServiceJobCode())) { // 如果是总监
			model.addAttribute("parentOrgId", user
						.getServiceDepId());
		} else if (TransJobs.TSJYZG.getCode().equals(user.getServiceJobCode())||TransJobs.TJYZG.getCode().equals(user.getServiceJobCode())) {// 如果是交易主管
			model.addAttribute("orgId", user
					.getServiceDepId());
		} else {
			model.addAttribute("userId", user.getId());
		}
		model.addAttribute("color", color);
		return "/mobile/workspace/ryLightList";
	}
}
