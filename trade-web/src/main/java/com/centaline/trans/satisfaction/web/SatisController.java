package com.centaline.trans.satisfaction.web;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.aist.common.web.validate.AjaxResponse;
import com.aist.uam.auth.remote.UamSessionService;
import com.aist.uam.userorg.remote.UamUserOrgService;
import com.aist.uam.userorg.remote.vo.User;
import com.centaline.trans.cases.entity.ToCase;
import com.centaline.trans.cases.entity.ToCaseInfo;
import com.centaline.trans.cases.service.ToCaseInfoService;
import com.centaline.trans.cases.service.ToCaseService;
import com.centaline.trans.cases.vo.CaseDetailProcessorVO;
import com.centaline.trans.cases.vo.CaseDetailShowVO;
import com.centaline.trans.common.entity.TgGuestInfo;
import com.centaline.trans.common.entity.TgServItemAndProcessor;
import com.centaline.trans.common.entity.ToPropertyInfo;
import com.centaline.trans.common.enums.TransJobs;
import com.centaline.trans.common.enums.TransPositionEnum;
import com.centaline.trans.common.service.TgGuestInfoService;
import com.centaline.trans.common.service.TgServItemAndProcessorService;
import com.centaline.trans.common.service.ToPropertyInfoService;
import com.centaline.trans.common.service.impl.PropertyUtilsServiceImpl;
import com.centaline.trans.engine.bean.ProcessInstance;
import com.centaline.trans.engine.bean.RestVariable;
import com.centaline.trans.engine.service.WorkFlowManager;
import com.centaline.trans.engine.vo.StartProcessInstanceVo;
import com.centaline.trans.satisfaction.entity.ToSatisfaction;
import com.centaline.trans.satisfaction.service.SatisfactionService;

@Controller
@RequestMapping("satis")
public class SatisController {
	
	@Autowired
	private SatisfactionService satisfactionService;
	@Autowired
	ToCaseService toCaseService;
	@Autowired
	ToCaseInfoService toCaseInfoService;
	@Autowired
	ToPropertyInfoService toPropertyInfoService;
	@Autowired
	TgServItemAndProcessorService tgServItemAndProcessorService;
	@Autowired
	TgGuestInfoService tgGuestInfoService;
	@Autowired
	PropertyUtilsServiceImpl propertyUtilsService;
	
	@Autowired
	UamUserOrgService uamUserOrgService;
	@Autowired
	UamSessionService uamSessionService;
	
	@Autowired
	WorkFlowManager workFlowManager;
	
	//页面
	@RequestMapping("/list")
	public String list(Model model){
		//消息sendSatisFinishMsgByIntermi
		//List<ToSatisfaction> satisfactionList = satisfactionService.queryToSatisfactionList();
		//model.addAttribute("satisfactionList", satisfactionList);
		return "satis/satisList";
	}
	
	@RequestMapping("/signDetail")
	public String signDetail(Model model, Long satisId){
		
		setCaseInfoToModel(model, satisId);
		
		return "satis/sign_detail";
	}
	
	@RequestMapping("/guohuDetail")
	public String guohuDetail(Model model, Long satisId){
		
		setCaseInfoToModel(model, satisId);
		
		return "satis/guohu_detail";
	}
	
	@RequestMapping("/signReturn")
	public String signReturn(Model model, Long satisId){
		
		setCaseInfoToModel(model, satisId);
				
		return "satis/sign_return";
	}
	
	@RequestMapping("/guohuReturn")
	public String guohuReturn(Model model, Long satisId){
	
		setCaseInfoToModel(model , satisId);
		
		return "satis/guohu_return";
	}
	
	//操作
	@RequestMapping("dispatch")
	public AjaxResponse<String> dispatch(List<String> caseCodes,String userId){
		AjaxResponse<String> response = new AjaxResponse<String>();
		uamSessionService.getSessionUserById(userId);
		try{
			List<RestVariable> variables = new ArrayList<RestVariable>();
			variables.add(new RestVariable("",""));
			variables.add(new RestVariable("",""));
/*			StartProcessInstanceVo vo = workFlowManager.startWorkFlow(new ProcessInstance(propertyUtilsService.getSatisProcessDfKey(),
					businessKey, variables));*/
			response.setSuccess(true);
			response.setMessage("操作成功！");
		}catch(Exception e){
			response.setSuccess(false);
			response.setMessage("操作失败!"+e.getMessage());
		}
		return response;
	}
	
