package com.centaline.trans.eloan.web;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aist.common.web.validate.AjaxResponse;
import com.aist.uam.auth.remote.UamSessionService;
import com.aist.uam.auth.remote.vo.SessionUser;
import com.aist.uam.userorg.remote.UamUserOrgService;
import com.aist.uam.userorg.remote.vo.Org;
import com.centaline.trans.common.enums.DepTypeEnum;
import com.centaline.trans.common.service.OrgService;
import com.centaline.trans.common.vo.OrgVO;
import com.centaline.trans.loan.entity.LoanAgent;
import com.centaline.trans.loan.service.LoanAgentService;
import com.centaline.trans.mgr.Consts;
import com.centaline.trans.task.entity.ToPropertyResearch;

@Controller
@RequestMapping(value="/eloan")
public class WarnListController {
	
	
	@Autowired(required = true)
	UamSessionService uamSessionService;
	
	@Autowired(required = true)
	UamUserOrgService uamUserOrgService;
	
	@Autowired
	LoanAgentService loanAgentService;
	
	@Autowired
	OrgService orgService;
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

		List<Org> districtOrgList = uamUserOrgService.getOrgByDepHierarchy(Consts.YU_SH_ORG_ROOT, Consts.YU_DISTRICT);
		request.setAttribute("districtOrgList", districtOrgList);
		
		return "eloan/warnList";
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
