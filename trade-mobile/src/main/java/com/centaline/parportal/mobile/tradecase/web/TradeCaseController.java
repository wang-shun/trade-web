package com.centaline.parportal.mobile.tradecase.web;

import java.text.MessageFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
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
import com.aist.uam.userorg.remote.vo.Org;
import com.aist.uam.userorg.remote.vo.User;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.centaline.parportal.mobile.util.Pages2JSONMoblie;
import com.centaline.trans.bizwarn.entity.BizWarnInfo;
import com.centaline.trans.bizwarn.service.BizWarnInfoService;
import com.centaline.trans.cases.entity.ToCase;
import com.centaline.trans.cases.service.ToCaseService;
import com.centaline.trans.common.enums.TransDictEnum;
import com.centaline.trans.common.service.ToModuleSubscribeService;
import com.centaline.trans.common.vo.ToModuleSubscribeVo;

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
	
    @Resource
    private ToModuleSubscribeService toModuleSubscribeService;
	
    @Resource
    private BizWarnInfoService bizWarnInfoService;
    
    @Resource
    private ToCaseService toCaseService;
    @Resource
    private UamUserOrgService uamUserOrgService;
	@RequestMapping(value = "list")
	@ResponseBody
	public String list(@RequestParam(required = true) Integer page,
			@RequestParam(required = true) Integer pageSize, Integer property, Integer status, Boolean onlyFocus,
			Boolean onlyLoanLostAlert, String q_text) {
		SessionUser user = sessionService.getSessionUser();
		JQGridParam gp = new JQGridParam();
		gp.setQueryId("queryTradeCastListMoblie");
		gp.setPage(page);
		gp.setRows(pageSize);

		Map<String, Object> paramMap = gp.getParamtMap();
		paramMap.put("q_text", q_text);
		if (null != onlyFocus && onlyFocus) {
			paramMap.put("onlyFocus", onlyFocus);
		}
		paramMap.put("status", status);
		//全部案件
		if(null != property && 30003006 != property){
			paramMap.put("property", property);
		}
		if (null != onlyLoanLostAlert && onlyLoanLostAlert) {
			paramMap.put("onlyLoanLostAlert", onlyLoanLostAlert);
		}
		Page<Map<String, Object>> pages = quickGridService.findPageForSqlServer(gp, user);
		List<Map<String, Object>> list = pages.getContent();
		buildZhongjieInfo(list);

		JSON json = Pages2JSONMoblie.pages2JsonMoblie(pages);
		String resultStr = JSON.toJSONString(json, SerializerFeature.WriteMapNullValue);
		return resultStr;
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
			if(map.containsKey("org")){
				json.put("org",map.get("org"));
			}
			
			map.put("zhongjie", json);
			
			map.remove("AGENT_NAME");
			map.remove("EMPLOYEE_CODE");
			map.remove("AGENT_PHONE");
		}
	}

	@RequestMapping(value = "{caseCode}")
	@ResponseBody
	public String getCaseInfo(@PathVariable("caseCode") String caseCode) {
		JSONObject result = new JSONObject();
		SessionUser user = sessionService.getSessionUser();

		buildBaseInfo(result,caseCode,user);
		buildMortInfo(result,caseCode,user);
		buildTradeInfo(result,caseCode,user);
		
		buildEplusInfo(result,caseCode,user);
		buildJianguanInfo(result,caseCode,user);
		return result.toJSONString();
	}
	
	private void buildEplusInfo(JSONObject result, String caseCode, SessionUser user) {
		result.put("eplusInfo", new JSONObject());
	}
	
	private void buildJianguanInfo(JSONObject result, String caseCode, SessionUser user) {
		result.put("jianguanInfo", new JSONObject());
	}

	private void buildTradeInfo(JSONObject result,String caseCode,SessionUser user){
		
		Map<String,Object> jo = (Map<String,Object>)result.get("caseInfo");
		if("未分单".equals(jo.get("status") + "")){
			result.put("tradeInfo", new JSONObject());
			return;
		}
		
		JQGridParam gp = new JQGridParam();
		gp.setQueryId("queryTradeCaseTradeInfoMoblie");
		gp.setPagination(false);
		Map<String, Object> paramMap = gp.getParamtMap();
		paramMap.put("caseCode", caseCode);

		Page<Map<String, Object>> pages = quickGridService.findPageForSqlServer(gp, user);
		List<Map<String, Object>> list = pages.getContent();
		if(list == null || list.isEmpty()){
			result.put("tradeInfo", new JSONObject());
			return;
		}
		
		Map<String, Object> map = list.get(0);

		
		// 购房年数
		if (map.containsKey("year")) {
			String holdYear = uamBasedataService.getDictValue(TransDictEnum.TGFNS.getCode(), String.valueOf(map.get("year")));
			map.put("year",StringUtils.isBlank(holdYear) ? "" : holdYear);
		}
		// 房屋性质
		if (map.get("houseProperty") != null ) {
			String houseProperty = uamBasedataService.getDictValue(TransDictEnum.TFWXZ.getCode(),
					String.valueOf(map.get("houseProperty")));
			map.put("houseProperty",StringUtils.isBlank(houseProperty) ? "" : houseProperty);
		}else{
			map.put("houseProperty","");
		}
		
		result.put("tradeInfo", map);
	}
	
	private void buildShangxiajiaInfo(Map<String, Object> map){
		if(map.containsKey("shangjia")){
			Object obj = map.get("shangjia");
			if(null != obj && obj instanceof String){
				map.put("shangjia", JSON.parseArray(obj.toString()));
			}
		}
		if(map.containsKey("xiajia")){
			Object obj = map.get("xiajia");
			if(null != obj && obj instanceof String){
				map.put("xiajia", JSON.parseArray(obj.toString()));
			}
		}
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
		if(!map.containsKey("houtai")){
			map.put("houtai", new JSONArray());
		}
		buildShangxiajiaInfo(map);
		buildQianTaiInfo(map);
		buildZhongjieInfo(list);
		buildZhuliInfo(map,caseCode);
		result.put("caseInfo", list.get(0));
		
	}
	
	private void buildZhuliInfo(Map<String, Object> map,String caseCode) {
		// 助理
		JQGridParam gp = new JQGridParam();
		gp.setQueryId("queryTradeCaseZhuliMoblie");
		gp.setPagination(false);
		Map<String, Object> paramMap = gp.getParamtMap();
		paramMap.put("caseCode", caseCode);
		Page<Map<String, Object>> pages = quickGridService.findPageForSqlServer(gp);
		
		List<Map<String,Object>> asList = pages.getContent();
		JSONObject ja = new JSONObject();
		if (asList != null && asList.size() > 0) {
			Map<String,Object> reusltMap = asList.get(0);
			ja.put("name", reusltMap.get("name"));
			Object ec = reusltMap.get("employeeCode");
			ja.put("avatar", ec == null ? "" : MessageFormat.format(imgUrl,ec) + ".jpg");
			ja.put("org", reusltMap.get("org"));
			ja.put("mobile", reusltMap.get("moblie"));
		}
		map.put("zhuli", ja);
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
		json.put("org", user.getOrgName());
		
		map.put("qiantai", json);
		map.remove("leadingProcessId");
	}
	
	
	@RequestMapping(value = "{caseCode}/process")
	@ResponseBody
	public String process(@PathVariable("caseCode") String caseCode ) {
		SessionUser user = sessionService.getSessionUser();
		JQGridParam gp = new JQGridParam();
		gp.setQueryId("getProcessListMobile");
		gp.setPagination(false);
		Map<String, Object> paramMap = gp.getParamtMap();
		paramMap.put("caseCode", caseCode);

		Page<Map<String, Object>> pages = quickGridService.findPageForSqlServer(gp, user);
		List<Map<String, Object>> list = pages.getContent();
		JSONArray ja = (JSONArray) JSONArray.toJSON(list);
		return ja.toJSONString();
	}
	
	
	@RequestMapping(value = "{caseCode}/focus")
	@ResponseBody
	public String focus( @PathVariable("caseCode") String caseCode,
			@RequestParam(required = true)Boolean action){
		SessionUser user = sessionService.getSessionUser();
		ToModuleSubscribeVo vo = new ToModuleSubscribeVo();
		vo.setModuleCode(caseCode);
		vo.setSubscriberId(user.getId());
		vo.setSubscribeType("2001");
		vo.setModuleType("1001");
		vo.setIsSubscribe(action);
        toModuleSubscribeService.saveOrDeleteCaseSubscribe(vo);
        JSONObject jo = new JSONObject();
        jo.put("success", true);
        return jo.toJSONString();
	}
	
	@RequestMapping(value = "{caseCode}/loanLostAlert")
	@ResponseBody
	public String loanLostAlert(@PathVariable("caseCode")String caseCode,
			@RequestParam(required = true)Boolean action,@RequestParam(required = true)String reason){
		BizWarnInfo bizWarnInfo = new BizWarnInfo();
		
		ToCase  toCase  = toCaseService.findToCaseByCaseCode(caseCode);
		SessionUser currentUser = sessionService.getSessionUser();
		
		bizWarnInfo.setCaseCode(caseCode);
		bizWarnInfo.setContent(reason);
		bizWarnInfo.setWarnType("LOANLOSS");
		bizWarnInfo.setCreateBy(currentUser.getId());
		bizWarnInfo.setParticipant(toCase.getLeadingProcessId());
		
		if(!StringUtils.isBlank(toCase.getOrgId())){
			Org currentOrg = uamUserOrgService.getOrgById(toCase.getOrgId());
			Org parentOrg = uamUserOrgService.getOrgById(currentOrg.getParentId());
			bizWarnInfo.setTeamId(toCase.getOrgId());
			bizWarnInfo.setDistrictId(parentOrg.getId());
		}
		bizWarnInfo.setStatus("0");
		bizWarnInfo.setCreateTime(new Date());
		bizWarnInfo.setWarnTime(new Date());
		bizWarnInfo.setCreateBy(currentUser.getId());
		bizWarnInfo.setParticipant(currentUser.getId());
		if(action){
			BizWarnInfo  bwi = bizWarnInfoService.getBizWarnInfoByCaseCode(caseCode);
			if(bwi == null){
				bizWarnInfoService.insertSelective(bizWarnInfo);
			}else{
				bizWarnInfoService.updateByCaseCode(bizWarnInfo);
			}
		}else{
			bizWarnInfoService.deleteByCaseCode(caseCode);
		}
        JSONObject jo = new JSONObject();
        jo.put("success", true);
        return jo.toJSONString();
	}
}
