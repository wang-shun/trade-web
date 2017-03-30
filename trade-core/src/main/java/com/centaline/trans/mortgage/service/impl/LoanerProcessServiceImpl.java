package com.centaline.trans.mortgage.service.impl;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aist.common.exception.BusinessException;
import com.aist.common.web.validate.AjaxResponse;
import com.aist.message.core.remote.UamMessageService;
import com.aist.uam.auth.remote.UamSessionService;
import com.aist.uam.auth.remote.vo.SessionUser;
import com.aist.uam.template.remote.UamTemplateService;
import com.aist.uam.userorg.remote.UamUserOrgService;
import com.aist.uam.userorg.remote.vo.User;
import com.centaline.trans.cases.service.ToCaseService;
import com.centaline.trans.common.enums.WorkFlowEnum;
import com.centaline.trans.common.enums.WorkFlowStatus;
import com.centaline.trans.common.service.MessageService;
import com.centaline.trans.common.service.TgGuestInfoService;
import com.centaline.trans.common.service.impl.PropertyUtilsServiceImpl;
import com.centaline.trans.engine.bean.ProcessInstance;
import com.centaline.trans.engine.bean.RestVariable;
import com.centaline.trans.engine.entity.ToWorkFlow;
import com.centaline.trans.engine.service.TaskService;
import com.centaline.trans.engine.service.ToWorkFlowService;
import com.centaline.trans.engine.service.WorkFlowManager;
import com.centaline.trans.engine.vo.PageableVo;
import com.centaline.trans.engine.vo.StartProcessInstanceVo;
import com.centaline.trans.engine.vo.TaskVo;
import com.centaline.trans.mgr.service.ToSupDocuService;
import com.centaline.trans.mortgage.entity.ToMortgage;
import com.centaline.trans.mortgage.repository.ToMortgageMapper;
import com.centaline.trans.mortgage.service.LoanerProcessService;
import com.centaline.trans.mortgage.service.ToMortgageService;
import com.centaline.trans.task.service.ToApproveRecordService;
import com.centaline.trans.task.service.UnlocatedTaskService;

@Service
@Transactional
public class LoanerProcessServiceImpl implements LoanerProcessService {

    @Autowired
    private ToMortgageMapper         toMortgageMapper;

    @Autowired
    private ToSupDocuService         toSupDocuService;
    @Autowired
    private TgGuestInfoService       tgGuestInfoService;
    @Autowired(required = true)
    private ToCaseService            toCaseService;
    @Autowired
    private WorkFlowManager          workFlowManager;
    @Autowired
    MessageService                   messageService;
    @Autowired
    private ToWorkFlowService        toWorkFlowService;
    @Autowired
    private UnlocatedTaskService     unlocatedTaskService;
    @Autowired
    private ToApproveRecordService   toApproveRecordService;

    @Autowired(required = true)
    private UamUserOrgService        uamUserOrgService;
    @Autowired
    private PropertyUtilsServiceImpl propertyUtilsService;
    @Autowired(required = true)
    UamSessionService                uamSessionService;
    @Autowired
    private UamTemplateService       uamTemplateService;
    @Qualifier("uamMessageServiceClient")
    
    @Autowired
    private UamMessageService        uamMessageService;
    
	@Autowired
	private ToMortgageService toMortgageService;
	
