package com.centaline.trans.remote.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aist.common.exception.BusinessException;
import com.aist.uam.auth.remote.UamSessionService;
import com.aist.uam.auth.remote.vo.SessionUser;
import com.aist.uam.userorg.remote.UamUserOrgService;
import com.aist.uam.userorg.remote.vo.User;
import com.alibaba.fastjson.JSONObject;
import com.centaline.trans.api.service.ApiLogService;
import com.centaline.trans.common.entity.TgGuestInfo;
import com.centaline.trans.common.entity.ToAttachment;
import com.centaline.trans.common.service.TgGuestInfoService;
import com.centaline.trans.common.service.ToAttachmentService;
import com.centaline.trans.eval.entity.ToEvaFeeRecord;
import com.centaline.trans.eval.service.ToEvaFeeRecordService;
import com.centaline.trans.mgr.entity.TsBankEvaRelationship;
import com.centaline.trans.mgr.service.TsBankEvaRelationshipService;
import com.centaline.trans.mortgage.entity.ToEguPricing;
import com.centaline.trans.mortgage.entity.ToEguPropertyInfo;
import com.centaline.trans.mortgage.entity.ToEvaReport;
import com.centaline.trans.mortgage.entity.ToMortgage;
import com.centaline.trans.mortgage.enums.Whether;
import com.centaline.trans.mortgage.service.ToEguPricingService;
import com.centaline.trans.mortgage.service.ToEguPropertyInfoService;
import com.centaline.trans.mortgage.service.ToEvaReportService;
import com.centaline.trans.mortgage.service.ToMortgageService;
import com.centaline.trans.mortgage.vo.ToEvaReportVo;
import com.centaline.trans.remote.Const;
import com.centaline.trans.remote.enums.SC;
import com.centaline.trans.remote.service.EguService;
import com.centaline.trans.remote.util.SignUtil;
import com.centaline.trans.remote.vo.BankInfo;
import com.centaline.trans.remote.vo.BankResultVo;
import com.centaline.trans.remote.vo.BankSearchVo;
import com.centaline.trans.remote.vo.BaseResult;
import com.centaline.trans.remote.vo.DisagreeApplyVo;
import com.centaline.trans.remote.vo.HouseInfoVo;
import com.centaline.trans.remote.vo.MortgageAttamentVo;
import com.centaline.trans.remote.vo.PricingConfirmResultVo;
import com.centaline.trans.remote.vo.PricingConfirmVo;
import com.centaline.trans.remote.vo.PricingResultVo;
import com.centaline.trans.remote.vo.Residence;
import com.centaline.trans.remote.vo.UploadFileResultVo;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import sun.misc.BASE64Encoder;

@Service
public class EguServiceImpl implements EguService {
	
	Logger logger = LoggerFactory.getLogger(EguServiceImpl.class);
	private final String EGU_MODULE ="EGU";
	

	@Autowired
	private ToAttachmentService toAttachmentService;
	
	@Autowired
	private UamUserOrgService uamUserOrgService;
	
	@Autowired
	private UamSessionService uamSessionService;
	
	@Autowired
	private ToEguPricingService toEguPricingService;
	
	@Autowired
	private ToMortgageService toMortgageService;
	
	@Autowired
	private TgGuestInfoService tgGuestInfoService;
	
	@Autowired
	private ToEvaReportService toEvaReportService;
	
	@Autowired
	private ToEvaFeeRecordService toEvaFeeRecordService;
	
	@Autowired
	private TsBankEvaRelationshipService tsBankEvaRelationshipService;
	
	@Autowired
	private ToEguPropertyInfoService toEguPropertyInfoService;
	
	@Autowired
	private ApiLogService apiLogService;
	
	private HttpClient createHttpClient(){
		// 设置Base Auth验证信息
		CredentialsProvider provider = new BasicCredentialsProvider();
		SessionUser u = uamSessionService.getSessionUser();
		User user = uamUserOrgService.getUserById(u.getId());
		UsernamePasswordCredentials credentials = new UsernamePasswordCredentials(
				user.getUsername(), user.getPassword());
		provider.setCredentials(AuthScope.ANY, credentials);
		// 创建HttpClient
		HttpClient client = HttpClientBuilder.create()
				.setDefaultCredentialsProvider(provider).build();
		return client;
	}
	
	private HttpResponse executeGet(String queryUrl)
			throws ClientProtocolException, IOException {
		// 创建HttpClient
		HttpClient client = createHttpClient();
		//HttpGet get = new HttpGet("http://stage.vcainfo.com/v1/" + queryUrl);
		HttpGet get = new HttpGet("http://www.asscol.com/api/v1/" + queryUrl);
		if(logger.isInfoEnabled()){
			logger.info("QueryEgu:"+"http://www.asscol.com/api/v1/" + queryUrl);
		}

		get.addHeader("vc-user-key","20918");
		return client.execute(get);
	}
	@Override
	public String saveAttachment(MortgageAttamentVo mortgageAttament) {
		List<String> preFileAdress = mortgageAttament.getPreFileAdress();
		List<String> attachmentIds = mortgageAttament.getAttachmentIds();
		if(CollectionUtils.isNotEmpty(preFileAdress)){
			ToAttachment attachment = null;
			for(int i=0; i< mortgageAttament.getPreFileAdress().size();i++){
				attachment = new ToAttachment();
				attachment.setPartCode("ToEvaReport");
				attachment.setCaseCode(mortgageAttament.getCaseCode());
				attachment.setPreFileAdress(mortgageAttament.getPreFileAdress().get(i));
				attachment.setPreFileCode(mortgageAttament.getPicTag().get(i));
				attachment.setFileName(mortgageAttament.getPicName().get(i));
				attachment.setFileCat(mortgageAttament.getPicName().get(i).substring(mortgageAttament.getPicName().get(i).indexOf(".")+1));
				toAttachmentService.saveToAttachment(attachment);
			}
			List<ToAttachment> deleteAttachmentList = getDeletedAttachment(attachmentIds,mortgageAttament.getCaseCode());
			if(CollectionUtils.isNotEmpty(deleteAttachmentList)){
				List<Long> ids = new ArrayList<Long>();
				for(ToAttachment att : deleteAttachmentList){
					ids.add(att.getPkid());
				}
				toAttachmentService.delAttachment(ids);
			}
		}
		return null;
	}
	
