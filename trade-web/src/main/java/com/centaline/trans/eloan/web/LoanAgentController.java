package com.centaline.trans.eloan.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

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
import com.aist.uam.basedata.remote.vo.Dict;
import com.aist.uam.userorg.remote.UamUserOrgService;
import com.aist.uam.userorg.remote.vo.Job;
import com.aist.uam.userorg.remote.vo.Org;
import com.aist.uam.userorg.remote.vo.User;
import com.aist.uam.userorg.remote.vo.UserOrgJob;
import com.centaline.trans.cases.service.MyCaseListService;
import com.centaline.trans.common.entity.TgGuestInfo;
import com.centaline.trans.common.enums.DepTypeEnum;
import com.centaline.trans.common.enums.TransJobs;
import com.centaline.trans.eloan.entity.LoanAgent;
import com.centaline.trans.eloan.entity.LoanStatusChange;
import com.centaline.trans.eloan.service.LoanAgentService;
import com.centaline.trans.mgr.Consts;

@Controller
@RequestMapping("/loan")
public class LoanAgentController {
	@Autowired
	private LoanAgentService loanAgentService;
	@Autowired
	private UamSessionService uamSessionService;
	@Autowired
	private UamBasedataService uamBasedataService;
	@Autowired
	private UamUserOrgService uamUserOrgService;
	@Autowired
	private MyCaseListService myCaseListService;

	@RequestMapping("submit")
	public String submit() {
		return "/loan/submit";
	}
	
	@RequestMapping("loanAgentList")
	public String loanAgentList(Model model, ServletRequest request) {
	    SessionUser sessionUser = uamSessionService.getSessionUser();
	    String jobCode = sessionUser.getServiceJobCode();
		if("YCYYZY".equals(sessionUser.getServiceJobCode())||"yucui_product".equals(sessionUser.getServiceJobCode())){//如果是誉萃产品部、誉萃运营专员岗位的话，查询誉翠下所有组织的数据
			request.setAttribute("serviceDepId","ff8080814f459a78014f45a73d820006");//ff8080814f459a78014f45a73d820006为誉翠投资的orgid
		}else{
			request.setAttribute("serviceDepId",sessionUser.getServiceDepId());
		}

		if(TransJobs.TJYGW.getCode().equals(jobCode)) {
			request.setAttribute("isJygw",true);
		}
		if(TransJobs.YCPRODUCT.getCode().equals(jobCode)) {
			request.setAttribute("isProduct",true);
		}
		
		request.setAttribute("isLoanAgentTimeType",request.getParameter("isLoanAgentTimeType"));
		if(!StringUtils.isBlank(request.getParameter("isLoanAgentTimeType"))) {
			request.setAttribute("startTime",request.getParameter("startTime"));
			request.setAttribute("endTime",request.getParameter("endTime"));
		}
		
		if (!StringUtils.isBlank(request.getParameter("sUserId"))) {
			if (TransJobs.TJYZG.getCode().equals(jobCode) || TransJobs.TSJYZG.getCode().equals(jobCode)) {
				request.setAttribute("serUserId", request.getParameter("sUserId"));
				request.setAttribute("userInfo", getUserInfo(request.getParameter("sUserId")));
			} else {
				request.setAttribute("serOrgId", request.getParameter("sUserId"));
				Org org=uamUserOrgService.getOrgById(request.getParameter("sUserId"));
				request.setAttribute("serOrgName", org.getOrgName());
			}
		}
		return "/loan/loanAgentList";
	}
	
	private String getUserInfo(String userId) {
		if (StringUtils.isBlank(userId)) {
			return userId;
		}
		List<UserOrgJob> uojs = uamUserOrgService.getUserOrgJobByUserId(userId);
		if (uojs != null && !uojs.isEmpty()) {
			UserOrgJob uoj = null;
			if (uojs.size() == 1) {
				uoj = uojs.get(0);
			} else {
				for (UserOrgJob userOrgJob : uojs) {
					if ("1".equals(userOrgJob.getIsmain())) {
						uoj = userOrgJob;
						break;
					}
				}
			}
			if (uoj == null) {
				uoj = uojs.get(0);
			}
			Org org = uamUserOrgService.getOrgById(uoj.getOrgId());
			User user = uamUserOrgService.getUserById(userId);
			return user.getRealName();
			//return user.getRealName() + "(" + org.getOrgName() + ")";
		}
		return null;
	}
	
