/**
 * AISThink.com Inc.
 * Copyright (c) 2015-2016 All Rights Reserved.
 */
package com.centaline.parportal.mobile.mortgage.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aist.common.quickQuery.bo.JQGridParam;
import com.aist.common.quickQuery.service.QuerysParseService;
import com.aist.common.quickQuery.service.QuickGridService;
import com.aist.uam.auth.remote.UamSessionService;
import com.aist.uam.auth.remote.vo.SessionUser;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.centaline.trans.common.vo.MobileHolder;

/**
 * 
 * @author sstonehu
 * @version $Id: MortgageListController.java, v 0.1 2016年12月12日 上午3:45:44
 *          sstonehu Exp $
 */
@Controller
@RequestMapping(value = "/mortgageList")
public class MortgageListController {

	private static Logger logger = LoggerFactory
			.getLogger(MortgageListController.class);

	@Resource(name = "quickGridService")
	private QuickGridService quickGridService;

	@Autowired
	private QuerysParseService querysParseService;

	@Autowired
	private UamSessionService uamSessionService;

	@RequestMapping(value = "list")
	@ResponseBody
	public String caseList(Integer page, Integer pageSize, String sidx,
			String sord, String q_text) {
		JQGridParam gp = new JQGridParam();
		gp.setPagination(true);
		gp.setPage(page);
		gp.setRows(pageSize);
		gp.setQueryId("findToMortgage4Par");
		gp.setSidx(sidx);
		gp.setSord(sord);
		Map<String, Object> paramter = new HashMap<String, Object>();

		SessionUser sessionUser = MobileHolder.getMobileUser();

		logger.error("################" + sessionUser.getId()
				+ "####################");

		// SessionUser sessionUser = uamSessionService
		// .getSessionUserById("ff80808158bd58c10158bda37f100020");
		paramter.put("userid", sessionUser.getId());

		if (q_text != null && !"".equals(q_text)) {
			String formatCondtion = q_text.trim();
			paramter.put("q_text", formatCondtion);
		}

		gp.putAll(paramter);
		querysParseService.reloadFile();
		Page<Map<String, Object>> returnPage = quickGridService
				.findPageForSqlServer(gp, sessionUser);

		JSONObject result = new JSONObject();
		result.put("page", page);
		result.put("total", returnPage.getTotalPages());
		result.put("records", returnPage.getTotalElements());
		result.put("pageSize", pageSize);
		List<Map<String, Object>> contentList = returnPage.getContent();

		JSONArray content = new JSONArray();
		for (int i = 0; i < contentList.size(); i++) {
			Map<String, Object> mapObj = contentList.get(i);

			content.add(JSONObject.toJSON(mapObj));
		}
		result.put("rows", content);

		return result.toJSONString();
	}
}