	public List<ToAttachment> getDeletedAttachment(List<String> attachmentIds,String caseCode){
		ToAttachment toAttachment = new ToAttachment();
		toAttachment.setCaseCode(caseCode);
		toAttachment.setPartCode("ToEvaReport");

		List<ToAttachment> attachmentList = toAttachmentService.quereyAttachments(toAttachment);
		List<ToAttachment> list = new ArrayList<ToAttachment>();
		//获取删除的附件id
		if(CollectionUtils.isNotEmpty(attachmentList)){
			for(ToAttachment attachment : attachmentList){
				for(String id : attachmentIds){
				
					if(id.equals(attachment.getPkid().toString())){
						list.add(attachment);
					}
				}
			}
			attachmentList.removeAll(list);
		}
		return list;
	}
	@Override
	public String getAttachmentInfo(MortgageAttamentVo mortgageAttament){
		StringBuilder json = new StringBuilder();

		if(mortgageAttament != null){
			List<String> preFileAdress = mortgageAttament.getPreFileAdress();
			List<String> picTag = mortgageAttament.getPicTag();
			List<String> picName = mortgageAttament.getPicName();
			List<String> attachmentIds = mortgageAttament.getAttachmentIds();
			Set<String> typeSet = new HashSet<String>();
			if(CollectionUtils.isNotEmpty(picTag)){
				typeSet.addAll(picTag);
			}
			
			List<ToAttachment> attachmentList = getDeletedAttachment(attachmentIds,mortgageAttament.getCaseCode());
			if(CollectionUtils.isNotEmpty(attachmentList)){
				for(ToAttachment toAttachment : attachmentList){
					typeSet.add(toAttachment.getPreFileCode());
				}
			}
			json.append("[");
			Iterator<String> it = typeSet.iterator();
			while(it.hasNext()){
				String type = it.next();
				json.append("{\"type\":\""+type+"\",\"file_ids\":[");
				//存在新增的附件信息
				if(CollectionUtils.isNotEmpty(preFileAdress)){
					for(int j = 0; j < picTag.size(); j++){
						//把type相等的附件放到一块
						if(type.equals(picTag.get(j))){
							json.append("{\"file_id\":\"" + preFileAdress.get(j) + "\",");
							json.append("\"file_type\":\"" + getFileType(picName.get(j)) + "\",");
							json.append("\"add_or_del\":1},");
						}
					}
				}
				//存在删除的附件
				if(CollectionUtils.isNotEmpty(attachmentList)){
					for(ToAttachment att : attachmentList){
						if(type.equals(att.getPreFileCode())){
							json.append("{\"file_id\":\"" + att.getPreFileAdress() + "\",");
							json.append("\"file_type\":\"" + att.getFileCat() + "\",");
							json.append("\"add_or_del\":0},");
						}
					}
				}
				json.deleteCharAt(json.lastIndexOf(","));
				json.append("]},");
			}
			if(json.length() > 3){
				json.deleteCharAt(json.lastIndexOf(","));
			}
			json.append("]");
			if(logger.isDebugEnabled()){
				logger.debug(json.toString());
			}
		}
		return json.toString();
	}
	
	private String getFileType(String fileName){
		return fileName.substring(fileName.indexOf(".") + 1);
	}
	
