package com.centaline.trans.taskList.web;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aist.common.exception.BusinessException;
import com.aist.common.web.validate.AjaxResponse;
import com.aist.message.core.remote.UamMessageService;
import com.aist.message.core.remote.vo.Message;
import com.aist.message.core.remote.vo.MessageType;
import com.aist.uam.auth.remote.UamSessionService;
import com.aist.uam.auth.remote.vo.SessionUser;
import com.aist.uam.template.remote.UamTemplateService;
import com.aist.uam.userorg.remote.UamUserOrgService;
import com.aist.uam.userorg.remote.vo.User;
import com.centaline.trans.cases.entity.ToCase;
import com.centaline.trans.cases.service.ToCaseService;
import com.centaline.trans.cases.vo.CaseBaseVO;
import com.centaline.trans.common.entity.TgGuestInfo;
import com.centaline.trans.common.enums.MsgCatagoryEnum;
import com.centaline.trans.common.enums.TransPositionEnum;
import com.centaline.trans.common.enums.WorkFlowEnum;
import com.centaline.trans.common.service.MessageService;
import com.centaline.trans.common.service.TgGuestInfoService;
import com.centaline.trans.common.service.ToWorkFlowService;
import com.centaline.trans.engine.bean.RestVariable;
import com.centaline.trans.engine.entity.ToWorkFlow;
import com.centaline.trans.engine.service.WorkFlowManager;
import com.centaline.trans.mgr.entity.ToSupDocu;
import com.centaline.trans.mgr.entity.TsFinOrg;
import com.centaline.trans.mgr.service.TsFinOrgService;
import com.centaline.trans.mortgage.entity.MortStep;
import com.centaline.trans.mortgage.entity.ToMortgage;
import com.centaline.trans.mortgage.service.MortStepService;
import com.centaline.trans.mortgage.service.ToMortgageService;
import com.centaline.trans.task.vo.ProcessInstanceVO;

@Controller
@RequestMapping(value="/task")
public class ToMortgageController {
		
	@Autowired
	private ToMortgageService toMortgageService;
	
	@Autowired
	private TgGuestInfoService tgGuestInfoService;
	
	@Autowired
	private WorkFlowManager workFlowManager;
	
	@Autowired
	private ToCaseService toCaseService;
		
