
package com.centaline.trans.ransom.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aist.uam.auth.remote.UamSessionService;
import com.aist.uam.auth.remote.vo.SessionUser;
import com.centaline.trans.common.enums.LampEnum;
import com.centaline.trans.common.enums.WorkFlowStatus;
import com.centaline.trans.common.service.PropertyUtilsService;
import com.centaline.trans.engine.bean.RestVariable;
import com.centaline.trans.engine.entity.ToWorkFlow;
import com.centaline.trans.engine.service.ProcessInstanceService;
import com.centaline.trans.engine.service.ToWorkFlowService;
import com.centaline.trans.engine.service.WorkFlowManager;
import com.centaline.trans.engine.vo.StartProcessInstanceVo;
import com.centaline.trans.ransom.entity.ToRansomDetailVo;
import com.centaline.trans.ransom.entity.ToRansomSubmitVo;
import com.centaline.trans.ransom.service.RansomService;

	/**
	 * 赎楼流程
	 * @author wbcaiyx
	 *
	 */
	@Controller
	@RequestMapping(value = "task/ransom")
	public class RansomController {
		
		@Value("${process.df.key.Ransom_Process}")
		private String PROCESS_DEFINITION_ID;
		
		@Autowired(required=true)
		private RansomService ransomService;
		
		@Autowired(required=true)
		private WorkFlowManager workFlowManager;
		
		@Autowired
		private UamSessionService uamSessionService;
		
		@Autowired
		private ToWorkFlowService toWorkFlowService;
		
		@Autowired
		private PropertyUtilsService propertyUtilsService;
		
		@Autowired
		private ProcessInstanceService processInstanceService;
		
		
		/**
		 * 赎楼待办任务列表
		 * by wbshume
		 * @return
		 */
		@RequestMapping(value="/taskList")
		public String taskList(ServletRequest request){
			SessionUser user = uamSessionService.getSessionUser();
			String[] lamps = LampEnum.getCodes();
			request.setAttribute("userId", user.getId());
			request.setAttribute("Lamp1", lamps[0]);
			request.setAttribute("Lamp2", lamps[1]);
			request.setAttribute("Lamp3", lamps[2]);
			request.setAttribute("PROCESS_DEFINITION_ID", PROCESS_DEFINITION_ID);
			return "ransom/ransomTaskList";
		}
		
		/**
		 * 赎楼详情画面 
		 * @param ransomCode 赎楼编号
		 * @param request
		 * @return
		 */
		@RequestMapping(value="ransomDetail")
		public String ransomDetail(String caseCode, ServletRequest request){
			
			getTaskBaseInfo(request, caseCode);

			return "ransom/ransomDetail";
		}
		
		/**
		 * 赎楼申请
		 * @param caseCode
		 * @param request
		 * @return
		 */
		@RequestMapping(value="ransomApply")
		public String  ransomApply(String caseCode, ServletRequest request){
			//公共基本信息
			getTaskBaseInfo(request, caseCode);
			
			return "ransom/ransomApply";
		}
		
		/**
		 * 赎楼申请提交
		 * @param submitVo
		 * @param request
		 * @return
		 */
		@RequestMapping(value="submitApply")
		@ResponseBody
		public boolean submitApply(ToRansomSubmitVo submitVo, ServletRequest request){
			//数据更新,申请表,赎楼表
			try{
				ransomService.updateRansomApply(submitVo);
			}catch(Exception e){
				System.out.println(e.getMessage());
				return false;
			}
			SessionUser user = uamSessionService.getSessionUser();
			//赎楼流程启动
			Map<String,Object> defValsMap = new HashMap<String,Object>();
			defValsMap.put("sessionUser", user.getUsername());
			String processDfId = propertyUtilsService.getProcessDfId("ransom_process");
			
			/** businsessKey还是用caseCode，因为在engineTask中caseCode兼容老程序,用的businessKey赋值 **/
			StartProcessInstanceVo pVo = processInstanceService.startWorkFlowByDfId(processDfId, submitVo.getCaseCode(), defValsMap);

			ToWorkFlow wf = new ToWorkFlow();
			wf.setBusinessKey("ransom_process");
			wf.setCaseCode(submitVo.getCaseCode());
			wf.setBizCode(submitVo.getRansomCode());
			wf.setProcessOwner(user.getId());
			wf.setProcessDefinitionId(processDfId);
			wf.setInstCode(pVo.getId());
			wf.setStatus(WorkFlowStatus.ACTIVE.getCode());
			toWorkFlowService.insertSelective(wf);

			return true;
		}
		
		/**
		 * 赎楼面签
		 * @param caseCode
		 * @param request
		 * @return
		 */
		@RequestMapping(value="ransomSign")
		public String ransomSign(String caseCode, ServletRequest request){
			
			//公共基本信息
			getTaskBaseInfo(request, caseCode);
			
			return "ransom/ransomSign";
		}
		
		/**
		 * 面签提交
		 * @param submitVo
		 * @param request
		 * @return
		 */
		@RequestMapping(value="submiSign")
		@ResponseBody
		public boolean submiSign(ToRansomSubmitVo submitVo, ServletRequest request){
			//检查是否有二抵
			int count = ransomService.queryErdiByRansomCode(submitVo.getRansomCode());
			//面签数据更新
			try{
				ransomService.updateRansomSign(submitVo,count);
			}catch(Exception e){
				System.out.println(e.getMessage());
				return false;
			}
				
			SessionUser user = uamSessionService.getSessionUser();
			//完成任务流程
			List<RestVariable> variables = new ArrayList<RestVariable>();
			

			if(count >0){
				variables.add(new RestVariable("erdi", true));
			}else{
				variables.add(new RestVariable("erdi", false));
			}
			boolean result = workFlowManager.submitTask(variables, submitVo.getTaskId(), submitVo.getProcessInstanceId(), user.getId(), submitVo.getCaseCode());		
			
			return result;
		}
		/**
		 * 陪同还贷
		 * @param caseCode 
		 * @param taskitem   
		 * @param request
		 * @return
		 */
		@RequestMapping(value="ransomMortgage")
		public String ransomMortgage(String caseCode, String taskitem, ServletRequest request){
			//公共基本信息
			getTaskBaseInfo(request, caseCode);
			
			Integer diyaType = 1;
			if("RansomMortgageTwo".equals(taskitem)){
				diyaType = 2;
			}
			
			request.setAttribute("diyaType", diyaType);

			return "ransom/ransomMortgage";
		}
		
		/**
		 * 陪同还贷提交
		 * @param request
		 * @return
		 */
		@RequestMapping(value="submitMortgage")
		@ResponseBody
		public boolean submitMortgage(ToRansomSubmitVo submitVo, ServletRequest request){
			//陪同还贷数据更新
			try{
				ransomService.updateRansomMortgage(submitVo);
			}catch(Exception e){
				System.out.println(e.getMessage());
				return false;
			}
			SessionUser user = uamSessionService.getSessionUser();
			//完成任务流程
			List<RestVariable> variables = new ArrayList<RestVariable>();
			
			boolean result = workFlowManager.submitTask(variables, submitVo.getTaskId(), submitVo.getProcessInstanceId(), user.getId(), submitVo.getCaseCode());		
			return result;
		}
		
		/**
		 * 注销抵押
		 * @param caseCode 案件编号
		 * @param taskitem 任务编号
		 * @param request
		 * @return
		 */
		@RequestMapping(value="ransomCancel")
		public String ransomCancel(String caseCode, String taskitem, ServletRequest request){
			//公共基本信息
			getTaskBaseInfo(request, caseCode);
			
			Integer diyaType = 1;
			if("RansomCancelTwo".equals(taskitem)){
				diyaType = 2;
			}
			request.setAttribute("diyaType", diyaType);

			return "ransom/ransomCancel";
		}
		
		/**
		 * 注销抵押提交
		 * @param taskId             任务id
		 * @param processInstanceId  流程实例id
		 * @param ransomCode         赎楼编号
		 * @param tailPkid           尾款表pkid(区分一抵/二抵)
		 * @param cancelDiyaTime     注销抵押时间
		 * @param request
		 * @return
		 */
		@RequestMapping(value="submitCancelDiya")
		@ResponseBody
		public boolean submitCancelDiya(ToRansomSubmitVo submitVo, ServletRequest request){
			//插入注销抵押数据
			try{
				ransomService.updateRansomCancel(submitVo.getRansomCode(), submitVo.getDiyaType(), submitVo.getCancelDiyaTime());
			}catch(Exception e){
				System.out.println(e.getMessage());
				return false;
			}
			SessionUser user =uamSessionService.getSessionUser(); 
			//完成任务流程
			List<RestVariable> variables = new ArrayList<RestVariable>();
			
			boolean result = workFlowManager.submitTask(variables, submitVo.getTaskId(), submitVo.getProcessInstanceId(), user.getId(), submitVo.getCaseCode());		
			return result;
		}
		
		
		/**
		 * 领取产证
		 * @param caseCode   
		 * @param taskitem  
		 * @param request
		 * @return
		 */
		@RequestMapping(value="ransomPermit")
		public String ransomPermit(String caseCode,String taskitem,ServletRequest request){

			//公共基本信息
			getTaskBaseInfo(request, caseCode);
			
			Integer diyaType = 1;
			if("RansomPermitTwo".equals(taskitem)){
				diyaType = 2;
			}
			
			request.setAttribute("diyaType", diyaType);
			return "ransom/ransomPermit";
		}
		
		/**
		 * 领取产证提交
		 * @param taskId             任务id
		 * @param processInstanceId  流程实例id
		 * @param ransomCode         赎楼编号
		 * @param diyaType           抵押类型
		 * @param permitTime         领取产证时间
		 * @param request
		 * @return
		 */
		@RequestMapping(value="submitPermit")
		@ResponseBody
		public boolean submitPermit(ToRansomSubmitVo submitVo, ServletRequest request){
			//插入领取产证数据
			try{
				ransomService.updateRansomPermit(submitVo.getRansomCode(), submitVo.getDiyaType(), submitVo.getPermitTime());
			}catch(Exception e){
				System.out.println(e.getMessage());
				return false;
			}
			SessionUser user =uamSessionService.getSessionUser(); 
			//完成任务流程
			List<RestVariable> variables = new ArrayList<RestVariable>();
			
			boolean result = workFlowManager.submitTask(variables, submitVo.getTaskId(), submitVo.getProcessInstanceId(), user.getId(), submitVo.getCaseCode());		
			return result;
		}
		
		
		/**
		 * 回款结清
		 * @param caseCode 
		 * @param request
		 * @return
		 */
		@RequestMapping(value="ransomPayment")
		public String ransomPayment(String caseCode, ServletRequest request){
		
			//公共基本信息
			getTaskBaseInfo(request, caseCode);
		
			return "ransom/ransomPayment";
		}
		
		/**
		 * 回款结清提交
		 * @param taskId             任务id
		 * @param processInstanceId  流程实例id
		 * @param ransomCode         赎楼编号
		 * @param paymentTime        回款结清时间
		 * @param request
		 * @return
		 */
		@RequestMapping(value="submitPayment")
		@ResponseBody
		public boolean submitPayment(ToRansomSubmitVo submitVo, ServletRequest request){
			//插入回款结清数据,完结赎楼单
			try{
				ransomService.updateRansomPayment(submitVo.getRansomCode(), submitVo.getPaymentTime());
			}catch(Exception e){
				System.out.println(e.getMessage());
				return false;
			}
			SessionUser user =uamSessionService.getSessionUser(); 
			//完成任务流程
			List<RestVariable> variables = new ArrayList<RestVariable>();
			
			boolean result = workFlowManager.submitTask(variables, submitVo.getTaskId(), submitVo.getProcessInstanceId(), user.getId(), submitVo.getCaseCode());		
			
			//flow完结
			ToWorkFlow flow=new ToWorkFlow();
			flow.setBusinessKey("ransom_process");
			flow.setCaseCode(submitVo.getCaseCode());
			flow.setBizCode(submitVo.getRansomCode());
			ToWorkFlow ransomFlow= toWorkFlowService.queryActiveToWorkFlowByCaseCodeBusKey(flow);
			ransomFlow.setStatus(WorkFlowStatus.COMPLETE.getCode());
			toWorkFlowService.updateByPrimaryKeySelective(ransomFlow);
		
			return result;
		}
		
		/**
		 * 赎楼页面公共信息
		 * @param request
		 * @param caseCode
		 */
		void getTaskBaseInfo(ServletRequest request,String caseCode){
			//公共基本信息
			ToRansomDetailVo detailVo = ransomService.getRansomDetail(caseCode);
			request.setAttribute("detailVo", detailVo);
		}
	}