	@Override
	public void assess(HouseInfoVo houseInfo) throws JsonParseException, JsonMappingException, IOException,BusinessException{
		
		ObjectMapper obj = new ObjectMapper();
		Map<String, String> paramMap = obj.readValue(JSONObject.toJSONString(houseInfo), new TypeReference<Map<String,String>>(){});
		SessionUser user = uamSessionService.getSessionUser();
		paramMap.put("timestamp", String.valueOf(new Date().getTime()));
		paramMap.put("nonce", String.valueOf(new Random().nextInt(9000)+1000));
		paramMap.put("un", user.getUsername());
		paramMap.put("case_id", houseInfo.getCaseCode());
		paramMap = SignUtil.pathFilter(paramMap);
		
		String token = SignUtil.buildRequestToken(paramMap, Const.TOKEN);
		String url = "assess?token="+token+"&"+SignUtil.createLinkString(paramMap);
		HttpResponse httpResponse = executeGet(url);
		HttpEntity entity = httpResponse.getEntity();

		ObjectMapper mapper = new ObjectMapper(); 
		String returnStr = EntityUtils.toString(entity, "UTF-8");
		Header header = entity.getContentType();
		if(header.getValue().indexOf("html") != -1){
			apiLogService.apiLog(EGU_MODULE, "/assess", url, returnStr, "0", returnStr);
			throw new BusinessException("-1",null,"egu接口访问出错！");
		}
		JSONObject object = JSONObject.parseObject(returnStr);
		if(!object.getString("sc").equals(SC.SUCCESS.getCode())){
			if(object.getString("sc").equals(SC.MULTI_ADDRESS.getCode())){
				String sb = null;
				String str = object.getString("msg");
				List<Residence> list = JSONObject.parseArray(str, Residence.class);
				if(CollectionUtils.isNotEmpty(list)){
					sb = JSONObject.toJSONString(list);
				}
				apiLogService.apiLog(EGU_MODULE, "/assess", url, returnStr, "0", returnStr);
				throw new BusinessException(SC.MULTI_ADDRESS.getCode(),null,sb);
			}else{
				apiLogService.apiLog(EGU_MODULE, "/assess", url, returnStr, "0", returnStr);
				throw new BusinessException(object.getString("sc"),null,SC.getValueByCode(object.getString("sc")));
			}
		}
		apiLogService.apiLog(EGU_MODULE, "/assess", url, returnStr, "1", null);
		BaseResult<PricingResultVo> result = mapper.readValue(returnStr,new TypeReference<BaseResult<PricingResultVo>>(){});
		ToEguPropertyInfo toEguPropertyInfo = new ToEguPropertyInfo();
		toEguPropertyInfo.setArea(houseInfo.getArea());
		toEguPropertyInfo.setAreaBasement(houseInfo.getArea_basement());
		toEguPropertyInfo.setAreaGround(houseInfo.getArea_ground());
		toEguPropertyInfo.setBuildingNo(houseInfo.getBuilding_no());
		toEguPropertyInfo.setFloor(houseInfo.getFloor());
		toEguPropertyInfo.setHall(houseInfo.getHall());
		toEguPropertyInfo.setNearStreet(houseInfo.getNear_street());
		toEguPropertyInfo.setPlaneType(houseInfo.getPlane_type());
		toEguPropertyInfo.setPropType(houseInfo.getProp_type());
		toEguPropertyInfo.setRemark(houseInfo.getRemark());

		toEguPropertyInfo.setResidenceName(houseInfo.getResidence_name());
		toEguPropertyInfo.setRoom(houseInfo.getRoom());
		toEguPropertyInfo.setRoomNo(houseInfo.getRoom_no());
		toEguPropertyInfo.setScape(houseInfo.getScape());
		toEguPropertyInfo.setToilet(houseInfo.getToilet());
		toEguPropertyInfo.setTotalFloor(houseInfo.getTotal_floor());
		toEguPropertyInfo.setTowards(houseInfo.getTowards());
		toEguPropertyInfo.setNearStreet(houseInfo.getNear_street());
		
		toEguPropertyInfo.setEvaCode(result.getResult().getCode());
		toEguPropertyInfoService.saveToEguPropertyInfo(toEguPropertyInfo);
		
		ToEguPricing toEguPricing = new ToEguPricing();
		toEguPricing.setAriseTime(new Date());
		toEguPricing.setCaseCode(houseInfo.getCaseCode());
		toEguPricing.setIsFinal(Whether.NO.getCode());
		toEguPricing.setEvaCode(result.getResult().getCode());
		toEguPricing.setResult(result.getResult().getConfirm_code());
			
		toEguPricing.setFinOrgCode(houseInfo.getBank_branch_id());
		toEguPricing.setIsMainLoanBank(houseInfo.getIsMainLoanBank());
		toEguPricing.setExpectRate(houseInfo.getDeal_price()*10000);
		toEguPricing.setAriserId(user.getId());
		toEguPricingService.saveToEguPricing(toEguPricing);
	}
	
	@Override
	public void disagree(DisagreeApplyVo disagreeApply) throws ClientProtocolException, IOException{
		String code = disagreeApply.getCode();
		SessionUser user = uamSessionService.getSessionUser();
		List<ToEguPricing> toPricingList = toEguPricingService.findToEguPricingByEvaCode(code);
		ToEguPricing toPricing = toPricingList.get(0);
		if(StringUtils.isEmpty(toPricing.getApplyCode())){
			throw new BusinessException("询价结果未返回，不能发起异议！");
		}
		ObjectMapper obj = new ObjectMapper();
		Map<String, String> paramMap = obj.readValue(JSONObject.toJSONString(disagreeApply), new TypeReference<Map<String,String>>(){});
		paramMap.put("timestamp", String.valueOf(new Date().getTime()));
		paramMap.put("nonce", String.valueOf(new Random().nextInt(9000)+1000));
		paramMap.put("un", user.getUsername());
		paramMap.put("apply_code", toPricing.getApplyCode());
		paramMap.put("case_id", toPricing.getCaseCode());
		paramMap = SignUtil.pathFilter(paramMap);

		String token = SignUtil.buildRequestToken(paramMap, Const.TOKEN);
		String url = code+"/disagree?token="+token+"&"+SignUtil.createLinkString(paramMap);
		HttpResponse httpResponse = executeGet(url);
		HttpEntity entity = httpResponse.getEntity();
		String returnStr = EntityUtils.toString(entity, "UTF-8");
		Header header = entity.getContentType();
		if(header.getValue().indexOf("html") != -1){
			apiLogService.apiLog(EGU_MODULE, "/disagree", url, returnStr, "0", returnStr);
			throw new BusinessException("egu接口访问出错！");
		}
		JSONObject object = JSONObject.parseObject(returnStr);

		//失败
		if(!object.getString("sc").equals(SC.SUCCESS.getCode())){
			if(StringUtils.isNotBlank(object.getString("msg"))){
				apiLogService.apiLog(EGU_MODULE, "/disagree", url, returnStr, "0", returnStr);
				throw new BusinessException(object.getString("msg"));	
			}else{
				apiLogService.apiLog(EGU_MODULE, "/disagree", url, returnStr, "0", returnStr);
				throw new BusinessException(SC.getValueByCode(object.getString("sc")));	
			}
		}
		apiLogService.apiLog(EGU_MODULE, "/disagree", url, returnStr, "1", null);

		List<ToEguPricing> toEguPricingList = toEguPricingService.findToEguPricingByEvaCode(code);
		ToEguPricing toEguPricing = toEguPricingList.get(0);
		toEguPricing.setResult(object.getJSONObject("result").getString("confirm_code"));
		toEguPricing.setExpectRate(Double.parseDouble(disagreeApply.getExpected_price())*10000);
		toEguPricingService.updateToEguPricing(toEguPricing);
	}
	
