package com.centaline.trans.workspace.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aist.common.quickQuery.bo.JQGridParam;
import com.aist.common.quickQuery.service.QuerysParseService;
import com.aist.common.quickQuery.service.QuickGridService;
import com.aist.uam.userorg.remote.UamUserOrgService;
import com.aist.uam.userorg.remote.vo.Org;
import com.centaline.trans.common.enums.LightColorEnum;
import com.centaline.trans.task.service.impl.TsTaskDelegateServiceImpl;
import com.centaline.trans.workspace.entity.WorkSpace;
import com.centaline.trans.workspace.service.WorkSpaceService;

@Controller
@RequestMapping("/mobile/dashboard/box")
public class DashboardController {
	@Autowired
	private WorkSpaceService workSpaceService;
	@Autowired
	private UamUserOrgService uamUserOrgService;
	
	@Resource(name = "quickGridService")
	private QuickGridService  quickGridService;
	
    @Autowired
    private QuerysParseService  querysParseService;
	
	@RequestMapping("showRLightList")
	public String showRLightList(String orgId,Model model) {
		model.addAttribute("orgId", orgId);
		return "mobile/workspace/showRLightList";
	}
	@RequestMapping("showRank")
	public String showRank(String orgId,Model model) {
		model.addAttribute("orgId", orgId);
		return "mobile/workspace/showRank";
	}	
	@RequestMapping("rLightList")
	@ResponseBody
	public Map rLightList(String orgId) {
		Map<String, Object>resutlMap=new HashMap<>();
		resutlMap.put("redLight", workSpaceService.rLightList(orgId));
		return resutlMap;
	}
	
	@RequestMapping(value = "queryRyLightList")
    @ResponseBody
    public Page<Map<String, Object>> queryRyLightList(HttpServletRequest request, HttpServletResponse response,
                              String orgId) {
        JQGridParam gp = new JQGridParam();
        gp.setQueryId("queryRyLightList");
        gp.setPagination(true);
        gp.setPage(1);
        gp.setRows(80);
        
        Map<String, String[]> searchParams = new HashMap<>();
        Map<String, String[]> arguParams = new HashMap<>();

        searchParams.put("color", new String[] { "0" });
        arguParams.put("parentOrgId", new String[] { orgId });

        querysParseService.reloadFile();
        Page<Map<String, Object>> returnPage = quickGridService.findPageForSqlServerByRowNumber(gp,searchParams, arguParams, "16555CFCBCEB4D588AFAAC0673C5CF4B");

        return returnPage;
    }
	
	@RequestMapping(value = "getNewAgent")
	@ResponseBody
	public Map getNewAgent(String orgId) {
		Map<String, Object>resutlMap=new HashMap<>();
		resutlMap.put("newLoanAgent",workSpaceService.listNewLoanAgent(orgId));
		return resutlMap;
	}
	

	@RequestMapping(value = "firstPage")
	@ResponseBody
	public Map firstPage(String orgId) {
		WorkSpace wk= new WorkSpace();
		List<Org> orgList = uamUserOrgService.getOrgByParentId(orgId);
		wk.setOrgs(orgListToListStr(orgList));
		wk.setColor(Integer.parseInt(LightColorEnum.RED.getCode()));
		int redLight = workSpaceService.countLight(wk);
		wk.setColor(Integer.parseInt(LightColorEnum.YELLOW.getCode()));
		int yeLight = workSpaceService.countLight(wk);
		
		Map<String, Object>resutlMap=new HashMap<>();
		resutlMap.put("redLight", redLight);
		resutlMap.put("yeLight", yeLight);
		resutlMap.put("lightList",workSpaceService.getLightListGroupByOrg(orgId));
		return resutlMap;
	}
	@RequestMapping(value = "select")
	public String select(Model model){
		Org org=uamUserOrgService.getOrgByCode(TsTaskDelegateServiceImpl.YC_ORG_CODE);
		List<Org>orgs=uamUserOrgService.getOrgByParentId(org.getId());
		model.addAttribute("orgs", orgs);
		return "mobile/workspace/select";
	}
	@RequestMapping(value = "startPage")
	public String startPage(Model model,String orgId){
		model.addAttribute("orgId", orgId);
		return "mobile/workspace/startPage";
	}
	@RequestMapping(value = "getRank")
	public Map getRank() {
		
		Map<String, Object>resutlMap=new HashMap<>();
		WorkSpace wk=new WorkSpace();
		wk.setRankType("Manager");
		wk.setRankCat("loan_rate");
		wk.setSize(6);
		wk.setRankDuration("MONTH");
		resutlMap.put("rateRankM", workSpaceService.topRankList1(wk));
		wk.setRankDuration("WEEK");
		resutlMap.put("rateRankW", workSpaceService.topRankList1(wk));
		wk.setRankType("consultant");
		wk.setRankCat("loan_amount");
		resutlMap.put("amountRankW", workSpaceService.topRankList1(wk));
		wk.setRankDuration("MONTH");
		resutlMap.put("amountRankM", workSpaceService.topRankList1(wk));
		return resutlMap;
	}
	
	@RequestMapping("workload")
	public String workload(String orgId,Model model) {
		model.addAttribute("orgId", orgId);
		Org org=uamUserOrgService.getOrgById(orgId);
		model.addAttribute("org", org);
		return "mobile/workspace/workload";
	}
	@RequestMapping(value = "workloadData")
	public Map workloadData(String orgId) {
		Map<String, Object>resutlMap=new HashMap<>();
		resutlMap.put("staUnallocateRank", workSpaceService.staUnallocateRank(orgId));
		resutlMap.put("staSignRank", workSpaceService.staSignRank(orgId));
		resutlMap.put("staHouseTranRank", workSpaceService.staHouseTranRank(orgId));
		resutlMap.put("staCpfLoanRank", workSpaceService.staCpfLoanRank(orgId));
		resutlMap.put("staBusinessOrComLoanRank", workSpaceService.staBusinessOrComLoanRank(orgId));
		return resutlMap;
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
}
