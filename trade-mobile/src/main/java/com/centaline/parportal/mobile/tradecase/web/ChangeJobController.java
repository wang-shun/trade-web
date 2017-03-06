package com.centaline.parportal.mobile.tradecase.web;

import java.util.List;

import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.aist.uam.auth.remote.UamSessionService;
import com.aist.uam.auth.remote.vo.SessionUser;
import com.aist.uam.userorg.remote.UamUserOrgService;
import com.aist.uam.userorg.remote.vo.UserOrgJob;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.centaline.parportal.mobile.util.Pages2JSONMoblie;
import com.fasterxml.jackson.annotation.JsonInclude;

@RestController
@RequestMapping(value = "/tradeCase")
@JsonInclude()
public class ChangeJobController {
	
	@Autowired
	private UamSessionService uamSessionService;
	
	@Autowired
	private UamUserOrgService uamUserOrgService;
	
	@RequestMapping(value = "userOrgJobList")
	@ResponseBody
	public String getUserOrgJobList(){
		SessionUser sessionUser = uamSessionService.getSessionUser();
		List<UserOrgJob> uojs = uamUserOrgService
				.getUserOrgJobByUserId(sessionUser.getId());
		
		JSONArray ja = new JSONArray();
		for (UserOrgJob uoj : uojs) {
			JSONObject jo = new JSONObject();
			jo.put("isMain", "1".equals(uoj.getIsmain()) ? true:false);
			jo.put("orgId", uoj.getOrgId());
			jo.put("orgName", uoj.getOrgName());
			jo.put("jobId", uoj.getJobId());
			jo.put("jobName", uoj.getJobName());
			
			ja.put(jo);
		}
		
		return JSON.toJSONString(ja);
	}
}
