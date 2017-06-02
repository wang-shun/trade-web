package com.centaline.trans.taskList.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aist.common.exception.BusinessException;
import com.aist.common.web.validate.AjaxResponse;
import com.aist.message.core.remote.UamMessageService;
import com.aist.uam.auth.remote.UamSessionService;
import com.aist.uam.auth.remote.vo.SessionUser;
import com.aist.uam.basedata.remote.UamBasedataService;
import com.aist.uam.basedata.remote.vo.Dict;
import com.aist.uam.userorg.remote.UamUserOrgService;
import com.aist.uam.userorg.remote.vo.User;
import com.centaline.trans.cases.entity.ToCase;
import com.centaline.trans.cases.entity.VCaseTradeInfo;
import com.centaline.trans.cases.service.ToCaseInfoService;
import com.centaline.trans.cases.service.ToCaseService;
import com.centaline.trans.cases.service.VCaseTradeInfoService;
import com.centaline.trans.cases.vo.CaseBaseVO;
import com.centaline.trans.cases.vo.CaseDetailShowVO;
import com.centaline.trans.common.entity.TgServItemAndProcessor;
import com.centaline.trans.common.enums.ToAttachmentEnum;
import com.centaline.trans.common.service.TgServItemAndProcessorService;
import com.centaline.trans.engine.bean.RestVariable;
import com.centaline.trans.engine.bean.TaskHistoricQuery;
import com.centaline.trans.engine.entity.ToWorkFlow;
import com.centaline.trans.engine.service.TaskService;
import com.centaline.trans.engine.service.ToWorkFlowService;
import com.centaline.trans.engine.service.WorkFlowManager;
import com.centaline.trans.engine.vo.PageableVo;
import com.centaline.trans.engine.vo.TaskVo;
import com.centaline.trans.mortgage.entity.ToMortgage;
import com.centaline.trans.mortgage.service.ToMortgageService;
import com.centaline.trans.satisfaction.service.SatisfactionService;
import com.centaline.trans.task.service.ToHouseTransferService;
import com.centaline.trans.task.vo.LoanlostApproveVO;
import com.centaline.trans.task.vo.ProcessInstanceVO;
import com.centaline.trans.utils.UiImproveUtil;

@Controller
@RequestMapping(value="/task/guohuApprove")
public class GuohuApproveController {

	@Autowired(required = true)
	private ToCaseService toCaseService;
	@Autowired
	private ToCaseInfoService toCaseInfoService;

	@Autowired
	private WorkFlowManager workFlowManager;
	
	/*发送消息*/
	@Autowired(required=true)
	@Qualifier("uamMessageServiceClient")
	private UamMessageService uamMessageService;
	@Autowired(required = true)
	private UamSessionService uamSessionService;/*用户信息*/
	@Autowired(required = true)
	private VCaseTradeInfoService vCaseTradeInfoService;
	@Autowired
	private ToMortgageService toMortgageService;
	@Autowired
	private ToHouseTransferService toHouseTransferService;
	@Autowired
	private UamBasedataService uamBasedataService;
	@Autowired
	private UamUserOrgService uamUserOrgService;
	@Autowired(required = true)
	ToWorkFlowService toWorkFlowService;
	@Autowired(required = true)
	TgServItemAndProcessorService tgServItemAndProcessorService;
	@Autowired
    TaskService taskService;
	@Autowired
	SatisfactionService satisfactionService;
	
