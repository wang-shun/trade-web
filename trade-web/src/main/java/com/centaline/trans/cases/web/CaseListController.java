package com.centaline.trans.cases.web;

import java.security.Security;
import java.util.List;

import javax.servlet.ServletRequest;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.aist.uam.auth.remote.UamSessionService;
import com.aist.uam.auth.remote.vo.SessionUser;
import com.aist.uam.basedata.remote.UamBasedataService;
import com.aist.uam.userorg.remote.UamUserOrgService;
import com.aist.uam.userorg.remote.vo.Org;
import com.centaline.trans.cases.service.MyCaseListService;
import com.centaline.trans.common.enums.DepTypeEnum;
import com.centaline.trans.common.enums.TransJobs;

/**
 * 
 * <p>Project: 案件总览</p>
 * <p>Description: </p>
 * <p>Copyright (c) 2015</p>
 * @author wanggh</a>
 */
@Controller
@RequestMapping(value="/case")
public class CaseListController {

	@Autowired(required = true)
	UamSessionService uamSessionService;
	@Autowired(required = true)
	UamUserOrgService uamUserOrgService;
	@Autowired(required = true)
	UamBasedataService uamBasedataService;
	@Autowired(required = true)
	MyCaseListService myCaseListService;
	
	/**
	 * 页面初始化
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value="myCaseList")
	public String myCaseList(Model model, ServletRequest request){

		SessionUser user = uamSessionService.getSessionUser();
		String userJob=user.getServiceJobCode();
		//to-do 处理下本人、本部搜索范围
		//to-do 处理下本人、本部搜索范围
		boolean isGroupList = SecurityUtils.getSubject().isPermitted("TRADE.CASE.LIST.DEPATMENT");
		boolean queryOrgFlag = false;
		boolean isAdminFlag = false;

        StringBuffer reBuffer = new StringBuffer();
        //如果登录用户不是交易顾问
        //TODO 如果非交易顾问(过户/贷款权证),查组织内案件
		if(isGroupList){
			queryOrgFlag=true;
			String depString = user.getServiceDepHierarchy();
			String userOrgIdString = user.getServiceDepId();
			//组别
			if(depString.equals(DepTypeEnum.TYCTEAM.getCode())){
				reBuffer.append(userOrgIdString);
			//区域
			}else if(depString.equals(DepTypeEnum.TYCQY.getCode())){
				List<Org> orgList = uamUserOrgService.getOrgByDepHierarchy(userOrgIdString, DepTypeEnum.TYCTEAM.getCode());
				for(Org org:orgList){
					reBuffer.append(org.getId());
					reBuffer.append(",");
				}
				reBuffer.deleteCharAt(reBuffer.length()-1);
				
			}else{
				isAdminFlag=true;//总部flag
			}
		}
		request.setAttribute("queryOrgs", reBuffer.toString());

		request.setAttribute("queryOrgFlag", queryOrgFlag);
		request.setAttribute("isAdminFlag", isAdminFlag);
		request.setAttribute("userId", user.getId());
		request.setAttribute("serviceDepId", user.getServiceDepId());//登录用户的org_id
		request.setAttribute("serviceDepName", user.getServiceDepName());
		return "case/mycase_list_new";
	}
	
	
	@RequestMapping(value="caseForChange")
	public String caseForChange(Model model, ServletRequest request){

		return "case/caseForChange";
	}		

}