	@Autowired
	private TaskService taskService;

    
    /*
     * @author:zhuody
     * @date:2017-03-24
     * @des:启动信贷员流程 
     * 
     */
	@Override
	public AjaxResponse<String> startLoanerOrderWorkFlow(String caseCode,String loanerUserId, String loanerOrgId, String bankOrgCode,int bankLevel,String isMainLoanBank) {
				
		AjaxResponse<String> response  = new AjaxResponse<String>();
        /*流程引擎相关*/
        List<RestVariable> variables = new ArrayList<RestVariable>();
        SessionUser user = uamSessionService.getSessionUser();      
		
		try {
			ToWorkFlow toWorkFlow = new ToWorkFlow();
			toWorkFlow.setBusinessKey(WorkFlowEnum.LOANER_PROCESS.getCode());
			toWorkFlow.setCaseCode(caseCode);
			//查询流程表记录
			ToWorkFlow record = toWorkFlowService.queryActiveToWorkFlowByCaseCodeBusKey(toWorkFlow);
			if(null != record){
				throw new BusinessException("启动失败,该流程正在运行！");
			}
			User loaner = uamUserOrgService.getUserById(loanerUserId);								
			
			variables.add(new RestVariable("loanerUserId", loaner.getUsername()));
			variables.add(new RestVariable("bankLevel", bankLevel)); 
			variables.add(new RestVariable("sessionUserId", user.getUsername()));   //派单人	
			//设置流程变量的2中方式	
			RestVariable restBankLevel = new RestVariable();
			restBankLevel.setName("bankLevelApprove");
			restBankLevel.setValue(false);
			variables.add(restBankLevel);   //预设值消息信息
			variables.add(new RestVariable("mainBankChoose", false)); //默认启动流程时，不走作废程序
            
            //启动流程 			
            ProcessInstance process = new ProcessInstance(propertyUtilsService.getProcessLoanerDfKey(), caseCode, variables);
            StartProcessInstanceVo vo = workFlowManager.startCaseWorkFlow(process,loaner.getUsername(), caseCode);    
            
            
    		// 启动流程之后 把交易顾问派单流程直接推送完 
			
			PageableVo pageableVo = taskService.listTasks(vo.getId(), false);//Loaner_Process:4:1030016
    		List<TaskVo> taskList = pageableVo.getData();
    		for (TaskVo task : taskList) {
    			if ("LoanerSendOrder".equals(task.getTaskDefinitionKey())) {//ZY-AJ-201605-1460
    				taskService.complete(task.getId() + "");
    				break;
    			}
    		}
            
            Map<String,Object>  map = new HashMap<String,Object>();
            map.put("caseCode", caseCode);
            map.put("loanerId", loanerUserId);
            ToMortgage toMortgageInfo = toMortgageMapper.findToMortgageByCaseCodeAndLoanerId(map);	
            String bizCode = "";            
            
            //添加贷款表中的  信贷员、派单员等信息
            ToMortgage toMortgage = new ToMortgage();
            toMortgage.setCaseCode(caseCode);
            toMortgage.setDispachUserId(user.getId());
            toMortgage.setDispachTime(new Date());
            toMortgage.setLoanerId(loanerUserId);
            toMortgage.setLoanerProcessInstCode(vo.getId());
            toMortgage.setIsActive("1");
            toMortgage.setIsMainLoanBank(isMainLoanBank);//0 备选银行,1 主选银行
            toMortgage.setFinOrgCode(bankOrgCode);//此处设置银行Code才能保证 先启的银行审批流程
            toMortgage.setBankLevel(String.valueOf(bankLevel));
            if(null != toMortgageInfo){          	
            	toMortgage.setPkid(toMortgageInfo.getPkid());
                toMortgageMapper.update(toMortgage); 
            	bizCode = toMortgageInfo.getPkid() == null ?"":toMortgageInfo.getPkid().toString();
            }else{ 
                toMortgageMapper.insertSelective(toMortgage);
            }
            //插入工作流表
            ToWorkFlow workFlow = new ToWorkFlow();
            workFlow.setBusinessKey(WorkFlowEnum.LOANER_PROCESS.getName());
            workFlow.setCaseCode(caseCode);
            workFlow.setBizCode(bizCode);
            workFlow.setInstCode(vo.getId());
            workFlow.setProcessDefinitionId(propertyUtilsService.getProcessLoanerDfKey());
            workFlow.setProcessOwner(user.getId());
            workFlow.setStatus(WorkFlowStatus.ACTIVE.getCode());
            toWorkFlowService.insertSelective(workFlow);             
            
            
            //派单给BC级别银行的信贷员之后,启动银行分级审批
            if(bankLevel == 1 || bankLevel == 9 ){
            	toMortgageService.startTmpBankWorkFlow(caseCode,vo.getId());
            } 
            
            response.setSuccess(true);
            response.setMessage("恭喜，交易顾问派单成功！");
        } catch (BusinessException e) {
        	response.setSuccess(false);
        	throw new BusinessException("Sorry,交易顾问派单失败！"); 
        }
        return response;
	}

	
	
	    
    /*
     * @author:zhuody
     * @date:2017-03-28
     * @des:信贷员是否接单确认
     * 
     */
    @Override
    public boolean isLoanerAcceptCase(boolean isLonaerAcceptCase,String taskId, String processInstanceId, String caseCode) {
    	
    	boolean  loanerAccetpFlag = false;
		if((null == caseCode ||"".equals(caseCode)) || (null == taskId ||"".equals(taskId)) || (null == processInstanceId ||"".equals(processInstanceId)) ){
			throw new BusinessException("信贷员接单确认请求参数异常！");			
		}	    	
    	    
    	SessionUser user = uamSessionService.getSessionUser();   
        List<RestVariable> variables = new ArrayList<RestVariable>();        
        
        try{
         	//信贷员接单
        	if(isLonaerAcceptCase == true){    			
    			variables.add(new RestVariable("loanerAccept", true));    			
    		
	    		ToMortgage toMortgage = toMortgageMapper.findToMortgageByCaseCodeAndDisTime(caseCode);
	    		if(null != toMortgage){
	    			ToMortgage toMortgageForUpdate = new ToMortgage();
	    			toMortgageForUpdate.setPkid(toMortgage.getPkid());
	    			toMortgageForUpdate.setLoanerAcceptTime(new Date());
	    			toMortgageForUpdate.setLoanerId(user.getId());
	    			toMortgageMapper.update(toMortgageForUpdate);
	    		}
        	}else{    			
    			variables.add(new RestVariable("loanerAccept", false));
        	}
        	//提交流程
            workFlowManager.submitTask(variables, taskId, processInstanceId, null, caseCode);
            loanerAccetpFlag = true;
            
        }catch(BusinessException e) {        	
        	throw new BusinessException("信贷员接单流程推进失败！");
        } 
        return loanerAccetpFlag;        
    }
   
    
    /*
     * @author:zhuody
     * @date:2017-03-28
     * @des:信贷员是否接单   银行审批
     * 
     */
    @Override
    public boolean isBankAcceptCase(boolean isBankAcceptCase,String taskId, String processInstanceId,String caseCode) {
    	
    	boolean  bankAccetpFlag = false;
    	
		if((null == caseCode ||"".equals(caseCode)) || (null == taskId ||"".equals(taskId)) || (null == processInstanceId ||"".equals(processInstanceId)) ){
			throw new BusinessException("信贷员接单银行确认请求参数异常！");			
		}	
    	
		SessionUser user = uamSessionService.getSessionUser();   	
        List<RestVariable> variables = new ArrayList<RestVariable>();
        try{
	        //信贷员接单
	    	if(isBankAcceptCase == true){		
				variables.add(new RestVariable("bankBusinessApprove", true));				
				//审批通过 更改流程状态
	            //更新流程状态为'4'：已完成
	            ToWorkFlow workFlow = new ToWorkFlow();
	            workFlow.setBusinessKey(WorkFlowEnum.LOANER_PROCESS.getCode());
	            workFlow.setCaseCode(caseCode);
	            ToWorkFlow record = toWorkFlowService.queryActiveToWorkFlowByCaseCodeBusKey(workFlow);
	            if (record != null) {
	                record.setStatus(WorkFlowStatus.COMPLETE.getCode());
	                toWorkFlowService.updateByPrimaryKeySelective(record);
	            }           

	            
	    		ToMortgage toMortgage = toMortgageMapper.findToMortgageByCaseCodeAndDisTime(caseCode);
	    		if(null != toMortgage){
	    			ToMortgage toMortgageForUpdate = new ToMortgage();
	    			toMortgageForUpdate.setPkid(toMortgage.getPkid());
	    			toMortgageForUpdate.setBankApproveTime(new Date());	
	    			toMortgageForUpdate.setBankApproveUserId(user.getId());
	    			//临时银行 1 审批通过，现在银行信贷员审核通过为 3
	    			toMortgageForUpdate.setTmpBankStatus("3");
	    			toMortgageMapper.update(toMortgageForUpdate);
	    		}
	
	    	}else{			
				variables.add(new RestVariable("bankBusinessApprove", false));			
	    	}
	    	//提交流程
	        workFlowManager.submitTask(variables, taskId, processInstanceId, null, caseCode);
	        bankAccetpFlag = true;
	     }catch(BusinessException e) {
	      	
	      	throw new BusinessException("信贷员接单流程推进失败！");
	     } 
        
         return bankAccetpFlag;      
    }


    
    /*
     * @author:zhuody
     * @date:2017-03-28
     * @des:交易顾问派单 流程关闭
     * 
     */
	@Override
	public AjaxResponse<String> loanerProcessDelete(String caseCode,String taskId, String processInstanceId) {
		
		AjaxResponse<String>  response = new AjaxResponse<String>();
		List<RestVariable> variables = new ArrayList<RestVariable>();
		if((null == caseCode ||"".equals(caseCode)) || (null == taskId ||"".equals(taskId)) || (null == processInstanceId ||"".equals(processInstanceId)) ){
			throw new BusinessException("结束交易顾问派单流程请求参数异常！");			
		}	
		
		try{
			//结束流程
			variables.add(new RestVariable("mainBankChoose", true));
	    	//提交流程
	        workFlowManager.submitTask(variables, taskId, processInstanceId, null, caseCode);
	        
	        //更新流程表的状态
	        ToWorkFlow workFlow = new ToWorkFlow();
	        workFlow.setBusinessKey(WorkFlowEnum.LOANER_PROCESS.getCode());//Loaner_Process:1:1012544
	        workFlow.setCaseCode(caseCode);
	        ToWorkFlow record = toWorkFlowService.queryActiveToWorkFlowByCaseCodeBusKey(workFlow);
	        if (record != null) {
	            record.setStatus(WorkFlowStatus.COMPLETE.getCode());
	            toWorkFlowService.updateByPrimaryKeySelective(record);
	        }
	        
	        response.setSuccess(true);
	        response.setMessage("交易顾问派单流程成功结束！");
		}catch(BusinessException e) {
	      	response.setSuccess(false);
	      	throw new BusinessException("交易顾问派单流程结束异常！");
	     } 
		
		return response;
	}