	@Override
	public void confirm(PricingConfirmVo pricingConfirm) throws JsonParseException, JsonMappingException, ParseException, IOException{
		String code = pricingConfirm.getCode();
		SessionUser user = uamSessionService.getSessionUser();

		Map<String, String> paramMap = new HashMap<String,String>();
		List<ToEguPricing> toEguPricingList = toEguPricingService.findToEguPricingByEvaCode(code);
		if(CollectionUtils.isEmpty(toEguPricingList)){
			throw new BusinessException("未找到询价记录！");
		}

		ToEguPricing toEguPricing = toEguPricingList.get(0);
		if(StringUtils.isEmpty(toEguPricing.getApplyCode())){
			throw new BusinessException("询价结果未返回，不能确认询价结果！");
		}
		ToEguPricing pricing = toEguPricingService.findIsFinalEguPricing(toEguPricing.getCaseCode());
		if(pricing == null){
			throw new BusinessException("该询价还未接受询价结果！");
		}
		String isMainLoanBank = pricing.getIsMainLoanBank();

		ToMortgage mortgage = new ToMortgage();
		mortgage.setCaseCode(toEguPricing.getCaseCode());
		mortgage.setIsMainLoanBank(isMainLoanBank);
		ToMortgage toMortgage = toMortgageService.findToMortgageByCaseCode(mortgage);
		if(toMortgage == null){
			throw new BusinessException("该询价还未添加贷款信息！");
		}
		//已确认过询价
		if(StringUtils.isNotBlank(toEguPricing.getComfirmSeq())){
			throw new BusinessException("");
		}
		paramMap.put("code", code);
		paramMap.put("timestamp", String.valueOf(new Date().getTime()));
		paramMap.put("nonce", String.valueOf(new Random().nextInt(9000)+1000));
		paramMap.put("un", user.getUsername());
		paramMap.put("case_id", toMortgage.getCaseCode());
		paramMap.put("apply_code", toEguPricing.getApplyCode());
		paramMap.put("lo_name", toMortgage.getLoanerName());
		paramMap.put("lo_phone",toMortgage.getLoanerPhone());
		paramMap = SignUtil.pathFilter(paramMap);
		String token = SignUtil.buildRequestToken(paramMap, Const.TOKEN);
		String url = code+"/confirm?token="+token+"&"+SignUtil.createLinkString(paramMap);
		HttpResponse httpResponse = executeGet(url);
		HttpEntity entity = httpResponse.getEntity();
		Header header = entity.getContentType();
		String returnStr = EntityUtils.toString(entity, "UTF-8");
		if(header.getValue().indexOf("html") != -1){
			apiLogService.apiLog(EGU_MODULE, "/confirm", url, returnStr, "0", returnStr);
			throw new BusinessException("egu接口访问出错！");
		}
		JSONObject object = JSONObject.parseObject(returnStr);

		ObjectMapper mapper = new ObjectMapper(); 
		//失败
		if(!object.getString("sc").equals(SC.SUCCESS.getCode())){
			if(StringUtils.isNotBlank(object.getString("msg"))){
				apiLogService.apiLog(EGU_MODULE, "/confirm", url, returnStr, "0", returnStr);
				throw new BusinessException(object.getString("msg"));	
			}else{
				apiLogService.apiLog(EGU_MODULE, "/confirm", url, returnStr, "0", returnStr);
				throw new BusinessException(SC.getValueByCode(object.getString("sc")));	
			}
		}
		apiLogService.apiLog(EGU_MODULE, "/confirm", url, returnStr, "1", null);
		BaseResult<PricingConfirmResultVo> result = mapper.readValue(returnStr,new TypeReference<BaseResult<PricingConfirmResultVo>>(){});

		toEguPricing.setResult(result.getResult().getConfirm_code());
		toEguPricing.setComfirmSeq(result.getResult().getConfirm_seq());
		toEguPricingService.saveToEguPricing(toEguPricing);
	}
	
