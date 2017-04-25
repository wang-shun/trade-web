package com.centaline.parportal.mobile.common.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

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

/**
 * 按揭和E+案件操作记录controller
 * 
 * @author yinjf2
 *
 */
@Controller
public class OperateListController {

	@Autowired
	private UamSessionService uamSessionService;

	@Autowired
	private QuerysParseService querysParseService;

	@Resource(name = "quickGridService")
	private QuickGridService quickGridService;

	/**
	 * E+和按揭案件操作记录列表
	 * 
	 * @param page
	 *            当前页
	 * @param pageSize
	 *            每页记录数
	 * @param sidx
	 * @param sord
	 * @return E+案件操作记录列表
	 */
	@RequestMapping(value = "/operateList")
	@ResponseBody
	public String operateList(Integer page, Integer pageSize, String sidx,
			String sord) {
		JSONObject result = new JSONObject();
		JQGridParam gp = new JQGridParam();
		gp.setPagination(true);
		gp.setPage(page);
		gp.setRows(pageSize);
		gp.setQueryId("eLoanOperateList");
		gp.setSidx(sidx);
		gp.setSord(sord);
		Map<String, Object> paramter = new HashMap<String, Object>();

		SessionUser sessionUser = uamSessionService.getSessionUser();
		paramter.put("loanerId", sessionUser.getId());
		// paramter.put("loanerId", "8a8493d45921d36901593e4adc95007b");

		gp.putAll(paramter);
		querysParseService.reloadFile();

		Page<Map<String, Object>> eloanContentMapList = quickGridService
				.findPageForSqlServer(gp, sessionUser);

		List<Map<String, Object>> eloanContentList = eloanContentMapList
				.getContent();

		gp.setQueryId("mortgageOperateList");
		gp.putAll(paramter);
		querysParseService.reloadFile();

		Page<Map<String, Object>> mortgageContentMapList = quickGridService
				.findPageForSqlServer(gp, sessionUser);

		List<Map<String, Object>> mortgageContentList = mortgageContentMapList
				.getContent();

		// E+操作记录数
		long eloanOperateCount = eloanContentMapList.getTotalElements();

		// 按揭操作记录数
		long mortOperateCount = mortgageContentMapList.getTotalElements();

		// 总记录数
		long totalElements = eloanOperateCount + mortOperateCount;

		// 总页数
		int totalPage = (int) (totalElements + pageSize - 1) / pageSize;

		result.put("page", page);
		result.put("total", totalPage);
		result.put("records", totalElements);
		result.put("pageSize", pageSize);

		JSONArray content = new JSONArray();
		for (int i = 0; i < mortgageContentList.size(); i++) {
			Map<String, Object> mapObj = mortgageContentList.get(i);

			content.add(JSONObject.toJSON(mapObj));
		}

		for (int i = 0; i < eloanContentList.size(); i++) {
			Map<String, Object> mapObj = eloanContentList.get(i);

			content.add(JSONObject.toJSON(mapObj));
		}

		result.put("rows", content);

		return result.toJSONString();
	}
}
