package com.centaline.trans.evaPricing.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aist.common.exception.BusinessException;
import com.aist.common.web.validate.AjaxResponse;
import com.aist.uam.auth.remote.UamSessionService;
import com.aist.uam.auth.remote.vo.SessionUser;
import com.alibaba.druid.util.StringUtils;
import com.centaline.trans.common.enums.WorkFlowStatus;
import com.centaline.trans.common.service.PropertyUtilsService;
import com.centaline.trans.engine.bean.RestVariable;
import com.centaline.trans.engine.entity.ToWorkFlow;
import com.centaline.trans.engine.service.ProcessInstanceService;
import com.centaline.trans.engine.service.ToWorkFlowService;
import com.centaline.trans.engine.service.WorkFlowManager;
import com.centaline.trans.engine.vo.StartProcessInstanceVo;
import com.centaline.trans.evaPricing.entity.ToEvaPricingVo;
import com.centaline.trans.evaPricing.service.EvaPricingService;

/**
 * 询价作业管理
 * @author wbcaiyx
 * 
 */
@Controller
@RequestMapping(value="/evaPricing")
public class EvaPricingListController {
	
	@Autowired(required=true)
	UamSessionService uamSessionService;
	@Autowired
	EvaPricingService evaPricingService;
	@Autowired
	private PropertyUtilsService propertyUtilsService;
	@Autowired
	private ProcessInstanceService processInstanceService;
	@Autowired
	private ToWorkFlowService toWorkFlowService;
	@Autowired
	private WorkFlowManager workFlowManager;
	
	/**
	 * 初始化
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value="list")
	public String evaluateList(Model model, ServletRequest request){
		SessionUser user = uamSessionService.getSessionUser();
		request.setAttribute("userId", user.getId());
		return "evaPricing/evaPricingList";
	}
	
	/**
	 * 新增询价
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value="addNewEvaPricing")
	public String addEvaluate(@RequestParam(value="caseCode",required=false)String caseCode, ServletRequest request){	
		request.setAttribute("caseCode", caseCode);
		return "evaPricing/evaPricingAdd";
	}
	
	/**
	 * 新增案件保存启动申请流程
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value="save")
	public AjaxResponse<String> saveEvaPricing(ToEvaPricingVo ToEvaPricingVo, ServletRequest request){
		//内勤申请，内勤回复
		SessionUser user = uamSessionService.getSessionUser();
		String userId = user.getId();
		ToEvaPricingVo.setAriserId(userId);//申请人
		AjaxResponse<String> result = new AjaxResponse<String>();
		List<String> evaCodes = new ArrayList<String>();
		try{
			evaCodes = evaPricingService.insertEvaPricing(ToEvaPricingVo);
		} catch(Exception e){
			result.setSuccess(false);
			result.setMessage(e.getMessage());
			return result;
		}
		
		if(evaCodes.size() > 0 ){
			for(String evaCode : evaCodes){
				//启动流程
				String processDefId = propertyUtilsService.getProcessDfId("evaPricing_process");
				Map<String, Object> vals = new HashMap<String,Object>();
				//目前为内勤本身
				vals.put("assistant", user.getUsername());
				
				StartProcessInstanceVo pVo = processInstanceService.startWorkFlowByDfId(processDefId, evaCode, vals);
	
				ToWorkFlow wf = new ToWorkFlow();
				wf.setBusinessKey("evaPricing_process");
				wf.setCaseCode(!StringUtils.isEmpty(ToEvaPricingVo.getCaseCode())?ToEvaPricingVo.getCaseCode():evaCode);
				wf.setBizCode(evaCode);
				wf.setProcessOwner(user.getId());
				wf.setProcessDefinitionId(processDefId);
				wf.setInstCode(pVo.getId());
				wf.setStatus(WorkFlowStatus.ACTIVE.getCode());
				toWorkFlowService.insertSelective(wf);
			}
		}
		
		
		return result;
	}
	
	/**
	 * 查看询价明细
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value="evaPricingDetail")
	public String evaPricingDetail(@RequestParam(value="PKID",required=true)Long PKID,
									@RequestParam(value="instCode",required=true)String instCode, Model model, ServletRequest request){
		if(PKID == null){
			return "";
		}
		
		ToEvaPricingVo toEvaPricingVo =  evaPricingService.findEvaPricingDetailByPKID(PKID,null);
		request.setAttribute("toEvaPricingVo", toEvaPricingVo);
		request.setAttribute("instCode", instCode);
		
		return "evaPricing/evaPricingDetail";
	}
	
	/**
	 * 询价详情操作
	 * @param taskId
	 * @param instCode
	 * @param isValid
	 * @param reason
	 * @return
	 */
	@RequestMapping("evaPricingDetailSubmit")
	public AjaxResponse<String> evaPricingDetailSubmit(ToEvaPricingVo toEvaPricingVo){
		AjaxResponse<String> result = new AjaxResponse<String>();
		
		try{
			//询价更新
			int count = evaPricingService.updateEvaPricingDetail(toEvaPricingVo.getPkid(), toEvaPricingVo.getIsValid(), toEvaPricingVo.getReason());
			if(count ==0){
				result.setSuccess(false);
				return result;
			}
			List<RestVariable> variables = new ArrayList<RestVariable>();
			workFlowManager.submitTask(variables, toEvaPricingVo.getTaskId(), toEvaPricingVo.getProcessInstanceId(), null, null);
			
			//flow完结
			ToWorkFlow flow=new ToWorkFlow();
			flow.setBusinessKey("evaPricing_process");
			flow.setCaseCode(!StringUtils.isEmpty(toEvaPricingVo.getCaseCode())?toEvaPricingVo.getCaseCode():toEvaPricingVo.getEvaCode());
			flow.setBizCode(toEvaPricingVo.getEvaCode());
			ToWorkFlow evaPricingFlow= toWorkFlowService.queryActiveToWorkFlowByCaseCodeBusKey(flow);
			evaPricingFlow.setStatus(WorkFlowStatus.COMPLETE.getCode());
			toWorkFlowService.updateByPrimaryKeySelective(evaPricingFlow);
		} catch(Exception e){
			result.setSuccess(false);
			result.setMessage(e.getMessage());
			return result;
		}
		
		
		
		return result;
	}
	