	private void upload(String caseCode,String evaCode,MortgageAttamentVo mortgageAttament) throws ClientProtocolException, IOException{
		//手机端发起的询价未做绑定的，不能上传图片和发起报告
		if(StringUtils.isEmpty(mortgageAttament.getCaseCode())){
			throw new BusinessException("案件编号为空，不能上传图片和发起报告申请！");
		}
		
		//附件上传
		ToAttachment toAttachment = new ToAttachment();
		toAttachment.setCaseCode(caseCode);
		toAttachment.setPartCode("ToEvaReport");
		Map<String, String> paramMap = new HashMap<String,String>();
		SessionUser user = uamSessionService.getSessionUser();

		List<ToAttachment> attList = toAttachmentService.quereyAttachments(toAttachment);
		if(CollectionUtils.isNotEmpty(mortgageAttament.getPreFileAdress()) || (CollectionUtils.isNotEmpty(attList)&&attList.size()!=mortgageAttament.getAttachmentIds().size())){
			String files = getAttachmentInfo(mortgageAttament);
			paramMap.put("code", evaCode);
			paramMap.put("timestamp", String.valueOf(new Date().getTime()));
			paramMap.put("nonce", String.valueOf(new Random().nextInt(9000)+1000));
			paramMap.put("un", user.getUsername());
			paramMap.put("case_id", mortgageAttament.getCaseCode());
			paramMap.put("files", new BASE64Encoder().encode(files.getBytes("UTF-8")).replace("\r\n", ""));

			paramMap = SignUtil.pathFilter(paramMap);

			String token = SignUtil.buildRequestToken(paramMap, Const.TOKEN);
			String url = evaCode +"/upload?token=" + token +"&"+ SignUtil.createLinkString(paramMap);
			url = url.replaceAll(" ", "%20");
			HttpResponse httpResponse = executeGet(url);

			HttpEntity entity = httpResponse.getEntity();
			String returnStr = EntityUtils.toString(entity, "UTF-8");
			Header header = entity.getContentType();
			if(header.getValue().indexOf("html") != -1){
				apiLogService.apiLog(EGU_MODULE, "/bank", url, returnStr, "0", returnStr);
				throw new BusinessException("egu接口访问出错！");
			}
			ObjectMapper mapper = new ObjectMapper(); 
			BaseResult<UploadFileResultVo> result = mapper.readValue(returnStr,new TypeReference<BaseResult<UploadFileResultVo>>(){});
			//失败
			if(!result.getSc().equals(SC.SUCCESS.getCode())){
				apiLogService.apiLog(EGU_MODULE, "/bank", url, returnStr, "0", returnStr);
				throw new BusinessException("上传文件操作失败！");
			}	
			apiLogService.apiLog(EGU_MODULE, "/bank", url, returnStr, "1", null);
			//保存附件信息
			saveAttachment(mortgageAttament);

		}
	}
	