	@RequestMapping("signPass")
	public AjaxResponse<String> signPass(ToSatisfaction toSatisfaction){
		AjaxResponse<String> response = new AjaxResponse<String>();
		try{
			//1.更新状态
			//SurveyStatusEnum.SIGN_SURVEY_PASS;
			//surveyService.updateSurveyVO(surveyVO);
			//2.设置流程变量
			List<RestVariable> variables = new ArrayList<RestVariable>();
			variables.add(new RestVariable("signResult",true));
			//3.推进流程
			//workFlowManager.submitTask(variables, taskId, processInstanceId, null, caseCode);
			response.setSuccess(true);
			response.setMessage("操作成功！");
		}catch(Exception e){
			response.setSuccess(false);
			response.setMessage("操作失败！"+e.getMessage());
		}
		return response;
	}
	
	@RequestMapping("signReject")
	public AjaxResponse<String> signReject(ToSatisfaction toSatisfaction){
		AjaxResponse<String> response = new AjaxResponse<String>();
		try{
			//1.更新状态
			//SurveyStatusEnum.SIGN_SURVEY_REJECT;
			//surveyService.updateSurveyVO(surveyVO);
			//2.设置流程变量
			List<RestVariable> variables = new ArrayList<RestVariable>();
			variables.add(new RestVariable("signResult",false));
			//3.推进流程
			//workFlowManager.submitTask(variables, taskId, processInstanceId, null, caseCode);
			response.setSuccess(true);
			response.setMessage("操作成功！");
		}catch(Exception e){
			response.setSuccess(false);
			response.setMessage("操作失败！"+e.getMessage());
		}
		return response;
	}
	
	@RequestMapping("signFollow")
	public AjaxResponse<String> signFollow(){
		AjaxResponse<String> response = new AjaxResponse<String>();
		try{
			response.setSuccess(true);
			response.setMessage("操作成功！");
		}catch(Exception e){
			response.setSuccess(false);
			response.setMessage("操作失败！"+e.getMessage());
		}
		return response;
	}
	
	@RequestMapping("transferPass")
	public AjaxResponse<String> transferPass(ToSatisfaction toSatisfaction){
		AjaxResponse<String> response = new AjaxResponse<String>();
		try{
			//1.更新状态
//			SurveyStatusEnum.TRANSFER_SURVEY_PASS;
			//surveyService.updateSurveyVO(surveyVO);
			//2.设置流程变量
			List<RestVariable> variables = new ArrayList<RestVariable>();
			variables.add(new RestVariable("transferResult",true));
			//3.推进流程
			//workFlowManager.submitTask(variables, taskId, processInstanceId, null, caseCode);
			response.setSuccess(true);
			response.setMessage("操作成功！");
		}catch(Exception e){
			response.setSuccess(false);
			response.setMessage("操作失败！"+e.getMessage());
		}
		return response;
	}
	
	@RequestMapping("transferReject")
	public AjaxResponse<String> transferReject(ToSatisfaction toSatisfaction){
		AjaxResponse<String> response = new AjaxResponse<String>();
		try{
			//1.更新状态
			//SurveyStatusEnum.TRANSFER_SURVEY_REJECT;
			//surveyService.updateSurveyVO(surveyVO);
			//2.设置流程变量
			List<RestVariable> variables = new ArrayList<RestVariable>();
			variables.add(new RestVariable("transferResult",false));
			//3.推进流程
			//workFlowManager.submitTask(variables, taskId, processInstanceId, null, caseCode);
			response.setSuccess(true);
			response.setMessage("操作成功！");
		}catch(Exception e){
			response.setSuccess(false);
			response.setMessage("操作失败！"+e.getMessage());
		}
		return response;
	}
	
	@RequestMapping("transferFollow")
	public AjaxResponse<String> transferFollow(){
		AjaxResponse<String> response = new AjaxResponse<String>();
		try{
			response.setSuccess(true);
			response.setMessage("操作成功！");
		}catch(Exception e){
			response.setSuccess(false);
			response.setMessage("操作失败！"+e.getMessage());
		}
		return response;
	}
	
