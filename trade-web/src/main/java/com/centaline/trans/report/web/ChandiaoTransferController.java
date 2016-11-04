package com.centaline.trans.report.web;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.aist.uam.auth.remote.UamSessionService;
import com.aist.uam.auth.remote.vo.SessionUser;
import com.aist.uam.basedata.remote.UamBasedataService;
import com.aist.uam.userorg.remote.UamUserOrgService;
import com.aist.uam.userorg.remote.vo.Org;
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
public class ChandiaoTransferController {

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
	@RequestMapping(value="chandiaoTransferList")
	public String chandiaoTransferList(Model model, ServletRequest request){
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
		
		//默认显示上周数据
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();
		int dayOfWeek=c1.get(Calendar.DAY_OF_WEEK)-1;
		c1.add(Calendar.DATE, -dayOfWeek-6);
		c2.add(Calendar.DATE, -dayOfWeek);
		String prCompleteTimeStart = new SimpleDateFormat("yyyy-MM-dd").format(c1.getTime());//last Monday
		String prCompleteTimeEnd = new SimpleDateFormat("yyyy-MM-dd").format(c2.getTime());//last Sunday
				
		request.setAttribute("prCompleteTimeStart", prCompleteTimeStart);
		request.setAttribute("prCompleteTimeEnd", prCompleteTimeEnd);

		
		request.setAttribute("queryOrgs", reBuffer.toString());

		request.setAttribute("queryOrgFlag", queryOrgFlag);
		request.setAttribute("isAdminFlag", isAdminFlag);
		return "report/chandiao_transfer_count";
	}
	
	/**
	 * 跳转到产调详情页面
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value="chandiaoDetail")
	public String chandiaoDetail (HttpServletRequest request){
		
		String  flag = request.getParameter("flag");
		String  prCompleteTimeStart = request.getParameter("prCompleteTimeStart");
		String  prCompleteTimeEnd = request.getParameter("prCompleteTimeEnd");
		
		//默认显示上周数据
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();
		int dayOfWeek=c1.get(Calendar.DAY_OF_WEEK)-1;
		c1.add(Calendar.DATE, -dayOfWeek-6);
		c2.add(Calendar.DATE, -dayOfWeek);
		if(flag==null){
			if(StringUtils.isEmpty(prCompleteTimeStart)){
				prCompleteTimeStart = new SimpleDateFormat("yyyy-MM-dd").format(c1.getTime());//last Monday
			}
			if(StringUtils.isEmpty(prCompleteTimeEnd)){
				prCompleteTimeEnd = new SimpleDateFormat("yyyy-MM-dd").format(c2.getTime());//last Sunday
			}
		}
		
		String  organId = request.getParameter("organId");
		String  prApplyTime = request.getParameter("dtBegin");
		String  prApplyTimeEnd = request.getParameter("dtEnd");
		String  prAccpetTimeStart = request.getParameter("prAccpetTimeStart");
		String  prAccpetTimeEnd = request.getParameter("prAccpetTimeEnd");
		String  teamCode = request.getParameter("teamCode");
		String  yuCuiOriGrpId = request.getParameter("yuCuiOriGrpId");
		
		
		
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
		
		request.setAttribute("organId", organId);
		request.setAttribute("prApplyTime", prApplyTime);
		request.setAttribute("prApplyTimeEnd", prApplyTimeEnd);
		request.setAttribute("prAccpetTimeStart", prAccpetTimeStart);
		request.setAttribute("prAccpetTimeEnd", prAccpetTimeEnd);
		request.setAttribute("prCompleteTimeStart", prCompleteTimeStart);
		request.setAttribute("prCompleteTimeEnd", prCompleteTimeEnd);
		request.setAttribute("teamCode", teamCode);
		request.setAttribute("yuCuiOriGrpId", yuCuiOriGrpId);
		
		return "report/chandiao_transfer_detail";
	}
	
	
}
