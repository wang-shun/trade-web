package com.centaline.parportal.mobile.eloancase.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aist.common.quickQuery.bo.JQGridParam;
import com.aist.common.quickQuery.service.QuerysParseService;
import com.aist.common.quickQuery.service.QuickGridService;
import com.aist.common.web.validate.AjaxResponse;
import com.aist.uam.auth.remote.UamSessionService;
import com.aist.uam.auth.remote.vo.SessionUser;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.centaline.trans.eloan.entity.ToEloanCase;
import com.centaline.trans.eloan.service.ToEloanCaseService;

/**
 * eLoan案件控制器
 * 
 * @author yinjf2
 *
 */
@Controller
@RequestMapping(value = "/eloanCase")
public class ELoanCaseController {

	@Resource(name = "quickGridService")
	private QuickGridService quickGridService;

	@Autowired(required = true)
	UamSessionService uamSessionService;
	@Autowired
	private QuerysParseService querysParseService;
	@Autowired
	ToEloanCaseService toEloanCaseService;

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private final static String queryELoanCaseDetail = "queryELoanCaseDetail";
	private final static String queryELoanProcess = "queryELoanProcess";
	private final static String queryELoanTradeProcess = "queryELoanTradeProcess";

	@RequestMapping(value = "/list")
	@ResponseBody
	public String list(Integer page, Integer pageSize, String sidx,
			String sord, String userId, String condition) {
		JQGridParam gp = new JQGridParam();
		gp.setPagination(true);
		gp.setPage(page);
		gp.setRows(pageSize);
		gp.setQueryId("eloanCaseListQuery");
		gp.setSidx(sidx);
		gp.setSord(sord);
		Map<String, Object> paramter = new HashMap<String, Object>();

		// SessionUser sessionUser = MobileHolder.getMobileUser();
		paramter.put("loanerId", userId);

		if (condition != null && !"".equals(condition)) {
			String formatCondtion = condition.trim();
			paramter.put("condition", formatCondtion);
		}

		gp.putAll(paramter);
		querysParseService.reloadFile();
		Page<Map<String, Object>> returnPage = quickGridService
				.findPageForSqlServer(gp);

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

	@RequestMapping(value = "/{eLoanCode}")
	@ResponseBody
	public String mortgageCaseDetail(@PathVariable String eLoanCode) {

		// 获取E+案件基本信息
		List<Map<String, Object>> eLoanCaseDetailMapList = getELoanCaseDetailList(
				queryELoanCaseDetail, eLoanCode);

		// 获取E+案件跟进内容
		List<Map<String, Object>> eLoanCaseProcessMapList = getELoanCaseProcessList(
				queryELoanProcess, eLoanCode);

		String caseCode = "";
		if (eLoanCaseDetailMapList != null && eLoanCaseDetailMapList.size() > 0) {
			caseCode = (String) eLoanCaseDetailMapList.get(0).get("caseCode");
		}

		// 获取E+案件环节
		List<Map<String, Object>> eLoanCaseTradeProcessMapList = getELoanCaseTradeProcessList(
				queryELoanTradeProcess, caseCode);

		return bulidELoanCaseDetailByJson(eLoanCode, eLoanCaseDetailMapList,
				eLoanCaseProcessMapList, eLoanCaseTradeProcessMapList);
	}

	/**
	 * 构造json格式的字符串作为结果集
	 * 
	 * @param eLoanCode
	 *            E+金融编号
	 * @param eLoanCaseDetailMapList
	 *            E+案件基本信息
	 * @param eLoanCaseProcessMapList
	 *            E+跟进信息列表
	 * @param eLoanCaseTradeProcessMapList
	 *            E+环节列表信息
	 * @return
	 */
	private String bulidELoanCaseDetailByJson(String eLoanCode,
			List<Map<String, Object>> eLoanCaseDetailMapList,
			List<Map<String, Object>> eLoanCaseProcessMapList,
			List<Map<String, Object>> eLoanCaseTradeProcessMapList) {

		Map<String, Object> contentMap = new HashMap<String, Object>();

		// 结果集map
		Map<String, Object> resultMap = new HashMap<String, Object>();

		// 构造eLoan案件基本信息
		Map<String, Object> eLoanCaseDetailMap = new HashMap<String, Object>();
		if (eLoanCaseDetailMapList != null && eLoanCaseDetailMapList.size() > 0) {
			eLoanCaseDetailMap = eLoanCaseDetailMapList.get(0);
		}

		resultMap.put(
				"prdType",
				eLoanCaseDetailMap.get("prdType") != null ? eLoanCaseDetailMap
						.get("prdType") : "");

		Map<String, Object> mortInfoMap = new HashMap<String, Object>();
		mortInfoMap.put("morCode", eLoanCode);
		mortInfoMap.put(
				"mortType",
				eLoanCaseDetailMap.get("mortType") != null ? eLoanCaseDetailMap
						.get("mortType") : "");
		mortInfoMap
				.put("totalAmount",
						eLoanCaseDetailMap.get("totalAmount") != null ? eLoanCaseDetailMap
								.get("totalAmount") : "0");
		mortInfoMap.put(
				"month",
				eLoanCaseDetailMap.get("month") != null ? eLoanCaseDetailMap
						.get("month") : "0");
		mortInfoMap
				.put("status",
						eLoanCaseDetailMap.get("eLoanStatus") != null ? eLoanCaseDetailMap
								.get("eLoanStatus") : "");

		Map<String, Object> yuOperatorMap = new HashMap<String, Object>();
		yuOperatorMap
				.put("userId",
						eLoanCaseDetailMap.get("excutorId") != null ? eLoanCaseDetailMap
								.get("excutorId") : "");
		yuOperatorMap
				.put("name",
						eLoanCaseDetailMap.get("excutorName") != null ? eLoanCaseDetailMap
								.get("excutorName") : "");
		yuOperatorMap
				.put("mobile",
						eLoanCaseDetailMap.get("excutorMobile") != null ? eLoanCaseDetailMap
								.get("excutorMobile") : "");
		yuOperatorMap
				.put("org",
						eLoanCaseDetailMap.get("excutorOrg") != null ? eLoanCaseDetailMap
								.get("excutorOrg") : "");

		Map<String, Object> custInfoMap = new HashMap<String, Object>();
		custInfoMap
				.put("name",
						eLoanCaseDetailMap.get("customerName") != null ? eLoanCaseDetailMap
								.get("customerName") : "");
		custInfoMap
				.put("mobile",
						eLoanCaseDetailMap.get("customerMobile") != null ? eLoanCaseDetailMap
								.get("customerMobile") : "");
		// 由于T_TO_ELOAN_CASE没有USER_ID这个字段,所以无法获取该字段信息,所以先暂时设置该字段为空字符串
		custInfoMap.put("custID", "");

		Map<String, Object> tradeInfoMap = new HashMap<String, Object>();
		tradeInfoMap.put(
				"caseCode",
				eLoanCaseDetailMap.get("caseCode") != null ? eLoanCaseDetailMap
						.get("caseCode") : "");
		tradeInfoMap
				.put("addr",
						eLoanCaseDetailMap.get("propertyAddress") != null ? eLoanCaseDetailMap
								.get("propertyAddress") : "");
		tradeInfoMap
				.put("status",
						eLoanCaseDetailMap.get("caseStatus") != null ? eLoanCaseDetailMap
								.get("caseStatus") : "");

		resultMap.put("mortInfo", mortInfoMap);
		resultMap.put("yuOperator", yuOperatorMap);
		resultMap.put("custInfo", custInfoMap);
		resultMap.put("tradeInfo", tradeInfoMap);

		if (eLoanCaseProcessMapList == null
				|| eLoanCaseProcessMapList.size() == 0) {
			eLoanCaseProcessMapList = new ArrayList<Map<String, Object>>();
		}

		if (eLoanCaseTradeProcessMapList == null
				|| eLoanCaseTradeProcessMapList.size() == 0) {
			eLoanCaseTradeProcessMapList = new ArrayList<Map<String, Object>>();
		}

		resultMap.put("mortProcess", eLoanCaseProcessMapList);
		resultMap.put("tradeProcess", eLoanCaseTradeProcessMapList);

		contentMap.put("content", resultMap);

		String str = JSONObject.toJSONString(contentMap);

		return str;
	}

	/**
	 * 获取eLoan案件基本信息
	 * 
	 * @param queryId
	 *            快速查询Id
	 * @param eLoanCode
	 *            E+金融编号
	 * @return E+案件基本信息
	 */
	private List<Map<String, Object>> getELoanCaseDetailList(String queryId,
			String eLoanCode) {
		JQGridParam gp = new JQGridParam();
		gp.setQueryId(queryId);
		gp.setPagination(false);
		gp.put("eLoanCode", eLoanCode);

		Page<Map<String, Object>> eLoanCaseDetailMapList = quickGridService
				.findPageForSqlServer(gp);

		if (eLoanCaseDetailMapList.getContent() != null
				&& eLoanCaseDetailMapList.getContent().size() > 0) {

			return eLoanCaseDetailMapList.getContent();
		}

		return null;
	}

	/**
	 * 获取E+案件环节信息
	 * 
	 * @param queryId
	 *            快速查询Id
	 * @param caseCode
	 *            案件编号
	 * @return E+案件环节列表
	 */
	private List<Map<String, Object>> getELoanCaseTradeProcessList(
			String queryId, String caseCode) {
		JQGridParam gp = new JQGridParam();
		gp.setQueryId(queryId);
		gp.setPagination(false);
		gp.put("caseCode", caseCode);

		Page<Map<String, Object>> eLoanCaseTradeProcessListMapList = quickGridService
				.findPageForSqlServer(gp);

		if (eLoanCaseTradeProcessListMapList.getContent() != null
				&& eLoanCaseTradeProcessListMapList.getContent().size() > 0) {

			return eLoanCaseTradeProcessListMapList.getContent();
		}

		return null;
	}

	/**
	 * 获取E+案件跟进内容
	 * 
	 * @param queryId
	 *            快速查询Id
	 * @param eLoanCode
	 *            E+金融编号
	 * @return E+案件跟进内容列表
	 */
	private List<Map<String, Object>> getELoanCaseProcessList(String queryId,
			String eLoanCode) {
		JQGridParam gp = new JQGridParam();
		gp.setQueryId(queryId);
		gp.setPagination(false);
		gp.put("eLoanCode", eLoanCode);

		Page<Map<String, Object>> eLoanCaseProcessMapList = quickGridService
				.findPageForSqlServer(gp);

		if (eLoanCaseProcessMapList.getContent() != null
				&& eLoanCaseProcessMapList.getContent().size() > 0) {

			return eLoanCaseProcessMapList.getContent();
		}

		return null;
	}
	
	/**
	 * 信贷员确认申请是否通过
	 * @param eloanCode
	 * @param taskId
	 * @param loanerApprove
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "loanerConfirm")
	@ResponseBody
	public AjaxResponse<String> loanerConfirm(String eloanCode, String taskId, String loanerApprove) {

		SessionUser user = uamSessionService.getSessionUser();
		try {
			ToEloanCase toEloanCase = new ToEloanCase();
			toEloanCase.setEloanCode( null==eloanCode ? "":eloanCode);
			
			boolean flag = false;
			Map<String, Object> map = new HashMap<String, Object>();
			if (StringUtils.isNotBlank(loanerApprove) && "1".equals(loanerApprove)) {
				toEloanCase.setLoanerId(user.getId());
				toEloanCase.setLoanerConfTime(new Date());
				map.put("LoanerApprove", true);
				flag = true;
			} else {
				map.put("LoanerApprove", false);
			}
			toEloanCaseService.eloanProcessLoanerConfirm(taskId, map, toEloanCase, flag);
			return AjaxResponse.success("操作成功");
		} catch (Exception e) {
			logger.debug("信贷员确认申请失败", e);
			return AjaxResponse.fail("操作失败");
		}
	}
}