	private void setCaseInfoToModel(Model model , Long satisId){
		
		ToSatisfaction satisfaction = satisfactionService.queryToSatisfactionById(satisId);
		
		//String caseCode = satisfaction.getCaseCode();
		String caseCode = "ZY-ZL-201705-0002";
		ToCase toCase = toCaseService.findToCaseByCaseCode(caseCode);
		
		//物业信息
		ToPropertyInfo toPropertyInfo = toPropertyInfoService.findToPropertyInfoByCaseCode(caseCode);
		//案件信息
		ToCaseInfo toCaseInfo = toCaseInfoService.findToCaseInfoByCaseCode(caseCode);
		// 经纪人
		User agentUser = null;
		CaseDetailShowVO reVo = new CaseDetailShowVO();
		if (!StringUtils.isBlank(toCaseInfo.getAgentCode())) {
			agentUser = uamUserOrgService.getUserById(toCaseInfo.getAgentCode());
		}
		if (agentUser != null) {
			reVo.setAgentName(agentUser.getRealName());
			// 分行经理
			List<User> mcList = uamUserOrgService.findHistoryUserByOrgIdAndJobCode(agentUser.getOrgId(),
					TransJobs.TFHJL.getCode());
			if (mcList != null && mcList.size() > 0) {
				User mcUser = mcList.get(0);
				reVo.setMcName(mcUser.getRealName());
				reVo.setMcMobile(mcUser.getMobile());
			}
		}
		
		// 交易顾问
		User consultUser = null;
		if(null != toCase.getLeadingProcessId()){
			consultUser = uamUserOrgService.getUserById(toCase.getLeadingProcessId());
		}
		if (consultUser != null) {
			reVo.setCpName(consultUser.getRealName());
			reVo.setCpMobile(consultUser.getMobile());
		}
		
		// 上下家
		List<TgGuestInfo> guestList = tgGuestInfoService.findTgGuestInfoByCaseCode(toCase.getCaseCode());
		StringBuffer seller = new StringBuffer();
		StringBuffer sellerMobil = new StringBuffer();
		StringBuffer buyer = new StringBuffer();
		StringBuffer buyerMobil = new StringBuffer();
		for (TgGuestInfo guest : guestList) {
			if (guest.getTransPosition().equals(TransPositionEnum.TKHSJ.getCode())) {
				seller.append(guest.getGuestName());
				sellerMobil.append(guest.getGuestPhone());
				seller.append("/");
				sellerMobil.append("/");
			} else if (guest.getTransPosition().equals(TransPositionEnum.TKHXJ.getCode())) {
				buyer.append(guest.getGuestName());
				buyerMobil.append(guest.getGuestPhone());
				buyer.append("/");
				buyerMobil.append("/");
			}
		}

		if (guestList.size() > 0) {
			if (seller.length() > 1) {
				seller.deleteCharAt(seller.length() - 1);
				sellerMobil.deleteCharAt(sellerMobil.length() - 1);
			}

			if (buyer.length() > 1) {
				buyer.deleteCharAt(buyer.length() - 1);
				buyerMobil.deleteCharAt(buyerMobil.length() - 1);
			}
		}

		reVo.setSellerName(seller.toString());
		reVo.setSellerMobile(sellerMobil.toString());
		reVo.setBuyerMobile(buyerMobil.toString());
		reVo.setBuyerName(buyer.toString());
		
		// 助理
		List<User> asList = uamUserOrgService.getUserByOrgIdAndJobCode(toCase.getOrgId(), TransJobs.TJYZL.getCode());
		if (asList != null && asList.size() > 0) {
			User assistUser = asList.get(0);
			reVo.setAsName(assistUser.getRealName());
			reVo.setAsMobile(assistUser.getMobile());
		}
		
		// 合作顾问
		List<CaseDetailProcessorVO> proList = new ArrayList<CaseDetailProcessorVO>();
		TgServItemAndProcessor inProcessor = new TgServItemAndProcessor();
		inProcessor.setCaseCode(toCase.getCaseCode());
		inProcessor.setProcessorId(toCase.getLeadingProcessId());
		List<String> tgproList = tgServItemAndProcessorService.findProcessorsByCaseCode(inProcessor);
		for (String sp : tgproList) {
			if (StringUtils.isEmpty(sp) || "-1".equals(sp))
				continue;
			CaseDetailProcessorVO proVo = new CaseDetailProcessorVO();
			User processor = uamUserOrgService.getUserById(sp);
			proVo.setProcessorName(processor.getRealName());
			proVo.setProcessorMobile(processor.getMobile());
			proList.add(proVo);
		}
		reVo.setProList(proList);
		
		model.addAttribute("caseDetailVO", reVo);
		model.addAttribute("toCaseInfo", toCaseInfo);
		model.addAttribute("toPropertyInfo", toPropertyInfo);
		model.addAttribute("satisfaction", satisfaction);
		
	}

}