	@RequestMapping("manage")
	public String manage(Model model, ServletRequest request) {
		SessionUser user = uamSessionService.getSessionUser();
		
		String adminOrg = "";
		if(StringUtils.isNotBlank(user.getAdminOrg())) {
			adminOrg = user.getAdminOrg().trim();
		}
		request.setAttribute("adminOrg", adminOrg);

		return "/loan/manage";
	}

	@RequestMapping("box/details")
	public String details(Long pkid,Long isOnlyRead, Model model) {
		//根据JOBCODE 获取JOB 增加城市参数 by:yinchao 2017/9/4
		Job j = uamUserOrgService.getJobByCode(TransJobs.YCPRODUCT.getCode(),uamSessionService.getSessionUser().getCityCode());
		List<User> users = uamUserOrgService.getUserByJobId(j.getId());
		if(SecurityUtils.getSubject().isPermitted("TRADE.LOAN.SUBMIT.BELONG")){
			SessionUser user = uamSessionService.getSessionUser();
			
			Org org = uamUserOrgService.getParentOrgByDepHierarchy(user.getServiceDepId(), DepTypeEnum.TYCQY.getCode());
			model.addAttribute("orgId", org.getId());
			
//			List<User> jygws = uamUserOrgService.getUserByOrgIdAndJobCode(user.getServiceDepId(), TransJobs.TJYGW.getCode());
//			model.addAttribute("jygws", jygws);
			
		}
		List<User> usersAvailable = new ArrayList<User>();
		for (User user : users) {
			if("1".equals(user.getAvailable())){
				usersAvailable.add(user);
			}
		}
		model.addAttribute("yuCuiProduct", usersAvailable);
		if (pkid != null) {
			LoanAgent loanAgent = loanAgentService.view(pkid);
			if (!StringUtils.isBlank(loanAgent.getConfirmStatus())) {
				Dict d = uamBasedataService.findDictByTypeAndCode(
						"yu_eplus_status", loanAgent.getConfirmStatus());
				if (d != null) {
					loanAgent.setConfirmStatus(d.getName());
				}
			}
			model.addAttribute("loanAgent", loanAgent);
			model.addAttribute("loanAgentName", uamUserOrgService.getUserById(loanAgent.getExecutorId()).getRealName());
		}
		if(isOnlyRead!=null&&isOnlyRead==1L) {
			return "/loan/detailsOnlyRead";
		} else {
			return "/loan/details";
		}
	
	}

	/*根据caseCoded查询客户详情*/
	@RequestMapping("getCaseDetails")
	@ResponseBody
	public List<TgGuestInfo> getCaseDetailsBypkid(String caseCode){
		List<TgGuestInfo> caseInfos = myCaseListService.findTgGuestInfoByCaseCode(caseCode);
		return caseInfos;
	}
	
	@RequestMapping("doSubmit")
	@ResponseBody
	public Object doSubmit(LoanAgent loanAgent) {
		SessionUser user = uamSessionService.getSessionUser();
		loanAgent.setExecutorTeam(user.getServiceDepId());
		if(StringUtils.isBlank(loanAgent.getExecutorId())){
			loanAgent.setExecutorId(user.getId());
		}
		
		if (StringUtils.isBlank(loanAgent.getCaseCode())) {
			loanAgent.setCaseCode(null);
		}
		doSetWanYuan(loanAgent);
		loanAgent.setOptUser(user.getId());
		if (loanAgent.getPkid() == null) {
			loanAgent.setCreatorId(user.getId());
			loanAgentService.add(loanAgent);
		} else {
			loanAgentService.update(loanAgent);
		}
		return loanAgent;
	}

	// 转成万元
	private void doSetWanYuan(LoanAgent loanAgent) {
		if (loanAgent.getLoanAmount() != null) {
			loanAgent.setLoanAmount(loanAgent.getLoanAmount() * 10000);
		}
		if (loanAgent.getSignAmount() != null) {
			loanAgent.setSignAmount(loanAgent.getSignAmount() * 10000);
		}
		if (loanAgent.getActualAmount() != null) {
			loanAgent.setActualAmount(loanAgent.getActualAmount() * 10000);
		}
	}

