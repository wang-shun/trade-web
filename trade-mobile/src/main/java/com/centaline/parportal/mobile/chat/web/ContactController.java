package com.centaline.parportal.mobile.chat.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aist.common.quickQuery.bo.JQGridParam;
import com.aist.common.quickQuery.service.QuickGridService;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

@Controller
@RequestMapping("/chat")
public class ContactController {
	@Resource(name = "quickGridService")
	private QuickGridService quickGridService;

	@ResponseBody
	@RequestMapping("casecontact")
	public JSONObject casecontact(String caseCode) {
		JQGridParam gp = new JQGridParam();
		gp.setPagination(false);

		gp.setQueryId("casecontact");

		Map<String, Object> paramter = new HashMap<String, Object>();
		paramter.put("caseCode", caseCode);
		gp.putAll(paramter);

		Page<Map<String, Object>> returnPage = quickGridService.findPageForSqlServer(gp);

		JSONObject result = new JSONObject();

		result.put("total", returnPage.getTotalPages());
		// result.put("records", returnPage.getTotalElements());
		List<Map<String, Object>> contentList = returnPage.getContent();

		/*
		 * JSONArray content = new JSONArray(); for (int i = 0; i <
		 * contentList.size(); i++) { Map<String, Object> mapObj =
		 * contentList.get(i);
		 * 
		 * content.add(JSONObject.toJSON(mapObj)); }
		 */
		result.put("rows", contentList);

		return result;
	}

	@ResponseBody
	@RequestMapping("contact")
	public JSONObject contact(Integer page, Integer pageSize, String sidx, String sord, String q_text) {

		JQGridParam gp = new JQGridParam();
		gp.setPagination(true);
		gp.setPage(page);
		gp.setRows(pageSize);
		gp.setSidx(sidx);
		gp.setSord(sord);
		gp.setQueryId("contact");

		Map<String, Object> paramter = new HashMap<String, Object>();
		paramter.put("q_text", q_text);
		gp.putAll(paramter);

		Page<Map<String, Object>> returnPage = quickGridService.findPageForSqlServer(gp);

		JSONObject result = new JSONObject();

		result.put("total", returnPage.getTotalPages());
		result.put("page", page);
		result.put("total", returnPage.getTotalPages());
		result.put("records", returnPage.getTotalElements());
		result.put("pageSize", pageSize);
		List<Map<String, Object>> contentList = returnPage.getContent();

		result.put("rows", contentList);

		return result;
	}

}
