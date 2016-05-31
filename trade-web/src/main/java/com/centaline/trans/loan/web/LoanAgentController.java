package com.centaline.trans.loan.web;

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
import com.centaline.trans.common.enums.TransJobs;
import com.centaline.trans.loan.entity.LoanAgent;
import com.centaline.trans.loan.entity.LoanStatusChange;
import com.centaline.trans.loan.service.LoanAgentService;
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

	@RequestMapping("submit")
	public String submit() {
		return "/loan/submit";
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
		Job j = uamUserOrgService.getJobByCode(TransJobs.YCPRODUCT.getCode());
		List<User> users = uamUserOrgService.getUserByJobId(j.getId());
		if(SecurityUtils.getSubject().isPermitted("TRADE.LOAN.SUBMIT.BELONG")){
			SessionUser user=uamSessionService.getSessionUser();
			List<User>jygws =uamUserOrgService.getUserByOrgIdAndJobCode(user.getServiceDepId(), TransJobs.TJYGW.getCode());
			model.addAttribute("jygws", jygws);
		}
		model.addAttribute("yuCuiProduct", users);
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
		}
		if(isOnlyRead!=null&&isOnlyRead==1L) {
			return "/loan/detailsOnlyRead";
		} else {
			return "/loan/details";
		}
	
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

}
