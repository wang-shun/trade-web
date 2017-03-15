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

import com.aist.common.exception.BusinessException;
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
import com.centaline.parportal.mobile.track.vo.CommentVo;
import com.centaline.parportal.mobile.util.Pages2JSONMoblie;
import com.centaline.trans.bizwarn.entity.BizWarnInfo;
import com.centaline.trans.bizwarn.service.BizWarnInfoService;
import com.centaline.trans.cases.entity.ToCase;
import com.centaline.trans.cases.service.ToCaseService;
import com.centaline.trans.comment.entity.ToCaseComment;
import com.centaline.trans.comment.service.ToCaseCommentService;
import com.centaline.trans.common.enums.DepTypeEnum;
import com.centaline.trans.common.enums.TransDictEnum;
import com.centaline.trans.common.enums.TransJobs;
import com.centaline.trans.common.service.ToModuleSubscribeService;
import com.centaline.trans.common.vo.MobileHolder;
import com.centaline.trans.common.vo.ToModuleSubscribeVo;
import com.centaline.trans.mortgage.service.ToMortgageService;
import com.centaline.trans.stuff.enums.CommentType;
import com.centaline.trans.track.service.TrackService;
import com.centaline.trans.utils.BeanUtils;

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
    
    @Autowired
    private ToCaseCommentService toCaseCommentService;

    @Autowired
    private ToMortgageService    toMortgageService;
    
    @Autowired
    private TrackService trackService;
    
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
		
		buildDataAuthorityParam(paramMap,user);
		
		if(!StringUtils.isBlank(q_text)){
			paramMap.put("q_text", q_text);
		}

		if (null != onlyFocus && onlyFocus) {
			paramMap.put("onlyFocus", onlyFocus);
		}
		paramMap.put("status", status);
		//全部案件
		if(null != property && 30003006 != property){
			paramMap.put("property", property);
		}
		if(!Integer.valueOf(30003002).equals(property)){
			paramMap.put("isNotResearchCloseCase", true);
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
	
	private void buildDataAuthorityParam(Map<String, Object> paramMap,SessionUser user){
		boolean isAdminFlag = false;

        StringBuffer reBuffer = new StringBuffer();
        //如果登录用户不是交易顾问
		if(!TransJobs.TJYGW.getCode().equals(user.getServiceJobCode())){
			String depString = user.getServiceDepHierarchy();
			String userOrgIdString = user.getServiceDepId();
			//组别
			if(depString.equals(DepTypeEnum.TYCTEAM.getCode())){
				reBuffer.append(userOrgIdString);
			//区域
			}else if(depString.equals(DepTypeEnum.TYCQY.getCode())){
				List<Org> orgList = uamUserOrgService.getOrgByDepHierarchy(userOrgIdString, DepTypeEnum.TYCTEAM.getCode());
				for(Org org:orgList){
					reBuffer.append(org.getId());
					reBuffer.append(",");
				}
				reBuffer.deleteCharAt(reBuffer.length()-1);
				
			}else{
				isAdminFlag=true;
			}
		}
		paramMap.put("queryorgs", reBuffer.toString().split(","));
		if(!isAdminFlag && StringUtils.isBlank(reBuffer.toString())){
			paramMap.put("idflag", true);
		}
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
		String orgId = String.valueOf(map.get("orgId"));
		List<User> asList = uamUserOrgService.getUserByOrgIdAndJobCode(orgId, TransJobs.TJYZL.getCode());
		
//		List<Map<String,Object>> asList = pages.getContent();
		JSONObject ja = new JSONObject();
		if (asList != null && asList.size() > 0) {
			User user = asList.get(0);
			ja.put("name", user.getRealName());
			ja.put("avatar", StringUtils.isBlank(user.getEmployeeCode()) ? "" : MessageFormat.format(imgUrl,user.getEmployeeCode()) + ".jpg");
			ja.put("org", user.getOrgName());
			ja.put("mobile", user.getMobile());
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
	
	
    @RequestMapping(value = "{caseCode}/track")
    @ResponseBody
    public String addTrack(@PathVariable("caseCode")String caseCode, CommentVo cmtVo) {

    	cmtVo.setCaseCode(caseCode);
    	SessionUser user = sessionService.getSessionUser();
    	
        ToCaseComment track = new ToCaseComment();
        //        boolean isNofigyCustomer = cmt.isNotifyCustomer();
        BeanUtils.copyProperties(cmtVo, track);
        //检查track的完整性。不完整时抛出业务异常
        this.checkTrackIntegrity(track);
        int resultCount = 0;

        switch (CommentType.valueOf(track.getType())) {
            case CMT:
                resultCount = toCaseCommentService.insertToCaseComment(track);
                break;
            case BUJIAN:
                Boolean isNotifyCustomer = cmtVo.getIsNotifyCustomer() != null
                    ? cmtVo.getIsNotifyCustomer() : false;
               resultCount=trackService.buJian(track, isNotifyCustomer);
                break;
            case REJECT:
                break;
            case TRACK:
                resultCount = toMortgageService.addMortgageTrack4Par(track);
                break;
            default:
                break;
        }

        if (resultCount <= 0) {
            throw new BusinessException("对不起,跟进保存失败");
        } else {
            return new StringBuilder("{\"msg\":\"").append(track.getCaseCode()).append("跟进保存成功")
                .append("\"}").toString();
        }

    }
    
    private boolean checkTrackIntegrity(ToCaseComment track) {

        if (null == track)
            throw new BusinessException("抱歉，提交的跟进为空");
        if (null == track.getSource())
            throw new BusinessException("抱歉，提交的跟进source为空,请联系技术支持");
        if (null == track.getType())
            throw new BusinessException("抱歉，提交的跟进type为空,请联系技术支持");
        if (null == track.getBizCode())
            throw new BusinessException("抱歉，提交的跟进bizCode为空,请联系技术支持");
        if (MobileHolder.getMobileUser() != null)
            track.setCreatorOrgId(MobileHolder.getMobileUser().getServiceDepId());

        return false;
    }
}
