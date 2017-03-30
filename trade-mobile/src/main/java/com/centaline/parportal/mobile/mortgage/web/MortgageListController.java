/**
 * AISThink.com Inc.
 * Copyright (c) 2015-2016 All Rights Reserved.
 */
package com.centaline.parportal.mobile.mortgage.web;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.aist.common.quickQuery.bo.JQGridParam;
import com.aist.common.quickQuery.service.QuerysParseService;
import com.aist.common.quickQuery.service.QuickGridService;
import com.aist.uam.auth.remote.vo.SessionUser;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.centaline.trans.comment.entity.ToCaseComment;
import com.centaline.trans.comment.service.ToCaseCommentService;
import com.centaline.trans.common.vo.MobileHolder;
import com.centaline.trans.mortgage.service.LoanerProcessService;

/**
 * 
 * @author sstonehu
 * @version $Id: MortgageListController.java, v 0.1 2016年12月12日 上午3:45:44
 *          sstonehu Exp $
 */
@RestController
@RequestMapping(value = "/mobile/case")
public class MortgageListController {

	private static Logger logger = LoggerFactory
			.getLogger(MortgageListController.class);

	@Resource(name = "quickGridService")
	private QuickGridService quickGridService;

	@Autowired
	private QuerysParseService querysParseService;

	@Autowired
	private LoanerProcessService loanerProcessService;

	@Autowired
	private ToCaseCommentService toCaseCommentService;

	@RequestMapping(value = "list")
	@ResponseBody
	public String caseList(HttpServletRequest request,
			HttpServletResponse response, Integer page, Integer pageSize,
			String sidx, String sord, String userid, String q_text) {

		long millisecond = System.currentTimeMillis();
		logger.info("Start:caseList 房源列表数据加载开始 ：" + millisecond + "/毫秒");
		JQGridParam gp = new JQGridParam();
		gp.setPagination(true);
		gp.setPage(page);
		gp.setRows(pageSize);
		gp.setQueryId("findToMortgage4Par");
		gp.setSidx(sidx);
		gp.setSord(sord);
		Map<String, Object> paramter = new HashMap<String, Object>();

		// SessionUser sessionUser = MobileHolder.getMobileUser();
		// paramter.put("userid", sessionUser.getId());
		paramter.put("userid", userid);

		if (StringUtils.isNotBlank(q_text) && StringUtils.isNotEmpty(q_text)) {
			paramter.put("q_text", q_text);
		}

		gp.putAll(paramter);
		querysParseService.reloadFile();
		Page<Map<String, Object>> returnPage = quickGridService
				.findPageForSqlServer(gp, MobileHolder.getMobileUser());

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

	/**
	 * 按揭贷款接单
	 * 
	 * @param taskId
	 *            任务id
	 * @param procInstanceId
	 *            流程实例id
	 * @param caseCode
	 *            案件编号
	 * @param comment
	 *            案件跟进备注
	 * @return
	 */
	@RequestMapping(value = "track/accept")
	@ResponseBody
	public String accept(String taskId, String procInstanceId, String caseCode,
			String comment) {
		boolean isSuccess = loanerProcessService.isLoanerAcceptCase(true,
				taskId, procInstanceId, caseCode);

		// 如果接单成功，添加案件跟进信息
		if (isSuccess) {
			SessionUser sessionUser = MobileHolder.getMobileUser();

			// 添加案件跟进信息
			ToCaseComment toCaseComment = new ToCaseComment();
			toCaseComment.setCaseCode(caseCode);
			toCaseComment.setType("TRACK");
			toCaseComment.setSource("MORT");
			toCaseComment.setSrvCode("accept");
			toCaseComment.setComment(comment);
			toCaseComment.setCreateTime(new Date());
			toCaseComment.setCreateBy(sessionUser.getId());
			toCaseComment.setCreatorOrgId(sessionUser.getServiceDepId());

			toCaseCommentService.insertToCaseComment(toCaseComment);

			return "true";
		}

		return null;
	}

	/**
	 * 按揭贷款打回
	 * 
	 * @param taskId
	 *            任务id
	 * @param procInstanceId
	 *            流程实例id
	 * 
	 * @param caseCode
	 *            案件编号
	 * @param comment
	 *            案件跟进备注
	 * @return
	 */
	@RequestMapping(value = "track/reject")
	@ResponseBody
	public String reject(String taskId, String procInstanceId, String caseCode,
			String comment) {
		boolean isSuccess = loanerProcessService.isLoanerAcceptCase(false,
				taskId, procInstanceId, caseCode);

		// 如果接单成功，添加案件跟进信息
		if (isSuccess) {
			SessionUser sessionUser = MobileHolder.getMobileUser();

			// 添加案件跟进信息
			ToCaseComment toCaseComment = new ToCaseComment();
			toCaseComment.setCaseCode(caseCode);
			toCaseComment.setType("TRACK");
			toCaseComment.setSource("MORT");
			toCaseComment.setSrvCode("reject");
			toCaseComment.setComment(comment);
			toCaseComment.setCreateTime(new Date());
			toCaseComment.setCreateBy(sessionUser.getId());
			toCaseComment.setCreatorOrgId(sessionUser.getServiceDepId());

			toCaseCommentService.insertToCaseComment(toCaseComment);

			return "true";
		}

		return null;
	}

}
