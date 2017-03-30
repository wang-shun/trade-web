package com.centaline.parportal.mobile.mortgage.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aist.common.quickQuery.bo.JQGridParam;
import com.aist.common.quickQuery.service.QuickGridService;
import com.aist.uam.auth.remote.vo.SessionUser;
import com.alibaba.fastjson.JSONObject;
import com.centaline.trans.comment.entity.ToCaseComment;
import com.centaline.trans.comment.service.ToCaseCommentService;
import com.centaline.trans.common.vo.MobileHolder;
import com.centaline.trans.mortgage.service.LoanerProcessService;

@Controller
@RequestMapping({ "/mobile/case", "/case" })
public class MortgageController {

	@Resource(name = "quickGridService")
	private QuickGridService quickGridService;

	@Autowired
	private LoanerProcessService loanerProcessService;

	@Autowired
	private ToCaseCommentService toCaseCommentService;

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private final static String queryDetail = "queryMortgageCaseDetail";
	private final static String queryMortProcess = "queryMortProcess";
	private final static String queryTradeProcess = "queryTradeProcess";

	/**
	 * 按揭贷款信贷员接单和打回
	 * 
	 * @param isPass
	 *            是否接单,isPass如果为true,信贷员接单;为false,信贷员打回;
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
	public String accept(String isPass, String taskId, String procInstanceId,
			String caseCode, String comment) {
		SessionUser sessionUser = MobileHolder.getMobileUser();
		ToCaseComment toCaseComment = null;

		// 信贷员接单
		if ("true".equals(isPass)) {
			// 处理流程,信贷员接单,流程进入银行审核流程
			loanerProcessService.isLoanerAcceptCase(true, taskId,
					procInstanceId, caseCode);

			// 设置案件跟进信息
			toCaseComment = setToCaseComment(sessionUser, caseCode, "accept",
					comment);
		}
		// 信贷员打回
		else if ("false".equals(isPass)) {
			// 处理流程,信贷员打回,流程进入交易顾问派单
			loanerProcessService.isLoanerAcceptCase(false, taskId,
					procInstanceId, caseCode);

			// 设置案件跟进信息
			toCaseComment = setToCaseComment(sessionUser, caseCode, "beatBack",
					comment);
		}

		// 保存案件跟进信息
		toCaseCommentService.insertToCaseComment(toCaseComment);

		return "true";
	}

	/**
	 * 银行审核
	 * 
	 * @param isPass
	 *            是否通过,isPass如果为true,银行审核通过;为false,银行审核驳回;
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
	@RequestMapping(value = "track/audit")
	@ResponseBody
	public String audit(String isPass, String taskId, String procInstanceId,
			String caseCode, String comment) {
		SessionUser sessionUser = MobileHolder.getMobileUser();

		ToCaseComment toCaseComment = null;

		// 银行审核通过
		if ("true".equals(isPass)) {
			// 处理流程,银行审核通过
			loanerProcessService.isBankAcceptCase(true, taskId, procInstanceId,
					caseCode);

			// 设置案件跟进信息
			toCaseComment = setToCaseComment(sessionUser, caseCode, "success",
					comment);
		}
		// 银行审核拒绝
		else if ("false".equals(isPass)) {
			// 处理流程,银行审核驳回
			loanerProcessService.isBankAcceptCase(false, taskId,
					procInstanceId, caseCode);
			// 设置案件跟进信息
			toCaseComment = setToCaseComment(sessionUser, caseCode, "reject",
					comment);
		}

		// 保存案件跟进信息
		toCaseCommentService.insertToCaseComment(toCaseComment);

		return "true";
	}

	/**
	 * 设置按揭贷款案件跟进信息
	 * 
	 * @param user
	 *            用户信息
	 * @param caseCode
	 *            案件编号
	 * @param srvCode
	 *            环节编码,其中accept:接单,beatBack:打回,success:银行审核通过,reject:银行审核驳回
	 * @param comment
	 *            跟进跟进内容
	 * @return 返回案件跟进信息
	 */
	private ToCaseComment setToCaseComment(SessionUser user, String caseCode,
			String srvCode, String comment) {
		// 添加案件跟进信息
		ToCaseComment toCaseComment = new ToCaseComment();
		toCaseComment.setCaseCode(caseCode);
		toCaseComment.setType("TRACK");
		toCaseComment.setSource("MORT");
		toCaseComment.setSrvCode(srvCode);
		toCaseComment.setComment(comment);
		toCaseComment.setCreateTime(new Date());
		toCaseComment.setCreateBy(user.getId());
		toCaseComment.setCreatorOrgId(user.getServiceDepId());

		return toCaseComment;
	}

	@RequestMapping(value = "/{bizCode}")
	@ResponseBody
	public String mortgageCaseDetail(@PathVariable String bizCode) {

		List<Map<String, Object>> respDetail = mortgageCaseInfoQuery(
				queryDetail, bizCode, 1, 10);

		List<Map<String, Object>> respMortProc = mortgageCaseInfoQuery(
				queryMortProcess, bizCode, 1, 10);

		String caseCode = (String) respDetail.get(0).get("tradeInfo_caseCode");

		List<Map<String, Object>> respTradeProc = mortgageCaseInfoQuery(
				queryTradeProcess, caseCode, 1, 10);

		Map<String, Object> result = new HashMap<String, Object>();
		this.parseMortDetail(result, respDetail.get(0));
		result.put("mortProcess", respMortProc);
		result.put("tradeProcess", respTradeProc);

		String str = JSONObject.toJSONString(result);

		return str;
	}

	private List<Map<String, Object>> mortgageCaseInfoQuery(String queryId,
			String bizCode, Integer page, Integer rows) {
		try {
			JQGridParam gp = new JQGridParam();
			gp.put("bizCode", bizCode);
			gp.setPage(page);
			gp.setRows(rows);
			gp.setQueryId(queryId);
			Page<Map<String, Object>> result = quickGridService
					.findPageForSqlServer(gp, MobileHolder.getMobileUser());

			if (null != result && null != result.getContent()
					&& result.getContent().size() > 0)
				return result.getContent();

		} catch (Exception e) {
			logger.info("quick query for mobile mortgate detail failed, queryID: #"
					+ queryId + "#, caseCode: #" + bizCode + "#");
		}
		return new ArrayList();
	}

	/**
	 * 将查询结果根据名称封装出层次
	 * 
	 * @param result
	 * @param detail
	 * @return
	 */
	private Map<String, Object> parseMortDetail(Map<String, Object> result,
			Map<String, Object> detail) {
		detail.forEach((k, v) -> {
			String[] p = k.split("_");
			Map<String, Object> parent = result;
			for (int i = 0; i < p.length - 1; i++) {
				if (!parent.containsKey(p[i]))
					parent.put(p[i], new HashMap<String, Object>());
				parent = (Map<String, Object>) parent.get(p[i]);
			}
			parent.put(p[p.length - 1], v);
		});
		return result;
	}

}