	@SuppressWarnings("rawtypes")
	@RequestMapping("confirmStatus")
	@ResponseBody
	public AjaxResponse confirmStatus(Model model, ServletRequest request,
			HttpServletRequest req, String pkId, String changePkId,String stTo) {
		AjaxResponse result = new AjaxResponse();
		try {
			SessionUser u=uamSessionService.getSessionUser();
			
			LoanAgent loanAgent = new LoanAgent();
			loanAgent.setPkid(Long.parseLong(pkId));
			loanAgent.setConfirmTime(new Date());
			loanAgent.setConfirmStatus(stTo);

			LoanStatusChange loanStatusChange = new LoanStatusChange();
			loanStatusChange.setPkid(Long.parseLong(changePkId));
			loanStatusChange.setIsConfirm("1");
			
			loanStatusChange.setConfirmTime(new Date());
			loanStatusChange.setConfirmUser(u.getId());
			
			loanStatusChange.setUpdateBy(u.getId());
			loanStatusChange.setUpdateTime(new Date());

			loanAgentService.updateStatusLoanAgent(loanAgent, loanStatusChange);

			return result.success("变更列表确认成功");
		} catch (Exception e) {
			return result.fail("变更列表确认成功");
		}
	}
	
	@RequestMapping("cancelStatus")
	@ResponseBody
	public AjaxResponse cancelStatus(Model model, ServletRequest request,
			HttpServletRequest req, String pkId, String changePkId,String stFrom) {
		AjaxResponse result = new AjaxResponse();
		try {
			SessionUser u=uamSessionService.getSessionUser();
			LoanAgent loanAgent = new LoanAgent();
			loanAgent.setPkid(Long.parseLong(pkId));
			loanAgent.setApplyStatus(stFrom);
			loanAgent.setApplyTime(new Date());

			LoanStatusChange loanStatusChange = new LoanStatusChange();
			loanStatusChange.setPkid(Long.parseLong(changePkId));
			loanStatusChange.setIsConfirm("2");
			
			loanStatusChange.setConfirmTime(new Date());
			loanStatusChange.setConfirmUser(u.getId());
			
			loanStatusChange.setUpdateBy(u.getId());
			loanStatusChange.setUpdateTime(new Date());

			loanAgentService.updateStatusLoanAgent(loanAgent, loanStatusChange);

			return result.success("变更列表取消成功");
		} catch (Exception e) {
			return result.fail("变更列表取消成功");
		}
	}

	@RequestMapping("doDelete")
	@ResponseBody
	public Object doDelete(LoanAgent loanAgent) {
		loanAgentService.doDelete(loanAgent);
		return loanAgent;
	}
	
	/**
	 * 警示列表
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value="warnList")
	public String warnList(ServletRequest request){
		SessionUser user = uamSessionService.getSessionUser();
		String userOrgId = user.getServiceDepId();
		request.setAttribute("queryOrg", userOrgId);
		
		String adminOrg = "";
		if(StringUtils.isNotBlank(user.getAdminOrg())) {
			adminOrg = user.getAdminOrg().trim();
		}
		request.setAttribute("adminOrg", adminOrg);

		List<Org> districtOrgList = uamUserOrgService.getOrgByDepHierarchy(Consts.YU_SH_ORG_ROOT, Consts.YU_DISTRICT);
		List<Org> showOrgList = new ArrayList<Org>();
		if(StringUtils.isNotBlank(adminOrg)) {
			for(Org org : districtOrgList) {
			     String[] adminOrgs = adminOrg.split(",");
				 for(int i = 0 ;i< adminOrgs.length;i++) {
					 String adminO = adminOrgs[i];
					 if(org.getId().equals(adminO)) {
						 showOrgList.add(org);
					 }
				 }
			}
		}
		request.setAttribute("districtOrgList", showOrgList);
		
		return "loan/warnList";
	}
	
	@RequestMapping(value="updateWarnListTime")
	@ResponseBody
	public AjaxResponse<LoanAgent> batchUpdateExportTime(Model model, ServletRequest request,String idStr){
		AjaxResponse<LoanAgent> result = new AjaxResponse<>();
		try {
			String[] pkidArr = idStr.split(",");
			for(String pkId : pkidArr) {
				LoanAgent loanAgent = new LoanAgent();
				loanAgent.setPkid(Long.parseLong(pkId));
				loanAgent.setLastExceedExportTime(new Date());
			    loanAgentService.updateLoanAgent(loanAgent);
			}
			result.setSuccess(true);
			result.setMessage("处理成功!");
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage("处理失败!");
		}
		return result;
	}

}
