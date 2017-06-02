package com.centaline.trans.award.web;


import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aist.common.web.validate.AjaxResponse;
import com.aist.uam.auth.remote.UamSessionService;
import com.aist.uam.auth.remote.vo.SessionUser;
import com.centaline.trans.award.service.KpiSrvCaseService;

@Controller
@RequestMapping(value = "/award")
public class KpiSyncSatisController {
	
	  @Autowired
	  KpiSrvCaseService kpiSrvCaseService;
	
	  @Autowired
	  UamSessionService uamSessionService;
	
	  //页面
	  @RequestMapping("/satisSyncList")
	  public String list(Model model){
		SessionUser user = uamSessionService.getSessionUser();
		model.addAttribute("sessionUserId", user.getId());
		model.addAttribute("serviceJobCode", user.getServiceJobCode());
		//默认当月
		Date now = new Date();
		String belongMonth =new SimpleDateFormat("yyyy-MM").format(now);
	    model.addAttribute("belongMonth", belongMonth);  
		
	    return "award/managerSatisfied";
	  }
	  
	  @RequestMapping("/syncSatisListToKpi")
	  @ResponseBody
	  public AjaxResponse<String> syncSatisListToKpi(){
		  AjaxResponse<String> response = new AjaxResponse<String>();
		  try {
			kpiSrvCaseService.callKpiSyncSatis();
			response.setSuccess(true);
		  } catch (Exception e) {
			e.printStackTrace();
			response.setSuccess(false);
			response.setMessage(e.getMessage());
		  }
		  
		  return response;
	  }

}
