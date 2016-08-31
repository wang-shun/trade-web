package com.centaline.trans.mortgage.web;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.aist.uam.auth.remote.UamSessionService;
import com.aist.uam.auth.remote.vo.SessionUser;
import com.aist.uam.userorg.remote.UamUserOrgService;
import com.aist.uam.userorg.remote.vo.Org;
import com.centaline.trans.common.enums.DepTypeEnum;
import com.centaline.trans.common.enums.TransJobs;
import com.centaline.trans.mgr.entity.TsFinOrg;
import com.centaline.trans.mgr.service.TsFinOrgService;
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
	
	@Autowired(required = true)
	TsFinOrgService tsFinOrgService;

	
	
	@RequestMapping(value = "/list")
	public String list(ServletRequest request) {
		SessionUser user = uamSessionService.getSessionUser();
		String userJob=user.getServiceJobCode();
		boolean queryOrgFlag = false;
		boolean isAdminFlag = false;

        StringBuffer reBuffer = new StringBuffer();
        //如果不是交易顾问
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
		request.setAttribute("serviceDepId", user.getServiceDepId());//登录用户的org_id
		
		
		//银行信息
		List<TsFinOrg> tsFinOrgList= tsFinOrgService.findAllFinOrg();
		List<Map<String, String>> FinOrgNameList=new ArrayList<Map<String,String>>();

		if(tsFinOrgList.size()>0){
			for(int i=0;i<tsFinOrgList.size();i++){
				Map<String,String> FinOrgNameMap=new HashMap<String,String>();
				if(null !=tsFinOrgList.get(i).getFinOrgName() && !"".equals(tsFinOrgList.get(i).getFinOrgName())){
					FinOrgNameMap.put("FinOrgName", tsFinOrgList.get(i).getFinOrgName());					
					FinOrgNameMap.put("FinOrgCode", tsFinOrgList.get(i).getFinOrgCode());
					FinOrgNameMap.put("FinOrgNameYc", tsFinOrgList.get(i).getFinOrgNameYc());
					FinOrgNameMap.put("FinOrgCodeYc", tsFinOrgList.get(i).getFaFinOrgCode());
				}
				FinOrgNameList.add(FinOrgNameMap);
			}
		}
		request.setAttribute("FinOrgNameList", FinOrgNameList);
		
		
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
		
		return "mortgage/mortgageInfoList2";		
	}
}
