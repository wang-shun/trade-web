package com.centaline.parportal.mobile.tradecase;

import java.text.MessageFormat;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.aist.common.quickQuery.bo.JQGridParam;
import com.aist.common.quickQuery.service.QuickGridService;
import com.aist.uam.auth.remote.UamSessionService;
import com.aist.uam.auth.remote.vo.SessionUser;
import com.alibaba.fastjson.JSONObject;

@RestController
@RequestMapping(value = "/tradeCase")
public class TradeCaseController {

	@Value("${agent.img.url}")
	private String imgUrl;
	
	@Autowired
	private UamSessionService sessionService;

	@Autowired
	private QuickGridService quickGridService;
	
	@RequestMapping(value = "list")
	@ResponseBody
	public Page<Map<String, Object>> tradeCaseList(@RequestParam(required = true) Integer page,
			@RequestParam(required = true) Integer pageSize, Integer property, Integer status, Boolean onlyFocus,
			Integer onlyLoanLostAlert, String q_text) {
		SessionUser user = sessionService.getSessionUser();
		JQGridParam gp = new JQGridParam();
		gp.setQueryId("queryTradeCastListMoblie");
		gp.setPage(page);
		gp.setRows(pageSize);
		
		Map<String,Object> paramMap = gp.getParamtMap();
		paramMap.put("q_text", q_text);
		paramMap.put("onlyFocus", onlyFocus);
		
		paramMap.put("status", status);
		paramMap.put("property", property);
		
		Page<Map<String, Object>> pages = quickGridService.findPageForSqlServer(gp, user);
		List<Map<String, Object>> list = pages.getContent();
		buildZhongjieInfo(list);
		
		
		
		return pages;
	}
	
	private void buildZhongjieInfo(List<Map<String, Object>> list){
		for (Map<String, Object> map : list) {
    		Object agentNameObj = map.get("AGENT_NAME");
			String name = null == agentNameObj ? "" : String.valueOf(agentNameObj);
			Object agentCodeObj = map.get("employee_code");
			String avatar = null == agentCodeObj ? "" : MessageFormat.format(imgUrl, String.valueOf(agentCodeObj)) + ".jpg";
			Object agentPhoneObj = map.get("AGENT_PHONE");
			String agentPhone = agentPhoneObj == null ? "" : String.valueOf(agentPhoneObj);
			
			JSONObject json = new JSONObject();
			json.put("name", name);
			json.put("avatar", avatar);
			json.put("mobile", agentPhone);
			
			map.put("zhongjie", json);
		}
	}
}
