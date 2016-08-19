package com.centaline.trans.mortgage.web;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.aist.uam.auth.remote.UamSessionService;
import com.aist.uam.auth.remote.vo.SessionUser;
import com.aist.uam.userorg.remote.UamUserOrgService;
import com.aist.uam.userorg.remote.vo.Org;
import com.centaline.trans.common.enums.DepTypeEnum;
import com.centaline.trans.common.enums.TransJobs;

@Controller
@RequestMapping(value = "mortgage")
public class MortgageLostController {
	@Autowired(required = true)
	UamSessionService uamSessionService;
	@Autowired
	private UamUserOrgService uamUserOrgService;

	@RequestMapping(value = "/mortgageApproveLost")
	public String personBonus(HttpServletRequest request) {
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
		request.setAttribute("queryOrgs", reBuffer.toString());//org_id至jsp、js分割-->数组
		request.setAttribute("queryOrgFlag", queryOrgFlag);//判断是否是交易顾问 即判断是否有上下级组织
		request.setAttribute("isAdminFlag", isAdminFlag);		
		request.setAttribute("serviceDepId", user.getServiceDepId());//登录用户的org_id
		
		//默认显示上周一至周日的时间
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();	
		/*获取当前月份的上月第一天和最后一天
		c1.add(Calendar.MONTH, -1);
		c1.set(Calendar.DAY_OF_MONTH,1);
		c2.set(Calendar.DAY_OF_MONTH,0);*/
		/*获取当前月份的第一天和最后一天*/
		c1.add(Calendar.MONTH, 0);
		c1.set(Calendar.DAY_OF_MONTH,1);//
		c2.set(Calendar.DAY_OF_MONTH, c2.getActualMaximum(Calendar.DAY_OF_MONTH));  
		String start = new SimpleDateFormat("yyyy-MM-dd").format(c1.getTime());//last Monday
		String end = new SimpleDateFormat("yyyy-MM-dd").format(c2.getTime());//last Sunday
		request.setAttribute("startTime", start);
		request.setAttribute("endTime", end);
		
				
		return "mortgage/mortgageApproveLost2";
	}
}