	@RequestMapping("process")
	public String doProcesss(HttpServletRequest request,
			HttpServletResponse response,String caseCode,String source,String processInstanceId){
		
		SessionUser user=uamSessionService.getSessionUser();
		request.setAttribute("approveType", "2");
		request.setAttribute("operator", user != null ? user.getId():"");
		request.setAttribute("source", source);
		CaseBaseVO caseBaseVO = toCaseService.getCaseBaseVO(caseCode);
		request.setAttribute("caseBaseVO", caseBaseVO);
		
		//交易信息
		VCaseTradeInfo caseInfo = vCaseTradeInfoService.queryCaseTradeInfoByCaseCode(caseCode);
		
		//贷款信息
		ToMortgage toMortgage = toMortgageService.findToMortgageByCaseCode(caseCode);
		CaseDetailShowVO reVo  = toCaseInfoService.getCaseDetailShowVO(caseCode,toMortgage);
		request.setAttribute("toMortgage", toMortgage);
		request.setAttribute("caseDetailVO", reVo);
		request.setAttribute("houseTransfer", toHouseTransferService.findToGuoHuByCaseCode(caseCode));
		request.setAttribute("caseInfo", caseInfo);
		Dict dict = uamBasedataService.findDictByType("guohu_not_approve");
		if(dict!=null){
			request.setAttribute("notApproves", dict.getChildren());
		}
		
		ToCase te=toCaseService.findToCaseByCaseCode(caseCode);
		String orgId = te.getOrgId();
		List<User> users = new ArrayList<User>();
		User cpUser = uamUserOrgService.getUserById(caseBaseVO.getAgentManagerInfo().getCpId());
		users.add(cpUser);

		User guohuUser = null;
		TaskHistoricQuery query =new TaskHistoricQuery();
		query.setFinished(true);
		query.setTaskDefinitionKey(ToAttachmentEnum.GUOHU.getCode());
		query.setProcessInstanceId(processInstanceId);
		PageableVo pageableVo=workFlowManager.listHistTasks(query);
		if(pageableVo.getData() != null && !pageableVo.getData().isEmpty()){
			guohuUser = uamUserOrgService.getHisUserByUsername(((TaskVo)pageableVo.getData().get(0)).getAssignee());
		}
		
		if(guohuUser == null){
			throw new BusinessException("没有找到过户环节处理人！");
		}
		
		users.add(guohuUser);
		
		ToWorkFlow workF = toWorkFlowService.queryWorkFlowByInstCode(processInstanceId);
		if(workF!=null &&"operation_process:40:645454".compareTo(workF.getProcessDefinitionId())<=0){		
	    	request.setAttribute("users", users);
		}

		
		return "task" + UiImproveUtil.getPageType(request) + "/taskGuohuApprove";
	}
	
	@RequestMapping("showPic")
	public String showPic(HttpServletRequest request, 
			HttpServletResponse response, String picUrl){
		request.setAttribute("picUrl", picUrl);
		return "task/showPicture";
	}
	
	
	

	@RequestMapping(value="guohuApprove")
	@ResponseBody
	public Boolean guohuApprove(HttpServletRequest request, ProcessInstanceVO processInstanceVO, LoanlostApproveVO loanlostApproveVO,
			String GuohuApprove, String GuohuApprove_response,String notApprove,String members) {
		
		return toHouseTransferService.guohuApprove(request, processInstanceVO, loanlostApproveVO, GuohuApprove, GuohuApprove_response, notApprove, members);
	}
	
	/**
	 * 过户信息修改
	 * @param caseId
	 * @param request
	 * @return
	 */
	@RequestMapping("guohuInfoModify")
	public String guohuInfoModify(String caseCode,HttpServletRequest request){
		// 基本信息
		ToCase toCase = toCaseService.findToCaseByCaseCode(caseCode);
		// 工作流
		ToWorkFlow inWorkFlow = new ToWorkFlow();
		inWorkFlow.setBusinessKey("operation_process");
		inWorkFlow.setCaseCode(toCase.getCaseCode());
		ToWorkFlow toWorkFlow = toWorkFlowService.queryActiveToWorkFlowByCaseCodeBusKey(inWorkFlow);
		// 已完成任务
		List<TaskVo> tasks = new ArrayList<TaskVo>();
		if (toWorkFlow != null) {
			List<String> insCodeList = toWorkFlowService.queryAllInstCodesByCaseCode(toCase.getCaseCode());
			for(String insCode : insCodeList) {
				TaskHistoricQuery tq = new TaskHistoricQuery();
				tq.setProcessInstanceId(insCode);
				tq.setFinished(true);
				
				List<TaskVo> taskList1 = taskDuplicateRemoval(workFlowManager.listHistTasks(tq).getData());
				tasks.addAll(taskList1);
			}
			// 本人做的任务
			List<TgServItemAndProcessor>myServiceCase= tgServItemAndProcessorService.findTgServItemAndProcessorByCaseCode(toCase.getCaseCode());
			request.setAttribute("toWorkFlow", toWorkFlow);
			request.setAttribute("myTasks",filterMyTask(myServiceCase,tasks)) ;	   
		    }
		 request.setAttribute("toCase", toCase);
		 
		 ToMortgage mortage = toMortgageService.findToMortgageByCaseCode2(caseCode);
		 request.setAttribute("mortage", mortage);
		 
			String loanReqType="FullPay";
			if (mortage != null) {
				if("1".equals(mortage.getIsDelegateYucui())){
					if("30016003".equals(mortage.getMortType())){
						loanReqType="PSFLoan";
					}else{
						loanReqType="ComLoan";
					}
				}else{
					loanReqType="SelfLoan";
				}
				}
			request.setAttribute("loanReqType", loanReqType);
		 
		 ToCase c = toCaseService.findToCaseByCaseCode(caseCode);
		 if(c != null) {
				CaseBaseVO caseBaseVO = toCaseService.getCaseBaseVO(c.getPkid());
				request.setAttribute("caseBaseVO", caseBaseVO);
			}

		 return "task/taskGuohuInfoModify";
	}
	
