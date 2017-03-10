package com.centaline.parportal.mobile.tradecase.web;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.aist.common.quickQuery.bo.JQGridParam;
import com.aist.uam.auth.remote.UamSessionService;

@RestController
@RequestMapping(value = "/task")
public class TaskController {

	@Autowired
	private UamSessionService uamSessionService;

	@RequestMapping(value = "list")
	@ResponseBody
	public String list(@RequestParam(required = true) Integer page, 
			@RequestParam(required = true) Integer pageSize,
			String q_text,@RequestParam(required = true)Boolean pastTask,
			@RequestParam(required = true)Boolean todayTask,
			@RequestParam(required = true)Boolean tmrTask) {
		JQGridParam gp = new JQGridParam();
		gp.setQueryId("queryTradeCastListMoblie");
		gp.setPage(page);
		gp.setRows(pageSize);
		Map<String, Object> paramMap = gp.getParamtMap();
		paramMap.put("q_text", q_text);
		paramMap.put("pastTask", pastTask);
		paramMap.put("todayTask", todayTask);
		paramMap.put("tmrTask", tmrTask);
		
		
		
		
		return null;
	}
}
