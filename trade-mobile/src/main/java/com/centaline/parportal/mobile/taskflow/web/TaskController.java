package com.centaline.parportal.mobile.taskflow.web;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import com.aist.common.quickQuery.bo.JQGridParam;
import com.aist.common.quickQuery.service.QuickGridService;
import com.aist.uam.auth.remote.UamSessionService;
import com.aist.uam.auth.remote.vo.SessionUser;
import com.aist.uam.userorg.remote.UamUserOrgService;
import com.aist.uam.userorg.remote.vo.User;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.centaline.trans.common.enums.TransJobs;
import com.centaline.trans.utils.Pages2JSONMoblie;

@RestController
@RequestMapping(value = "/task")
public class TaskController {

	@Value("${agent.img.url}")
	private String imgUrl;
	
	@Autowired
	private UamSessionService sessionService;

	@Autowired
	private QuickGridService quickGridService;
	
    @Resource
    private UamUserOrgService uamUserOrgService;

	@RequestMapping(value = "list")
	@ResponseBody
	public JSONObject list(@RequestParam(required = true) Integer page, 
			@RequestParam(required = true) Integer pageSize,
			String q_text,@RequestParam(required = true)Boolean pastTask,
			@RequestParam(required = true)Boolean todayTask,
			@RequestParam(required = true)Boolean tmrTask) {
		JQGridParam gp = new JQGridParam();
		gp.setQueryId("queryTaskListItemListMobile");
		gp.setPage(page);
		gp.setRows(pageSize);
		Map<String, Object> paramMap = gp.getParamtMap();
		paramMap.put("q_text", q_text);
		List<String> taskTag = new ArrayList<String>();
		if(pastTask) {
			taskTag.add("-1");
		}
		if(todayTask) {
			taskTag.add("0");
		}
		if(tmrTask) {
			taskTag.add("1");
		}
		if(!taskTag.isEmpty()) {
			paramMap.put("taskTag",(String[])taskTag.toArray(new String[taskTag.size()]));
		}
		SessionUser user = sessionService.getSessionUser();
		Page<Map<String, Object>> pages = quickGridService.findPageForSqlServer(gp, user);
		buildZhongjieInfo(pages.getContent());
		buildZhuliInfo(pages.getContent());
		buildHoutaiInfo(pages.getContent());
		return Pages2JSONMoblie.pages2JsonMoblie(pages);
	}

	@RequestMapping(value = "quantity")
	@ResponseBody
	public Object taskQuantity(String q_text){
		JSONObject jsonObject = new JSONObject();
		JQGridParam gp = new JQGridParam();
		gp.setQueryId("queryTaskListItemListMobile");
		gp.setPagination(false);
		gp.setCountOnly(true);
		Map<String, Object> paramMap = gp.getParamtMap();
		paramMap.put("q_text", q_text);
		List<String> taskTag = new ArrayList<String>();

		SessionUser user = sessionService.getSessionUser();

		taskTag.add("-1");
		paramMap.put("taskTag", (String[]) taskTag.toArray(new String[taskTag.size()]));
		Page<Map<String, Object>> pages = quickGridService.findPageForSqlServer(gp, user);
		jsonObject.put("pastTaskCount",pages.getTotalElements());

		taskTag.clear();
		taskTag.add("0");
		paramMap.put("taskTag", (String[]) taskTag.toArray(new String[taskTag.size()]));
		pages = quickGridService.findPageForSqlServer(gp, user);
		jsonObject.put("todayTaskCount", pages.getTotalElements());

		taskTag.clear();
		taskTag.add("1");
		paramMap.put("taskTag", (String[]) taskTag.toArray(new String[taskTag.size()]));
		pages = quickGridService.findPageForSqlServer(gp, user);
		jsonObject.put("tmrTaskTaskCount", pages.getTotalElements());

		paramMap.remove("taskTag");
		pages = quickGridService.findPageForSqlServer(gp, user);
		jsonObject.put("allTaskCount", pages.getTotalElements());

		return jsonObject;
	}



	private void buildHoutaiInfo(List<Map<String, Object>> list) {
		if(CollectionUtils.isEmpty(list)) {
			return ;
		}
		
		for (Map<String, Object> map : list) {
			JSONArray ja = (JSONArray) map.get("houtai");
			if(ja == null) {
				continue;
			}
			Iterator<Object> it = ja.iterator();
			List<Object> nameList = new ArrayList<Object>();
			while (it.hasNext()) {
				JSONObject jo = (JSONObject) it.next();
				nameList.add(jo.get("name"));
			}
			if(!CollectionUtils.isEmpty(nameList) ) {
				map.put("houtai", nameList);
			}
		}
	}
	private void buildZhuliInfo(List<Map<String, Object>> list) {
		if(CollectionUtils.isEmpty(list)) {
			return ;
		}
		
		for (Map<String, Object> map : list) {
			// 助理
			String orgId = String.valueOf(map.get("orgId"));
			String zhuli = "";
			List<User> asList = uamUserOrgService.getUserByOrgIdAndJobCode(orgId, TransJobs.TJYZL.getCode());
			if (CollectionUtils.isNotEmpty(asList)) {
				User user = asList.get(0);
				zhuli = user.getRealName();
			}
			map.put("zhuli", zhuli);
		}
	}
	
	private void buildZhongjieInfo(List<Map<String, Object>> list) {
		for (Map<String, Object> map : list) {
			
			Object agentNameObj = map.get("AGENT_NAME");
			String name = null == agentNameObj ? "" : String.valueOf(agentNameObj);
			Object agentCodeObj = map.get("EMPLOYEE_CODE");
			String avatar = null == agentCodeObj ? ""
					: MessageFormat.format(imgUrl, String.valueOf(agentCodeObj)) + ".jpg";
			Object agentPhoneObj = map.get("AGENT_PHONE");
			String agentPhone = agentPhoneObj == null ? "" : String.valueOf(agentPhoneObj);
			
			
			JSONObject json = new JSONObject();
			json.put("name", name);
			json.put("avatar", avatar);
			json.put("mobile", agentPhone);
			if(map.containsKey("org")){
				json.put("org",map.get("org"));
			}
			
			map.put("zhongjie", json);
			map.remove("AGENT_NAME");
			map.remove("EMPLOYEE_CODE");
			map.remove("AGENT_PHONE");
		}
	}

}
