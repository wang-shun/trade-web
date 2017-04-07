package com.centaline.parportal.mobile.mortgage.web;

import java.util.ArrayList;
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
import com.aist.uam.auth.remote.UamSessionService;
import com.aist.uam.auth.remote.vo.SessionUser;
import com.alibaba.fastjson.JSONObject;
import com.centaline.trans.common.vo.MobileHolder;
import com.centaline.trans.mortgage.service.ToMortgageService;
import com.centaline.trans.mortgage.vo.MortgageVo;

@Controller
@RequestMapping({ "/mobile/case", "/case" })
public class MortgageController {

	@Resource(name = "quickGridService")
	private QuickGridService quickGridService;

	@Autowired
	private ToMortgageService toMortgageService;

	@Autowired
	private UamSessionService uamSessionService;

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
	 * @return 返回true,操作成功;返回false,操作失败。
	 */
	@RequestMapping(value = "track/accept")
	@ResponseBody
	public String accept(String bizCode, String isPass, String taskId,
			String procInstanceId, String stateInBank, String caseCode,
			String comment) {
		// 获取当前用户信息
		SessionUser sessionUser = uamSessionService.getSessionUser();

		// 设置前台传的参数信息
		MortgageVo mortgageVo = new MortgageVo();
		mortgageVo.setBizCode(bizCode);
		mortgageVo.setIsPass(isPass);
		mortgageVo.setTaskId(taskId);
		mortgageVo.setProcInstanceId(procInstanceId);
		mortgageVo.setStateInBank(stateInBank);
		mortgageVo.setCaseCode(caseCode);
		mortgageVo.setComment(comment);
		mortgageVo.setUser(sessionUser);

		try {
			toMortgageService.accept(mortgageVo);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * 银行审核
	 * 
	 * @param bizCode
	 *            按揭贷款信息id
	 * @param isPass
	 *            是否通过,isPass如果为true,银行审核通过;为false,银行审核驳回;
	 * @param taskId
	 *            任务id
	 * @param procInstanceId
	 *            流程实例id
	 * @param stateInBank
	 *            状态
	 * @param caseCode
	 *            案件编号
	 * @param comment
	 *            案件跟进备注
	 * @return 返回true,操作成功;返回false,操作失败。
	 */
	@RequestMapping(value = "track/followUp")
	@ResponseBody
	public String followUp(String bizCode, String isPass, String taskId,
			String procInstanceId, String stateInBank, String caseCode,
			String comment) {

		// 获取当前用户信息
		SessionUser sessionUser = uamSessionService.getSessionUser();

		// 设置前台传的参数信息
		MortgageVo mortgageVo = new MortgageVo();
		mortgageVo.setBizCode(bizCode);
		mortgageVo.setIsPass(isPass);
		mortgageVo.setTaskId(taskId);
		mortgageVo.setProcInstanceId(procInstanceId);
		mortgageVo.setStateInBank(stateInBank);
		mortgageVo.setCaseCode(caseCode);
		mortgageVo.setComment(comment);
		mortgageVo.setUser(sessionUser);

		try {
			toMortgageService.followUp(mortgageVo);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	@RequestMapping(value = "/{bizCode}")
	@ResponseBody
	public String mortgageCaseDetail(@PathVariable String bizCode) {
		List<Map<String, Object>> respDetail = mortgageCaseInfoQuery(
				queryDetail, bizCode, 1, 10);

		List<Map<String, Object>> respMortProc = mortgageCaseInfoQuery(
				queryMortProcess, bizCode, 1, 10);

		Map<String, Object> result = new HashMap<String, Object>();
		String caseCode = "";
		if (respDetail != null && respDetail.size() > 0) {
			caseCode = (String) respDetail.get(0).get("tradeInfo_caseCode");
			this.parseMortDetail(result, respDetail.get(0));
		}

		List<Map<String, Object>> respTradeProc = mortgageCaseInfoQuery(
				queryTradeProcess, caseCode, 1, 10);

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
