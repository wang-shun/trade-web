package com.centaline.trans.taskList.web;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aist.common.exception.BusinessException;
import com.aist.common.web.validate.AjaxResponse;
import com.aist.uam.auth.remote.UamSessionService;
import com.aist.uam.auth.remote.vo.SessionUser;
import com.aist.uam.basedata.remote.UamBasedataService;
import com.aist.uam.basedata.remote.vo.Dict;
import com.centaline.trans.cases.entity.ToCase;
import com.centaline.trans.cases.service.ToCaseService;
import com.centaline.trans.common.entity.TgGuestInfo;
import com.centaline.trans.common.entity.ToPropertyInfo;
import com.centaline.trans.common.enums.TransPositionEnum;
import com.centaline.trans.common.service.TgGuestInfoService;
import com.centaline.trans.common.service.ToPropertyInfoService;
import com.centaline.trans.engine.bean.RestVariable;
import com.centaline.trans.engine.service.WorkFlowManager;
import com.centaline.trans.mgr.entity.ToSupDocu;
import com.centaline.trans.mortgage.entity.MortStep;
import com.centaline.trans.mortgage.entity.ToMortgage;
import com.centaline.trans.mortgage.service.MortStepService;
import com.centaline.trans.mortgage.service.ToMortgageService;
import com.centaline.trans.task.entity.TsMsgSendHistory;
import com.centaline.trans.task.service.TsMsgSendHistoryService;
import com.centaline.trans.task.vo.ProcessInstanceVO;
import com.centaline.trans.utils.NumberUtil;

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
			ToMortgage mortgage = toMortgageService.findToMortgageByCaseCode(toMortgage);
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
    public AjaxResponse<String> completeMortgage(ToMortgage toMortgage,HttpServletRequest request) {
		AjaxResponse<String> response = new AjaxResponse<String>();
		
		try{
			ToMortgage entity = toMortgageService.findToMortgageById(toMortgage.getPkid());
			toMortgage.setComAmount(NumberUtil.multiply(toMortgage.getComAmount(), new BigDecimal(10000)));
			toMortgage.setMortTotalAmount(NumberUtil.multiply(toMortgage.getMortTotalAmount(), new BigDecimal(10000)));
			toMortgage.setPrfAmount(NumberUtil.multiply(toMortgage.getPrfAmount(), new BigDecimal(10000)));
			entity.setApprDate(toMortgage.getApprDate());
			toMortgageService.saveToMortgage(toMortgage);
			
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
			ToMortgage toMortgage = toMortgageService.findToMortgageByCaseCode(mortgage);
			if(toMortgage == null){
				response.setMessage("未找到该案件的按揭贷款信息，请先保存按揭贷款信息！");
				return response;
			}else if(StringUtils.isEmpty(toMortgage.getLastLoanBank())){
				response.setMessage("该案件还未确定最终贷款银行，不能提交流程！");
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
}