	/**
	 * 回复页面
	 * @param businessKey 流程启动时的bussinessKey参数：evaCode
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value="evaPricingEnter")
	public String evaPricingEnter(String businessKey,Model model, ServletRequest request){

		ToEvaPricingVo toEvaPricingVo =  evaPricingService.findEvaPricingDetailByPKID(null,businessKey);
		request.setAttribute("toEvaPricingVo", toEvaPricingVo);
		
		return "evaPricing/evaPricingEnter";
	}
	
	/**
	 * 回复提交保存
	 * @param toEvaPricingVo
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value="evaPricingEnterSubmit")
	public AjaxResponse<String> evaPricingEnterSubmit(ToEvaPricingVo toEvaPricingVo ,Model model, ServletRequest request){
		AjaxResponse<String> result = new AjaxResponse<String>();
		try{
			evaPricingService.updateEvaPricing(toEvaPricingVo);
		
			if(toEvaPricingVo.getIsSubmit() == 1){
				List<RestVariable> variables = new ArrayList<RestVariable>();
				workFlowManager.submitTask(variables, toEvaPricingVo.getTaskId(), toEvaPricingVo.getProcessInstanceId(), null, null);
				
				//flow完结
				ToWorkFlow flow=new ToWorkFlow();
				flow.setBusinessKey("evaPricing_process");
				flow.setCaseCode(!StringUtils.isEmpty(toEvaPricingVo.getCaseCode())?toEvaPricingVo.getCaseCode():toEvaPricingVo.getEvaCode());
				flow.setBizCode(toEvaPricingVo.getEvaCode());
				ToWorkFlow evaPricingFlow= toWorkFlowService.queryActiveToWorkFlowByCaseCodeBusKey(flow);
				evaPricingFlow.setStatus(WorkFlowStatus.COMPLETE.getCode());
				toWorkFlowService.updateByPrimaryKeySelective(evaPricingFlow);
			}
		} catch(Exception e){
			result.setSuccess(false);
			result.setMessage(e.getMessage());
			return result;
		}
		
		return result;
	}
	
	/**
	 * 取消询价
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value="evaPricingCancel")
	public AjaxResponse<String> evaPricingCancel(@RequestParam(value="PKID",required=true)Long PKID, Model model, ServletRequest request){
		AjaxResponse<String> result = new AjaxResponse<String>();
		try{
			evaPricingService.cancelByPKID(PKID);
		} catch(BusinessException e){
			 result.setSuccess(false);
			 result.setMessage(e.getMessage());
		}
		return result;
	}
	

	/**
	 * 获取评估公司
	 * @return
	 */
	@RequestMapping(value="getEvaFinOrg")
	@ResponseBody
	public AjaxResponse<List<Map<String,String>>> getEvaFinOrg(){
		AjaxResponse<List<Map<String,String>>> result = new AjaxResponse<List<Map<String,String>>>();
		
		List<Map<String,String>> lis = evaPricingService.queryEvaFinOrg();
		result.setContent(lis);
		return result;
	}
	
	/**
	 * 询价关联案件
	 * @param pkid
	 * @param caseCode
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("evaPricingRelation")
	@ResponseBody
	public AjaxResponse<Boolean> evaPricingRelation(long pkid,String caseCode){
		
		boolean result = evaPricingService.evalRelation(pkid, caseCode);
		return AjaxResponse.successContent(result);
	}
	
	/**
	 * 询价代办任务
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value="evaPricingTaskList")
	public String evaPricingTaskList(Model model, ServletRequest request){
		SessionUser user = uamSessionService.getSessionUser();
		request.setAttribute("userId", user.getId());

		return "evaPricing/evaPricingTaskList";
	}
	
}