	@Override
	public void prereport(ToEvaReportVo evaReport,MortgageAttamentVo mortgageAttament) throws JsonParseException, JsonMappingException, ParseException, IOException{
		String code = evaReport.getEvaCode();
		SessionUser user = uamSessionService.getSessionUser();
		List<ToEguPricing> toPricingList = toEguPricingService.findToEguPricingByEvaCode(evaReport.getEvaCode());
		ToEguPricing toPricing = toPricingList.get(0);
		if(StringUtils.isEmpty(toPricing.getApplyCode())){
			throw new BusinessException("询价结果未返回，不能发起预估单申请！");
		}
		if(StringUtils.isEmpty(toPricing.getComfirmSeq())){
			throw new BusinessException("询价结果未确认，不能发起预估单申请！");
		}
		ToEvaFeeRecord toEvaFeeRecord = toEvaFeeRecordService.findToEvaFeeRecordByCaseCode(evaReport.getCaseCode());
		if(toEvaFeeRecord == null || !StringUtils.equals(toEvaFeeRecord.getIsEvalFeeGet(),"1")){
			throw new BusinessException("该案件的评估费未收齐，不能发起预估单申请！");
		}
		//调用上传接口
		upload(evaReport.getCaseCode(),code,mortgageAttament);
		//发起预估单申请
		Map<String, String> paramMap = new HashMap<String,String>();
		paramMap.put("timestamp", String.valueOf(new Date().getTime()));
		paramMap.put("nonce", String.valueOf(new Random().nextInt(9000)+1000));
		paramMap.put("un", user.getUsername());
		paramMap.put("case_id", mortgageAttament.getCaseCode());

		ToMortgage mortgage = new ToMortgage();
		mortgage.setCaseCode(mortgageAttament.getCaseCode());
		mortgage.setIsMainLoanBank(mortgageAttament.getIsMainLoanBank());
		
		ToMortgage toMortgage = toMortgageService.findToMortgageByCaseCode(mortgage);
		if(toMortgage == null){
			throw new BusinessException("未找到该案件的按揭贷款信息！");
		}
		if(StringUtils.isBlank(toMortgage.getCustName())){
			throw new BusinessException("未找到主贷人名字！");
		}
		paramMap.put("proposer", toMortgage.getCustName());
		paramMap.put("code", evaReport.getEvaCode());
		paramMap.put("confirm_seq", toPricing.getComfirmSeq());
		paramMap.put("hc_upload", "1");

		paramMap = SignUtil.pathFilter(paramMap);
		String token = SignUtil.buildRequestToken(paramMap, Const.TOKEN);
		String url = code+"/prereport?token="+token+"&"+SignUtil.createLinkString(paramMap);
		HttpResponse httpResponse = executeGet(url);
		HttpEntity entity = httpResponse.getEntity();
		String returnStr = EntityUtils.toString(entity, "UTF-8");
		Header header = entity.getContentType();
		if(header.getValue().indexOf("html") != -1){
			apiLogService.apiLog(EGU_MODULE, "/prereport", url, returnStr, "0", returnStr);
			throw new BusinessException("egu接口访问出错！");
		}
		JSONObject object = JSONObject.parseObject(returnStr);

		if(!object.getString("sc").equals(SC.SUCCESS.getCode())){
			if(StringUtils.isNotBlank(object.getString("msg"))){
				apiLogService.apiLog(EGU_MODULE, "/prereport", url, returnStr, "0", returnStr);
				throw new BusinessException(object.getString("msg"));	
			}else{
				apiLogService.apiLog(EGU_MODULE, "/prereport", url, returnStr, "0", returnStr);
				throw new BusinessException(SC.getValueByCode(object.getString("sc")));	
			}

		}
		JSONObject result = object.getJSONObject("result");
		if(result == null || StringUtils.isBlank(result.getString("serial_no"))){
			apiLogService.apiLog(EGU_MODULE, "/prereport", url, returnStr, "0", returnStr);
			throw new BusinessException("egu接口返回数据错误！");
		}
		apiLogService.apiLog(EGU_MODULE, "/prereport", url, returnStr, "1", null);
		ToEvaReport report = new ToEvaReport();
		report.setEvaCode(evaReport.getEvaCode());
		report.setReportType(evaReport.getReportType());

		List<ToEvaReport> toEvaReportList = toEvaReportService.findToEvaReportByEvaCode(report);
		if(CollectionUtils.isNotEmpty(toEvaReportList)){
			toEvaReportList.get(0).setSerialNumber(result.getString("serial_no"));
			toEvaReportService.saveToEvaReport(toEvaReportList.get(0));
		}else{
			ToEvaReport toEvaReport = new ToEvaReport();
			toEvaReport.setCaseCode(evaReport.getCaseCode());
			toEvaReport.setFinOrgCode(evaReport.getFinOrgCode());
			toEvaReport.setReportAriseTime(new Date());
			toEvaReport.setEvaCode(code);
			toEvaReport.setReportType(evaReport.getReportType());
			toEvaReport.setSerialNumber(result.getString("serial_no"));
			toEvaReportService.saveToEvaReport(toEvaReport);	
		}
	}
	@Override
	public void inquiryreport(ToEvaReportVo evaReport,MortgageAttamentVo mortgageAttament) throws JsonParseException, JsonMappingException, ParseException, IOException{
		String code = evaReport.getEvaCode();
		SessionUser user = uamSessionService.getSessionUser();
		List<ToEguPricing> toPricingList = toEguPricingService.findToEguPricingByEvaCode(evaReport.getEvaCode());
		ToEguPricing toPricing = toPricingList.get(0);
		if(StringUtils.isEmpty(toPricing.getApplyCode())){
			throw new BusinessException("询价结果未返回，不能发起询价单申请！");
		}
		if(StringUtils.isEmpty(toPricing.getComfirmSeq())){
			throw new BusinessException("询价结果未确认，不能发起询价单申请！");
		}
		ToEvaFeeRecord toEvaFeeRecord = toEvaFeeRecordService.findToEvaFeeRecordByCaseCode(evaReport.getCaseCode());
		if(toEvaFeeRecord == null || !StringUtils.equals(toEvaFeeRecord.getIsEvalFeeGet(),"1")){
			throw new BusinessException("该案件的评估费未收齐，不能发起询价单申请！");
		}
		//调用上传接口
		upload(evaReport.getCaseCode(),code,mortgageAttament);
		
		Map<String,String> paramMap = new HashMap<String,String>();
		paramMap.put("timestamp", String.valueOf(new Date().getTime()));
		paramMap.put("nonce", String.valueOf(new Random().nextInt(9000)+1000));
		paramMap.put("un", user.getUsername());
		paramMap.put("case_id", mortgageAttament.getCaseCode());
		ToMortgage mortgage = new ToMortgage();
		
		mortgage.setCaseCode(mortgageAttament.getCaseCode());
		mortgage.setIsMainLoanBank(mortgageAttament.getIsMainLoanBank());
		ToMortgage toMortgage = toMortgageService.findToMortgageByCaseCode(mortgage);
		if(toMortgage == null){
			throw new BusinessException("未找到该案件的按揭贷款信息！");
		}
		if(StringUtils.isBlank(toMortgage.getCustName())){
			throw new BusinessException("未找到主贷人名字！");
		}
		paramMap.put("proposer", toMortgage.getCustName());
		paramMap.put("code", evaReport.getEvaCode());
		paramMap.put("confirm_seq", toPricing.getComfirmSeq());
		paramMap.put("hc_upload", "1");

		paramMap = SignUtil.pathFilter(paramMap);
		String token = SignUtil.buildRequestToken(paramMap, Const.TOKEN);
		String url = code+"/inquiryreport?token="+token+"&"+SignUtil.createLinkString(paramMap);
		HttpResponse httpResponse = executeGet(url);
		HttpEntity entity = httpResponse.getEntity();
		String returnStr = EntityUtils.toString(entity, "UTF-8");
		Header header = entity.getContentType();
		if(header.getValue().indexOf("html") != -1){
			apiLogService.apiLog(EGU_MODULE, "/inquiryreport", url, returnStr, "0", returnStr);
			throw new BusinessException("egu接口访问出错！");
		}
		JSONObject object = JSONObject.parseObject(returnStr);

		if(!object.getString("sc").equals(SC.SUCCESS.getCode())){
			if(StringUtils.isNotBlank(object.getString("msg"))){
				apiLogService.apiLog(EGU_MODULE, "/inquiryreport", url, returnStr, "0", returnStr);
				throw new BusinessException(object.getString("msg"));	
			}else{
				apiLogService.apiLog(EGU_MODULE, "/inquiryreport", url, returnStr, "0", returnStr);
				throw new BusinessException(SC.getValueByCode(object.getString("sc")));	
			}
		}
		JSONObject result = object.getJSONObject("result");
		if(result == null || StringUtils.isBlank(result.getString("serial_no"))){
			apiLogService.apiLog(EGU_MODULE, "/inquiryreport", url, returnStr, "0", returnStr);
			throw new BusinessException("egu接口返回数据错误！");
		}
		apiLogService.apiLog(EGU_MODULE, "/inquiryreport", url, returnStr, "1", null);
		ToEvaReport report = new ToEvaReport();
		report.setEvaCode(evaReport.getEvaCode());
		report.setReportType(evaReport.getReportType());

		List<ToEvaReport> toEvaReportList = toEvaReportService.findToEvaReportByEvaCode(report);
		if(CollectionUtils.isNotEmpty(toEvaReportList)){
			toEvaReportList.get(0).setSerialNumber(result.getString("serial_no"));
			toEvaReportService.saveToEvaReport(toEvaReportList.get(0));
		}else{
			ToEvaReport toEvaReport = new ToEvaReport();
			toEvaReport.setCaseCode(evaReport.getCaseCode());
			toEvaReport.setFinOrgCode(evaReport.getFinOrgCode());
			toEvaReport.setReportAriseTime(new Date());
			toEvaReport.setEvaCode(code);
			toEvaReport.setReportType(evaReport.getReportType());
			toEvaReport.setSerialNumber(result.getString("serial_no"));
			toEvaReportService.saveToEvaReport(toEvaReport);	
		}
	}
	@Override
	public void report(ToEvaReportVo evaReport,MortgageAttamentVo mortgageAttament) throws ClientProtocolException, IOException{
		String code = evaReport.getEvaCode();
		SessionUser user = uamSessionService.getSessionUser();
		List<ToEguPricing> toPricingList = toEguPricingService.findToEguPricingByEvaCode(evaReport.getEvaCode());
		ToEguPricing toPricing = toPricingList.get(0);
		if(StringUtils.isEmpty(toPricing.getApplyCode())){
			throw new BusinessException("询价结果未返回，不能发起评估报告申请！");
		}
		if(StringUtils.isEmpty(toPricing.getComfirmSeq())){
			throw new BusinessException("询价结果未确认，不能发起评估报告申请！");
		}
		//先判断评估费是否收齐
		ToEvaFeeRecord toEvaFeeRecord = toEvaFeeRecordService.findToEvaFeeRecordByCaseCode(evaReport.getCaseCode());
		if(toEvaFeeRecord == null || !StringUtils.equals(toEvaFeeRecord.getIsEvalFeeGet(),"1")){
			throw new BusinessException("该案件的评估费未收齐，不能发起评估报告申请！");
		}
		//调用上传接口
		upload(evaReport.getCaseCode(),code,mortgageAttament);
		
		Map<String,String> paramMap = new HashMap<String,String>();
		paramMap.put("timestamp", String.valueOf(new Date().getTime()));
		paramMap.put("nonce", String.valueOf(new Random().nextInt(9000)+1000));
		paramMap.put("un", user.getUsername());
		paramMap.put("case_id", mortgageAttament.getCaseCode());
		ToMortgage mortgage = new ToMortgage();
		mortgage.setCaseCode(mortgageAttament.getCaseCode());
		mortgage.setIsMainLoanBank(mortgageAttament.getIsMainLoanBank());
		ToMortgage toMortgage = toMortgageService.findToMortgageByCaseCode(mortgage);
		if(toMortgage == null){
			throw new BusinessException("未找到该案件的按揭贷款信息！");
		}
		TgGuestInfo tgGuestInfo = tgGuestInfoService.findTgGuestInfoById(Long.parseLong(toMortgage.getCustCode()));
		if(tgGuestInfo == null){
			throw new BusinessException("未找到贷款客户信息！");
		}
		paramMap.put("proposer", tgGuestInfo.getGuestName());
		paramMap.put("proposer_phone", tgGuestInfo.getGuestPhone());
		paramMap.put("code", evaReport.getEvaCode());
		paramMap.put("confirm_seq", toPricing.getComfirmSeq());
		paramMap.put("hc_upload", "1");
		paramMap.put("ic_upload", "1");

		paramMap = SignUtil.pathFilter(paramMap);
		String token = SignUtil.buildRequestToken(paramMap, Const.TOKEN);
		String url = code+"/report?token="+token+"&"+SignUtil.createLinkString(paramMap);
		HttpResponse httpResponse = executeGet(url);
		HttpEntity entity = httpResponse.getEntity();
		String returnStr = EntityUtils.toString(entity, "UTF-8");
		Header header = entity.getContentType();
		if(header.getValue().indexOf("html") != -1){
			apiLogService.apiLog(EGU_MODULE, "/report", url, returnStr, "0", returnStr);
			throw new BusinessException("egu接口访问出错！");
		}
		JSONObject object = JSONObject.parseObject(returnStr);

		if(!object.getString("sc").equals(SC.SUCCESS.getCode())){
			if(StringUtils.isNotBlank(object.getString("msg"))){
				apiLogService.apiLog(EGU_MODULE, "/report", url, returnStr, "0", returnStr);
				throw new BusinessException(object.getString("msg"));	
			}else{
				apiLogService.apiLog(EGU_MODULE, "/report", url, returnStr, "0", returnStr);
				throw new BusinessException(SC.getValueByCode(object.getString("sc")));	
			}
		}
		JSONObject result = object.getJSONObject("result");
		if(result == null || StringUtils.isBlank(result.getString("serial_no"))){
			apiLogService.apiLog(EGU_MODULE, "/report", url, returnStr, "0", returnStr);
			throw new BusinessException("egu接口返回数据错误！");
		}
		apiLogService.apiLog(EGU_MODULE, "/report", url, returnStr, "1", returnStr);
		ToEvaReport report = new ToEvaReport();
		report.setEvaCode(evaReport.getEvaCode());
		report.setReportType(evaReport.getReportType());
		List<ToEvaReport> toEvaReportList = toEvaReportService.findToEvaReportByEvaCode(report);
		if(CollectionUtils.isNotEmpty(toEvaReportList)){
			toEvaReportList.get(0).setSerialNumber(result.getString("serial_no"));
			toEvaReportService.saveToEvaReport(toEvaReportList.get(0));
		}else{
			ToEvaReport toEvaReport = new ToEvaReport();
			toEvaReport.setCaseCode(evaReport.getCaseCode());
			toEvaReport.setFinOrgCode(evaReport.getFinOrgCode());
			toEvaReport.setReportAriseTime(new Date());
			toEvaReport.setEvaCode(code);
			toEvaReport.setSerialNumber(result.getString("serial_no"));
			toEvaReport.setReportType(evaReport.getReportType());
			toEvaReportService.saveToEvaReport(toEvaReport);	
		}
	}
	
