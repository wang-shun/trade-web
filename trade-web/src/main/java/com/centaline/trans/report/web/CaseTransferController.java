package com.centaline.trans.report.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.aist.uam.auth.remote.UamSessionService;
import com.aist.uam.auth.remote.vo.SessionUser;
import com.aist.uam.basedata.remote.UamBasedataService;
import com.aist.uam.userorg.remote.UamUserOrgService;
import com.aist.uam.userorg.remote.vo.Org;
import com.aist.uam.userorg.remote.vo.User;
import com.centaline.trans.common.enums.DepTypeEnum;
import com.centaline.trans.common.enums.TransJobs;

/**
 * 
 * <p>Project: 过户案件总览</p>
 * <p>Description: </p>
 * <p>Copyright (c) 2016</p>
 * @author zhoujp</a>
 */
@Controller
@RequestMapping(value="/report")
public class CaseTransferController {

	@Autowired(required = true)
	UamSessionService uamSessionService;
	@Autowired(required = true)
	UamUserOrgService uamUserOrgService;
	@Autowired(required = true)
	UamBasedataService uamBasedataService;
	
	
	/**
	 * 页面初始化
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value="caseTransferList")
	public String caseTransferList(Model model, ServletRequest request){
		//TODO
		SessionUser user = uamSessionService.getSessionUser();
		String userJob=user.getServiceJobCode();
		boolean queryOrgFlag = false;
		boolean isAdminFlag = false;

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
		//页面获取 组织结构显示用 服务部门ID
		request.setAttribute("serviceDepId",user.getServiceDepId());
		
		
		
		//页面获取 组织结构显示用
		String depId = user.getServiceDepId(); // 用户的部门
		String userId = null; // 交易顾问id
		String tempUser = null; // 交易主管下用户id
		String tempName = null; // 交易主管下用户姓名
		boolean isConsultant=false; //是否为交易顾问
		String personalId = user.getId();
		
/*		 验证当前用户所属组织和url传来的值是否一致 
		if (TransJobs.TZJL.getCode().equals(user.getServiceJobCode())) {// 总经理
			if (arg != null && !"".equals(arg)) {
				org = arg;
			}
			List<Org> orgList = uamUserOrgService.getOrgByParentId(depId);
			List<String> disOrgs = new ArrayList<String>(); // 贵宾服务组集合
			List<String> orgs = new ArrayList<String>(); // 组织集合
			if (orgList != null && !orgList.isEmpty()) {
				for (Org o : orgList) {
					disOrgs.add(o.getId());
					List<Org> subOrgs = uamUserOrgService.getOrgByParentId(o
							.getId());
					if (subOrgs != null && !subOrgs.isEmpty()) {
						for (Org oo : subOrgs) {
							orgs.add(oo.getId());
						}
					}
				}
			}
			if (!depId.equals(org) && !disOrgs.contains(org)
					&& !orgs.contains(org)) {
				throw new RuntimeException("不好意思,发生错误,组织ID与当前用户的不符合!!!");
			}
		} else if (TransJobs.TZJ.getCode().equals(user.getServiceJobCode())) {// 誉萃总监
			if (arg != null && !"".equals(arg)) {
				org = arg;
			}
			List<Org> orgList = uamUserOrgService.getOrgByParentId(depId); // 组织集合
			List<String> orgs = new ArrayList<String>();
			if (orgList != null && !orgList.isEmpty()) {
				for (Org o : orgList) {
					orgs.add(o.getId());
				}
			}
			if (!depId.equals(org) && !orgs.contains(org)) {
				throw new RuntimeException("不好意思,发生错误,组织ID与当前用户的不符合!!!");
			}
		} else if (TransJobs.TSJYZG.getCode().equals(user.getServiceJobCode())
				|| TransJobs.TJYZG.getCode().equals(user.getServiceJobCode())) {// 交易主管
			if (!depId.equals(org)) {
				throw new RuntimeException("不好意思,发生错误,组织ID与当前用户的不符合!!!");
			}
			if (arg != null && !"".equals(arg)) {
				tempUser = arg;
				tempName = uamUserOrgService.getUserById(tempUser).getRealName();
				List<String> uList = new ArrayList<String>();
				List<User> userList = uamUserOrgService
						.getUserByOrgIdAndJobCode(user.getServiceDepId(),
								TransJobs.TJYGW.getCode());
				if (null != userList && !userList.isEmpty()) {
					for (User u : userList) {
						uList.add(u.getId());
					}
				}
				if (!uList.contains(tempUser)) {
					throw new RuntimeException("不好意思,发生错误,此交易主管下无该交易顾问!!!");
				}
			}
		} else { // 交易顾问
			userId = user.getId();
			isConsultant = true;
			if (!depId.equals(org)) {
				throw new RuntimeException("不好意思,发生错误,组织ID与当前用户的不符合!!!");
			}
		}

		String orgName = uamUserOrgService.getOrgById(org).getOrgName(); // 获取组织名
*/
	
		
		request.setAttribute("depId", depId);		
		request.setAttribute("userId", userId);
		request.setAttribute("tempUser", tempUser);
		request.setAttribute("tempName", tempName);
		request.setAttribute("isConsultant", isConsultant);
		request.setAttribute("personalId", personalId);

		return "report/case_transfer";
	}
	
}
