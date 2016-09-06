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

import javax.annotation.Resource;
import javax.servlet.ServletRequest;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aist.common.quickQuery.bo.JQGridParam;
import com.aist.common.quickQuery.service.QuickGridService;
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
import com.centaline.trans.task.entity.ToTransPlanOrToPropertyInfo;
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
 * @author zhuody
 * @version V1.1  2016-08-31
 *     
 */
@Controller
@SuppressWarnings("all")
@RequestMapping(value = "/workspace")
public class WorkSpaceController {
	@Autowired
	private WorkSpaceService workSpaceService;
	@Autowired(required = true)
	UamSessionService uamSessionService;
	
	//快速查询接口
	@Resource(name = "quickGridService")
	private QuickGridService quickGridService;

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
	
	
	
	@RequestMapping(value = "dashboard")
	public String showWorkSpace2(Model model, HttpServletRequest request, HttpServletResponse response) throws IOException {
		SessionUser user = uamSessionService.getSessionUser();
		//SessionUser currentUser = uamSessionService.getSessionUser();		
		/*判断是否为移动端登录*/
		boolean isMobile = checkMobile(request);
		if (isMobile) {
			return mainPage(model, request);
		}
		
		//本月目标达成报表，数据查询开始时间 在当前月份的基础上推前5个月开始查询
		String startDate = DateUtil.getFormatDate(DateUtil.plusMonth(new Date(),-5),"yyyy-MM-01");
		model.addAttribute("startDate",startDate);
		
		//红 黄 商贷流失预警案件数灯
		/*WorkSpace wk= buildWorkSpaceBean(null, null);
		wk.setColor(0);
		int redLight = workSpaceService.countLight(wk);
		wk.setColor(1);
		int yeLight = workSpaceService.countLight(wk);
		int bizwarnCaseCount = 0;
		if ("yucui_team".equals(currentUser.getServiceDepHierarchy())) {
			bizwarnCaseCount = bizWarnInfoService
					.getAllBizwarnCountByTeam(currentUser.getUsername()); // 获取本组所有的状态为生效的商贷预警数
		} else {
			bizwarnCaseCount = bizWarnInfoService
					.getAllBizwarnCountByDistinct(currentUser
							.getServiceCompanyId()); // 获取本区所有的状态为生效的商贷预警数
		}*/
		//int bizwarnCaseCount = bizWarnInfoService.getAllBizwarnCount(currentUser.getUsername());   //获取所有的状态为生效的商贷预警数
		Map map=new HashMap();
		String jobCode = user.getServiceJobCode();
		//设置当前系统用户的登录名
		map.put("candidateId", user.getUsername());
		//非交易主管
		if (!TransJobs.TJYZG.getCode().equals(jobCode)) {
			map.put("managerFlag", "1");
		} else {
			map.put("orgId", user.getServiceDepId());
		}
		int unLocatedCase =  workSpaceService.getUnlocatedCaseCount();
		int unLocatedTask = workSpaceService.getUnlocatedTaskCount(map);
		
/*		model.addAttribute("bizwarnCaseCount", bizwarnCaseCount);
		model.addAttribute("redLight", redLight);
		model.addAttribute("yeLight", yeLight);*/
		
		
		model.addAttribute("userId", user.getId());
		
		//判断组织是否为后台的
		TsTeamProperty tp = teamPropertyService.findTeamPropertyByTeamCode(user.getServiceDepCode());
		boolean isBackTeam = false;
		if (tp != null) {
			isBackTeam = "yu_back".equals(tp.getTeamProperty());
		}		
		
		List<User> uList = new ArrayList<User>();
		String userId = user.getId();
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
/*			if (SecurityUtils.getSubject().isPermitted("TRADE.WORKSPACE.CALENDAR")) {
				model.addAttribute("rank", doGetRank(user));
			}*/
			
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
	
	
	//龙虎榜快速查询
	@RequestMapping(value = "qqGetRank")
	@ResponseBody	
	public Map doGetRankByQuickQuery(HttpServletRequest request, HttpServletResponse response) throws IOException {	
		SessionUser user = uamSessionService.getSessionUser();
		JQGridParam gp = new JQGridParam();
		gp.setPagination(false);		

		Map<String,Object> map = new HashMap<String,Object>();
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
			
			if(work!=null){
				gp.put("user_Id", work.getUserId());
				gp.put("org_Id", work.getOrgId());			
				gp.put("orgs", work.getOrgs());				
				gp.put("rankType", work.getRankType());
				gp.put("RankDuration", work.getRankDuration());
				gp.setQueryId("personalWorkGetRankListQuery");
			}
			
			gp.put("rankCat", "loan_amount");	
			List  loanAmountList=null;
			Page<Map<String, Object>> loanAmountListResult = quickGridService.findPageForSqlServer(gp);			
			if(loanAmountListResult!=null && loanAmountListResult.getContent()!=null && loanAmountListResult.getContent().size()>0){				
				for(int i=0;i<loanAmountListResult.getContent().size();i++){
					loanAmountList=new ArrayList();
					loanAmountList.add(loanAmountListResult.getContent());
				}		
			}
			map.put("loanAmountRankList", loanAmountList);
			
			List  signAmountList=null;
			gp.put("rankCat", "sign_amount");	
			Page<Map<String, Object>> signAmountListResult = quickGridService.findPageForSqlServer(gp);			
			if(signAmountListResult!=null && signAmountListResult.getContent()!=null && signAmountListResult.getContent().size()>0){				
				for(int i=0;i<signAmountListResult.getContent().size();i++){
					signAmountList=new ArrayList();
					signAmountList.add(signAmountListResult.getContent());
				}		
			}
			map.put("signAmountRankList", signAmountList);
			
			
			List  actualAmountList=null;
			gp.put("rankCat", "actual_amount");		
			Page<Map<String, Object>> actualAmountListResult = quickGridService.findPageForSqlServer(gp);			
			if(actualAmountListResult!=null && actualAmountListResult.getContent()!=null && actualAmountListResult.getContent().size()>0){				
				for(int i=0;i<actualAmountListResult.getContent().size();i++){
					actualAmountList=new ArrayList();
					actualAmountList.add(actualAmountListResult.getContent());
				}		
			}
			map.put("actualAmountRankList", actualAmountList);
			
			map.put("loanAmountRank", null);
			map.put("signAmountRank", null);
			map.put("actualAmountRank", null);
			
		} else if(TransJobs.TZJ.getCode().equals(jobCode)) { //总监
			work.setRankType(jobCode);
			work.setOrgId(null);
			List<Org> orgList = uamUserOrgService.getOrgByDepHierarchy(
					uamUserOrgService.getParentOrgByDepHierarchy(user.getServiceDepId(), DepTypeEnum.TYCZB.getCode()).getId(), DepTypeEnum.TYCQY.getCode());
			if (CollectionUtils.isNotEmpty(orgList)) {
				for (Org toOrgVo : orgList) {
					args.add(toOrgVo.getId());
				}
			}
			args.add(user.getServiceDepId());
			work.setOrgs(args);	
			
			if(work!=null){
				gp.put("user_Id", work.getUserId());
				gp.put("org_Id", work.getOrgId());			
				gp.put("orgs", work.getOrgs());				
				gp.put("rankType", work.getRankType());				
				gp.put("RankDuration", work.getRankDuration());
				gp.setQueryId("personalWorkGetRankListQuery");
			}
		
			
			gp.put("rankCat", "loan_amount");	
			List  loanAmountList=null;
			Page<Map<String, Object>> loanAmountListResult = quickGridService.findPageForSqlServer(gp);			
			if(loanAmountListResult!=null && loanAmountListResult.getContent()!=null && loanAmountListResult.getContent().size()>0){				
				for(int i=0;i<loanAmountListResult.getContent().size();i++){
					loanAmountList=new ArrayList();
					loanAmountList.add(loanAmountListResult.getContent());
				}		
			}
			map.put("loanAmountRankList", loanAmountList);
			
			List  signAmountList=null;
			gp.put("rankCat", "sign_amount");	
			Page<Map<String, Object>> signAmountListResult = quickGridService.findPageForSqlServer(gp);			
			if(signAmountListResult!=null && signAmountListResult.getContent()!=null && signAmountListResult.getContent().size()>0){				
				for(int i=0;i<signAmountListResult.getContent().size();i++){
					signAmountList=new ArrayList();
					signAmountList.add(signAmountListResult.getContent());
				}		
			}
			map.put("signAmountRankList", signAmountList);
			
			
			List  actualAmountList=null;
			gp.put("rankCat", "actual_amount");		
			Page<Map<String, Object>> actualAmountListResult = quickGridService.findPageForSqlServer(gp);			
			if(actualAmountListResult!=null && actualAmountListResult.getContent()!=null && actualAmountListResult.getContent().size()>0){				
				for(int i=0;i<actualAmountListResult.getContent().size();i++){
					actualAmountList=new ArrayList();
					actualAmountList.add(actualAmountListResult.getContent());
				}		
			}
			map.put("actualAmountRankList", actualAmountList);			
			
			work.setOrgId(user.getServiceDepId());
			gp.put("org_Id", work.getOrgId());	
			gp.setQueryId("personalWorkGetRankQuery");
			gp.put("rankCat", "loan_amount");
			int loan_amount=0;
			Page<Map<String, Object>> loanAmountListResult2 = quickGridService.findPageForSqlServer(gp);			
			if(loanAmountListResult2!=null && loanAmountListResult2.getContent()!=null && loanAmountListResult2.getContent().size()>0){	
				loan_amount= (int) loanAmountListResult2.getContent().get(0).get("rank_no");
			}		
			map.put("loanAmountRank", loan_amount);
			
			
			gp.put("rankCat", "sign_amount");
			int sign_amount=0;
			Page<Map<String, Object>> signAmountListResult2 = quickGridService.findPageForSqlServer(gp);			
			if(signAmountListResult2!=null && signAmountListResult2.getContent()!=null && signAmountListResult2.getContent().size()>0){	
				sign_amount= (int) signAmountListResult2.getContent().get(0).get("rank_no");
			}
			map.put("signAmountRank", sign_amount);
			
			gp.put("rankCat", "actual_amount");
			int actual_amount=0;
			Page<Map<String, Object>> actualAmountListResult2 = quickGridService.findPageForSqlServer(gp);			
			if(actualAmountListResult2!=null && actualAmountListResult2.getContent()!=null && actualAmountListResult2.getContent().size()>0){	
				actual_amount= (int) actualAmountListResult2.getContent().get(0).get("rank_no");
			}
			map.put("actualAmountRank", actual_amount);
			
		} else if (TransJobs.TSJYZG.getCode().equals(jobCode) || TransJobs.TJYZG.getCode().equals(jobCode)) { //(高级)交易主管
			work.setRankType(TransJobs.TJYZG.getCode());
			work.setOrgId(null);
			// 主管只看当前组织
			List<Org> orgList = uamUserOrgService.getOrgByDepHierarchy(
					uamUserOrgService.getParentOrgByDepHierarchy(user.getServiceDepId(), DepTypeEnum.TYCQY.getCode()).getId(), DepTypeEnum.TYCTEAM.getCode());
			if (CollectionUtils.isNotEmpty(orgList)) {
				for (Org toOrgVo : orgList) {
					args.add(toOrgVo.getId());
				}
			}
			args.add(user.getServiceDepId());
			work.setOrgs(args);			
			if(work!=null){
				gp.put("user_Id", work.getUserId());
				gp.put("org_Id", work.getOrgId());			
				gp.put("orgs", work.getOrgs());				
				gp.put("rankType", work.getRankType());				
				gp.put("RankDuration", work.getRankDuration());
				gp.setQueryId("personalWorkGetRankListQuery");
			}
			
			gp.put("rankCat", "loan_amount");	
			List  loanAmountList=null;
			Page<Map<String, Object>> loanAmountListResult = quickGridService.findPageForSqlServer(gp);			
			if(loanAmountListResult!=null && loanAmountListResult.getContent()!=null && loanAmountListResult.getContent().size()>0){				
				for(int i=0;i<loanAmountListResult.getContent().size();i++){
					loanAmountList=new ArrayList();
					loanAmountList.add(loanAmountListResult.getContent());
				}		
			}
			map.put("loanAmountRankList", loanAmountList);
			
			List  signAmountList=null;
			gp.put("rankCat", "sign_amount");	
			Page<Map<String, Object>> signAmountListResult = quickGridService.findPageForSqlServer(gp);			
			if(signAmountListResult!=null && signAmountListResult.getContent()!=null && signAmountListResult.getContent().size()>0){				
				for(int i=0;i<signAmountListResult.getContent().size();i++){
					signAmountList=new ArrayList();
					signAmountList.add(signAmountListResult.getContent());
				}		
			}
			map.put("signAmountRankList", signAmountList);
			
			
			List  actualAmountList=null;
			gp.put("rankCat", "actual_amount");		
			Page<Map<String, Object>> actualAmountListResult = quickGridService.findPageForSqlServer(gp);			
			if(actualAmountListResult!=null && actualAmountListResult.getContent()!=null && actualAmountListResult.getContent().size()>0){				
				for(int i=0;i<actualAmountListResult.getContent().size();i++){
					actualAmountList=new ArrayList();
					actualAmountList.add(actualAmountListResult.getContent());
				}		
			}
			map.put("actualAmountRankList", actualAmountList);			
			
			work.setOrgId(user.getServiceDepId());
			gp.put("org_Id", work.getOrgId());	
			gp.setQueryId("personalWorkGetRankQuery");
			gp.put("rankCat", "loan_amount");
			int loan_amount=0;
			Page<Map<String, Object>> loanAmountListResult2 = quickGridService.findPageForSqlServer(gp);			
			if(loanAmountListResult2!=null && loanAmountListResult2.getContent()!=null && loanAmountListResult2.getContent().size()>0){	
				loan_amount= (int) loanAmountListResult2.getContent().get(0).get("rank_no");
			}		
			map.put("loanAmountRank", loan_amount);
			
			
			gp.put("rankCat", "sign_amount");
			int sign_amount=0;
			Page<Map<String, Object>> signAmountListResult2 = quickGridService.findPageForSqlServer(gp);			
			if(signAmountListResult2!=null && signAmountListResult2.getContent()!=null && signAmountListResult2.getContent().size()>0){	
				sign_amount= (int) signAmountListResult2.getContent().get(0).get("rank_no");
			}
			map.put("signAmountRank", sign_amount);
			
			gp.put("rankCat", "actual_amount");
			int actual_amount=0;
			Page<Map<String, Object>> actualAmountListResult2 = quickGridService.findPageForSqlServer(gp);			
			if(actualAmountListResult2!=null && actualAmountListResult2.getContent()!=null && actualAmountListResult2.getContent().size()>0){	
				actual_amount= (int) actualAmountListResult2.getContent().get(0).get("rank_no");
			}
			map.put("actualAmountRank", actual_amount);
		} else if (TransJobs.TJYGW.getCode().equals(jobCode)) { //交易顾问
			work.setRankType(jobCode);
			work.setOrgs(null);
			work.setOrgId(user.getServiceDepId());
			
			if(work!=null){
				gp.put("user_Id", work.getUserId());
				gp.put("org_Id", work.getOrgId());			
				gp.put("orgs", work.getOrgs());				
				gp.put("rankType", work.getRankType());				
				gp.put("RankDuration", work.getRankDuration());
				gp.setQueryId("personalWorkGetRankListQuery");
			}
			gp.put("rankCat", "loan_amount");	
			List  loanAmountList=null;
			Page<Map<String, Object>> loanAmountListResult = quickGridService.findPageForSqlServer(gp);			
			if(loanAmountListResult!=null && loanAmountListResult.getContent()!=null && loanAmountListResult.getContent().size()>0){				
				for(int i=0;i<loanAmountListResult.getContent().size();i++){
					loanAmountList=new ArrayList();
					loanAmountList.add(loanAmountListResult.getContent());
				}		
			}
			map.put("loanAmountRankList", loanAmountList);
			
			List  signAmountList=null;
			gp.put("rankCat", "sign_amount");	
			Page<Map<String, Object>> signAmountListResult = quickGridService.findPageForSqlServer(gp);			
			if(signAmountListResult!=null && signAmountListResult.getContent()!=null && signAmountListResult.getContent().size()>0){				
				for(int i=0;i<signAmountListResult.getContent().size();i++){
					signAmountList=new ArrayList();
					signAmountList.add(signAmountListResult.getContent());
				}		
			}
			map.put("signAmountRankList", signAmountList);
			
			
			List  actualAmountList=null;
			gp.put("rankCat", "actual_amount");		
			Page<Map<String, Object>> actualAmountListResult = quickGridService.findPageForSqlServer(gp);			
			if(actualAmountListResult!=null && actualAmountListResult.getContent()!=null && actualAmountListResult.getContent().size()>0){				
				for(int i=0;i<actualAmountListResult.getContent().size();i++){
					actualAmountList=new ArrayList();
					actualAmountList.add(actualAmountListResult.getContent());
				}		
			}
			map.put("actualAmountRankList", actualAmountList);			
			
			work.setOrgId(user.getServiceDepId());
			gp.put("org_Id", work.getOrgId());	
			gp.setQueryId("personalWorkGetRankQuery");
			gp.put("rankCat", "loan_amount");
			int loan_amount=0;
			Page<Map<String, Object>> loanAmountListResult2 = quickGridService.findPageForSqlServer(gp);			
			if(loanAmountListResult2!=null && loanAmountListResult2.getContent()!=null && loanAmountListResult2.getContent().size()>0){	
				loan_amount= (int) loanAmountListResult2.getContent().get(0).get("rank_no");
			}		
			map.put("loanAmountRank", loan_amount);
			
			
			gp.put("rankCat", "sign_amount");
			int sign_amount=0;
			Page<Map<String, Object>> signAmountListResult2 = quickGridService.findPageForSqlServer(gp);			
			if(signAmountListResult2!=null && signAmountListResult2.getContent()!=null && signAmountListResult2.getContent().size()>0){	
				sign_amount= (int) signAmountListResult2.getContent().get(0).get("rank_no");
			}
			map.put("signAmountRank", sign_amount);
			
			gp.put("rankCat", "actual_amount");
			int actual_amount=0;
			Page<Map<String, Object>> actualAmountListResult2 = quickGridService.findPageForSqlServer(gp);			
			if(actualAmountListResult2!=null && actualAmountListResult2.getContent()!=null && actualAmountListResult2.getContent().size()>0){	
				actual_amount= (int) actualAmountListResult2.getContent().get(0).get("rank_no");
			}
			map.put("actualAmountRank", actual_amount);
		}

		return map;
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
			List<Org> orgList = uamUserOrgService.getOrgByDepHierarchy(
					uamUserOrgService.getParentOrgByDepHierarchy(user.getServiceDepId(), DepTypeEnum.TYCZB.getCode()).getId(), DepTypeEnum.TYCQY.getCode());
			if (CollectionUtils.isNotEmpty(orgList)) {
				for (Org toOrgVo : orgList) {
					args.add(toOrgVo.getId());
				}
			}
			args.add(user.getServiceDepId());
			work.setOrgs(args);			
		
			work.setRankCat("loan_amount");
			map.put("loanAmountRankList", workSpaceService.topRankList(work));
			work.setRankCat("sign_amount");
			map.put("signAmountRankList", workSpaceService.topRankList(work));
			work.setRankCat("actual_amount");
			map.put("actualAmountRankList", workSpaceService.topRankList(work));
			
			
			
			work.setOrgId(user.getServiceDepId());
			work.setRankCat("loan_amount");
			map.put("loanAmountRank", workSpaceService.getRank(work));
			work.setRankCat("sign_amount");
			map.put("signAmountRank", workSpaceService.getRank(work));
			work.setRankCat("actual_amount");
			map.put("actualAmountRank", workSpaceService.getRank(work));
			
		} else if (TransJobs.TSJYZG.getCode().equals(jobCode) || TransJobs.TJYZG.getCode().equals(jobCode)) { //(高级)交易主管
			work.setRankType(TransJobs.TJYZG.getCode());
			work.setOrgId(null);
			// 主管只看当前组织
			List<Org> orgList = uamUserOrgService.getOrgByDepHierarchy(
					uamUserOrgService.getParentOrgByDepHierarchy(user.getServiceDepId(), DepTypeEnum.TYCQY.getCode()).getId(), DepTypeEnum.TYCTEAM.getCode());
			if (CollectionUtils.isNotEmpty(orgList)) {
				for (Org toOrgVo : orgList) {
					args.add(toOrgVo.getId());
				}
			}
			args.add(user.getServiceDepId());
			work.setOrgs(args);			
		
			work.setRankCat("loan_amount");
			map.put("loanAmountRankList", workSpaceService.topRankList(work));
			work.setRankCat("sign_amount");
			map.put("signAmountRankList", workSpaceService.topRankList(work));
			work.setRankCat("actual_amount");
			map.put("actualAmountRankList", workSpaceService.topRankList(work));
			
			work.setOrgId(user.getServiceDepId());
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

	
	
	@RequestMapping(value = "trafficLightTips")
	@ResponseBody
	public Map trafficLightTips(HttpServletRequest request, HttpServletResponse response) throws IOException {
		SessionUser currentUser = uamSessionService.getSessionUser();		
		Map  map =new HashMap();
		//红 黄 商贷流失预警案件数灯
		WorkSpace wk= buildWorkSpaceBean(null, null);
		int redLight = redLightCountQuery(wk);
		int yeLight = yeLightCountQuery(wk);
		//无主案件预警数
		Long unLocatedCase =  getUnlocatedCaseCount();
		//无主任务预警数
		Long unLocatedTask = getUnlocatedTaskCount(currentUser);
		//待分配任务预警数
		Long caseDistributeCount =  getCaseDistributeCount();
		
		int bizwarnCaseCount = 0;
		if ("yucui_team".equals(currentUser.getServiceDepHierarchy())) {
			bizwarnCaseCount = benchBizwarnCaseCountQueryByTeam(currentUser.getUsername());
			//bizWarnInfoService.getAllBizwarnCountByTeam(currentUser.getUsername()); // 获取本组所有的状态为生效的商贷预警数
		} else {
			bizwarnCaseCount = benchBizwarnCaseCountQueryByDistinct(currentUser.getServiceCompanyId());
			//bizWarnInfoService.getAllBizwarnCountByDistinct(currentUser.getServiceCompanyId()); // 获取本区所有的状态为生效的商贷预警数
		}		
		map.put("bizwarnCaseCount", bizwarnCaseCount);
		map.put("redLight", redLight);
		map.put("yeLight", yeLight);
		
		map.put("unLocatedCaseCount", unLocatedCase);
		map.put("unLocatedTaskCount", unLocatedTask);
		map.put("caseDistributeCount", caseDistributeCount);
		
		return map;
	}
	
	/**
	 * 待分配任务预警数
	 */
	public Long getCaseDistributeCount(){
		JQGridParam gp = new JQGridParam();
		gp.setCountOnly(true);
		gp.setQueryId("queryCastListItemListUnDistribute");
		Page<Map<String, Object>> pages = quickGridService.findPageForSqlServer(gp);
		return pages.getTotalElements();
	}
	
	/**
	 * 无主任务预警数
	 * @return
	 */
	public Long getUnlocatedTaskCount(SessionUser currentUser){
		JQGridParam gp = new JQGridParam();
		String jobCode = currentUser.getServiceJobCode();
		//设置当前系统用户的登录名
		gp.put("candidateId", currentUser.getUsername());
		//非交易主管
		if (!TransJobs.TJYZG.getCode().equals(jobCode)) {
			gp.put("managerFlag", "1");
		} else {
			gp.put("mOrgId", currentUser.getServiceDepId());
		}
		gp.setCountOnly(true);
		gp.setQueryId("queryUnlocatedTask");
		Page<Map<String, Object>> pages = quickGridService.findPageForSqlServer(gp);
		return pages.getTotalElements();
	}
	
	/**
	 * 无主案件预警数
	 * @return
	 */
	public Long getUnlocatedCaseCount(){
		JQGridParam gp = new JQGridParam();
		gp.setCountOnly(true);
		gp.setQueryId("queryUnlocatedCase");
		Page<Map<String, Object>> pages = quickGridService.findPageForSqlServer(gp);
		return pages.getTotalElements();
	}
	
	//查找红灯预警数量
	private int redLightCountQuery(WorkSpace wk) {		
		JQGridParam gp = new JQGridParam();
		
		gp.setPagination(false);		
		if(wk!=null){
			gp.put("user_Id", wk.getUserId());
			gp.put("org_Id", wk.getOrgId());			
			gp.put("orgs", wk.getOrgs());
			gp.put("color", "0");
		}
		gp.setQueryId("personalWorkbenchRedLightCountQuery");
		Page<Map<String, Object>> redLightResult = quickGridService.findPageForSqlServer(gp);
		System.out.println("redLightResult===="+redLightResult);
		int redLight = 0;
		if(redLightResult!=null && redLightResult.getContent()!=null && redLightResult.getContent().size()>0){		
			redLight= (int) redLightResult.getContent().get(0).get("redLight");	
		}
		return redLight;
	}
	
	//查找黄灯预警数量
	private int yeLightCountQuery(WorkSpace wk) {		
		JQGridParam gp = new JQGridParam();
		gp.setPagination(false);
		if(wk!=null){			
			gp.put("user_Id", wk.getUserId());
			gp.put("org_Id", wk.getOrgId());			
			gp.put("orgs", wk.getOrgs());
			gp.put("color", "1");
		}

		gp.setQueryId("personalWorkbenchYeLightCountQuery");
		Page<Map<String, Object>> yeLightResult = quickGridService.findPageForSqlServer(gp);		
		int yeLight = 0;
		if(yeLightResult!=null && yeLightResult.getContent()!=null && yeLightResult.getContent().size()>0){		
			yeLight= (int) yeLightResult.getContent().get(0).get("yeLight");	
		}
		

		return yeLight;
	}
	
	//查找本组流失预警数量
	private int benchBizwarnCaseCountQueryByTeam(String userName) {	
		
		JQGridParam gp = new JQGridParam();
		gp.setPagination(false);				
		gp.put("user_LoginName", userName);
		
		gp.setQueryId("personalWorkbenchBizwarnCaseCountQueryByTeam");
		Page<Map<String, Object>> benchBizwarnCaseResultByTeam = quickGridService.findPageForSqlServer(gp);		
		int bizwarnCaseCount = 0;
		if(benchBizwarnCaseResultByTeam!=null && benchBizwarnCaseResultByTeam.getContent()!=null && benchBizwarnCaseResultByTeam.getContent().size()>0){		
			bizwarnCaseCount= (int) benchBizwarnCaseResultByTeam.getContent().get(0).get("bizwarnCaseCountByTeam");	
		}
		return bizwarnCaseCount;
	}
	
	//查找本组流失预警数量
	private int benchBizwarnCaseCountQueryByDistinct(String ServiceCompanyId) {	
		
		JQGridParam gp = new JQGridParam();
		gp.setPagination(false);				
		gp.put("currentOrgId", ServiceCompanyId);
		
		gp.setQueryId("personalWorkbenchBizwarnCaseCountQueryByDistinct");
		Page<Map<String, Object>> benchBizwarnCaseResultByDistinct = quickGridService.findPageForSqlServer(gp);		
		int bizwarnCaseCount = 0;
		if(benchBizwarnCaseResultByDistinct!=null && benchBizwarnCaseResultByDistinct.getContent()!=null && benchBizwarnCaseResultByDistinct.getContent().size()>0){		
			bizwarnCaseCount= (int) benchBizwarnCaseResultByDistinct.getContent().get(0).get("bizwarnCaseCountByDistinct");	
		}
		return bizwarnCaseCount;
	}
	
	
	//个人工作台 性能优化测试
	@RequestMapping(value = "newWorkSpaceSta")
	@ResponseBody
	public Map newWorkSpaceSta(String serachId, String mo) {
		WorkSpace work = buildWorkSpaceBean(serachId, mo);		
		JQGridParam gp = new JQGridParam();
		gp.setPagination(false);
		if(work!=null){			
			gp.put("mo", work.getMo());
			gp.put("org_Id", work.getOrgId());			
			gp.put("orgs", work.getOrgs());				
			gp.put("user_Id", work.getUserId());
		}
		
		Map workSpaceMap=new HashMap();
		//接单
		String receiveCount=receiveOrder(gp);
		if(!"".equals(receiveCount) && receiveCount!=null){
			workSpaceMap.put("receiveCount", receiveCount);
		}else{
			workSpaceMap.put("receiveCount", "0");
		}
		//签约
		String signCount=signAmount(gp);
		if(!"".equals(signCount) && signCount!=null){
			workSpaceMap.put("signCount", signCount);
		}else{
			workSpaceMap.put("signCount", "0");
		}
		
		//贷款申请
		String loanApplyCount=loanApplyCount(gp);
		if(!"".equals(loanApplyCount) && loanApplyCount!=null){
			workSpaceMap.put("loanApplyCount", loanApplyCount);
		}else{
			workSpaceMap.put("loanApplyCount", "0");
		}
		
		//结案
		String closeCount=closeCount(gp);
		if(!"".equals(closeCount) && closeCount!=null){
			workSpaceMap.put("closeCount", closeCount);
		}else{
			workSpaceMap.put("closeCount", "0");
		}
		
		NumberFormat formatter = new DecimalFormat("###,##0.00万");
		NumberFormat formatter2 = new DecimalFormat("###,##0.00");
		
		Map map1=actualAmountAndConvRate(gp);
		if (map1.get("actualAmount") == null) {
			workSpaceMap.put("actualAmount", "0.00万");
		} else {
			workSpaceMap.put("actualAmount", formatter.format(( new BigDecimal((String)map1.get("actualAmount")) ).divide(new BigDecimal(10000))));
		}
		
		String loanAmount=loanAmountQuery(gp);
		if (loanAmount == null) {
			workSpaceMap.put("loanAmount", "0.00万");
		} else {
			workSpaceMap.put("loanAmount", formatter.format(Double.valueOf(loanAmount)/10000));
		}
		
		String signAmount=signAmountQuery(gp);
		if (signAmount == null) {
			workSpaceMap.put("signAmount", "0.00万");
		} else {
			workSpaceMap.put("signAmount", formatter.format(Double.valueOf(signAmount)/10000));
		}
		
		String convRate=convRateQuery(gp);
		if (convRate == null) {
			workSpaceMap.put("convRate", "0.00%");
		} else {
			workSpaceMap.put("convRate", formatter2.format(Double.valueOf(convRate)*100) + "%");
		}
		String efConverRt=efConverRtQuery(gp);
		if (efConverRt == null) {
			workSpaceMap.put("efConverRt", "0.00%");
		} else {
			workSpaceMap.put("efConverRt", formatter2.format(Double.valueOf(efConverRt)) + "%");
		}


		Map map2=staEvaFeeQuery(gp);
		if (map2 != null) {
			workSpaceMap.putAll(map2);
		}
		if (map2.get("evalFee") == null) {
			workSpaceMap.put("evalFee", "0.00");
		} else {
			workSpaceMap.put("evalFee", formatter2.format(new BigDecimal(map2.get("evalFee").toString())));
		}
		if (map2.get("efConvRate") == null) {
			workSpaceMap.put("efConvRate", "0.00%");
		} else {
			workSpaceMap.put("efConvRate", formatter2.format(new BigDecimal(map2.get("efConvRate").toString())) + "%");
		}		
		
		workSpaceMap.put("staLoanApply", staLoanApplyQuery(gp));
		workSpaceMap.put("staLoanSign",  staLoanSignQuery(gp));
		workSpaceMap.put("staLoanRelease",  staLoanReleaseQuery(gp));
		
		return workSpaceMap; 
	}
	
	//查询借贷详情
	private Object staLoanApplyQuery(JQGridParam gp) {
		
		gp.setQueryId("personalWorkbenchStaLoanApplyQuery");
		Page<Map<String, Object>> staLoanApplyResult = quickGridService.findPageForSqlServer(gp);		
		Object staLoanApply = null;
		if(staLoanApplyResult!=null && staLoanApplyResult.getContent()!=null && staLoanApplyResult.getContent().size()>0){
			staLoanApply = JSONArray.toJSON(staLoanApplyResult.getContent());		
		}
		return staLoanApply;
	}
	//查询签约详情
	private Object staLoanSignQuery(JQGridParam gp) {
		gp.setQueryId("personalWorkbenchStaLoanSignQueryQuery");
		Page<Map<String, Object>> staLoanSignResult = quickGridService.findPageForSqlServer(gp);		
		Object staLoanSign =null;		
		if(staLoanSignResult!=null && staLoanSignResult.getContent()!=null && staLoanSignResult.getContent().size()>0){
			staLoanSign=JSONArray.toJSON(staLoanSignResult.getContent());		
		}
		return staLoanSign;
	}
	//查询放贷详情
	private Object staLoanReleaseQuery(JQGridParam gp) {
		gp.setQueryId("personalWorkbenchStaLoanReleaseQuery");
		Page<Map<String, Object>> staLoanReleaseResult = quickGridService.findPageForSqlServer(gp);
		Object staLoanRelease = null;
		if(staLoanReleaseResult!=null && staLoanReleaseResult.getContent()!=null && staLoanReleaseResult.getContent().size()>0){
			staLoanRelease=JSONArray.toJSON(staLoanReleaseResult.getContent());		
		}
		return staLoanRelease;
	}
	
	//查询接单数
	private String receiveOrder(JQGridParam gp) {	
		gp.setQueryId("personalWorkbenchReceiveOrderQuery");
		Page<Map<String, Object>> receiveOrderResult = quickGridService.findPageForSqlServer(gp);		
		String receiveCount = null;		
		if(receiveOrderResult!=null && receiveOrderResult.getContent()!=null && receiveOrderResult.getContent().size()>0){
			if(receiveOrderResult.getContent().get(0).get("receiveCount")!=null){
				receiveCount= receiveOrderResult.getContent().get(0).get("receiveCount").toString();	
			}			
		}		
		return receiveCount;		
	}
	
	//查询签约数
	private String signAmount(JQGridParam gp) {
		gp.setQueryId("personalWorkbenchSignCountQuery");
		Page<Map<String, Object>> signAmountResult = quickGridService.findPageForSqlServer(gp);
		System.out.println(signAmountResult.toString());
		String signCount = null;		
		if(signAmountResult!=null && signAmountResult.getContent()!=null && signAmountResult.getContent().size()>0){
			if(signAmountResult.getContent().get(0).get("signCount")!=null){
				signCount= signAmountResult.getContent().get(0).get("signCount").toString();
			}			
		}		
		return signCount;	

	}
	
	//查询贷款申请数
	private String loanApplyCount(JQGridParam gp) {
		String loanApplyCount=null;
		gp.setQueryId("personalWorkbenchLoanApplyCountQuery");
		Page<Map<String, Object>> loanApplyCountResult = quickGridService.findPageForSqlServer(gp);
		System.out.println("loanApplyCountResult"+loanApplyCountResult.toString());		
		if(loanApplyCountResult!=null && loanApplyCountResult.getContent()!=null && loanApplyCountResult.getContent().size()>0){
			if(loanApplyCountResult.getContent().get(0).get("loanApplyCount")!=null){
				loanApplyCount= loanApplyCountResult.getContent().get(0).get("loanApplyCount").toString();	
			}			
		}		
		return loanApplyCount;	
	}
	
	//查询结案数
	private String closeCount(JQGridParam gp) {
		String closeCount =null;
		gp.setQueryId("personalWorkbenchCloseCountQuery");
		Page<Map<String, Object>> closeCountResult = quickGridService.findPageForSqlServer(gp);		
		if(closeCountResult!=null && closeCountResult.getContent()!=null && closeCountResult.getContent().size()>0){
			if(closeCountResult.getContent().get(0).get("closeCount")!=null){
				closeCount= closeCountResult.getContent().get(0).get("closeCount").toString();
			}
				
		}		
		return closeCount;	
	}
	//查询放款金额和转化率
	private Map<String,String> actualAmountAndConvRate(JQGridParam gp) {
		Map map=new HashMap();
		gp.setQueryId("personalWorkbenchActualAmountAndConvRateQuery");
		Page<Map<String, Object>> actualAmountAndConvRateResult = quickGridService.findPageForSqlServer(gp);
		System.out.println("actualAmountAndConvRateResult==="+actualAmountAndConvRateResult.getContent().get(0).get("actualAmount"));
		if(actualAmountAndConvRateResult!=null && actualAmountAndConvRateResult.getContent()!=null && actualAmountAndConvRateResult.getContent().size()>0){
			if(null != actualAmountAndConvRateResult.getContent().get(0).get("actualAmount")){
				String actualAmount = actualAmountAndConvRateResult.getContent().get(0).get("actualAmount").toString();			
				map.put("actualAmount", actualAmount);
			}
		}
		return map;
	}
	
	//查询贷款金额
	private String loanAmountQuery(JQGridParam gp) {
		gp.setQueryId("personalWorkbenchLoanAmountQuery");
		String loanAmount=null;
		Page<Map<String, Object>> loanAmountResult = quickGridService.findPageForSqlServer(gp);
		System.out.println("loanAmountResult==="+loanAmountResult.getContent().get(0).get("loanAmount"));
		if(loanAmountResult!=null && loanAmountResult.getContent()!=null && loanAmountResult.getContent().size()>0){
			if(loanAmountResult.getContent().get(0).get("loanAmount")!=null){
				loanAmount= loanAmountResult.getContent().get(0).get("loanAmount").toString();	
			}
		}		
		return loanAmount;		
	}
	//查询面签金额
	private String signAmountQuery(JQGridParam gp) {
		String signAmount=null;
		gp.setQueryId("personalWorkbenchSignAmountQuery");
		Page<Map<String, Object>> signAmountResult = quickGridService.findPageForSqlServer(gp);
		System.out.println("signAmountResult==="+signAmountResult.getContent().get(0).get("signAmount"));
		if(signAmountResult!=null && signAmountResult.getContent()!=null && signAmountResult.getContent().size()>0){
			if(signAmountResult.getContent().get(0).get("signAmount")!=null){
				signAmount= signAmountResult.getContent().get(0).get("signAmount").toString();	
			}
		}		
		return signAmount;		
	}
	
	//查询E+转化率
	private String convRateQuery(JQGridParam gp) {
		String convRate=null;
		gp.setQueryId("personalWorkbenchConvRateQuery");
		Page<Map<String, Object>> convRateResult = quickGridService.findPageForSqlServer(gp);		
		if(convRateResult!=null && convRateResult.getContent()!=null && convRateResult.getContent().size()>0){
			if(convRateResult.getContent().get(0).get("convRate")!=null){
				convRate= convRateResult.getContent().get(0).get("convRate").toString();	
			}			
		}		
		return convRate;	
		
	}
	//查询E+转化率
	private String efConverRtQuery(JQGridParam gp) {
		gp.setQueryId("personalWorkbenchEfConverRtResultQuery");
		Page<Map<String, Object>> efConverRtResult = quickGridService.findPageForSqlServer(gp);		
		String efConverRt =null;
		if(efConverRtResult!=null && efConverRtResult.getContent()!=null && efConverRtResult.getContent().size()>0){
			if(efConverRtResult.getContent().get(0).get("efConverRt")!=null){
				efConverRt= efConverRtResult.getContent().get(0).get("efConverRt").toString();
			}		
		}
		return efConverRt;
	}
	
	//查询放款金额和转化率
	private Map<String,String> staEvaFeeQuery(JQGridParam gp) {		
		Map map=new HashMap();
		gp.setQueryId("personalWorkbenchStaEvaFeeQuery");
		Page<Map<String, Object>> staEvaFeeResult = quickGridService.findPageForSqlServer(gp);
		System.out.println("staEvaFeeResult====================="+staEvaFeeResult.toString());
		if(staEvaFeeResult!=null && staEvaFeeResult.getContent()!=null && staEvaFeeResult.getContent().size()>0){				
			if(null != staEvaFeeResult.getContent().get(0).get("evalFee")){
				String evalFee = staEvaFeeResult.getContent().get(0).get("evalFee").toString();
				map.put("evalFee", evalFee);
			}
			if(null !=staEvaFeeResult.getContent().get(0).get("efConvRate")){
				String efConvRate = staEvaFeeResult.getContent().get(0).get("efConvRate").toString();
				
				map.put("efConvRate", efConvRate);
			}
			
		}
		return map;
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
		Double efConverRt = workSpaceService.staEvaFeeCount(work);// 评估费转化率

		NumberFormat formatter = new DecimalFormat("###,##0.00万");
		NumberFormat formatter2 = new DecimalFormat("###,##0.00");

		if (loanAmount == null) {
			m.put("loanAmount", "0.00万");
		} else {
			m.put("loanAmount", formatter.format(loanAmount / 10000));
		}

		if (signAmount == null) {
			m.put("signAmount", "0.00万");
		} else {
			m.put("signAmount", formatter.format(signAmount / 10000));
		}

		if (m.get("actualAmount") == null) {
			m.put("actualAmount", "0.00万");
		} else {
			m.put("actualAmount", formatter.format(((BigDecimal) m
					.get("actualAmount")).divide(new BigDecimal(10000))));
		}

		if (convRate == null) {
			m.put("convRate", "0.00%");
		} else {
			m.put("convRate", formatter2.format(convRate * 100) + "%");
		}

		if (efConverRt == null) {
			m.put("efConverRt", "0.00%");
		} else {
			m.put("efConverRt", formatter2.format(efConverRt) + "%");
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
		// m.put("transferCount", workSpaceService.staTransferCount(work));
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
				List<Org> orgList = uamUserOrgService.getOrgByParentId(user.getServiceDepId());
				orgs = orgListToListStr(orgList);
				orgs.add(user.getServiceDepId());
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
