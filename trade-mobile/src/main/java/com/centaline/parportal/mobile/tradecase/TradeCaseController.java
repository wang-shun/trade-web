package com.centaline.parportal.mobile.tradecase;

import java.text.MessageFormat;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.aist.common.quickQuery.bo.JQGridParam;
import com.aist.common.quickQuery.service.QuickGridService;
import com.aist.uam.auth.remote.UamSessionService;
import com.aist.uam.auth.remote.vo.SessionUser;
import com.aist.uam.basedata.remote.UamBasedataService;
import com.aist.uam.userorg.remote.UamUserOrgService;
import com.aist.uam.userorg.remote.vo.User;
import com.alibaba.fastjson.JSONObject;
import com.centaline.trans.common.enums.TransDictEnum;

@RestController
@RequestMapping(value = "/tradeCase")
public class TradeCaseController {

	@Value("${agent.img.url}")
	private String imgUrl;

	@Autowired
	private UamSessionService sessionService;

	@Autowired
	private QuickGridService quickGridService;
	
	@Autowired
	private UamUserOrgService userOrgService;

	@Autowired
	private UamBasedataService uamBasedataService;
	
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

		Map<String, Object> paramMap = gp.getParamtMap();
		paramMap.put("q_text", q_text);
		paramMap.put("onlyFocus", onlyFocus);

		paramMap.put("status", status);
		paramMap.put("property", property);

		Page<Map<String, Object>> pages = quickGridService.findPageForSqlServer(gp, user);
		List<Map<String, Object>> list = pages.getContent();
		buildZhongjieInfo(list);