    /*
     * @author:zhuody
     * @date:2017-03-28
     * @des:交易顾问派单信息提交
     * 
     */
	@Override
	public AjaxResponse<String> loanerProcessSubmit(ToMortgage toMortgage,String caseCode, String taskId, String processInstanceId,int bankLevel) {
		
		
		if((null == caseCode ||"".equals(caseCode)) || (null == taskId ||"".equals(taskId)) || (null == processInstanceId ||"".equals(processInstanceId)) ){
			throw new BusinessException("交易顾问派单流程请求参数异常！");			
		}
		
		AjaxResponse<String>  response = new AjaxResponse<String>();
        List<RestVariable> variables = new ArrayList<RestVariable>();
		try{
			
			variables.add(new RestVariable("bankLevel", bankLevel)); 
			variables.add(new RestVariable("mainBankChoose", false)); 
			
        	//提交流程
            workFlowManager.submitTask(variables, taskId, processInstanceId, null, caseCode);	
			
            //派单给BC级别银行的信贷员之后,启动银行分级审批
            if(bankLevel == 1 || bankLevel == 9 ){
            	toMortgageService.startTmpBankWorkFlow(caseCode,processInstanceId);
            }
			
			//更新 信贷员信息、贷款机构信息		
			if(null != toMortgage){
				toMortgageService.updateToMortgage(toMortgage);//toMortgage 主键pkid作为条件更新
			}
            
	        response.setSuccess(true);
	        response.setMessage("交易顾问派单信息提交成功！");
		}catch(BusinessException e) {
	      	response.setSuccess(false);
	      	throw new BusinessException("交易顾问派单流程结束异常！");
	     } 		
		return response;
	}



    /*
     * @author:zhuody
     * @date:2017-03-29
     * @des:交易顾问派单流程是否已起判断
     * 
     */
	@Override
	public AjaxResponse<String> isLoanerProcessStart(String caseCode) {
		
		AjaxResponse<String>  response = new AjaxResponse<String>();
		try {
			ToWorkFlow toWorkFlow = new ToWorkFlow();
			toWorkFlow.setBusinessKey(WorkFlowEnum.LOANER_PROCESS.getCode());
			toWorkFlow.setCaseCode(caseCode);
			//查询流程表记录
			ToWorkFlow record = toWorkFlowService.queryActiveToWorkFlowByCaseCodeBusKey(toWorkFlow);
			if(null == record){
		        response.setSuccess(true);
		        response.setMessage("交易顾问派单流程没有启动！");
			}else{
		        response.setSuccess(false);
		        response.setMessage("交易顾问派单流程已经启动！");
			}

		}catch(BusinessException e) {
	      	response.setSuccess(false);
	      	throw new BusinessException("查询交易顾问派单流程是否启动异常！");
	     } 		
		return response;
	}
}
