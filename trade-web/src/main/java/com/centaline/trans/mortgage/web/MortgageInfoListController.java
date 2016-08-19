package com.centaline.trans.mortgage.web;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import javax.servlet.ServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.aist.uam.auth.remote.UamSessionService;
import com.aist.uam.auth.remote.vo.SessionUser;
import com.aist.uam.userorg.remote.UamUserOrgService;
import com.aist.uam.userorg.remote.vo.Org;
import com.centaline.trans.common.enums.DepTypeEnum;
import com.centaline.trans.common.enums.TransJobs;
import com.centaline.trans.mortgage.service.ToMortgageService;

@Controller
@RequestMapping(value="mortgageInfo")
public class MortgageInfoListController {

	@Autowired
	ToMortgageService toMortgageService;
	
	@Autowired(required = true)
	UamSessionService uamSessionService;
	
	@Autowired(required = true)
	UamUserOrgService uamUserOrgService;
	
	@RequestMapping(value = "/list")
	public String list(ServletRequest request) {
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
		
		//默认显示上月数据
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();
		
		c1.set(Calendar.DAY_OF_MONTH, 1); 
		c1.add(Calendar.MONTH,-1);
		
		c2.set(Calendar.DAY_OF_MONTH, 0); 

		String start = new SimpleDateFormat("yyyy-MM-dd").format(c1.getTime());//last Month start
		String end = new SimpleDateFormat("yyyy-MM-dd").format(c2.getTime());//last Month end
		
		String signTimeStart = request.getParameter("signTimeStart");
		String signTimeEnd = request.getParameter("signTimeEnd");

		if(StringUtils.isEmpty(signTimeStart) && StringUtils.isEmpty(signTimeEnd)){
			signTimeStart = start;
			signTimeEnd = end;
		}

		request.setAttribute("signTimeStart", signTimeStart);
		request.setAttribute("signTimeEnd", signTimeEnd);
		
		return "mortgage/mortgageInfoList";		
	}
}