	@Override
	public void saveEguBank(BankSearchVo bankSearch) throws JsonParseException, JsonMappingException, ParseException, IOException{
		SessionUser user = uamSessionService.getSessionUser();

		Map<String,String> paramMap = new HashMap<String,String>();
		paramMap.put("timestamp", String.valueOf(new Date().getTime()));
		paramMap.put("nonce", String.valueOf(new Random().nextInt(9000)+1000));
		paramMap.put("un", user.getUsername());
		paramMap.put("parent", bankSearch.getParent());
		paramMap = SignUtil.pathFilter(paramMap);
		String token = SignUtil.buildRequestToken(paramMap, Const.TOKEN);
		String url = "bank?token="+token+"&"+SignUtil.createLinkString(paramMap);
		HttpResponse httpResponse = executeGet(url);
		HttpEntity entity = httpResponse.getEntity();
		String returnStr = EntityUtils.toString(entity, "UTF-8");
		Header header = entity.getContentType();
		if(header.getValue().indexOf("html") != -1){
			apiLogService.apiLog(EGU_MODULE, "/bank", url, returnStr, "0", returnStr);
			throw new BusinessException("egu接口访问出错！");
		}
		ObjectMapper mapper = new ObjectMapper(); 
		
		BankResultVo result = mapper.readValue(returnStr,BankResultVo.class);
		if(!result.getSc().equals(SC.SUCCESS.getCode())){
			apiLogService.apiLog(EGU_MODULE, "/bank", url, returnStr, "0", returnStr);
			throw new BusinessException(SC.getValueByCode(result.getSc()));
		}
		apiLogService.apiLog(EGU_MODULE, "/bank", url, returnStr, "1", null);
		if(CollectionUtils.isNotEmpty(result.getResult())){
			for(BankInfo bankInfo : result.getResult()){
				paramMap.put("parent", bankInfo.getType());
				paramMap.put("limit", "5000");
				paramMap = SignUtil.pathFilter(paramMap);
				token = SignUtil.buildRequestToken(paramMap, Const.TOKEN);
				url = "bank?token="+token+"&"+SignUtil.createLinkString(paramMap);
				httpResponse = executeGet(url);
				entity = httpResponse.getEntity();
				mapper = new ObjectMapper(); 
				returnStr = EntityUtils.toString(entity, "UTF-8");
				result = mapper.readValue(returnStr,BankResultVo.class);
				if(!result.getSc().equals(SC.SUCCESS.getCode())){
					apiLogService.apiLog(EGU_MODULE, "/bank", url, returnStr, "0", returnStr);
					throw new BusinessException(SC.getValueByCode(result.getSc()));
				}
				apiLogService.apiLog(EGU_MODULE, "/bank", url, returnStr, "1", null);
				TsBankEvaRelationship tsBankEvaRelationship = null;
				for(BankInfo bi : result.getResult()){
					tsBankEvaRelationship = new TsBankEvaRelationship();
					tsBankEvaRelationship.setBankCode(bi.getId());
					tsBankEvaRelationship.setEvaCompanyCode("P00021");
					List<TsBankEvaRelationship> bankList = tsBankEvaRelationshipService.findByBankCode(bi.getId());
					if(CollectionUtils.isNotEmpty(bankList)){
						continue;
					}
					tsBankEvaRelationshipService.saveTsBankEvaRelationship(tsBankEvaRelationship);
				}				
			}
		}
	}
}
