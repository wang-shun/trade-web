/**
 * AISThink.com Inc.
 * Copyright (c) 2015-2016 All Rights Reserved.
 */
package com.centaline.parportal.mobile.mortgage.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aist.common.quickQuery.bo.JQGridParam;
import com.aist.common.quickQuery.service.QuerysParseService;
import com.aist.common.quickQuery.service.QuickGridService;
import com.aist.uam.auth.remote.UamSessionService;
import com.aist.uam.auth.remote.vo.SessionUser;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * 
 * @author sstonehu
 * @version $Id: MortgageListController.java, v 0.1 2016年12月12日 上午3:45:44
 *          sstonehu Exp $
 */
@Controller
@RequestMapping(value = "/mortgageList")
public class MortgageListController {

	@SuppressWarnings("unused")
	private static Logger logger = LoggerFactory.getLogger(MortgageListController.class);

	@Resource(name = "quickGridService")
	private QuickGridService quickGridService;

	@Autowired
	private QuerysParseService querysParseService;

	@Autowired
	private UamSessionService uamSessionService;

	@RequestMapping(value = "list")
	@ResponseBody
	public String caseList(Integer page, Integer pageSize, String sidx, String sord, String q_text,
			String loanerStatusCode) {

		// 设置快速查询id和分页参数
		JQGridParam gp = new JQGridParam();
		gp.setPagination(true);
		gp.setPage(page);
		gp.setRows(pageSize);
		gp.setQueryId("findToMortgage4Par");
		gp.setSidx(sidx);
		gp.setSord(sord);
		Map<String, Object> paramter = new HashMap<String, Object>();

		// 设置用户id
		SessionUser sessionUser = uamSessionService.getSessionUser();
		paramter.put("userid", sessionUser.getId());
		if (StringUtils.isNotBlank(loanerStatusCode)) {
			paramter.put("loanerStatusCode", loanerStatusCode.split(","));
		}

		// 设置用户查询条件
		if (q_text != null) {
			String formatCondtion = q_text.trim();

			if (!"".equals(formatCondtion)) {
				paramter.put("q_text", formatCondtion);
			}
		}

		gp.putAll(paramter);
		querysParseService.reloadFile();

		// 查询按揭列表信息(待接单、待审核、完成状态)
		Page<Map<String, Object>> mortgageMapList = quickGridService.findPageForSqlServer(gp, sessionUser);
		List<Map<String, Object>> mortgageList = mortgageMapList.getContent();

		JSONObject result = new JSONObject();
		result.put("page", page);
		result.put("total", mortgageMapList.getTotalPages());
		result.put("records", mortgageMapList.getTotalElements());
		result.put("pageSize", pageSize);

		JSONArray content = new JSONArray();
		for (int i = 0; i < mortgageList.size(); i++) {
			Map<String, Object> mapObj = mortgageList.get(i);

			content.add(JSONObject.toJSON(mapObj));
		}

		result.put("rows", content);

		return result.toJSONString();
	}
}