	@Autowired
	private MortStepService mortStepService;
	@Autowired
	private UamSessionService uamSessionService;
	@Autowired
	@Qualifier("uamMessageServiceClient")
	private UamMessageService uamMessageService;
	@Autowired
	private UamTemplateService uamTemplateService;
	@Autowired
	private TsFinOrgService tsFinOrgService;
	@Autowired
	private UamUserOrgService uamUserOrgService;
	@Autowired
	private MessageService messageService;
	@Autowired
	private ToWorkFlowService toWorkFlowService;
	/**
	 * 查询贷款信息
	 * @param toMortgage
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/getMortgageInfo")  
	@ResponseBody
    public AjaxResponse<ToMortgage> getMortgageInfo(ToMortgage toMortgage,HttpServletRequest request) {	
		AjaxResponse<ToMortgage> response = new AjaxResponse<ToMortgage>();
		try{
			ToMortgage mortgage = toMortgageService.findToMortgageByCaseCodeWithCommLoan(toMortgage);
			// 分行支行
			String finOrgCodeString = mortgage.getFinOrgCode();
			if (!StringUtils.isEmpty(finOrgCodeString)) {
				TsFinOrg bank = tsFinOrgService.findBankByFinOrg(finOrgCodeString);
				 mortgage.setBankName(bank.getFinOrgName());
				if (!StringUtils.isEmpty(bank.getFaFinOrgCode())) {
						TsFinOrg faBank = tsFinOrgService.findBankByFinOrg(bank.getFaFinOrgCode());
						 mortgage.setParentBankName(faBank.getFinOrgName());
				}
			}
			if(StringUtils.isNotBlank(mortgage.getTmpBankUpdateBy())){
				User u= uamUserOrgService.getUserById(mortgage.getTmpBankUpdateBy());
				if(u!=null){
					mortgage.setTmpBankUpdateByStr(u.getRealName());
				}
			}
			
			mortgage.setComAmount(mortgage.getComAmount() != null ? mortgage.getComAmount()
					.divide(new BigDecimal(10000)) : null);
			mortgage.setMortTotalAmount(mortgage.getMortTotalAmount() != null ?mortgage.getMortTotalAmount().divide(
					new BigDecimal(10000)):null);
			mortgage.setPrfAmount(mortgage.getPrfAmount() != null ? mortgage.getPrfAmount()
					.divide(new BigDecimal(10000)) : null);
			
			//临时银行开启时不允许反选
			ToWorkFlow twf = new ToWorkFlow();

			twf.setBusinessKey(WorkFlowEnum.TMP_BANK_DEFKEY.getCode());

			twf.setCaseCode(toMortgage.getCaseCode());
			ToWorkFlow record = toWorkFlowService.queryActiveToWorkFlowByCaseCodeBusKey(twf);
			if(record != null){
				//流程已开启
				response.setCode("1");
			}else{
				//流程未开启
				response.setCode("0");
			}
	
			response.setContent(mortgage);
		}catch(Exception e){
			response.setSuccess(false);
			response.setMessage("查询出错！"+e.getMessage());
		}
		
        return response;
    }
	
	/**
	 * 保存贷款信息
	 * @param toMortgage
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/saveMortgage")  
	@ResponseBody
    public AjaxResponse<String> saveMortgage(ToMortgage toMortgage,ToSupDocu toSupDocu,HttpServletRequest request) {
		//贷款签约 时登录用户即为贷款专员 需保持进数据库
		SessionUser user = uamSessionService.getSessionUser();
		AjaxResponse<String> response = new AjaxResponse<String>();
		try{
			
			if(toMortgage.getMortTotalAmount() != null){
				toMortgage.setMortTotalAmount(toMortgage.getMortTotalAmount().multiply(new BigDecimal(10000)));
			}
			if(toMortgage.getComAmount() != null){
				toMortgage.setComAmount(toMortgage.getComAmount().multiply(new BigDecimal(10000)));
			}
			if(toMortgage.getPrfAmount() != null){
				toMortgage.setPrfAmount(toMortgage.getPrfAmount().multiply(new BigDecimal(10000)));
			}
			if(toMortgage.getIfReportBeforeLend() == null){
				toMortgage.setIfReportBeforeLend("0");
			}
			if(toMortgage.getIsLoanerArrive() == null){
				toMortgage.setIsLoanerArrive("0");
			}
			if(toMortgage.getIsTmpBank() == null){
				toMortgage.setIsTmpBank("0");
			}	
			//贷款专员的id和组织id
			toMortgage.setLoanAgent(user.getId());
			toMortgage.setLoanAgentTeam(user.getServiceDepId());			
			toMortgage.setToSupDocu(toSupDocu);
			toMortgageService.saveToMortgageAndSupDocu(toMortgage);
			
		}catch(BusinessException e){
			response.setCode(e.getCode());
			response.setSuccess(false);
			response.setMessage("操作失败！"+e.getMessage());
		}catch(Exception e1){
			response.setSuccess(false);
			response.setMessage("操作失败！"+e1.getMessage());
			e1.printStackTrace();
		}
        return response;
    }
	
	/**
	 * 贷款完成
	 * @param toMortgage
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/completeMortgage")  
	@ResponseBody
    public AjaxResponse<String> completeMortgage(ToMortgage toMortgage,HttpServletRequest request,String check) {
		AjaxResponse<String> response = new AjaxResponse<String>();

		try{
			ToMortgage entity = toMortgageService.findToMortgageById(toMortgage.getPkid());
//			entity.setComAmount(NumberUtil.multiply(toMortgage.getComAmount(), new BigDecimal(10000)));
//			entity.setMortTotalAmount(NumberUtil.multiply(toMortgage.getMortTotalAmount(), new BigDecimal(10000)));
//			entity.setPrfAmount(NumberUtil.multiply(toMortgage.getPrfAmount(), new BigDecimal(10000)));
			entity.setApprDate(toMortgage.getApprDate());
			entity.setFormCommLoan("1");
			entity.setLastLoanBank(toMortgage.getLastLoanBank());
			entity.setPartCode(toMortgage.getPartCode());
			toMortgageService.saveToMortgage(entity);
			if("1".equals(entity.getIsTmpBank())&&entity.getTmpBankUpdateBy()==null){
				response.setMessage("临时银行未处理，请等待处理！");
			}
			/**
			 * 功能: 给客户发送短信
			 * 作者：zhangxb16
			 */
			int result=tgGuestInfoService.sendMsgHistory(toMortgage.getCaseCode(), toMortgage.getPartCode());
			if(result>0){
			}else{
				response.setMessage("短信发送失败, 请您线下手工再次发送！");
			}
		}catch(Exception e){
			response.setSuccess(false);
			response.setMessage("操作失败！"+e.getMessage());
		}
        return response;
    }
	
	/**
	 * 查询客户下家信息
	 * @param tgGuestInfo
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/getGuestInfo")  
	@ResponseBody
    public List<TgGuestInfo> getGuestInfo(TgGuestInfo tgGuestInfo,HttpServletRequest request) {
		List<TgGuestInfo> list = null;
		tgGuestInfo.setTransPosition(TransPositionEnum.TKHXJ.getCode());
		list = tgGuestInfoService.findTgGuestInfosByCaseCode(tgGuestInfo);
        return list;
    }
	
	/**
	 * 提交完成贷款流程
	 * @param toMortgage
	 * @param processInstanceVO
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/submitMortgage")  
	@ResponseBody
    public AjaxResponse<String> submitMortgage(ProcessInstanceVO processInstanceVO,String isMainLoanBank, HttpServletRequest request) {
		AjaxResponse<String> response = new AjaxResponse<String>();
		try{
			ToMortgage mortgage = new ToMortgage();
			mortgage.setCaseCode(processInstanceVO.getCaseCode());
			mortgage.setIsMainLoanBank(isMainLoanBank);
			ToMortgage toMortgage = toMortgageService.findToMortgageByCaseCodeWithCommLoan(mortgage);
			if(toMortgage == null){
				response.setMessage("未找到该案件的按揭贷款信息，请先保存按揭贷款信息！");
				return response;
			}else if(StringUtils.isEmpty(toMortgage.getLastLoanBank())){
				response.setMessage("该案件还未确定最终贷款银行，不能提交流程！");
				return response;
			}else if ("1".equals(toMortgage.getIsTmpBank())&&toMortgage.getTmpBankUpdateBy()==null){
				response.setMessage("临时银行未处理，请等待处理！");
				return response;
			}
			
			List<RestVariable> variables = new ArrayList<RestVariable>();
			//不需要放款前报告
			if(toMortgage.getIfReportBeforeLend().equals("0")){
				RestVariable variable = new RestVariable();
				variable.setName("EvaReportNeedAtLoanRelease");
				variable.setValue(false);
				variables.add(variable);
			}
			//需要放款前报告
			if(toMortgage.getIfReportBeforeLend().equals("1")){
				RestVariable variable = new RestVariable();
				variable.setName("EvaReportNeedAtLoanRelease");
				variable.setValue(true);
				variables.add(variable);
			}
			ToCase toCase = toCaseService.findToCaseByCaseCode(processInstanceVO.getCaseCode());	
			workFlowManager.submitTask(variables, processInstanceVO.getTaskId(), processInstanceVO.getProcessInstanceId(), 
					toCase.getLeadingProcessId(), processInstanceVO.getCaseCode());
			
			//提交流程之后需要更新贷款专员
			SessionUser user = uamSessionService.getSessionUser();
			toMortgage.setLoanAgent(user.getId());
			toMortgage.setLoanAgentTeam(user.getServiceDepId());
			toMortgageService.updateToMortgage(toMortgage);
			
			// 发送消息
			ToWorkFlow wf=new ToWorkFlow();
			wf.setCaseCode(processInstanceVO.getCaseCode());
			wf.setBusinessKey(WorkFlowEnum.WBUSSKEY.getCode());
			ToWorkFlow wordkFlowDB = toWorkFlowService.queryActiveToWorkFlowByCaseCodeBusKey(wf);
			if(wordkFlowDB!=null && "operation_process:40:645454".compareTo(wordkFlowDB.getProcessDefinitionId())<=0) {
				messageService.sendMortgageFinishMsgByIntermi(wordkFlowDB.getInstCode());
				//设置主流程任务的assignee
				workFlowManager.setAssginee(wordkFlowDB.getInstCode(), toCase.getLeadingProcessId(), toCase.getCaseCode());
				
				ToWorkFlow workFlowOld =new ToWorkFlow();
				// 流程结束状态
				workFlowOld.setStatus("4");
				workFlowOld.setInstCode(processInstanceVO.getProcessInstanceId());
				toWorkFlowService.updateWorkFlowByInstCode(workFlowOld);
			}
			
		}catch(Exception e){
			response.setSuccess(false);
			response.setMessage("操作失败！"+e.getMessage());
			e.printStackTrace();
		}
        return response;
    }
	
	
	@RequestMapping(value="/taskReturnMort")  
	@ResponseBody
    public AjaxResponse<String> taskReturnMort(ProcessInstanceVO processInstanceVO, HttpServletRequest request) {
		AjaxResponse<String> response = new AjaxResponse<String>();
		try{
			
			List<RestVariable> variables = new ArrayList<RestVariable>();
			
			ToCase toCase = toCaseService.findToCaseByCaseCode(processInstanceVO.getCaseCode());	
			workFlowManager.submitTask(variables, processInstanceVO.getTaskId(), processInstanceVO.getProcessInstanceId(), 
					toCase.getLeadingProcessId(), processInstanceVO.getCaseCode());
		}catch(Exception e){
			response.setSuccess(false);
			response.setMessage("操作失败！"+e.getMessage());
		}
        return response;
    }
	
	@RequestMapping(value="/saveMortStep")  
	@ResponseBody
    public AjaxResponse<String> saveMortStep(MortStep mortStep) {
		AjaxResponse<String> response = new AjaxResponse<String>();
		try{
			mortStepService.saveMortStep(mortStep);
		
		}catch(Exception e){
			response.setSuccess(false);
			response.setMessage("操作失败！"+e.getMessage());
		}
        return response;
    }
	
	@RequestMapping(value="/getStepInfo")  
	@ResponseBody
    public AjaxResponse<MortStep> getStepInfo(MortStep mortStep) {
		AjaxResponse<MortStep> response = new AjaxResponse<MortStep>();
		try{
			MortStep step = mortStepService.findMortStep(mortStep);
			response.setContent(step);
		}catch(Exception e){
			response.setSuccess(false);
			response.setMessage("操作失败！"+e.getMessage());
		}
        return response;
    }
	@RequestMapping(value="/toTmpBankList") 
	public String toTmpBankList(){
		return "mortgage/tmpBankList";
	}
	@RequestMapping(value="/box/tmpBank")
	public String tmpBank(String pkid,HttpServletRequest request){
		ToMortgage mortage= toMortgageService.findToMortgageById(Long.valueOf(pkid));


		request.setAttribute("mortage", mortage);


		CaseBaseVO caseBaseVO = toCaseService.getCaseBaseVO(mortage.getCaseCode());
		request.setAttribute("caseBaseVO", caseBaseVO);
	
		return "mortgage/tmpBank";
	}
	@RequestMapping(value="/doProcessTmpBank")
	@ResponseBody
	public AjaxResponse doProcessTmpBank(ToMortgage mortage,String prAddress,String tmpBankName){
		SessionUser user=uamSessionService.getSessionUser();
		
		ToMortgage mortageDb= toMortgageService.findToMortgageById(mortage.getPkid());
		ToCase c = toCaseService.findToCaseByCaseCode(mortageDb.getCaseCode());
		mortageDb.setLastLoanBank(mortage.getFinOrgCode());
		mortageDb.setFinOrgCode(mortage.getFinOrgCode());
		mortageDb.setTmpBankUpdateBy(user.getId());
		mortageDb.setTmpBankUpdateTime(new Date());
		toMortgageService.updateToMortgage(mortageDb);
		
		Map<String, Object>params=new HashMap<String, Object>();
		params.put("case_code", mortageDb.getCaseCode());
		params.put("property_address", prAddress);
		params.put("bank", tmpBankName);
		
		String content = uamTemplateService.mergeTemplate("TMP_BANK_REMINDER", params);
		Message message = new Message();
		// 消息标题
		message.setTitle("临时银行处理提醒");
		// 消息类型
		message.setType(MessageType.SITE);
		/* 设置提醒列别 */

		message.setMsgCatagory(MsgCatagoryEnum.NEWS.getCode());

		/* 内容 */
		message.setContent(content);
		/* 发送人 */
		String senderId = user.getId();
		/* 设置发送人 */
		message.setSenderId(senderId);

		uamMessageService.sendMessageByUser(message, c.getLeadingProcessId());
		
		return AjaxResponse.success();
	}
}
