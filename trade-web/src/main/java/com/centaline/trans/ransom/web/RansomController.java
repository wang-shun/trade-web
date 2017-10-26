package com.centaline.trans.ransom.web;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.aist.common.rapidQuery.paramter.ParamterHander;
import com.aist.uam.auth.remote.UamSessionService;
import com.aist.uam.auth.remote.vo.SessionUser;
import com.centaline.trans.common.enums.LampEnum;
import com.centaline.trans.common.enums.RansomDiyaEnum;
import com.centaline.trans.common.service.PropertyUtilsService;
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
		
		@Autowired(required=true)
		private RansomService ransomService;
		
		@Autowired
		private UamSessionService uamSessionService;

		@Autowired
		private PropertyUtilsService propertyUtilsService;

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
			request.setAttribute("PROCESS_DEFINITION_ID_RANSOM", propertyUtilsService.getProcessDfId("ransom_process"));
			request.setAttribute("PROCESS_DEFINITION_ID_RANSOM_SUSPEND", propertyUtilsService.getProcessDfId("ransom_suspend"));
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
		 * 赎楼申请,流程图改后分两个节点
		 * @param ransomCode jsp过来启动
		 * @param caseCode taskController过来赎楼编号
		 * @param request
		 * @return
		 */
		@RequestMapping(value="ransomApply")
		public String  ransomApply(@RequestParam(value="ransomCode",required=false)String ransomCode,
									@RequestParam(value="caseCode",required=false)String caseCode,
									String processInstanceId,String taskId,ServletRequest request){
			if(!StringUtils.isEmpty(ransomCode)){
				//公共基本信息
				ToRansomDetailVo detailVo = ransomService.getRansomDetail(ransomCode);
				request.setAttribute("detailVo", detailVo);
				
				//更新赎楼状态为在途并启动流程
				StartProcessInstanceVo pVo = ransomService.updateRansomIsStart(ransomCode,detailVo.getCaseCode());
				
				request.setAttribute("processInstanceId", pVo.getId());
				request.setAttribute("taskId", pVo.getActiveTaskId());
			}else if(!StringUtils.isEmpty(caseCode)){
				/**
				 * task过来:流程启动用的ransomCode，caseCode实际为赎楼编号
				 */
				ToRansomDetailVo detailVo = ransomService.getRansomDetail(caseCode);

				request.setAttribute("detailVo", detailVo);
				
				request.setAttribute("processInstanceId", processInstanceId);
				request.setAttribute("taskId", taskId);
			}
			request.setAttribute("PROCESS_DEFINITION_ID_RANSOM", propertyUtilsService.getProcessDfId("ransom_process"));
			
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
				e.printStackTrace();
				return false;
			}
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

			//面签数据更新
			try{
				ransomService.updateRansomSign(submitVo);
			}catch(Exception e){
				e.printStackTrace();
				return false;
			}
				
			return true;
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
			if(RansomDiyaEnum.PAYLOAN_TWO.getPart().equals(taskitem)){
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
				e.printStackTrace();
				return false;
			}
			return true;
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
			if(RansomDiyaEnum.CANCELDIYA_TWO.getPart().equals(taskitem)){
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
				ransomService.updateRansomCancel(submitVo);
			}catch(Exception e){
				e.printStackTrace();
				return false;
			}
			return true;
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
			if(RansomDiyaEnum.RECEIVE_TWO.getPart().equals(taskitem)){
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
				ransomService.updateRansomPermit(submitVo);
			}catch(Exception e){
				e.printStackTrace();
				return false;
			}
			return true;
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
				ransomService.updateRansomPayment(submitVo);
			}catch(Exception e){
				e.printStackTrace();
				return false;
			}
		
			return true;
		}
		
		/**
		 * 赎楼页面公共信息
		 * @param request
		 * @param caseCode
		 */
		ToRansomDetailVo getTaskBaseInfo(ServletRequest request,String ransomCode){
			//公共基本信息
			ToRansomDetailVo detailVo = ransomService.getRansomDetail(ransomCode);

			request.setAttribute("detailVo", detailVo);
			request.setAttribute("PROCESS_DEFINITION_ID_RANSOM", propertyUtilsService.getProcessDfId("ransom_process"));
			
			return detailVo;
		}
		
		/**
		 * 变更赎楼单的金融权证负责人
		 * @param req
		 * @param userId
		 * @param caseCode
		 * @return
		 */
		@RequestMapping("changeRansomOwner")
		@ResponseBody
		public boolean changeRansomOwner(HttpServletRequest req, String changeToUserId, String caseCode, String ransomCode) {
			//检查是否有对应的赎楼流程(活动、暂停都要查)
			Map<String, String[]> paramMap = ParamterHander.getParameters(req);
	        Map<String, Object> paramObj = new HashMap<String, Object>();
	        ParamterHander.mergeParamter(paramMap, paramObj);
			return ransomService.changeRansomOwner(paramObj, changeToUserId, caseCode, ransomCode);
		}

	}

