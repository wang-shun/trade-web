package com.centaline.trans.report.web;

import java.util.List;

import javax.servlet.ServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.aist.uam.auth.remote.UamSessionService;
import com.aist.uam.auth.remote.vo.SessionUser;
import com.aist.uam.basedata.remote.UamBasedataService;
import com.aist.uam.userorg.remote.UamUserOrgService;
import com.aist.uam.userorg.remote.vo.Org;
import com.centaline.trans.common.entity.LampRule;
import com.centaline.trans.common.enums.DepTypeEnum;
import com.centaline.trans.common.enums.TransJobs;
import com.centaline.trans.common.service.LampRuleService;

/**
 * 
 * <p>Project: 过户案件总览</p>
 * <p>Description: </p>
 * <p>Copyright (c) 2016</p>
 * @author zhoujp</a>
 */
@Controller
@RequestMapping(value="/report")
public class RedGreenTaskController {

	@Autowired(required = true)
	UamSessionService uamSessionService;
	@Autowired(required = true)
	UamUserOrgService uamUserOrgService;
	@Autowired(required = true)
	UamBasedataService uamBasedataService;
	@Autowired(required = true)
	LampRuleService	lampRuleService;
	
	
	/**
	 * 页面初始化
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value="redGreenTaskList")
	public String redGreenTaskList(Model model, ServletRequest request){
		//TODO
		SessionUser user = uamSessionService.getSessionUser();
		String userJob=user.getServiceJobCode();
		boolean queryOrgFlag = false;
		boolean isAdminFlag = false;
		List<LampRule> LampRuleList = lampRuleService.queryLampRuleList();
		if(null != LampRuleList){
			for(LampRule lampRule:LampRuleList){
				if(StringUtils.equals(lampRule.getColor(), "0")){request.setAttribute("redDelaytime", lampRule.getDelaytime());}
				if(StringUtils.equals(lampRule.getColor(), "1")){request.setAttribute("yellowDelaytime", lampRule.getDelaytime());}
			}
		}
		
        StringBuffer reBuffer = new StringBuffer();
		if(!userJob.equals(TransJobs.TJYGW.getCode())){
			queryOrgFlag=true;
			String depString = user.getServiceDepHierarchy();
			String userOrgIdString = user.getServiceDepId();
			if(depString.equals(DepTypeEnum.TYCTEAM.getCode())){
				reBuffer.append(userOrgIdString);
			}else if(depString.equals(DepTypeEnum.TYCQY.getCode())){
				List<Org> orgList = uamUserOrgService.getOrgByDepHierarchy(userOrgIdString, DepTypeEnum.TYCTEAM.getCode());
				for(Org org:orgList){
					reBuffer.append(org.getId());
					reBuffer.append(",");
				}
				reBuffer.deleteCharAt(reBuffer.length()-1);
				
			}else{
				isAdminFlag=true;
			}
		}
		request.setAttribute("queryOrgs", reBuffer.toString());

		request.setAttribute("queryOrgFlag", queryOrgFlag);
		request.setAttribute("isAdminFlag", isAdminFlag);
		return "report/redgreen_task_count";
	}
	
	/**
	 * 跳转到产调详情页面
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value="redgreenTaskDetail")
	public String redgreenTaskDetail(Model model, ServletRequest request){
		
		String organId = request.getParameter("organId");
		String redId = request.getParameter("redID");
		SessionUser user = uamSessionService.getSessionUser();
		String userJob=user.getServiceJobCode();
		boolean queryOrgFlag = false;
		boolean isAdminFlag = false;
		List<LampRule> LampRuleList = lampRuleService.queryLampRuleList();
		if(null != LampRuleList){
			for(LampRule lampRule:LampRuleList){
				if(StringUtils.equals(lampRule.getColor(), "0")){request.setAttribute("redDelaytime", lampRule.getDelaytime());}
				if(StringUtils.equals(lampRule.getColor(), "1")){request.setAttribute("yellowDelaytime", lampRule.getDelaytime());}
			}
		}

        StringBuffer reBuffer = new StringBuffer();
		if(!userJob.equals(TransJobs.TJYGW.getCode())){
			queryOrgFlag=true;
			String depString = user.getServiceDepHierarchy();
			String userOrgIdString = user.getServiceDepId();
			if(depString.equals(DepTypeEnum.TYCTEAM.getCode())){
				reBuffer.append(userOrgIdString);
			}else if(depString.equals(DepTypeEnum.TYCQY.getCode())){
				List<Org> orgList = uamUserOrgService.getOrgByDepHierarchy(userOrgIdString, DepTypeEnum.TYCTEAM.getCode());
				for(Org org:orgList){
					reBuffer.append(org.getId());
					reBuffer.append(",");
				}
				reBuffer.deleteCharAt(reBuffer.length()-1);
				
			}else{
				isAdminFlag=true;
			}
		}
		request.setAttribute("queryOrgs", reBuffer.toString());

		request.setAttribute("queryOrgFlag", queryOrgFlag);
		request.setAttribute("isAdminFlag", isAdminFlag);
		
		request.setAttribute("organId", organId);
		return "report/redgreen_task_detail";
	}
	/**
	 * 跳转到产调详情页面(根据颜色区分)
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value="redgreenTaskDetailColour")
	public String redgreenTaskDetailColour(Model model, ServletRequest request){
		
		String organId = request.getParameter("organId");
		String colourId = request.getParameter("colourId");
		String orgName1 = request.getParameter("orgName1");
		String orgName2 = request.getParameter("orgName2");
		
		SessionUser user = uamSessionService.getSessionUser();
		String userJob=user.getServiceJobCode();
		boolean queryOrgFlag = false;
		boolean isAdminFlag = false;
		List<LampRule> LampRuleList = lampRuleService.queryLampRuleList();
		if(null != LampRuleList){
			for(LampRule lampRule:LampRuleList){
				if(StringUtils.equals(lampRule.getColor(), "0")){request.setAttribute("redDelaytime", lampRule.getDelaytime());}
				if(StringUtils.equals(lampRule.getColor(), "1")){request.setAttribute("yellowDelaytime", lampRule.getDelaytime());}
			}
		}
		
		StringBuffer reBuffer = new StringBuffer();
		if(!userJob.equals(TransJobs.TJYGW.getCode())){
			queryOrgFlag=true;
			String depString = user.getServiceDepHierarchy();
			String userOrgIdString = user.getServiceDepId();
			if(depString.equals(DepTypeEnum.TYCTEAM.getCode())){
				reBuffer.append(userOrgIdString);
			}else if(depString.equals(DepTypeEnum.TYCQY.getCode())){
				List<Org> orgList = uamUserOrgService.getOrgByDepHierarchy(userOrgIdString, DepTypeEnum.TYCTEAM.getCode());
				for(Org org:orgList){
					reBuffer.append(org.getId());
					reBuffer.append(",");
				}
				reBuffer.deleteCharAt(reBuffer.length()-1);
				
			}else{
				isAdminFlag=true;
			}
		}
		request.setAttribute("queryOrgs", reBuffer.toString());
		
		request.setAttribute("queryOrgFlag", queryOrgFlag);
		request.setAttribute("isAdminFlag", isAdminFlag);
		
		request.setAttribute("organId", organId);
		request.setAttribute("colourId", colourId);
		request.setAttribute("orgName1", orgName1);
		request.setAttribute("orgName2", orgName2);
		return "report/redgreen_task_detail";
	}
	
	
}