package com.centaline.parportal.mobile.tradecase.web;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.aist.common.web.validate.AjaxResponse;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
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
import com.centaline.parportal.mobile.track.vo.CommentVo;
import com.centaline.trans.attachment.entity.ToAttachment;
import com.centaline.trans.attachment.service.ToAttachmentService;
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
import com.centaline.trans.utils.Pages2JSONMoblie;

@RestController
@RequestMapping(value = "/tradeCase")
public class TradeCaseController {

	private Logger logger = Logger.getLogger(TradeCaseController.class);

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
    @Autowired
    private ToAttachmentService toAttachmentService;
	@RequestMapping(value = "list")
	@ResponseBody
	public JSONObject list(@RequestParam(required = true) Integer page,
			@RequestParam(required = true) Integer pageSize, Integer property, Integer status, Boolean onlyFocus,
			Boolean onlyLoanLostAlert, String q_text) {
		SessionUser user = sessionService.getSessionUser();
		JQGridParam gp = new JQGridParam();
		gp.setQueryId("queryTradeCaseListMoblie");
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

		JSONObject json = Pages2JSONMoblie.pages2JsonMoblie(pages);
		return json;
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
	public JSONObject getCaseInfo(@PathVariable("caseCode") String caseCode) {
		JSONObject result = new JSONObject();
		SessionUser user = sessionService.getSessionUser();

		buildBaseInfo(result,caseCode,user);
		buildMortInfo(result,caseCode,user);
		buildTradeInfo(result,caseCode,user);
		
		buildEplusInfo(result,caseCode,user);
		buildJianguanInfo(result, caseCode, user);
		return result;
	}
	
	private void buildEplusInfo(JSONObject result, String caseCode, SessionUser user) {
		JQGridParam gp = new JQGridParam();
		gp.setQueryId("queryEplusInfo");
		gp.setPagination(false);
		Map<String, Object> paramMap = gp.getParamtMap();
		paramMap.put("caseCode", caseCode);

		Page<Map<String, Object>> pages = quickGridService.findPageForSqlServer(gp, user);
		List<Map<String, Object>> list = pages.getContent();
		if(list == null || list.isEmpty()){
			result.put("eplusInfo", new JSONObject());
			return;
		}
		generateWantData(list, "queryEplusInfo");
		result.put("eplusInfo", list);
	}
	
	

	private void buildJianguanInfo(JSONObject result, String caseCode, SessionUser user) {
		
		JQGridParam gp = new JQGridParam();
		gp.setQueryId("queryJianguanInfo");
		gp.setPagination(false);
		Map<String, Object> paramMap = gp.getParamtMap();
		paramMap.put("caseCode", caseCode);

		Page<Map<String, Object>> pages = quickGridService.findPageForSqlServer(gp, user);
		List<Map<String, Object>> list = pages.getContent();
		if(list == null || list.isEmpty()){
			result.put("jianguanInfo", new JSONObject());
			return;
		}
		generateWantData(list, "jianguanInfo");
		
		result.put("jianguanInfo", list);
	}

	private void buildTradeInfo(JSONObject result,String caseCode,SessionUser user){
		
		Map<String,Object> jo = (Map<String,Object>)result.get("caseInfo");
		if(jo == null || jo.isEmpty() || "未分单".equals(jo.get("status") + "")){
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
		if(CollectionUtils.isEmpty(list)){
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
	public Object process(@PathVariable("caseCode") String caseCode ) {
		AjaxResponse ajaxResponse = new AjaxResponse();
		SessionUser user = sessionService.getSessionUser();
		JQGridParam gp = new JQGridParam();
		gp.setQueryId("getProcessListMobile");
		gp.setPagination(false);
		Map<String, Object> paramMap = gp.getParamtMap();
		paramMap.put("caseCode", caseCode);

		Page<Map<String, Object>> pages = quickGridService.findPageForSqlServer(gp, user);
		List<Map<String, Object>> list = pages.getContent();
		ajaxResponse.setContent(list);

		return ajaxResponse;
	}
	
	
	@RequestMapping(value = "{caseCode}/focus")
	@ResponseBody
	public Object focus( @PathVariable("caseCode") String caseCode,
			@RequestParam(required = true)Boolean action){
		SessionUser user = sessionService.getSessionUser();
		AjaxResponse ajaxResponse = new AjaxResponse();
		ToModuleSubscribeVo vo = new ToModuleSubscribeVo();
		vo.setModuleCode(caseCode);
		vo.setSubscriberId(user.getId());
		vo.setSubscribeType("2001");
		vo.setModuleType("1001");
		vo.setIsSubscribe(action);
		vo.setRemark("");
		try{
		   toModuleSubscribeService.saveOrDeleteCaseSubscribe(vo);
			ajaxResponse.setSuccess(true);
			ajaxResponse.setMessage("关注成功");
		}catch (BusinessException e){
			e.printStackTrace();
			ajaxResponse.setSuccess(false);
			ajaxResponse.setMessage(e.getMessage());
		}
        return ajaxResponse;
	}
	
	@RequestMapping(value = "{caseCode}/loanLostAlert")
	@ResponseBody
	public JSONObject loanLostAlert(@PathVariable("caseCode")String caseCode,
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
        return jo;
	}
	
	
    @RequestMapping(value = "{caseCode}/track")
    @ResponseBody
    public JSONObject addTrack(@PathVariable("caseCode")String caseCode, CommentVo cmtVo) {

    	cmtVo.setCaseCode(caseCode);
    	
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
        	JSONObject json = new JSONObject();
        	json.put("msg", track.getCaseCode() + "跟进保存成功!");
            return json;
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
    
    private void generateWantData(List<Map<String, Object>> list,String type) {
		Map<String,Object> map = null;
		if("queryEplusInfo".equals(type)){
			for(Map<String, Object> singleMap:list){
				map = new HashMap<String,Object>();
				// 申请时间
				if (null!=singleMap.get("applyTime")) {
					String applyTime = singleMap.get("applyTime").toString();
					map.put("applyTime", StringUtils.isBlank(applyTime) ? "" : applyTime);
				}else{
					map.put("applyTime","");
				}
				//申请金额
				if (null!=singleMap.get("amount")) {
					String amount = singleMap.get("amount").toString();
					map.put("applyAmount", StringUtils.isBlank(amount) ? "" : amount);
				}else{
					map.put("applyAmount","");
				}
				//签约时间
				if (null!=singleMap.get("signTime")) {
					String signTime = singleMap.get("signTime").toString();
					map.put("signTime", StringUtils.isBlank(signTime) ? "" : signTime);
				}else{
					map.put("signTime","");
				}
				//签约金额
				if (null!=singleMap.get("signAmount")) {
					String signAmount = singleMap.get("signAmount").toString();
					map.put("signAmount", StringUtils.isBlank(signAmount) ? "" : signAmount);
				}else{
					map.put("signAmount","");
				}
				//放款时间
				if (null!=singleMap.get("releaseTime")) {
					String releaseTime = singleMap.get("releaseTime").toString();
					map.put("releaseTime", StringUtils.isBlank(releaseTime) ? "" : releaseTime);
				}else{
					map.put("releaseTime","");
				}
				//放款金额
				if (null!=singleMap.get("releaseAmount")) {
					String releaseAmount = singleMap.get("releaseAmount").toString();
					map.put("releaseAmount", StringUtils.isBlank(releaseAmount) ? "" : releaseAmount);
				}else{
					map.put("releaseAmount","");
				}
				
				singleMap.put("progress", map);
				singleMap.remove("LOAN_SRV_CODE");
				singleMap.remove("LOANER_ORG_CODE");
				singleMap.remove("EXCUTOR_ID");
				singleMap.remove("EXCUTOR_TEAM");
				singleMap.remove("applyTime");
				singleMap.remove("signAmount");
				singleMap.remove("releaseTime");
				singleMap.remove("signTime");
				singleMap.remove("releaseAmount");
			}
		}else if("jianguanInfo".equals(type)){
			for(Map<String, Object> singleMap:list){
				map = new HashMap<String,Object>();
				// 申请时间
				if (null!=singleMap.get("applyTime")) {
					String applyTime = singleMap.get("applyTime").toString();
					map.put("applyTime", StringUtils.isBlank(applyTime) ? "" : applyTime);
				}else{
					map.put("applyTime","");
				}
				
				//签约时间
				if (null!=singleMap.get("signTime")) {
					String signTime = singleMap.get("signTime").toString();
					map.put("signTime", StringUtils.isBlank(signTime) ? "" : signTime);
				}else{
					map.put("signTime","");
				}
				
				//结束时间
				if (null!=singleMap.get("closeTime")) {
					String closeTime = singleMap.get("closeTime").toString();
					map.put("closeTime", StringUtils.isBlank(closeTime) ? "" : closeTime);
				}else{
					map.put("closeTime","");
				}
				singleMap.put("progress", map);
				singleMap.remove("APPLY_USER");
				singleMap.remove("RISK_CONTROL_OFFICER");
				singleMap.remove("applyTime");
				singleMap.remove("signTime");
				singleMap.remove("closeTime");
			}
			
		}
	}
    @RequestMapping(value = "{caseCode}/fileUpload" )
	@ResponseBody
	public Object fileUpload(@RequestParam(required = true)String fileList,@PathVariable(required=true,name="caseCode") String caseCode,String partCode) {

		AjaxResponse ajaxResponse = new AjaxResponse();
		JSONArray fileListArray= JSONArray.parseArray(fileList);
		List<ToAttachment>toAttachments=new ArrayList<>();
    	try{
			for (Object object : fileListArray) {
				JSONObject file=(JSONObject)object;
				ToAttachment attach=new ToAttachment();
				attach.setAvailable("Y");
				attach.setPartCode(partCode);
				attach.setPreFileAdress(file.getString("fileID"));
				attach.setPreFileCode(file.getString("fileCode"));
				attach.setFileCat(file.getString("fileCat"));
				attach.setFileName(file.getString("fileName"));
				attach.setCaseCode(caseCode);
				toAttachments.add(attach);
			}
			toAttachmentService.saveToAttachment(toAttachments);
			ajaxResponse.setSuccess(true);
			ajaxResponse.setMessage("保存成功");
		}catch(Exception e){
			e.printStackTrace();
			logger.error(e.getMessage());
			ajaxResponse.setSuccess(false);
			ajaxResponse.setMessage("保存失败");
		}
		return ajaxResponse;
    }

}