		return pages;
	}

	private void buildZhongjieInfo(List<Map<String, Object>> list) {
		for (Map<String, Object> map : list) {
			Object agentNameObj = map.get("AGENT_NAME");
			String name = null == agentNameObj ? "" : String.valueOf(agentNameObj);
			Object agentCodeObj = map.get("EMPLOYEE_CODE");
			String avatar = null == agentCodeObj ? ""
					: MessageFormat.format(imgUrl, String.valueOf(agentCodeObj)) + ".jpg";
			Object agentPhoneObj = map.get("AGENT_PHONE");
			String agentPhone = agentPhoneObj == null ? "" : String.valueOf(agentPhoneObj);

			JSONObject json = new JSONObject();
			json.put("name", name);
			json.put("avatar", avatar);
			json.put("mobile", agentPhone);

			map.put("zhongjie", json);
			
			map.remove("AGENT_NAME");
			map.remove("EMPLOYEE_CODE");
			map.remove("AGENT_PHONE");
		}
	}

	@RequestMapping(value = "{caseCode}")
	public JSONObject getCaseInfo(@PathVariable("caseCode") String caseCode) {
		JSONObject result = new JSONObject();
		SessionUser user = sessionService.getSessionUser();

		buildBaseInfo(result,caseCode,user);
		buildMortInfo(result,caseCode,user);
		buildTradeInfo(result,caseCode,user);
		
		buildEplusInfo(result,caseCode,user);
		buildJianguanInfo(result,caseCode,user);
		return result;
	}
	
	private void buildEplusInfo(JSONObject result, String caseCode, SessionUser user) {
		result.put("eplusInfo", new JSONObject());
	}
	
	private void buildJianguanInfo(JSONObject result, String caseCode, SessionUser user) {
		result.put("jianguanInfo", new JSONObject());
	}

	private void buildTradeInfo(JSONObject result,String caseCode,SessionUser user){
		JQGridParam gp = new JQGridParam();
		gp.setQueryId("queryTradeCaseTradeInfoMoblie");
		gp.setPagination(false);
		Map<String, Object> paramMap = gp.getParamtMap();
		paramMap.put("caseCode", caseCode);

		Page<Map<String, Object>> pages = quickGridService.findPageForSqlServer(gp, user);
		List<Map<String, Object>> list = pages.getContent();
		if(list == null || list.get(0) == null){
			return;
		}
		
		Map<String, Object> map = list.get(0);

		
		JSONObject json = new JSONObject();
		
		// 合同价
		if (map.containsKey("conPrice")) {
			json.put("conPrice",map.get("conPrice"));
		}
		// 购房年数
		if (map.containsKey("holdYear")) {
			String holdYear = uamBasedataService.getDictValue(TransDictEnum.TGFNS.getCode(), String.valueOf(map.get("holdYear")));
			json.put("holdYear",holdYear);
		}
		// 是否唯一
		if (map.containsKey("isUniqueHome")) {
			String isUniqueHome = uamBasedataService.getDictValue(TransDictEnum.TWYZF.getCode(), String.valueOf(map.get("isUniqueHome")));
			json.put("isUniqueHome",isUniqueHome);
		}
		// 房屋性质
		if (map.containsKey("houseProperty")) {
			String houseProperty = uamBasedataService.getDictValue(TransDictEnum.TFWXZ.getCode(),
					String.valueOf(map.get("houseProperty")));
			json.put("houseProperty",houseProperty);
		}
		
		// 首期款
		if (map.containsKey("amount1")) {
			json.put("amount1",map.get("amount1"));
		}
		// 首期款
		if (map.containsKey("amount2")) {
			json.put("amount2",map.get("amount2"));
		}
		// 尾款
		if (map.containsKey("amount3")) {
			json.put("amount3",map.get("amount3"));
		}
		// 装修款
		if (map.containsKey("amount4")) {
			json.put("amount4",map.get("amount4"));
		}
		
		//房产税
		if (map.containsKey("houseHodingTax")) {
			json.put("houseHodingTax",map.get("houseHodingTax"));
		}
		//契税 
		if (map.containsKey("contractTax")) {
			json.put("contractTax",map.get("contractTax"));
		}
		//营业税
		if (map.containsKey("yingyeTax")) {
			json.put("yingyeTax",map.get("yingyeTax"));
		}
		
		//营业税
		if (map.containsKey("tudiTax")) {
			json.put("tudiTax",map.get("tudiTax"));
		}
		
		//个人所得
		if (map.containsKey("gerenTax")) {
			json.put("gerenTax",map.get("gerenTax"));
		}
		
		
		result.put("tradeInfo", json);
	}
	
	private void buildBaseInfo(JSONObject result,String caseCode,SessionUser user){
		JQGridParam gp = new JQGridParam();
		gp.setQueryId("queryTradeCaseInfoMoblie");
		gp.setPagination(false);
		Map<String, Object> paramMap = gp.getParamtMap();
		paramMap.put("caseCode", caseCode);

		Page<Map<String, Object>> pages = quickGridService.findPageForSqlServer(gp, user);
		List<Map<String, Object>> list = pages.getContent();
		if(list == null || list.get(0) == null){
			return;
		}
		
		Map<String, Object> map = list.get(0);
		buildQianTaiInfo(map);
		buildZhongjieInfo(list);
		result.put("caseInfo", list.get(0));
		
	}
	
	private  void buildMortInfo(JSONObject result,String caseCode,SessionUser user){
		JQGridParam gp2 = new JQGridParam();
		gp2.setQueryId("queryTradeCaseMortInfoMoblie");
		gp2.setPagination(false);
		Map<String, Object> paramMap2 = gp2.getParamtMap();
		paramMap2.put("caseCode", caseCode);
		Page<Map<String, Object>> mortPage = quickGridService.findPageForSqlServer(gp2, user);
		List<Map<String, Object>> mortList = mortPage.getContent();
		if(mortList == null || mortList.isEmpty() || mortList.get(0) == null){
			result.put("mortInfo", new JSONObject());
			return ;
		}
		Map<String, Object> mortMap = mortList.get(0);
		result.put("mortInfo", mortMap);
	}

	private void buildQianTaiInfo(Map<String, Object> map) {
		String leadingProcessId = map.containsKey("leadingProcessId") ? String.valueOf(map.get("leadingProcessId")) : null;
		User user = userOrgService.getUserById(leadingProcessId);
		
		if(user == null ){
			map.put("qiantai", new JSONObject());
			return ;
		}
		
		String avatar = MessageFormat.format(imgUrl,user.getEmployeeCode()) + ".jpg";
		
		JSONObject json = new JSONObject();
		json.put("name", user.getRealName());
		json.put("avatar", avatar);
		json.put("mobile", user.getMobile());
		
		map.put("qiantai", json);
		map.remove("leadingProcessId");
	}
}
