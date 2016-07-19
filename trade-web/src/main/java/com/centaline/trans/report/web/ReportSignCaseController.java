package com.centaline.trans.report.web;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import javax.servlet.ServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.aist.uam.auth.remote.UamSessionService;
import com.aist.uam.auth.remote.vo.SessionUser;
import com.aist.uam.userorg.remote.UamUserOrgService;
import com.aist.uam.userorg.remote.vo.Org;
import com.centaline.trans.common.enums.DepTypeEnum;
import com.centaline.trans.common.enums.TransJobs;

/**
 * 签约案件
 * @author gongjd
 *
 */
@Controller
@RequestMapping("/report")
public class ReportSignCaseController {
	
	@Autowired(required = true)
	UamSessionService uamSessionService;
	@Autowired(required = true)
	UamUserOrgService uamUserOrgService;
	
	@RequestMapping(value = "signCase")
	public String querySignCase(Model model, ServletRequest request){
		
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
		
		//默认显示上周数据
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();
		int dayOfWeek=c1.get(Calendar.DAY_OF_WEEK)-1;
		c1.add(Calendar.DATE, -dayOfWeek-6);
		c2.add(Calendar.DATE, -dayOfWeek);
		String start = new SimpleDateFormat("yyyy-MM-dd").format(c1.getTime());//last Monday
		String end = new SimpleDateFormat("yyyy-MM-dd").format(c2.getTime());//last Sunday
		
		String signTimeStart = request.getParameter("signTimeStart");
		String signTimeEnd = request.getParameter("signTimeEnd");

		if(StringUtils.isEmpty(signTimeStart) && StringUtils.isEmpty(signTimeEnd)){
			signTimeStart = start;
			signTimeEnd = end;
		}

		request.setAttribute("signTimeStart", signTimeStart);
		request.setAttribute("signTimeEnd", signTimeEnd);

		request.setAttribute("queryOrgFlag", queryOrgFlag);
		request.setAttribute("isAdminFlag", isAdminFlag);
		return "report/sign_case";
		
	}
	}