	/**
	 * 过户信息修改提交
	 * @param caseId
	 * @param request
	 * @return
	 */
	@RequestMapping("guohuInfoModifySubmit")
	@ResponseBody
	public Boolean guohuInfoModifySubmit(HttpServletRequest request,String taskId, String processInstanceId, String caseCode){
		// 基本信息
		ToCase toCase = toCaseService.findToCaseByCaseCode(caseCode);
		List<RestVariable> variables = new ArrayList<RestVariable>();
		return workFlowManager.submitTask(variables, taskId, processInstanceId, 
				toCase.getLeadingProcessId(), caseCode);
	}
	
		private List<TaskVo>filterMyTask(List<TgServItemAndProcessor>mySerivceItems,List<TaskVo>tasks){
			if(tasks==null||mySerivceItems==null||tasks.isEmpty()||mySerivceItems.isEmpty()){return tasks;}
			Set<String>taskDfKeys=new HashSet<>();
			mySerivceItems.parallelStream().forEach(item->{
				Dict d =uamBasedataService.findDictByType(item.getSrvCode());
				if(d!=null&&d.getChildren()!=null){
					d.getChildren().parallelStream().forEach(sc->{
						if(!taskDfKeys.contains(sc.getCode())){
							taskDfKeys.add(sc.getCode());
						}
					});
				}
			});
			Iterator<TaskVo> it=tasks.iterator();
			while (it.hasNext()) {
				TaskVo task=it.next();
				if(!taskDfKeys.contains(task.getTaskDefinitionKey())){
					it.remove();
				}
			}
			return tasks;
		}
		
		private List<TaskVo> taskDuplicateRemoval(List<TaskVo> oList) {
			Map<String, TaskVo> hashMap = new HashMap<>();
			/*
			 * hashMap=oList.stream().collect(Collectors.toMap(TaskVo::
			 * getTaskDefinitionKey, (p) -> p));
			 */
			for (TaskVo taskVo : oList) {
				hashMap.put(taskVo.getTaskDefinitionKey(), taskVo);
			}
			List<TaskVo> result = new ArrayList<>(hashMap.values());
			return result;
		}
	/**
	  * @throws Exception 
	  * @Title:  
	  * @author: hejf 
	  * @return caseCode
	  * @throws
	  */
	 @RequestMapping("getAttType")
	 @ResponseBody
	 public AjaxResponse<String> cashFlowOutApprDeleteCashFlowAll(HttpServletRequest request,String caseCode) throws Exception {
		 AjaxResponse<String> response = new AjaxResponse<>();
		 try{
			 String type = toCaseService.selectAtt(caseCode);
			 response.setSuccess(true);
			 response.setCode(type);
		 }catch(Exception e){
			 response.setMessage(e.getMessage());
			 response.setSuccess(false);
			 response.setCode("N");
			 throw e;
		 }
		 return response;
	 }
	
}
